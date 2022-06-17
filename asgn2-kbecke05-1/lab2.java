import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;

// a program that returns the strongly connected components of a graph
// uses Kosaraju's algorithm


public class lab2 {
    public static void main(String args[]) throws FileNotFoundException {
        //reading in input file and converting to nested array
        File file = new File(args[0]);
        Scanner sc = new Scanner(file); 
        int num_lines = 0;
        int num_vertices = 0;
        ArrayList<int[]> nested_arr = new ArrayList<int[]>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] line_arr = line.split(",");


            //converint from array of Strings to array of ints (primitive)
            int[] array = new int[line_arr.length];
            for (int i = 0; i < line_arr.length; i++) {
                line_arr[i] = line_arr[i].strip();
                array[i] = Integer.parseInt(line_arr[i]);
                if (array[i] > num_vertices) {
                    num_vertices = array[i];
                }
            }
            nested_arr.add(num_lines, array);
            num_lines++;
        }
        //create original graph to do first DFS
        Graph g = new Graph(num_vertices + 1);
        for(int i = 0; i< nested_arr.size(); i++) {
            g.add_edge(nested_arr.get(i)[0], nested_arr.get(i)[1]);
        }

        Stack<Integer> s = new Stack<Integer>();
        //new nested arrays to store answer
        ArrayList<ArrayList<Integer>> SCCs = new ArrayList<ArrayList<Integer>>();
        // to index into above array
        int scc_idx = 0;

        //create seperate ArrayList<boolean> to represent visited or not
        boolean visited[] = new boolean[g.size];
        for(int i = 0; i < g.size; i++) {
            visited[i] = false;
        }

        //do first DFS 
        
        for (int i = 0; i < g.size; i++) {
            if (visited[i] == false) {
                g.DFS(i, visited, true, s, SCCs, scc_idx);
            }
        }

        //make new transposed graph
        Graph g_trans = new Graph(num_vertices + 1);
        for(int i = 0; i< nested_arr.size(); i++) {
            g_trans.add_edge(nested_arr.get(i)[1], nested_arr.get(i)[0]);
        }
        //go back through and mark everything as not visited again
        for(int i = 0; i < g.size; i++) {
            visited[i] = false;
        }

        //create new nested arraylist to store answer of SCCs
        while(s.empty() == false) {
            Integer vertex = s.pop();
            if (visited[vertex] == false) {
                SCCs.add(new ArrayList<Integer>());
                g_trans.DFS(vertex, visited, false, s, SCCs, scc_idx);
                scc_idx++;
            }
        }

        //print answer
        System.out.println(SCCs.size() + " Strongly Connected Component(s):");
        for (int i = 0; i<SCCs.size();i++){
            SCCs.get(i).sort(Comparator.naturalOrder());
            for (int j = 0; j<SCCs.get(i).size()-1;j++) {
                System.out.print(SCCs.get(i).get(j) + ", ");
            }
            System.out.print(SCCs.get(i).get(SCCs.get(i).size()-1));
            System.out.println();
        }
        sc.close();
    }

}