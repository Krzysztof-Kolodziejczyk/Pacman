package classes;

public class MapCell {
    public int value;
    public boolean visited;
    public Vector2d parent;
    public int cost;

    public MapCell(int value)
    {
        cost = 10000;
        this.value = value;
        visited = false;
    }

    public String toString()
    {
        if(parent == null)
        {
            return null;
        }
        return parent.x + " " + parent.y;
    }
}
