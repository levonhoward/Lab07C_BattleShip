import javax.swing.*;

public class BattleshipBoardTile extends JButton
{
    int row, col;

    public BattleshipBoardTile(int row, int col)
    {
        super();
        this.row = row;
        this.col = col;
    }

    public int getRow() {return row;}

    public int getCol() {return col;}
}
