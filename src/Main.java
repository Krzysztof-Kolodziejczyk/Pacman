
import javax.swing.*;

public class Main extends JFrame {
    Board board;
    private final int width;
    private final int height;

    public Main()
    {
        board = new Board();
        width = board.boardSizeX;
        height = board.boardSizeY;
        add(board);
    }

    public static void main(String[] args) {
        Main pacman = new Main();
        pacman.setVisible(true);
        pacman.setTitle("Pacman");
        pacman.setSize(pacman.width, pacman.height+60);
        pacman.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pacman.setLocationRelativeTo(null);
    }
}
