package Game3072;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import static Game3072.Command.Type.*;
import static Game3072.GameException.error;

/** Runs a session of this game
 *  @author Christian Choi
 */

class Game {

    /** Input source. */
    private final ReaderSource _input;

    /** The Board. */
    private Board board;

    /** Stack containing previous boards. */
    private Stack<int[][]> prevBoards;

    /** Stack containing previous scores. */
    private Stack<Integer> prevScores;

    /** Number of undos player can use. */
    private int undoCount;

    /** The current state of the game. */
    private String state;

    /** File where highscores are stored. */
    private PrintWriter highscores;

    /** Reading initially from BASESOURCE. */
    Game(ReaderSource baseSource) {
        _input = baseSource;
        board = new Board();
        prevBoards = new Stack<int[][]>();
        prevScores = new Stack<Integer>();
        undoCount = 3;
    }

    /** Run a session of 2048. */
    void process() {
        state = "before";
        while (true) {
            doCommand();
        }
    }

    /** Perform the next command from our input source. */
    void doCommand() {
        try {
            Command cmnd =
                Command.parseCommand(_input.getLine("3072: "));
            Command.Type t = cmnd.commandType();
            String[] op = cmnd.operands();
            _commands.get(t).accept(op);
        } catch (GameException excp) {
            System.out.println(excp.getMessage());
        }
    }

    /** Makes a move by shifting tiles. */
    void doMove(String[] operands) {
        checkState("Move", "playing");
        String dir = operands[0];
        int[][] grid = createCopy(board.board());
        String boardStr = board.toStr();
        int boardScore = board.score();
        if (dir.equals("up") || dir.equals("w")) {
            board.doUp();
        } else if (dir.equals("down") || dir.equals("s")) {
            board.doDown();
        } else if (dir.equals("left") || dir.equals("a")) {
            board.doLeft();
        } else {
            board.doRight();
        }
        if (!board.toStr().equals(boardStr)) {
            prevScores.push(boardScore);
            prevBoards.push(grid);
            board.addTile();
            doPrint();
            if (gameOver()) {
                gameOverMsg();
            }
        } else {
            System.out.println("Invalid move.");
        }
    }

    /** Prints message when game is over and updates high scores. */
    void gameOverMsg() {
        System.out.println("Game Over.");
        state = "finished";
        String name = _input.getLine("Your name: ");
        String score = Integer.toString(board.score());
        String highScore = "Player: " + name + ", Score: " + score;
        System.out.println(highScore);
        try {
            highscores = new PrintWriter(new BufferedWriter(
                new FileWriter("highscores.txt", true)));
        } catch (IOException excp) {
            try {
                highscores = new PrintWriter(new BufferedWriter(
                    new FileWriter(new File("highscores.txt"), true)));
            } catch (IOException e) {
                System.out.println("error");
            }
        }
        highscores.println(name + " - " + score);
        highscores.println("");
        highscores.close();
    }

    /** Creates and returns copy of multidimensional array. */
    int[][] createCopy(int[][] arr) {
        int[][] ret = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            for (int j = 0; j < arr.length; j += 1) {
                ret[i][j] = arr[i][j];
            }
        }
        return ret;
    }

    /** Command to undo a move. */
    void doUndo(String[] operands) {
        checkState("Move", "playing");
        if (undoCount > 0) {
            undoCount -= 1;
            if (!prevBoards.isEmpty()) {
                undo();
                doPrint();
                System.out.println("You have "
                    + Integer.toString(undoCount)
                    + " undos remaining.");
            } else {
                System.out.println("There is nothing to undo.");
            }
        } else {
            System.out.println("You don't have any undos left.");
        }
    }

    /** Undoes the last move. */
    void undo() {
        if (!prevBoards.isEmpty()) {
            board.setScore(prevScores.pop());
            board.setBoard(prevBoards.pop());
        }
    }

    /** Checks if game is over by seeing if moves
     * make any changes to the board. */
    boolean gameOver() {
        String str = board.toStr();
        int score = board.score();
        int[][] oldBoard = createCopy(board.board());
        prevBoards.push(oldBoard);
        prevScores.push(score);
        board.doUp();
        if (!str.equals(board.toStr())) {
            undo();
            return false;
        }
        board.doDown();
        if (!str.equals(board.toStr())) {
            undo();
            return false;
        }
        board.doRight();
        if (!str.equals(board.toStr())) {
            undo();
            return false;
        }
        board.doLeft();
        if (!str.equals(board.toStr())) {
            undo();
            return false;
        }
        return true;
    }

    /** Error for invalid commands. */
    void doError(String[] unused) {
        throw error("Command not understood");
    }

    /** Prints String representation of Board. */
    void doPrint() {
        System.out.println("Score: " + Integer.toString(board.score()));
        System.out.println("");
        System.out.println(board.toStr());
        System.out.println("");
        System.out.flush();
    }

    /** Starts the game. */
    void doStart(String[] operands) {
        checkState("Start", "before");
        state = "playing";
        doPrint();
    }

    /** Resets the game. */
    void doReset(String[] operands) {
        state = "before";
        board = new Board();
        prevBoards.clear();
    }

    /** Exits the game. */
    void doQuit(String[] operands) {
        System.exit(0);
    }

    /** Comparator for printing highscores in order. */
    private class StrComp implements Comparator<String> {
        @Override
        public int compare(String x, String y) {
            String[] xArr = x.split("\\s+");
            String[] yArr = y.split("\\s+");
            int xScore = Integer.parseInt(xArr[xArr.length - 1]);
            int yScore = Integer.parseInt(yArr[yArr.length - 1]);
            if (xScore > yScore) {
                return -1;
            } else if (xScore < yScore) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /** Prints highscores history from this game. */
    void doHighScore(String[] operands) {
        checkState("highscores", "before", "finished");
        try {
            PriorityQueue<String> scores =
                new PriorityQueue<>(new StrComp());
            Scanner reader =
                new Scanner(new FileReader("highscores.txt"));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!line.equals("")) {
                    scores.add(line);
                }
            }
            while (!scores.isEmpty()) {
                System.out.println(scores.poll());
            }
        } catch (IOException excp) {
            System.out.println("No highscores yet.");
        }
    }

    /** Checks to see if input commands
     * are appropriate to state of game. */
    void checkState(String cmnd, String... states) {
        for (String s : states) {
            if (s.equals(state)) {
                return;
            }
        }
        throw error("Input command is not allowed now.");
    }

     /** All existing commands. */
    private final HashMap<Command.Type, Consumer<String[]>> _commands
        = new HashMap<>();
    {
        _commands.put(MOVE, this::doMove);
        _commands.put(UNDO, this::doUndo);
        _commands.put(RESET, this::doReset);
        _commands.put(START, this::doStart);
        _commands.put(QUIT, this::doQuit);
        _commands.put(ERROR, this::doError);
        _commands.put(HIGHSCORE, this::doHighScore);

    }

}

