// Physical memory allocator, intended to allocate
// memory for user processes, kernel stacks, page table pages,
// and pipe buffers. Allocates 4096-byte pages.

#include "types.h"
#include "defs.h"
#include "param.h"
#include "mmu.h"
#include "spinlock.h"

struct run {
  struct run *next;
};

struct {
  struct spinlock lock;
  struct run *freelist;

  /*
  P5 changes
  */
  uint free_pages; //track free pages
  uint ref_cnt[PHYSTOP / PGSIZE]; //track reference count

} kmem;

extern char end[]; // first address after kernel loaded from ELF file

// Initialize free list of physical pages.
void
kinit(void)
{
  char *p;

  initlock(&kmem.lock, "kmem");
  p = (char*)PGROUNDUP((uint)end);
  kmem.free_pages = 0;
  for(; p + PGSIZE <= (char*)PHYSTOP; p += PGSIZE)
    kfree(p);
}

// Free the page of physical memory pointed at by v,
// which normally should have been returned by a
// call to kalloc().  (The exception is when
// initializing the allocator; see kinit above.)
void
kfree(char *v)
{
  struct run *r;

  if((uint)v % PGSIZE || v < end || (uint)v >= PHYSTOP) 
    panic("kfree");
  

  acquire(&kmem.lock);
  r = (struct run*)v;
  if (kmem.ref_cnt[(uint)v / PGSIZE] > 0) {
    kmem.ref_cnt[(uint)v / PGSIZE] = kmem.ref_cnt[(uint)v / PGSIZE] - 1;
  }
  if (kmem.ref_cnt[(uint)v / PGSIZE] == 0) {   
    memset(v, 1, PGSIZE); 
    r->next = kmem.freelist;
    kmem.freelist = r;
    kmem.free_pages++;
  }
  release(&kmem.lock);
}

// Allocate one 4096-byte page of physical memory.
// Returns a pointer that the kernel can use.
// Returns 0 if the memory cannot be allocated.
char*
kalloc(void)
{
  struct run *r;

  acquire(&kmem.lock);
  r = kmem.freelist;
  if(r) {
    kmem.freelist = r->next;
    kmem.free_pages--;
    kmem.ref_cnt[(uint)((char*)r) / PGSIZE] = 1;
  }
  release(&kmem.lock);
  return (char*)r;
}

int
kfreepagescount(void)
{
  int count = 0;
  acquire(&kmem.lock);
  count = kmem.free_pages;
  release(&kmem.lock);
  return count;
}

int
krefcount(uint pa)
{
  int ref_count = 0;
  acquire(&kmem.lock);
  ref_count = kmem.ref_cnt[pa / PGSIZE];
  release(&kmem.lock);
  return ref_count;
}

void 
inc_count(uint pa) {
    acquire(&kmem.lock);
    kmem.ref_cnt[pa / PGSIZE] = kmem.ref_cnt[pa / PGSIZE] + 1;
    release(&kmem.lock);
}

void 
dec_count(uint pa) {
    acquire(&kmem.lock);
    kmem.ref_cnt[pa / PGSIZE] = kmem.ref_cnt[pa / PGSIZE] - 1;
    release(&kmem.lock);
}
