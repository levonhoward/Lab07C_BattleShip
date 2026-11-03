import java.util.ArrayList;
import java.util.Random;

public class BattleshipGameLogic
{
    final int ROWS = 10;
    final int COLS = 10;
    final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};

    final String BLANK = " ";
    final String MISS = "M";
    final String HIT = "X";

    Random rnd;

    int miss, strike, totalMiss, totalHit;

    String[][] playerBoard;

    ArrayList<BattleshipShip> ships;
    BattleshipShip[][] shipBoard;

    public BattleshipGameLogic()
    {
        playerBoard = new String[ROWS][COLS];
        rnd = new Random();
        ships = new ArrayList<>();
        shipBoard = new BattleshipShip[ROWS][COLS];
        resetGame();
    }

    public void resetGame()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                playerBoard[i][j] = BLANK;
            }
        }

        miss = 0;
        strike = 0;
        totalMiss = 0;
        totalHit = 0;

        placeShips();
    }

    /**
     * Clears the board, places ships randomly on the grid
     */
    private void placeShips()
    {
        // Clear array list
        ships.clear();

        // Clear array
        for (int i = 0; i < shipBoard.length; i++)
        {
            for (int j = 0; j < shipBoard.length; j++)
            {
                shipBoard[i][j] = null;
            }
        }

        for (int shipLength : SHIP_LENGTHS)
        {
            boolean shipPlaced = false;
            do
            {
                int row = rnd.nextInt(ROWS);
                int col = rnd.nextInt(COLS);
                boolean isHorizontal = rnd.nextBoolean();

                if (canPlaceShip(row, col, shipLength, isHorizontal))
                {
                    ArrayList<int[]> tiles = new ArrayList<>();
                    for (int i = 0; i < shipLength; i++)
                    {
                        if (isHorizontal)
                        {
                            tiles.add(new int[] {row, col + i});
                        }
                        else
                        {
                            tiles.add(new int[] {row + i, col});
                        }
                    }

                    BattleshipShip ship = new BattleshipShip(tiles);
                    ships.add(ship);

                    for (int[] tile : tiles)
                    {
                        shipBoard[tile[0]][tile[1]] = ship;
                    }

                    shipPlaced = true;
                }
            } while (!shipPlaced);
        }
    }

    /**
     * Checks if a ship is allowed to be placed at the specified location.
     * A ship can be placed if it doesn't overlap with another ship and it doesn't go off the board.
     * @param row - The starting row for the ship's first tile
     * @param col - The starting column for the ship's first tile
     * @param length - The ship's total length
     * @param isHorizontal - The ship's orientation
     * @return true if the placement follows the rules.
     */
    private boolean canPlaceShip(int row, int col, int length, boolean isHorizontal)
    {
        boolean canPlaceShip = true;

        if (isHorizontal && (col + length) <= ROWS)
        {
            for (int i = col; i < length; i++) {
                if (shipBoard[row][i] != null)
                {
                    canPlaceShip = false;
                }
            }
        }
        else if (!isHorizontal && (row + length) <= COLS)
        {
            for (int i = row; i < length; i++) {
                if (shipBoard[i][col] != null)
                {
                    canPlaceShip = false;
                }
            }
        }
        else
        {
            canPlaceShip = false;
        }

        return canPlaceShip;
    }

    public String getCell(int row, int col)
    {
        return playerBoard[row][col];
    }
}
