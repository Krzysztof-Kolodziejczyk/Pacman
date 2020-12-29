
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener {

    private final Maze maze;
    private Dimension dimension;
    public final int cellSize = 30;
    public int boardSizeX, boardSizeY;
    private Game game;
    private Pacman pacman;
    private Timer timer;

    public Board()
    {
        maze = new Maze();
        initVariables();
        Listener listener = new Listener(this);
        addKeyListener(listener);
        game = listener.game;
        pacman = game.pacman;
        setFocusable(true);

    }

    private void initVariables() {
        boardSizeX = cellSize * maze.width;
        boardSizeY = cellSize * maze.height;
        dimension = new Dimension(boardSizeX, boardSizeY);
        timer = new Timer(40, this);
        timer.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, dimension.width, dimension.height+40);

        drawMaze(graphics2D);
        drawStartLabel(graphics2D);

        if(game.gameIsRunning)
        {
            pacman.move();
            pacman.draw(graphics2D);
        }
        else
        {
            // TODO
        }


        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }

    private void drawStartLabel(Graphics2D graphics2D) {

        String startLabel = "click ENTER to start the game";
        graphics2D.setColor(Color.red);
        graphics2D.drawString(startLabel, boardSizeX/2 - 100, boardSizeY/2);
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

                if(maze.mazeMap[i][j] != -1)
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

    public boolean canMoveTo(int x, int y)
    {
        System.out.println(y + " " + x + " value: " + maze.mazeMap[y][x]);
        return maze.mazeMap[y][x] <= 0;
    }
}
