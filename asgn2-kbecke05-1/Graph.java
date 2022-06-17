import java.util.Stack;
import java.util.ArrayList;

// Algorithm:

// 1. Create an empty stack s
// 2. DFS traversal of graph, keeping track of previsit and postvisit numbers, adding node to stack only once a node is completely finished
// 3. Transpose the graph by reversing the direction of all the edges, mark everything as undiscovered
// 4. While the stack isn't empty, pop and call DFS on popped vertex if not already discovered
// 5. Print each resulting SCC

public class Graph{
    int size;
    ArrayList<ArrayList<Integer>> adj_list;

    public Graph(int size){
        this.size = size;
        //nested array construct an adjancecy list
        adj_list = new ArrayList<ArrayList<Integer>>();
        //initialize each nested ArrayList<Integer>
        for (int i=0; i<size; i++) {
            adj_list.add(new ArrayList<Integer>());
        }
    }

    ArrayList<Integer> get(int i) { return adj_list.get(i);}

    //function to add edges to the adjancecy lists
    void add_edge(int x, int y) {
        this.adj_list.get(x).add(y);
    }

    void DFS(int vertex, boolean[] visited, boolean first, Stack<Integer> s, 
                ArrayList<ArrayList<Integer>> SCCs, int scc_idx) {
        visited[vertex] = true;
        if (first == false) {
            SCCs.get(scc_idx).add(vertex);
        }
        for (int i=0; i<adj_list.get(vertex).size(); i++) {
            int adj_vertex_num = adj_list.get(vertex).get(i);
            if (visited[adj_vertex_num] == false) {
                DFS(adj_vertex_num, visited, first, s, SCCs, scc_idx);
            }
        }
        if (first == true) {
            s.push(vertex);
        }
    }
}