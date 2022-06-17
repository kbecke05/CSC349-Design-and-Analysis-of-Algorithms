import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;

// Takes as input two strings of letters representing sequenced DNA and a scoring matrix
// Returns the score and alignment of strings with best score


public class alignment {
    public static void main(String args[]) throws FileNotFoundException {
        
        File file = new File(args[0]);
        Scanner sc = new Scanner(file); 

        String input_x = sc.nextLine().strip();
        String input_y  = sc.nextLine().strip();

        int[][] score_matrix = readInput(sc);

        //create distance matrix
        Cell[][] distance_matrix = new Cell[input_y.length() + 1][input_x.length()+ 1];

        for (int i = 0; i < input_y.length()+1; i++) {
            for (int j = 0; j < input_x.length()+1; j++) {
                distance_matrix[i][j] = new Cell("", "", 0);
            }
        }

        //base cases
        base_cases(input_x, input_y, distance_matrix, score_matrix);

        //main functionality
        int choose_upper, choose_left, choose_diagonal;

        for(int col = 1; col < distance_matrix.length; col ++) {
            for (int row = 1; row < distance_matrix[0].length; row++) {
                char curr_x = input_x.charAt(row-1);
                char curr_y = input_y.charAt(col-1);
                Cell current_cell = distance_matrix[col][row];
    
                choose_left = distance_matrix[col][row-1].score + getScore(curr_y, '-', score_matrix);
                choose_upper = distance_matrix[col-1][row].score + getScore(curr_x, '-', score_matrix);
                choose_diagonal = distance_matrix[col-1][row-1].score + getScore(curr_x, curr_y, score_matrix);

                if (choose_left > choose_upper && choose_left > choose_diagonal) {
                    current_cell.score = choose_left;
                    current_cell.x_string = distance_matrix[col][row-1].x_string + curr_x;
                    current_cell.y_string = distance_matrix[col][row-1].y_string + "-";
                }
                else if (choose_upper >= choose_left && choose_upper > choose_diagonal) {
                    current_cell.score = choose_upper;
                    current_cell.x_string = distance_matrix[col-1][row].x_string + "-";
                    current_cell.y_string = distance_matrix[col-1][row].y_string + curr_y;
                }
                else {
                    current_cell.score = choose_diagonal;
                    current_cell.x_string = distance_matrix[col-1][row-1].x_string + curr_x;
                    current_cell.y_string = distance_matrix[col-1][row-1].y_string + curr_y;
                }
            }
        }

        //print output
        print_output(input_x, input_y, distance_matrix);
    }

    public static void base_cases(String input_x, String input_y, Cell[][] distance_matrix, int[][] score_matrix) {
        for (int i = 1; i<distance_matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                distance_matrix[i][0].x_string += "-"; //vertical
            }
            distance_matrix[i][0].y_string = input_y.substring(0, i);
            distance_matrix[i][0].score = distance_matrix[i-1][0].score + getScore(input_y.charAt(i-1), '-', score_matrix);
        }
        for (int i = 1; i<distance_matrix[0].length; i++) {
            for (int j = 0; j < i; j++) {
                distance_matrix[0][i].y_string += "-"; //vertical
            }
            distance_matrix[0][i].x_string = input_x.substring(0, i);
            distance_matrix[0][i].score = distance_matrix[0][i-1].score + getScore(input_x.charAt(i-1), '-', score_matrix);
        }
    }

    public static int[][] readInput(Scanner sc) {
        String currLine;
        int[][] scores = new int[5][5];
        sc.nextLine();
    
        for (int i = 0; i < 5; i++) {
            currLine = sc.nextLine();
            String[] line = currLine.split(" ");
            for (int j = 1; j < 6; j++) {
                try {
                    scores[i][j - 1] = Integer.parseInt(line[j]);
                }
                catch (NumberFormatException e) { }
            }
        }
        return scores;
    }

    public static int getScore(char char1, char char2, int[][] score_matrix) {
        int idx1, idx2;
        switch (char1) {
            case 'A':
                idx1 = 0;
                break;
            case 'C':
                idx1 = 1;
                break;
            case 'G':
                idx1 = 2;
                break;
            case 'T':
                idx1 = 3;
                break;
            default:
                idx1 = 4;
                break;
        }
        switch (char2) {
            case 'A':
                idx2 = 0;
                break;
            case 'C':
                idx2 = 1;
                break;
            case 'G':
                idx2 = 2;
                break;
            case 'T':
                idx2 = 3;
                break;
            default:
                idx2 = 4;
                break;
        }
        return score_matrix[idx1][idx2];
    }

    public static void print_output(String input_x, String input_y, Cell[][] distance_matrix) {
        System.out.println("x: " + distance_matrix[input_y.length()][input_x.length()].x_string.replace("", " ").trim());
        System.out.println("y: " + distance_matrix[input_y.length()][input_x.length()].y_string.replace("", " ").trim());
        System.out.println("Score: " + distance_matrix[input_y.length()][input_x.length()].score);
    }
}
