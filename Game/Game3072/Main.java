package Game3072;

import java.io.InputStreamReader;
import java.io.FileNotFoundException;

/** The main program for 3072 game
 *  @author Christian Choi */

public class Main {

    public static void main(String[] args) {
        Game game;
        InputStreamReader reader = new InputStreamReader(System.in);
        game = new Game(new ReaderSource(reader));
        game.process();
    }
}
