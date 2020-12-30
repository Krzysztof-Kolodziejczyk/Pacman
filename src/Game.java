import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
    public boolean gameIsRunning;
    public Pacman pacman;
    private final Board mapBoard;
    public Ghost[] ghosts;

    public Game(Board board, int startNumberOfGhosts)
    {
        gameIsRunning = false;
        mapBoard = board;
        pacman = new Pacman(mapBoard);

        ghosts = new Ghost[startNumberOfGhosts];
        Vector2d[] possibleStartGhostPosition = {new Vector2d(9,9), new Vector2d(10,9), new Vector2d(11,9)};

        Random random = new Random();
        for(int i=0; i<startNumberOfGhosts; i++) {
            Vector2d newPosition = possibleStartGhostPosition[random.nextInt(3)];
            ghosts[i] = new Ghost(newPosition, pacman);
        }

    }

    public void startGame()
    {
        gameIsRunning = true;
    }

    public void pauseGame()
    {
        gameIsRunning = false;
    }

    public void drawGhosts(Graphics2D graphics2D)
    {
        for (Ghost ghost: ghosts)
        {
            ghost.move();
            graphics2D.drawImage(ghost.ghostImage, ghost.realPosition.x + 4, ghost.realPosition.y + 4, mapBoard);
        }

    }

}
