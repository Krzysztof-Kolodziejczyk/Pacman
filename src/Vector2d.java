import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;

        if(that.x != x) return false;

        if(that.y != y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public String toString()
    {
        return x + " " + y;
    }

}
