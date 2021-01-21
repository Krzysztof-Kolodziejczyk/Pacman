package classes;

import java.util.ArrayList;

public class ShortestPathFinder {
    public MapCell[][] map;
    private final Board mapBoard;
    public ArrayList<Vector2d> freeCells;

    public ShortestPathFinder(Board mapBoard)
    {
        freeCells = new ArrayList<>();
        map = new MapCell[mapBoard.maze.mazeMap.length][mapBoard.maze.mazeMap[0].length];
        this.mapBoard = mapBoard;

        for(int i=0; i<mapBoard.maze.mazeMap.length; i++)
        {
            for(int j=0; j<mapBoard.maze.mazeMap[0].length; j++)
            {
                if(mapBoard.maze.mazeMap[i][j] <= 0 && mapBoard.maze.mazeMap[i][j] > -2 && j > 0 && j < 20)
                {
                    freeCells.add(new Vector2d(j,i));
                }
            }
        }

    }

    public MapDirection pathFinder(int start_x, int start_y, int search_x, int search_y)
    {
        for(int i=0; i<mapBoard.maze.mazeMap.length; i++)
        {
            for(int j=0; j<mapBoard.maze.mazeMap[0].length; j++)
            {
                int value;
                if(mapBoard.maze.mazeMap[i][j] <= 0 && mapBoard.maze.mazeMap[i][j] > -2 && j > 0 && j < 20)
                {
                    value = 1;
                }
                else value = 0;

                map[i][j] = new MapCell(value);
            }
        }

        map[start_y][start_x].cost = 0;
        dijkstra(start_x,start_y);

        int current_x = search_x;
        int current_y = search_y;
        while(!(current_x == start_x && current_y == start_y))
        {
            int tmp_current_x = current_x;
            int tmp_current_y = current_y;
            Vector2d parent = map[current_y][current_x].parent;

            if(parent == null)
                return null;
            current_x = parent.x;
            current_y = parent.y;
            if(current_x == start_x && current_y == start_y)
            {
                if(tmp_current_y < current_y)
                    return MapDirection.UP;
                if(tmp_current_y > current_y)
                    return MapDirection.DOWN;
                if(tmp_current_x < current_x)
                    return MapDirection.LEFT;
                return MapDirection.RIGHT;
            }
        }
        return null;
    }

    private void dijkstra(int current_x, int current_y)
    {
        // UP
        if(map[current_y-1][current_x].value == 1 && relax(current_x, current_y-1, current_x, current_y))
        {
            dijkstra(current_x, current_y-1);
        }
        // RIGHT
        if(map[current_y][current_x+1].value == 1 && relax(current_x+1, current_y, current_x, current_y))
        {
            dijkstra(current_x+1, current_y);
        }
        // DOWN
        if(map[current_y+1][current_x].value == 1 && relax(current_x, current_y+1, current_x, current_y))
        {
            dijkstra(current_x, current_y+1);
        }
        // LEFT
        if(map[current_y][current_x-1].value == 1 && relax(current_x-1, current_y, current_x, current_y))
        {
            dijkstra(current_x-1, current_y);
        }
    }


    private boolean relax(int next_x, int next_y, int current_x, int current_y)
    {
        if(map[current_y][current_x].cost + 1 < map[next_y][next_x].cost)
        {
            map[next_y][next_x].cost = map[current_y][current_x].cost + 1;
            map[next_y][next_x].parent = new Vector2d(current_x, current_y);return true;
        }
        return false;
    }
}
