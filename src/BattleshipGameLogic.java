import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BattleshipGameLogic
{
    final int ROWS = 10;
    final int COLS = 10;
    final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};
    final int TOTAL_SHIP_LENGTHS = 17;

    final String BLANK = " ";
    final String MISS = "M";
    final String HIT = "X";

    final String RESULT_MISS = "miss";
    final String RESULT_HIT = "hit";
    final String RESULT_SUNK = "sunk";
    final String RESULT_STRIKE = "strike";
    final String RESULT_WIN = "win";
    final String RESULT_LOSE = "lose";

    Random rnd;

    int miss, strike, totalMiss, totalHit;

    String[][] playerBoard;
    BattleshipShip[][] shipBoard;
    ArrayList<BattleshipShip> ships;

    public BattleshipGameLogic() throws IOException, FontFormatException {
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
                    // Store array of coordinates for the ship
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

                    // Build ship and add it to the array
                    BattleshipShip ship = new BattleshipShip(tiles);
                    ships.add(ship);

                    // Place each tile of the ship on the secret board
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

        if (isHorizontal && (col + length) <= COLS)
        {
            for (int i = col; i < (col + length); i++)
            {
                if (shipBoard[row][i] != null)
                {
                    canPlaceShip = false;
                }
            }
        }
        else if (!isHorizontal && (row + length) <= ROWS)
        {
            for (int i = row; i < (row + length); i++)
            {
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

    /**
     * Returns the results of fire attempt
     * @param row - row on the board to target
     * @param col - column on the board to target
     * @return the result of the move
     */
    public String fire (int row, int col)
    {
        String result;

        if (shipBoard[row][col] != null)
        {
            result = RESULT_HIT;
            totalHit++;
            miss = 0;

            playerBoard[row][col] = HIT;

            shipBoard[row][col].hitShip();
            if (shipBoard[row][col].isSunk())
            {
                result = RESULT_SUNK;

                if (totalHit >= TOTAL_SHIP_LENGTHS)
                {
                    result = RESULT_WIN;
                }
            }
        }
        else
        {
            result = RESULT_MISS;
            totalMiss++;
            miss++;

            playerBoard[row][col] = MISS;

            if (miss >= 5)
            {
                result = RESULT_STRIKE;
                miss = 0;
                strike++;

                if (strike >= 3)
                {
                    result = RESULT_LOSE;
                }
            }
        }

        return result;
    }

    public String getCell(int row, int col)
    {
        return playerBoard[row][col];
    }

    public boolean isEmpty(int row, int col)
    {
        return playerBoard[row][col].equals(BLANK);
    }

    public int getTotalMiss() {return totalMiss;}

    public int getTotalHit() {return totalHit;}

    public int getMiss() {return miss;}

    public int getStrike() {return strike;}
}