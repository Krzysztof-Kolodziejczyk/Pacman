import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Game {
    public boolean gameIsRunning;
    public Pacman pacman;
    private final Board mapBoard;
    public HashMap<Vector2d, Ghost> ghostsMap;

    public Game(Board board, int startNumberOfGhosts)
    {
        gameIsRunning = false;
        mapBoard = board;
        pacman = new Pacman(mapBoard);

        ghostsMap = new HashMap<>();
        Vector2d[] possibleStartGhostPosition = {new Vector2d(9,9), new Vector2d(10,9), new Vector2d(11,9)};

        Random random = new Random();
        for(int i=0; i<startNumberOfGhosts; i++)
        {
            Vector2d newPosition = possibleStartGhostPosition[random.nextInt(3)];
            ghostsMap.put(newPosition, new Ghost(newPosition, pacman));
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
        for (Map.Entry mapElement : ghostsMap.entrySet())
        {
            Ghost ghost = (Ghost) mapElement.getValue();
            ghost.move();
            graphics2D.drawImage(ghost.ghostImage, ghost.position.x*mapBoard.cellSize + 4, ghost.position.y*mapBoard.cellSize + 4, mapBoard);

        }

    }

}
