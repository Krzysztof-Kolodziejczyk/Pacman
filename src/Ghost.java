import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {

    public Image ghostImage;
    public Vector2d position;
    public Vector2d realPosition;
    private final int cellSize;
    private MapDirection ghostDirection;
    private int ghostSpeed = 2;
    private DFS dfs;
    private Pacman pacman;


    private Vector2d target;

    public boolean isActive;

    public Ghost(Vector2d position, Pacman pacman)
    {
        dfs = new DFS(pacman.mapBoard);
        Vector2d pacmanPosition = pacman.mapCords(pacman.getCenterOfPacman());
        ghostDirection = dfs.DFSFiner(position.x, position.y, pacmanPosition.x, pacmanPosition.y);
        this.pacman = pacman;
        cellSize = pacman.mapBoard.cellSize;
        ghostImage = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/ghost.gif").getImage();
        this.position = position;
        this.realPosition = new Vector2d(position.x*cellSize, position.y*cellSize );
        isActive = false;

        target = getNewTarget();

        Random random = new Random();
        if(random.nextInt(6)%2 == 0)
        {
            isActive = true;
        }

    }

    public void move()
    {
        if(realPosition.x / cellSize != position.x || realPosition.y / cellSize != position.y)
        {
            position.x = realPosition.x / cellSize;
            position.y = realPosition.y / cellSize;
            if(isActive)
            {
                if(target.equals(pacman.mapCords(pacman.getCenterOfPacman())))
                {
                    target = getNewTarget();
                    isActive = false;
                }
                else
                {
                    target = pacman.mapCords(pacman.getCenterOfPacman());
                }
            }
            else
            {
                if(position.equals(target))
                {
                    target = getNewTarget();
                }
            }

            ghostDirection = dfs.DFSFiner(position.x, position.y, target.x, target.y);
        }
        switch (ghostDirection) {
            case UP -> realPosition.y -= ghostSpeed;
            case RIGHT -> realPosition.x += ghostSpeed;
            case DOWN -> realPosition.y += ghostSpeed;
            case LEFT -> realPosition.x -= ghostSpeed;
        }
    }

    public void activate()
    {
        isActive = true;
    }

    public void deactivate()
    {
        isActive = false;
    }

    private Vector2d getNewTarget()
    {
        Random random = new Random();
        Vector2d target = dfs.freeCells.get(random.nextInt(dfs.freeCells.size()));
        return target;
    }


}
