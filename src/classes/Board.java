package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener {

    public Maze maze;
    private Dimension dimension;
    public final int cellSize = 30;
    public int boardSizeX, boardSizeY;
    private final Game game;
    private final Pacman pacman;
    public Food food;
    public int score;
    private Image heartImage;
    private Image appleImage;
    private boolean playerWon;

    public Board()
    {
        maze = new Maze();
        initVariables();
        Listener listener = new Listener(this);
        addKeyListener(listener);
        game = listener.game;
        pacman = game.pacman;
        setFocusable(true);
        food = new Food(this);
        playerWon = false;
    }

    private void initVariables() {

        heartImage = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/heart.png").getImage();
        appleImage = new ImageIcon("/Users/user/IdeaProjects/MyPacMan/resources/images/apple.png").getImage();

        score = 0;
        boardSizeX = cellSize * maze.width;
        boardSizeY = cellSize * maze.height;
        dimension = new Dimension(boardSizeX, boardSizeY);
        Timer timer = new Timer(40, this);
        timer.start();

    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, dimension.width, dimension.height+40);

        drawMaze(graphics2D);
        drawScore(graphics2D);
        drawHearts(graphics2D);

        if(game.gameIsRunning)
        {
            if(game.isPacmanGhostMeet())
            {
                game.newGame();
            }
            else
            {
                pacman.move();
                pacman.draw(graphics2D);
                game.drawGhosts(graphics2D);
            }
            if(game.pacman.lives == 0)
            {
                game.gameOver = true;
                playerWon = false;
            }

        }
        else if(!game.gameOver)
        {
            drawStartLabel(graphics2D);
        }

        if(food.foods.size() == 0)
        {
            game.gameOver = true;
            playerWon = true;
            game.gameIsRunning = false;
        }


        if(!game.gameIsRunning && game.gameOver)
        {
            if(playerWon)
            {
                drawWinnerLabel(graphics2D);
            }
            else
            {
                drawLoserLabel(graphics2D);
            }
        }


        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }


    private void drawWinnerLabel(Graphics2D graphics2D)
    {
        String wonLabel = "You Win";
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString(wonLabel, boardSizeX/2 - 30, boardSizeY/2 - 5);
        wonLabel = "Your Score " + score + "!";
        graphics2D.drawString(wonLabel, boardSizeX/2 - 45, boardSizeY/2 +10);
        wonLabel =  "CLick enter to restart the game";
        graphics2D.drawString(wonLabel, boardSizeX/2 - 100, boardSizeY/2 + 30);

    }

    private void drawLoserLabel(Graphics2D graphics2D)
    {
        String loseLabel = "You Lose";
        graphics2D.setColor(Color.yellow);
        graphics2D.drawString(loseLabel, boardSizeX/2 - 30, boardSizeY/2 - 5);
        loseLabel = "Your Score " + score + "!";
        graphics2D.drawString(loseLabel, boardSizeX/2 - 45, boardSizeY/2 +10);
        loseLabel =  "CLick enter to restart the game";
        graphics2D.drawString(loseLabel, boardSizeX/2 - 100, boardSizeY/2 + 30);
    }

    private void drawStartLabel(Graphics2D graphics2D) {

        String startLabel = "click ENTER to start the game";
        graphics2D.setColor(Color.red);
        graphics2D.drawString(startLabel, boardSizeX/2 - 100, boardSizeY/2);
    }


    private void drawHearts(Graphics2D graphics2D)
    {
        for(int i=0; i<game.pacman.lives; i++)
        {
            graphics2D.drawImage(heartImage, 80 + i*30, boardSizeY + 10, this);
        }
    }


    private void drawScore(Graphics2D graphics2D)
    {
        int i = score;

        graphics2D.setColor(Color.yellow);
        String stringBuilder = "Score " +
                i;
        graphics2D.drawString(stringBuilder, 10, boardSizeY+30);

    }

    private void drawMaze(Graphics2D graphics2D) {
        int i=0;
        int j=0;
        for(int y=0; y<boardSizeY; y += cellSize)
        {
            for(int x=0; x<boardSizeX; x+= cellSize)
            {
                if(maze.mazeMap[i][j] > 0)
                {
                    graphics2D.setColor(new Color(50,0,170));
                    graphics2D.fillRect(x,y,cellSize,cellSize);
                }
                j++;
            }
            j = 0;
            i++;
        }
        i=0;
        j=0;

        graphics2D.setStroke(new BasicStroke(2));
        for(int y=0; y<boardSizeY; y += cellSize)
        {
            for(int x=0; x<boardSizeX; x+= cellSize)
            {

                if(maze.mazeMap[i][j] >= 0)
                {
                    graphics2D.setColor(Color.CYAN);
                    if((maze.mazeMap[i][j] & 1) != 0)
                    {
                        graphics2D.drawLine(x,y,x+cellSize, y);
                    }
                    if((maze.mazeMap[i][j] & 2) != 0)
                    {
                        graphics2D.drawLine(x+cellSize,y,x+cellSize, y+cellSize);
                    }
                    if((maze.mazeMap[i][j] & 4) != 0)
                    {
                        graphics2D.drawLine(x,y+cellSize,x+cellSize, y+cellSize);
                    }
                    if((maze.mazeMap[i][j] & 8) != 0)
                    {
                        graphics2D.drawLine(x,y,x, y+cellSize);
                    }
                    if (maze.mazeMap[i][j] == 0)
                    {
                        graphics2D.setColor(Color.white);
                        graphics2D.fillOval(x + cellSize/2 - 3 , y + cellSize/2 - 3, 6, 6);
                    }
                }
                if(maze.mazeMap[i][j] == -3)
                {
                    graphics2D.drawImage(appleImage, x + 7 ,y + 7,this);
                }

                j++;
            }
            i++;
            j = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public boolean canMoveTo(Vector2d position1, Vector2d position2)
    {
        Vector2d cords1 = mapCords(position1);
        Vector2d cords2 = mapCords(position2);

        if(cords1.x < 0 || cords2.x < 0 || cords1.x >=  21 || cords2.y >= 21)
        {
            return true;
        }
        return maze.mazeMap[cords1.y][cords1.x] <= 0 && maze.mazeMap[cords2.y][cords2.x] <= 0;

    }

    private Vector2d mapCords(Vector2d vector2d)
    {
        int x,y;
        x = vector2d.x / cellSize;
        y = vector2d.y / cellSize;

        return new Vector2d(x,y);
    }
}
