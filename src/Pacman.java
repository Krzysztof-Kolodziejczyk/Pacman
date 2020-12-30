import javax.swing.*;
import java.awt.*;

public class Pacman {

    public MapDirection pacmanDirection;
    private final int pacmanSpeed;
    private final Image up;
    private final Image right;
    private final Image down;
    private final Image left;
    private Image currentImage;
    public final Board mapBoard;
    private final Vector2d pacmanRealPositionTopLeft;
    private final Vector2d pacmanRealPositionTopRight;
    private final Vector2d pacmanRealPositionBottomLeft;
    private final Vector2d pacmanRealPositionBottomRight;

    public Pacman(Board board)
    {
        pacmanDirection = null;
        pacmanSpeed = 3;
        down = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/down.gif").getImage();
        up = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/up.gif").getImage();
        right = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/right.gif").getImage();
        left = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/left.gif").getImage();

        currentImage = right;

        mapBoard = board;
        int cellSize = board.cellSize;

        int pacmanPositionX = 10;
        int pacmanPositionY = 15;

        pacmanRealPositionTopLeft = new Vector2d(pacmanPositionX *cellSize + 4, pacmanPositionY * cellSize + 4);
        pacmanRealPositionTopRight = new Vector2d(pacmanPositionX *cellSize + 26, pacmanPositionY * cellSize + 4);
        pacmanRealPositionBottomLeft = new Vector2d(pacmanPositionX *cellSize + 4, pacmanPositionY * cellSize + 26);
        pacmanRealPositionBottomRight = new Vector2d(pacmanPositionX *cellSize + 26, pacmanPositionY * cellSize + 26);


    }

