import java.util.Random;

public class BattleshipGameLogic
{
    final int ROWS = 10;
    final int COLS = 10;
    final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};

    final String BLANK = " ";
    final String MISS = "M";
    final String HIT = "X";
    final String SHIP = "S";

    int miss, strike, totalMiss, totalHit;

    String[][] playerBoard;
    String[][] secretBoard;

    public BattleshipGameLogic()
    {
        playerBoard = new String[ROWS][COLS];
        secretBoard = new String[ROWS][COLS];
        resetGame();
    }

    public void resetGame()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                playerBoard[i][j] = BLANK;
                secretBoard[i][j] = BLANK;
            }
        }
    }

    public void pla
}
