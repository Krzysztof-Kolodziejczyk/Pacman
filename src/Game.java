import java.awt.*;
import java.util.Random;

public class Game {
    public boolean gameIsRunning;
    public Pacman pacman;
    private final Board mapBoard;
    public Ghost[] ghosts;
    private final int startNumberOfGhosts;
    public Game(Board board, int startNumberOfGhosts)
    {
        this.startNumberOfGhosts = startNumberOfGhosts;
        gameIsRunning = false;
        mapBoard = board;
        pacman = new Pacman(mapBoard,this);

        initGhosts();

    }

    private void initGhosts()
    {
        ghosts = new Ghost[startNumberOfGhosts];
        for(int i=0; i<startNumberOfGhosts; i++) {
            ghosts[i] = new Ghost(pacman);
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
            graphics2D.drawImage(ghost.ghostImage, ghost.realPosition.x - 11, ghost.realPosition.y - 11, mapBoard);
        }
    }

    public boolean isPacmanGhostMeet()
    {
        for(Ghost ghost: ghosts)
        {
            if(ghost.pacmanGhostCollision())
            {
                return true;
            }
        }
        return false;
    }

    public void newGame()
    {
        gameIsRunning = false;
        initGhosts();
        pacman.initPacmanPosition();
    }

    public void makeGhostsEscaping()
    {
        for(Ghost ghost: ghosts)
        {
            ghost.switchToEscapeMode();
        }
    }


}
