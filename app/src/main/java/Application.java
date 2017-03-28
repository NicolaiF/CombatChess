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
        System.out.println(board.toString());
        player1.giveStartingPieces(board.getBoard()[7], (board.getBoard()[6]));
        player2.giveStartingPieces(board.getBoard()[0], (board.getBoard()[1]));
        System.out.println(player1.getPieces());
        System.out.println(player2.getPieces());
    }

    public static void main(String [] args)
    {
        Application app = new Application();
        app.init();
        app.run();
    }
}
