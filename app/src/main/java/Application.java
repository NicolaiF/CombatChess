import Model.ChessBoard;
import Model.ChessPlayer;

class Application {

    private ChessBoard chessBoard;
    private ChessPlayer player1;
    private ChessPlayer player2;

    private void init(){
        chessBoard = new ChessBoard();
        player1 = new ChessPlayer("Magnus Carlsen");
        player2 = new ChessPlayer("Sergey Karjakin");
        // TODO: It is a bit confusing that white pieces is at row 0-1. This is inverted from a real chess board. We must decide how to represent the board.
        player1.giveStartingPieces(chessBoard.getBoard().get(0), chessBoard.getBoard().get(1));
        player2.giveStartingPieces(chessBoard.getBoard().get(7), chessBoard.getBoard().get(6));
    }

    private void run(){
        System.out.println(chessBoard.toString());
        System.out.println(player1.getID() + " pieces: " + player1.getPieces());
        System.out.println(player2.getID() + " pieces: " + player2.getPieces());
    }

    public static void main(String [] args)
    {
        Application app = new Application();
        app.init();
        app.run();
    }
}