    public void turnPacman(MapDirection newDirection)
    {
        switch (newDirection)
        {
            case UP:
                if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopLeft.x, pacmanRealPositionTopLeft.y - 2 - pacmanSpeed),
                        new Vector2d(pacmanRealPositionTopRight.x, pacmanRealPositionTopRight.y - 2 - pacmanSpeed)))
                {
                    pacmanDirection = newDirection;
                    currentImage = up;
                }
                break;
            case RIGHT:
                if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopRight.x + 2 + pacmanSpeed, pacmanRealPositionTopRight.y),
                        new Vector2d(pacmanRealPositionBottomRight.x + 2 + pacmanSpeed, pacmanRealPositionTopRight.y)))
                {
                    pacmanDirection = newDirection;
                    currentImage = right;
                }
                break;
            case DOWN:
                if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionBottomLeft.x, pacmanRealPositionBottomLeft.y + 2 + pacmanSpeed),
                        new Vector2d(pacmanRealPositionBottomRight.x, pacmanRealPositionBottomRight.y + 2 + pacmanSpeed)))
                {
                    pacmanDirection = newDirection;
                    currentImage = down;
                }
                break;
            case LEFT:
                if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y),
                        new Vector2d(pacmanRealPositionBottomLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y)))
                {
                    pacmanDirection = newDirection;
                    currentImage = left;
                }
                break;
        }
    }


    // pacman is a rectangle 22 x 22 pixels
    public void move()
    {
        if(pacmanDirection != null)
        {
            switch (pacmanDirection)
            {
                case UP:
                    if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopLeft.x, pacmanRealPositionTopLeft.y - 2 - pacmanSpeed),
                            new Vector2d(pacmanRealPositionTopRight.x, pacmanRealPositionTopRight.y - 2 - pacmanSpeed)))
                    {
                        pacmanRealPositionTopLeft.y -= pacmanSpeed;
                        pacmanRealPositionTopRight.y -= pacmanSpeed;
                        pacmanRealPositionBottomLeft.y -= pacmanSpeed;
                        pacmanRealPositionBottomRight.y -= pacmanSpeed;
                    }
                    break;

                case RIGHT:
                    if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopRight.x + 2 + pacmanSpeed, pacmanRealPositionTopRight.y),
                            new Vector2d(pacmanRealPositionBottomRight.x + 2 + pacmanSpeed, pacmanRealPositionTopRight.y)))
                    {
                        Vector2d mapCords = mapCords(new Vector2d(pacmanRealPositionTopLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y));
                        if(mapCords.x > 21)
                        {
                            pacmanRealPositionTopLeft.x = 0;
                            pacmanRealPositionTopRight.x = 22;
                            pacmanRealPositionBottomLeft.x = 0;
                            pacmanRealPositionBottomRight.x = 22;
                        }
                        else
                        {
                            pacmanRealPositionTopLeft.x += pacmanSpeed;
                            pacmanRealPositionTopRight.x += pacmanSpeed;
                            pacmanRealPositionBottomLeft.x += pacmanSpeed;
                            pacmanRealPositionBottomRight.x += pacmanSpeed;
                        }
                    }
                    break;
                case DOWN:
                    if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionBottomLeft.x, pacmanRealPositionBottomLeft.y + 2 + pacmanSpeed),
                            new Vector2d(pacmanRealPositionBottomRight.x, pacmanRealPositionBottomRight.y + 2 + pacmanSpeed)))
                    {
                        pacmanRealPositionTopLeft.y += pacmanSpeed;
                        pacmanRealPositionTopRight.y += pacmanSpeed;
                        pacmanRealPositionBottomLeft.y += pacmanSpeed;
                        pacmanRealPositionBottomRight.y += pacmanSpeed;
                    }
                    break;
                case LEFT:
                    if(mapBoard.canMoveTo(new Vector2d(pacmanRealPositionTopLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y),
                            new Vector2d(pacmanRealPositionBottomLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y)))
                    {
                        Vector2d mapCords = mapCords(new Vector2d(pacmanRealPositionTopLeft.x - 2 - pacmanSpeed, pacmanRealPositionTopLeft.y));
                        if(mapCords.x < 0)
                        {
                            pacmanRealPositionTopLeft.x = mapBoard.boardSizeX-22;
                            pacmanRealPositionTopRight.x = mapBoard.boardSizeX;
                            pacmanRealPositionBottomLeft.x = mapBoard.boardSizeX-22;
                            pacmanRealPositionBottomRight.x = mapBoard.boardSizeX;
                        }
                        else
                        {
                            pacmanRealPositionTopLeft.x -= pacmanSpeed;
                            pacmanRealPositionTopRight.x -= pacmanSpeed;
                            pacmanRealPositionBottomLeft.x -= pacmanSpeed;
                            pacmanRealPositionBottomRight.x -= pacmanSpeed;
                        }
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D)
    {
        Vector2d eatenFoodPos = isFoodEaten();
        if(eatenFoodPos != null)
        {
            mapBoard.maze.mazeMap[eatenFoodPos.y][eatenFoodPos.x] = -1;
            mapBoard.score += 1;
        }

        graphics2D.drawImage(currentImage, pacmanRealPositionTopLeft.x, pacmanRealPositionTopLeft.y, mapBoard);
    }

    private Vector2d isFoodEaten()
    {
        Vector2d pos = mapCords(getCenterOfPacman());
        Vector2d eatenFoodPos = mapBoard.food.foods.get(pos);

        if(eatenFoodPos != null)
        {
            mapBoard.food.foods.remove(eatenFoodPos);
            return eatenFoodPos;
        }
        return null;
    }


    public Vector2d mapCords(Vector2d vector2d)
    {
        int x,y;
        x = vector2d.x / mapBoard.cellSize;
        y = vector2d.y / mapBoard.cellSize;

        return new Vector2d(x,y);
    }

    public Vector2d getCenterOfPacman()
    {
        return new Vector2d((pacmanRealPositionTopRight.x + pacmanRealPositionBottomRight.x)/2, (pacmanRealPositionTopRight.y + pacmanRealPositionBottomLeft.y)/2);
    }


}
