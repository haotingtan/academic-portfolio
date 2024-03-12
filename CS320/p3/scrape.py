from collections import deque
import pandas as pd
import requests
import time
import os
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium import webdriver


class GraphSearcher:
    def __init__(self):
        self.visited = set()
        self.order = []

    def visit_and_get_children(self, node):
        """ Record the node value in self.order, and return its children
        param: node
        return: children of the given node
        """
        raise Exception("must be overridden in sub classes -- don't change me here!")

    def dfs_search(self, node):
        # 1. clear out visited set and order list
        self.visited = set()
        self.order = []
        # 2. start recursive search by calling dfs_visit
        self.dfs_visit(node)

    def dfs_visit(self, node):
        # 1. if this node has already been visited, just `return` (no value necessary)
        if node in self.visited:
            return
        # 2. mark node as visited by adding it to the set
        self.visited.add(node)
        # 3. call self.visit_and_get_children(node) to get the children
        children = self.visit_and_get_children(node)
        # 4. in a loop, call dfs_visit on each of the children
        for child in children:
            self.dfs_visit(child)
        
    def bfs_search(self, node):
        self.visited = set()
        self.order = []
        to_visit = deque([node])
        
        while len(to_visit) > 0:
            curr_node = to_visit.popleft()
            
            if curr_node in self.visited:
                continue
            self.visited.add(curr_node)
            children = self.visit_and_get_children(curr_node)
            for child in children:
                if child not in self.visited:
                    to_visit.append(child)
        
        
class MatrixSearcher(GraphSearcher):
    def __init__(self, df):
        super().__init__() # call constructor method of parent class
        self.df = df

    def visit_and_get_children(self, node):
        # TODO: Record the node value in self.order
        self.order.append(node)
        
        children = []
        # TODO: use `self.df` to determine what children the node has and append them
        for node, has_edge in self.df.loc[node].items():
            if has_edge:
                children.append(node)
        return children
    
    
class FileSearcher(GraphSearcher):
    def __init__(self):
        super().__init__()
        self.folder_name = "file_nodes"
    
    def visit_and_get_children(self, node):
        with open(os.path.join(self.folder_name, node), "r") as f:
            self.order.append(f.readline().strip('\n'))
            children = f.readline().strip('\n').split(',')
        return children
                
    def concat_order(self):
        order_string = ""
        for value in self.order:
            order_string += value
        return order_string
    

class WebSearcher(GraphSearcher):
    def __init__(self, driver):
        super().__init__()
        self.driver = driver
        self.tables = []
    
    def visit_and_get_children(self, node):
        self.order.append(node) 
        
        self.driver.get(node)
        hyperlinks = self.driver.find_elements("tag name", "a")
        self.tables.append(pd.read_html(node)[0])
        children = []
        for i in hyperlinks:
            children.append(i.get_attribute("href"))
        return children
    
    def table(self):
        return pd.concat(self.tables, ignore_index=True)
        
        
def reveal_secrets(driver, url, travellog):
    password = ""
    for num in travellog["clue"]:
        password += str(num)
        
    driver.get(url)
    inp = driver.find_element("id", "password-textbox")
    button_go = driver.find_element("id", "submit-button")
    
    inp.clear()
    inp.send_keys(int(password))
    button_go.click()
    time.sleep(1)

    button_view = driver.find_element("id", "view-location-button")
    button_view.click()
    time.sleep(2)

    img_url = driver.find_element("id", "image").get_attribute("src")
    response = requests.get(img_url)

    with open("Current_Location.jpg", "wb") as f:
        f.write(response.content)
        
    return driver.find_element("id", "location").text
