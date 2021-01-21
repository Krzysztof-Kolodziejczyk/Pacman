package classes;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {

    public Image ghostImage;
    public Image escapeGhostImage;
    public Image chaseGhostImageOrange;
    public Image chaseGhostImageTurkus;
    public Image chaseGhostImage;
    public Vector2d position;
    public Vector2d realPosition;
    private final int cellSize;
    private MapDirection ghostDirection;
    private int ghostSpeed = 1;
    private final ShortestPathFinder shortestPathFinder;
    private final Pacman pacman;
    public boolean chaseModeOn;
    public boolean escapeModeOn;
    private final Vector2d[] possibleStartGhostPosition;


    private Vector2d target;

    public boolean isActive;

    public Ghost(Pacman pacman)
    {
        possibleStartGhostPosition = new Vector2d[]{new Vector2d(9, 9), new Vector2d(10, 9), new Vector2d(11, 9)};

        cellSize = pacman.mapBoard.cellSize;
        this.position = getNewGhostStartPosition();
        this.realPosition = new Vector2d(position.x*cellSize + 15, position.y*cellSize + 15);

        chaseModeOn = true;
        escapeModeOn = false;

        shortestPathFinder = new ShortestPathFinder(pacman.mapBoard);
        Vector2d pacmanPosition = pacman.mapCords(pacman.getCenterOfPacman());
        this.pacman = pacman;
        chaseGhostImageTurkus = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/turkusGhost.gif").getImage();
        chaseGhostImageOrange = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/ghost.gif").getImage();
        escapeGhostImage = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/escapeGhost.gif").getImage();

        Random random = new Random();
        int rand = random.nextInt(2);
        switch (rand)
        {
            case 0 -> ghostImage = chaseGhostImageOrange;
            case 1 -> ghostImage = chaseGhostImageTurkus;
        }
        chaseGhostImage = ghostImage;

        isActive = false;

        target = getNewTarget();
        ghostDirection = shortestPathFinder.pathFinder(position.x, position.y, target.x, target.y);


        if(random.nextInt(6)%2 == 0)
        {
            isActive = true;
        }

    }


    private Vector2d getNewGhostStartPosition()
    {
        Random random = new Random();
        Vector2d newPosition = possibleStartGhostPosition[random.nextInt(3)];
        return new Vector2d(newPosition.x, newPosition.y);
    }


    public void move()
    {
        boolean flag = false;
        if(Math.abs(realPosition.x - position.x*cellSize - 15) >= cellSize || Math.abs(realPosition.y - position.y*cellSize - 15) >= cellSize)
        {
            if(Math.abs(realPosition.x - position.x*cellSize - 15) > cellSize)
            {
                position.x = realPosition.x / cellSize;

            }
            if(Math.abs(realPosition.y - position.y*cellSize - 15) > cellSize)
            {
                position.y = realPosition.y / cellSize;

            }
            if(isActive)
            {
                if(chaseModeOn)
                {
                    target = pacman.mapCords(pacman.getCenterOfPacman());
                    while((target.y == 9 && (target.x < 5 || target.x > 15)))
                    {
                        target = getNewTarget();
                    }
                }
                else
                {
                    flag = true;
                    switch (pacman.pacmanDirection)
                    {
                        case RIGHT:
                            if(pacman.mapBoard.maze.mazeMap[position.y][position.x +1] <=0)
                            {
                                ghostDirection = MapDirection.RIGHT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y - 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.UP;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y + 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.DOWN;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x-1] <=0)
                            {
                                ghostDirection = MapDirection.LEFT;
                            }
                        case LEFT:
                            if(pacman.mapBoard.maze.mazeMap[position.y][position.x-1] <=0)
                            {
                                ghostDirection = MapDirection.LEFT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y - 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.UP;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y + 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.DOWN;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x +1] <=0)
                            {
                                ghostDirection = MapDirection.RIGHT;
                            }
                        case DOWN:
                            if(pacman.mapBoard.maze.mazeMap[position.y + 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.DOWN;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x-1] <=0)
                            {
                                ghostDirection = MapDirection.LEFT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x +1] <=0)
                            {
                                ghostDirection = MapDirection.RIGHT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y - 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.UP;
                            }
                        case UP:
                            if(pacman.mapBoard.maze.mazeMap[position.y - 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.UP;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x-1] <=0)
                            {
                                ghostDirection = MapDirection.LEFT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y][position.x +1] <=0)
                            {
                                ghostDirection = MapDirection.RIGHT;
                            }
                            else if(pacman.mapBoard.maze.mazeMap[position.y + 1][position.x] <=0)
                            {
                                ghostDirection = MapDirection.DOWN;
                            }
                    }
                }
            }
            else
            {
                if(position.equals(target))
                {
                    target = getNewTarget();
                }
            }

            if(!flag)
            {
                ghostDirection = shortestPathFinder.pathFinder(position.x, position.y, target.x, target.y);
            }

        }

        try {
            switch (ghostDirection) {
                case UP -> realPosition.y -= ghostSpeed;
                case RIGHT -> realPosition.x += ghostSpeed;
                case DOWN -> realPosition.y += ghostSpeed;
                case LEFT -> realPosition.x -= ghostSpeed;
            }
        }catch (Exception ex)
        {
            target = getNewTarget();
        }

        if(!isActive)
        {
            if(distance(realPosition, pacman.getCenterOfPacman()) < 150)
            {
                isActive = true;
            }
        }
        else
        {
            if(distance(realPosition, pacman.getCenterOfPacman()) >= 150)

            {
                isActive = false;
            }

        }
    }

    private Vector2d getNewTarget()
    {
        Random random = new Random();
        Vector2d target = shortestPathFinder.freeCells.get(random.nextInt(shortestPathFinder.freeCells.size()));
        return target;
    }

    private int distance(Vector2d a, Vector2d b)
    {
        int x = Math.abs(a.x - b.x);
        int y = Math.abs(a.y - b.y);
        return (int) Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }


    public void switchToEscapeMode()
    {
        ghostImage = escapeGhostImage;
        chaseModeOn = false;
        escapeModeOn = true;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ghostImage = chaseGhostImage;
                        chaseModeOn = true;
                        escapeModeOn = false;
                    }
                }, 5000
        );
    }

    public boolean pacmanGhostCollision()
    {
        if(this.position.equals(pacman.mapCords(pacman.getCenterOfPacman())))
        {
            if(this.chaseModeOn)
            {
                pacman.lives -= 1;
                return true;
            }
            else
            {
                pacman.mapBoard.score += 30;
                this.position = getNewGhostStartPosition();
                this.realPosition = new Vector2d(position.x*cellSize + 15, position.y*cellSize + 15);
                target = getNewTarget();
                ghostDirection = shortestPathFinder.pathFinder(position.x, position.y, target.x, target.y);
                return false;
            }
        }
        return false;
    }

}
