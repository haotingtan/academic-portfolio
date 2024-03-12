# Project 3: Find the Path!

## Overview

In this project you will practice inheritance, graph search, and web
scraping. You'll hand-in a module called `scrape.py`.

Make sure to run the tests before handing in.  During development, we
recommend having a debug.ipynb notebook to make calls to your module.

## Testing

Run `python3 tester.py` often and work on fixing any issues.


## Part 1: Base Class `GraphSearcher` and DFS on Matrices (`MatrixSearcher`)

see scrape.py

## Part 2: BFS on Matrix (`MatrixSearcher`) and Files (`FileSearcher` )

Add a `bfs_search` method to the base searcher `GraphSearcher`.  It should behave the same as
`dfs_search`, but use the BFS algorithm instead of DFS.  The
difference will be evident at the end if someone looks at the `.order`
attribute.

### `FileSearcher` Class

Add another class, `FileSearcher`, which also inherits from
`GraphSearcher`.  You job is to implement the three methods `__init__`, `visit_and_get_children`, and `concat_order` and inherit other methods.
1. `__init__`: the constructor of `FileSearcher` which does not take additional parameter besides the instance itself
2. `visit_and_get_children`: visit the file to record its value and get its children
3. `concat_order`: concatenate the values in `self.order` to a string

The nodes of this graph are files in the `file_nodes` directory.  For
example, `1.txt` contains this:

```
M
2.txt,4.txt
```


```python
import scrape
f = scrape.FileSearcher()
print(f.visit_and_get_children("1.txt"), f.order, f.concat_order())
```

## Part 3: Web Crawling (`WebSearcher`)

### Launching the Website

You'll be scraping a website implemented as a web application built
using the Flask framework (you don't need to know flask for this
project, though you'll learn it soon and get a chance to build your
own website in the next project).  In an SSH session, run the
following to launch it:

```
python3 application.py
```

Then, open `http://<YOUR-VM-IP>:5000` in your web browser. **Do not**
use the IP address that is printed to console in the ssh session (it
won't work).  It should look like this:

<img src="webpage.png" width=600>

If you click "TRAVEL HISTORY", you'll enter a graph of pages, each
with a table fragment.  Your job is to search the graph (using the
search methods you wrote earlier), collect all the table fragments,
and concatenate them into one big DataFrame.

### `WebSearcher` Class

Write a `WebSearcher` class that inherits from `GraphSearcher`.  The
constructor should take a Chrome webdriver object as a parameter so
that it is possible to create `WebSearcher` object with `ws =
scrape.WebSearcher(some_driver)`.

The `visit_and_get_children` method of `WebSearcher` should treat the node as a URL.  It
should use the webdriver to visit that page and return the URLs of
other pages to which the visited page has hyperlinks.  See `web_test`
in the tester for examples of how it should behave.

## Part 4: `reveal_secrets` function

Write a function (remember that functions aren't inside any class) in
`scrape.py` like the following:

```python
def reveal_secrets(driver, url, travellog):
    ....
```

The function should do the following:

1. generate a password from the "clue" column of the `travellog` DataFrame.  For example, if `travellog` is the big DataFrame built after doing BFS (as shown earlier), the password will start with "17138..."
2. visit `url` with the `driver`
3. automate typing the password in the box and clicking "GO"
4. wait until the pages is loaded (perhaps with `time.sleep`)
5. click the "View Location" button and wait until the result finishes loading
6. save the image that appears to a file named 'Current_Location.jpg' (use the `requests` module to do the download, once you get the URL from selenium)
7. return the current location that appears on the page (should be "BASCOM HALL")
