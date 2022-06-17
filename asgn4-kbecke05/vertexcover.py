import itertools
import sys
import random
import copy

# Uses 3 different algorithms (two approximations and brute force) to return vertex covers of a given graph

class Edge:
    def __init__(self, u, v):
        self.edge1 = u
        self.edge2 = v
        self.visited = False

class Vertex:
    def __init__(self, num):
        self.adj_list = []
        self.num = num
        self.visited = False

class Graph:
    def __init__(self):
        self.vertices = {}
        #list of edge objects
        self.edges = []

    def addEdge(self, u,v):
        self.vertices[u].adj_list.append(v)

def main():
    graph = Graph()

    filename = sys.argv[1]
    read_in(filename, graph)

    print("log-Approximation: " + ' '.join(logn_approx(graph)))
    print("2-Approximation: " + ' '.join(two_approx(graph)))
    print("Exact Solution: " + ' '.join(map(str,brute_force(graph))))


def print_graph(graph):
    print ("Adjancency Lists")
    for item in graph.vertices:
        print (item + " " + str(graph.vertices.get(item).adj_list))
    print("Edge list")
    for i in range(len(graph.edges)):
        print(graph.edges[i].edge1 + " " + graph.edges[i].edge2)

def read_in(filename, graph):
    f = open(filename, "r")
    for line in f:
        arr = line.strip().split(" ")
        #add edges to edge list
        edge = Edge(arr[0], arr[1])
        graph.edges.append(edge)
        #add all vertices to adjanceny lists
        if arr[0] not in graph.vertices: 
            graph.vertices[arr[0]] = Vertex(arr[0])
            graph.addEdge(arr[0], arr[1])
        else:
            graph.addEdge(arr[0], arr[1])
        if arr[1] not in graph.vertices: 
            graph.vertices[arr[1]] = Vertex(arr[1])
            graph.addEdge(arr[1], arr[0])
        else:
            graph.addEdge(arr[1], arr[0])

def logn_approx(graph):
    graph_copy = copy.deepcopy(graph)
    C = []
    max_vertex = with_max_degree(graph_copy)
    while max_vertex != -1:
        for adj_vertex in graph_copy.vertices.get(max_vertex).adj_list:
            dict_ = graph_copy.vertices
            if adj_vertex in graph_copy.vertices:
                if max_vertex in graph_copy.vertices.get(adj_vertex).adj_list:
                    graph_copy.vertices.get(adj_vertex).adj_list.remove(max_vertex)
        C.append(max_vertex)
        del dict_[max_vertex]
        max_vertex = with_max_degree(graph_copy)
    return C

def with_max_degree(graph):
    max_vertex = -1
    max_length = 0
    for item in graph.vertices:
        if len(graph.vertices.get(item).adj_list) > max_length and len(graph.vertices.get(item).adj_list)!= 0:
            max_length = len(graph.vertices.get(item).adj_list)
            max_vertex = item
    return max_vertex

def two_approx(graph):
    graph_copy = copy.deepcopy(graph)
    C = []
    while bool(graph_copy.edges):
        edge = random.choice(graph_copy.edges)
        remove_adj_edge(graph_copy, edge)
        C.append(edge.edge1)
        C.append(edge.edge2)
    return C

def remove_adj_edge(graph, edge):
    new_edge_list = []
    for other_edge in graph.edges:
        if edge.edge1 != other_edge.edge1 and edge.edge1 != other_edge.edge2 and edge.edge2 != other_edge.edge1 and edge.edge2 != other_edge.edge2:
            new_edge_list.append(other_edge)
    graph.edges = new_edge_list

def brute_force(graph):
    current_best = list(graph.vertices.keys())
    num_vertices = len(graph.vertices.keys())
    all_combos = []
    # all combos is a list of list of tuples where the tuples are each possible combination 
    # and each nested list of tuples contains all the tuples of one size
    for i in range(1, num_vertices):
        a = itertools.combinations(range(num_vertices), i)
        all_combos.append(list(a))
    for combo_size in all_combos:
        for tuple_ in combo_size:
            if is_vertex_cover(graph, tuple_) and len(tuple_)<len(current_best):
                current_best = tuple_
                break
    return list(current_best)

def is_vertex_cover(graph, tuple_):
    for edge_obj in graph.edges:
        if (not (int(edge_obj.edge1) in tuple_)) and (not (int(edge_obj.edge2) in tuple_)):
            return False
    return True
        


if __name__ == "__main__":
    main()