public class Game {
    public boolean gameIsRunning;
    public Pacman pacman;
    private final Board mapBoard;

    public Game(Board board)
    {
        gameIsRunning = false;
        mapBoard = board;
        pacman = new Pacman(mapBoard);
    }

    public void startGame()
    {
        gameIsRunning = true;
    }

    public void pauseGame()
    {
        gameIsRunning = false;
    }

}
