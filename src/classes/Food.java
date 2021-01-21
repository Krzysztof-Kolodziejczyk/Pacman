package classes;

import java.util.HashMap;

public class Food {
    public HashMap<Vector2d, Vector2d> foods = new HashMap<>();

    public Food(Board board)
    {
        for(int i=0; i<board.maze.mazeMap.length; i++)
        {
            for(int j=0; j<board.maze.mazeMap[0].length; j++)
            {
                if(board.maze.mazeMap[i][j] == 0)
                {
                    foods.put(new Vector2d(j,i), new Vector2d(j,i));
                }
            }
        }
    }
}
