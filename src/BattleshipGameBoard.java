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
    BattleshipGUI gui;

    BattleshipGameBoard(BattleshipGameLogic game, BattleshipGUI gui) throws IOException, FontFormatException
    {
        super();
        board = new BattleshipBoardTile[ROWS][COLS];
        this.game = game;
        this.gui = gui;

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
            String result = game.fire(row, col);
            refreshBoard();
            gui.updateStatusPanel();
            resultResponse(result);
        }
    }

    private void refreshBoard()
    {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                String status = game.getCell(i, j);
                BattleshipBoardTile tile = board[i][j];

                tile.setText(status);

                if (status.equals("M"))
                {
                    tile.setForeground(Color.decode("#fff000"));
                }
                else if (status.equals("X"))
                {
                    tile.setForeground(Color.decode("#ff0000"));
                }
                else
                {
                    tile.setForeground(Color.decode("#0000ff"));
                }
            }
        }
    }

    private void resultResponse(String status)
    {
        int playAgainChoice = -2;

        if (status.equals("sunk"))
        {
            JOptionPane.showMessageDialog(this, "You sunk a ship!", "Sunk!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (status.equals("strike"))
        {
            JOptionPane.showMessageDialog(this, "You missed again! That's a strike!", "Strike!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (status.equals("win"))
        {
            playAgainChoice = JOptionPane.showConfirmDialog(this, "You won! Play again?", "Winner!", JOptionPane.YES_NO_OPTION);
        }
        else if (status.equals("lose"))
        {
            playAgainChoice = JOptionPane.showConfirmDialog(this, "You lost! Play again?", "Game Over!", JOptionPane.YES_NO_OPTION);
        }

        if (playAgainChoice == JOptionPane.YES_OPTION)
        {
            game.resetGame();
            gui.updateStatusPanel();
            refreshBoard();
        }
        else if (playAgainChoice == JOptionPane.NO_OPTION || playAgainChoice == JOptionPane.CLOSED_OPTION)
        {
            System.exit(0);
        }
    }
}
