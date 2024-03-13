#!/bin/bash -e

make_rcheck() {
cat << 'EOF'
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

char usage[] =
    "Record Checker (binary files may differ but are still sorted correctly)\n\n\
usage: rcheck file1 file2\n\n\
positional arguments:\n\
  file1\t\trecord file\n\
  file2\t\tsorted record file\n";

typedef struct {
  int key;
  char *pointer;
} key_pointer;

void swap(key_pointer *a, key_pointer *b) {
  key_pointer t = *a;
  *a = *b;
  *b = t;
}

key_pointer *part(key_pointer *lower, key_pointer *upper) {
  key_pointer *l = lower;
  for (key_pointer *c = lower; c < upper; c++)
    if (c->key < upper->key)
      swap(c, l++);
  swap(l, upper);
  return l;
}

void qs(key_pointer *lower, key_pointer *upper) {
  if (lower < upper) {
    key_pointer *pivot = part(lower, upper);
    qs(lower, pivot - 1);
    qs(pivot + 1, upper);
  }
}

int main(int argc, char **argv) {
  if (argc < 3) {
    fprintf(stderr, "%s", usage);
    exit(EXIT_FAILURE);
  }

  struct {
    int fd;
    char *map;
    char *fn;
  } rec, sort;
  rec.fn = argv[1];
  sort.fn = argv[2];

  if ((rec.fd = open(rec.fn, O_RDONLY)) == -1 ||
      (sort.fd = open(sort.fn, O_RDONLY)) == -1) {
    perror("Error opening file");
    exit(EXIT_FAILURE);
  }

  struct stat st;
  stat(rec.fn, &st);
  unsigned int recsize = st.st_size;
  stat(sort.fn, &st);
  unsigned int sortsize = st.st_size;

  if (sortsize != recsize) {
    fprintf(stderr, "ERROR record file and sorted file don't have the same "
                    "number of records\n");
    exit(EXIT_FAILURE);
  }

  if ((rec.map = mmap(0, recsize, PROT_READ, MAP_SHARED, rec.fd, 0)) ==
          MAP_FAILED ||
      (sort.map = mmap(0, sortsize, PROT_READ, MAP_SHARED, sort.fd, 0)) ==
          MAP_FAILED)
    exit(EXIT_FAILURE);

  unsigned int size = sortsize = recsize;
  size = size / 100;
  key_pointer *key_pointer_map =
      (key_pointer *)malloc(size * sizeof(key_pointer));
  key_pointer *c = key_pointer_map;

  for (char *r = rec.map; r < rec.map + size * 100; r += 100) {
    c->key = *(int *)r;
    c->pointer = r;
    c++;
  }

  qs(key_pointer_map, key_pointer_map + size - 1);

  int ret = 0;
  int limit = 100;
  int count = 0;
  for (unsigned int c = 0; c < size * 100 && limit; c += 100)
    if (key_pointer_map[c / 100].key != *(int *)&sort.map[c]) {
      if (count++ < limit) {
	    printf("[%08u]   %d !=  %d\n",
             c / 100, key_pointer_map[c / 100].key, *(int *)&sort.map[c]);
      }
      ret = EXIT_FAILURE;
    }

  if (count) printf("%d/%d lines of difference displayed\n", limit > count ? count : limit, count);
  free(key_pointer_map);

  if (munmap(rec.map, size) == -1 || munmap(sort.map, size) == -1)
    exit(EXIT_FAILURE);

  close(sort.fd);
  close(rec.fd);

  return ret;
}
EOF
}

LOG=$PWD/p6_tests.log

function shutdown() {
ret=$?
if [ $ret -ne 0 ]
	then
		echo "Exited with $ret, check $LOG"
	fi
	tput cnorm
	[ -z $try ] || rm -rf /nobackup/$try
	[ -z $pid ] || kill $pid 2> /dev/null
}
trap shutdown EXIT

oops() {
	echo "ERROR: $*" | tee -a $LOG
	exit 1
}

# Source: https://unix.stackexchange.com/a/565551
function spinner() {
	# make room for title and 4 tail lines
	printf '\n\n\n\n\n'
	tput cuu 5
	
	local LC_CTYPE=C
	pid=$1
	shift
	local spin='⣾⣽⣻⢿⡿⣟⣯⣷'
	local charwidth=3
	
	local i=0
	tput civis
	printf '%s ' "${*}"
	tput sc
	while kill -0 $pid 2>/dev/null; do
		local i=$(((i + $charwidth) % ${#spin}))
		printf '\033[36m%s\n\033[3;32m%s\n\033[0m' "${spin:$i:$charwidth}" "$(tail -n 4 $LOG)"
		tput ed
		sleep .2
		tput rc
	done
	printf '\r'
	tput ed
	tput cnorm
	wait $pid
	ret=$?
	unset pid
	return $ret
}

date > $LOG

file=$PWD/psort.c

[ ! -e $PWD/Makefile ] && [ ! -e $PWD/makefile ] && oops couldn\'t find $file
make &>> $LOG &
spinner $! running make

try=$RANDOM
while [ -e /nobackup/$try ]
do
	try=$RANDOM
done

mkdir /nobackup/$try
cp psort /nobackup/$try

cd /nobackup/$try

make_rcheck | gcc -Wall -Werror -x c -o rcheck - &>> $LOG &
spinner $! compiling test file

sizes=(0 10 100 1000 4000)

for test in {1..4}
do
	dd if=/dev/urandom iflag=fullblock of=test$test.img bs=1000000 count=${sizes[$test]} &>> $LOG &
	spinner $! generating test $test

	echo "Test $test" >> $LOG
	in=test$test.img
	for threads in {2..16..2}
	do
		./psort $in out $threads &>> $LOG &
		spinner $! TEST $test $threads threads
		./rcheck $in out &>> $LOG &
		spinner $! TEST $test checking...
	done
done

echo 'Success!! All tests passed!'
