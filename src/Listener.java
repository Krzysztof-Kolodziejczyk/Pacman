import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Listener extends KeyAdapter {

    public final Game game;

    public Listener(Board board)
    {
        game = new Game(board);
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        int key = event.getKeyCode();
        if(game.gameIsRunning)
        {
            // up
            if (key == KeyEvent.VK_W) {
                game.pacman.turnPacman(MapDirection.UP);
            }
            // right
            else if (key == KeyEvent.VK_D) {
                game.pacman.turnPacman(MapDirection.RIGHT);
            }
            // down
            else if (key == KeyEvent.VK_S) {
                game.pacman.turnPacman(MapDirection.DOWN);
            }
            // left
            else if (key == KeyEvent.VK_A) {
                game.pacman.turnPacman(MapDirection.LEFT);
            }
            // to pause game
            else if(key == KeyEvent.VK_SPACE)
            {
                game.pauseGame();
            }
        }
        else
        {
            if (key == KeyEvent.VK_ENTER) {
                game.startGame();
            }
        }
    }

}
