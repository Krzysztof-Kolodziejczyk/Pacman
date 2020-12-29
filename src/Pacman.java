import javax.swing.*;
import java.awt.*;

public class Pacman {

    public MapDirection pacmanDirection;
    private int pacmanSpeed;
    private int pacmanPositionX;
    private int pacmanRealPositionX;
    private int pacmanPositionY;
    private int pacmanRealPositionY;
    private int[][] map;
    private final Image up;
    private final Image right;
    private final Image down;
    private final Image left;
    private Image currentImage;
    private final Board mapBoard;

    public Pacman(Board board)
    {
        pacmanDirection = null;
        pacmanSpeed = 2;
        down = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/down.gif").getImage();
        up = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/up.gif").getImage();
        right = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/right.gif").getImage();
        left = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/left.gif").getImage();

        currentImage = right;

        mapBoard = board;
        int cellSize = board.cellSize;

        pacmanPositionX = 10;
        // center of pacman X
        pacmanRealPositionX = pacmanPositionX * cellSize + 15;

        pacmanPositionY = 15;
        // center of pacman Y
        pacmanRealPositionY = pacmanPositionY * cellSize + 15;
    }

    public void turnPacman(MapDirection newDirection)
    {
        pacmanDirection = newDirection;
        switch (newDirection)
        {
            case UP -> currentImage = up;
            case RIGHT -> currentImage = right;
            case DOWN -> currentImage = down;
            case LEFT -> currentImage = left;
        }
    }


    // pacman is a rectangle 22 x 22
    public void move()
    {
        if(pacmanDirection != null)
        {
            switch (pacmanDirection)
            {
                case UP:
                    if((pacmanRealPositionY - 15 - pacmanSpeed)/ mapBoard.cellSize < pacmanPositionY)
                    {
                        if(mapBoard.canMoveTo(pacmanPositionX, pacmanPositionY-1))
                        {
                            System.out.println("if up");
                            pacmanPositionY -= 1;
                            pacmanRealPositionY -= pacmanSpeed;
                        }
                    }
                    else
                    {
                        pacmanRealPositionY -= pacmanSpeed;
                    }
                    break;
                case RIGHT:
                    if((pacmanRealPositionX + 15 + pacmanSpeed)/ mapBoard.cellSize > pacmanPositionX)
                    {
                        if(mapBoard.canMoveTo(pacmanPositionX + 1, pacmanPositionY))
                        {
                            System.out.println("if right");
                            pacmanPositionX ++;
                            pacmanRealPositionX += pacmanSpeed;
                            System.out.println(pacmanPositionX);
                        }
                    }
                    else
                    {
                        System.out.println("else");
                        pacmanRealPositionX += pacmanSpeed;
                    }
                    break;
                case DOWN:
                    if((pacmanRealPositionY + 15 + pacmanSpeed)/ mapBoard.cellSize > pacmanPositionY)
                    {
                        if(mapBoard.canMoveTo(pacmanPositionX, pacmanPositionY+1))
                        {
                            System.out.println("if down");
                            pacmanPositionY += 1;
                            pacmanRealPositionY += pacmanSpeed;
                        }
                    }
                    else {
                        pacmanRealPositionY += pacmanSpeed;
                    }
                    break;
                case LEFT:
                    System.out.println();
                    System.out.println("this is left");
                    if((pacmanRealPositionX - 15 - pacmanSpeed)/ mapBoard.cellSize < pacmanPositionX)
                    {
                        if(mapBoard.canMoveTo(pacmanPositionX - 1, pacmanPositionY))
                        {
                            System.out.println("if left");
                            System.out.println(pacmanPositionX + " "+ pacmanPositionY);
                            System.out.println(pacmanRealPositionX + " "+ pacmanRealPositionY);


                            pacmanPositionX -= 1;
                            pacmanRealPositionX -= pacmanSpeed;
                        }
                        else
                        {
                            System.out.println("any condition ");
                        }
                    }
                    else
                    {
                        System.out.println("else left");
                        System.out.println(pacmanPositionX + " "+ pacmanPositionY);
                        System.out.println(pacmanRealPositionX + " "+ pacmanRealPositionY);

                        pacmanRealPositionX -= pacmanSpeed;
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D)
    {
        graphics2D.drawImage(currentImage, pacmanRealPositionX - 11, pacmanRealPositionY - 11, mapBoard);
    }



}
