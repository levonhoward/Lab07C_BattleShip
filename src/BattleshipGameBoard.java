import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BattleshipGameBoard extends JPanel
{
    final int ROWS = 10;
    final int COLS = 10;

    BattleshipBoardTile[][] board;
    BattleshipGameLogic game;

    BattleshipGameBoard(BattleshipGameLogic game) throws IOException, FontFormatException
    {
        super();
        this.game = game;
        board = new BattleshipBoardTile[ROWS][COLS];

        // Aesthetics
        File boardFontFile = new File("src/fonts/KiwiSoda.ttf");
        Font boardFont = Font.createFont(Font.TRUETYPE_FONT, boardFontFile).deriveFont(Font.PLAIN, 36f);
        setLayout(new GridLayout(ROWS, COLS));

        // Build buttons
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                final int row = i;
                final int col = j;

                BattleshipBoardTile button = new BattleshipBoardTile(row, col);
                button.setFont(boardFont);
                button.setForeground(Color.decode("#0000ff"));
                button.addActionListener((ActionEvent ae) -> handleButtonClick(button));
                board[row][col] = button;
                add(button);
            }
        }

        placeShips();
    }

    private void handleButtonClick(BattleshipBoardTile button)
    {
        int row = button.getRow();
        int col = button.getCol();

        if(!game.isEmpty(row, col))
        {
            JOptionPane.showMessageDialog(this, "Please select an empty square.", "Occupied square!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            // BUTTON LOGIC HERE //
        }
    }

    public void printBoard()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private void clearBoard()
    {
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                board[i][j].setText(" ");
            }
        }
    }
}
