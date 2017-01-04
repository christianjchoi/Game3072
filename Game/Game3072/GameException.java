package Game3072;

/** A general error report exception for Game2048 package.
 *  @author Christian Choi
 */
class GameException extends RuntimeException {

    GameException(String msg) {
        super(msg);
    }

    /** Returns a new exception with a message
     *  errMsg */
    static GameException error(String errMsg) {
        return new GameException(errMsg);
    }

}
