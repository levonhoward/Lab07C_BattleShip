import java.util.ArrayList;
import java.util.Arrays;

public class BattleshipShip
{
    int length;
    ArrayList<int[]> tiles;
    int hits;

    BattleshipShip(ArrayList<int[]> tiles)
    {
        this.tiles = tiles;
        length = tiles.size();
        hits = 0;
    }

    public void hitShip()
    {
        hits++;
    }

    public boolean isSunk()
    {
        return (length <= hits);
    }

    public boolean occupies(int row, int col)
    {
        boolean doesOccupy = false;

        int[] target = {row, col};
        for (int[] tile : tiles)
        {
            if (Arrays.equals(target, tile))
            {
                doesOccupy = true;
            }
        }

        return doesOccupy;
    }
}
