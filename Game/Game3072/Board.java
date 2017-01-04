package Game3072;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class Board {

    /** Random number generator. */
    private Random r;

    /** Multi-dimensional array representation of board. */
    private int[][] board;

    /** Dimensions of board. */
    private int dim;

    /** Current score of this board. */
    private int score;

    Board() {
        board = new int[4][4];
        dim = board.length;
        r = new Random();
        int fRow = r.nextInt(dim);
        int fCol = r.nextInt(dim);
        int sRow = fRow;
        int sCol = fCol;
        board[fRow][fCol] = 3;
        while (sRow == fRow && sCol == fCol) {
            sRow = r.nextInt(dim);
            sCol = r.nextInt(dim);
        }
        board[sRow][sCol] = 3;
        score = 0;
    }

    /** String representation of the board. */
    String toStr() {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < dim; i += 1) {
            String str = "";
            for (int j = 0; j < dim; j += 1) {
                if (board[i][j] != 0) {
                    str += Integer.toString(board[i][j]);
                } else {
                    str += "-";
                }
                if (j != dim - 1) {
                    str += "\t";
                }
            }
            if (i != dim - 1) {
                str += "\n\n\n";
            }
            lines.add(str);
        }
        String board = "";
        for (String s : lines) {
            board += s;
        }
        return board;
    }

    /** Getter method for the score of the board. */
    int score() {
        return score;
    }

    /** Setter method for the score of the board. */
    void setScore(int s) {
        score = s;
    }

    /** Getter method for this board. */
    int[][] board() {
        return board;
    }

    /** Setter method for this board. */
    void setBoard(int[][] arr) {
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                board[i][j] = arr[i][j];
            }
        }
    }

    /** Helper method for adding zeros. */
    void zeroFill(ArrayList<Integer> lst) {
        while (lst.size() != this.dim) {
            lst.add(0);
        }
    }


    /** Helper method for combining like tiles
      * in proper order. */
    void combine(ArrayList<Integer> lst) {
        zeroFill(lst);
        for (int i = 0; i < lst.size() - 1; i += 1) {
            if (lst.get(i) != 0 && (int) lst.get(i)
                == (int) lst.get(i + 1)) {
                lst.set(i, lst.get(i) * 2);
                lst.set(i + 1, 0);
                score += lst.get(i);
                if ((int) lst.get(i) == 3072) {
                    System.out.println("Congratulations! You Win!");
                }
            }
        }
        for (int i = 0; i < lst.size() - 1; i += 1) {
            if (lst.get(i) == 0) {
                lst.remove(i);
            }
        }
        zeroFill(lst);
    }

    /** Adds a 3-tile to a random spot on board if there
     * are any spots available. */
    void addTile() {
        boolean zero = false;
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                if (board[i][j] == 0) {
                    zero = true;
                    break;
                }
            }
        }
        if (!zero) {
            return;
        }
        int rRow = r.nextInt(dim);
        int rCol = r.nextInt(dim);
        while (board[rRow][rCol] != 0) {
            rRow = r.nextInt(dim);
            rCol = r.nextInt(dim);
        }
        board[rRow][rCol] = 3;
    }

    /** Shifts all tiles up. */
    void doUp() {
        for (int col = 0; col < dim; col += 1) {
            ArrayList<Integer> newCol = new ArrayList<>();
            for (int row = 0; row < dim; row += 1) {
                int num = board[row][col];
                if (num != 0) {
                    newCol.add(num);
                }
            }
            combine(newCol);
            for (int row = 0; row < dim; row += 1) {
                board[row][col] = newCol.get(row);
            }
        }
    }

    /** Shifts all tiles down. */
    void doDown() {
        for (int col = 0; col < dim; col += 1) {
            ArrayList<Integer> newCol = new ArrayList<>();
            for (int row = dim - 1; row >= 0; row -= 1) {
                int num = board[row][col];
                if (num != 0) {
                    newCol.add(num);
                }
            }
            combine(newCol);
            Collections.reverse(newCol);
            for (int row = 0; row < dim; row += 1) {
                board[row][col] = newCol.get(row);
            }
        }
    }

    /** Shifts all tiles right. */
    void doRight() {
        for (int row = 0; row < dim; row += 1) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int col = dim - 1; col >= 0; col -= 1) {
                int num = board[row][col];
                if (num != 0) {
                    newRow.add(num);
                }
            }
            combine(newRow);
            Collections.reverse(newRow);
            for (int i = 0; i < dim; i += 1) {
                board[row][i] = newRow.get(i);
            }
        }
    }



    /** Shifts all tiles left. */
    void doLeft() {
        for (int row = 0; row < dim; row += 1) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int col = 0; col < dim; col += 1) {
                int num = board[row][col];
                if (num != 0) {
                    newRow.add(num);
                }
            }
            combine(newRow);
            for (int i = 0; i < dim; i += 1) {
                board[row][i] = newRow.get(i);
            }
        }
    }

}

