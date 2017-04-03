import controller.ChessBoardController;
import model.Board;
import model.Player;

class Application {

    private Board board;
    private Player player1;
    private Player player2;
    private ChessBoardController chessBoardController;

    private void init(){
        board = new Board();
        chessBoardController = new ChessBoardController(board);
        player1 = new Player("Magnus Carlsen");
        player2 = new Player("Sergey Karjakin");
    }

    private void run(){
        player1.giveStartingPieces(board.getBoard()[7], (board.getBoard()[6]));
        player2.giveStartingPieces(board.getBoard()[0], (board.getBoard()[1]));

        //System.out.println(player1.getID() + " pieces: " + player1.getPieces());
        //System.out.println(player2.getID() + " pieces: " + player2.getPieces());
        System.out.println(board.toString());
        chessBoardController.showLegalMoves("D1", player1);

        chessBoardController.movePiece(player1, "C2", "C3");
        chessBoardController.movePiece(player2, "D7", "D6");
        System.out.println(board.toString());


        chessBoardController.showLegalMoves("D1", player1);
        chessBoardController.movePiece(player1, "D1", "A4");
        System.out.println(board.toString());

        // Checking a lot of moves for debugging
        chessBoardController.showLegalMoves("A4", player1);
        chessBoardController.showLegalMoves("B7", player2);
        chessBoardController.showLegalMoves("C7", player2);
        chessBoardController.showLegalMoves("D8", player2);
        chessBoardController.showLegalMoves("C8", player2);
        chessBoardController.showLegalMoves("E8", player2);
        chessBoardController.showLegalMoves("F8", player2);
    }

    public static void main(String [] args)
    {
        Application app = new Application();
        app.init();
        app.run();
    }
}
