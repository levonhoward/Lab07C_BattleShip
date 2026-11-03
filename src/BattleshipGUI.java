import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleshipGUI extends JFrame
{
    JPanel mainPnl;
    JPanel headerPnl;
    JPanel boardPnl;
    JPanel statusPnl;
    JPanel quitPnl;

    JLabel titleLbl;

    JLabel missTitleLbl, missNumberLbl;
    JLabel strikeTitleLbl, strikeNumberLbl;
    JLabel totalMissTitleLbl, totalMissNumberLbl;
    JLabel totalHitTitleLbl, totalHitNumberLbl;
    ArrayList<JLabel> statuses;

    JButton quitBtn;

    BattleshipGameLogic game;

    BattleshipGUI() throws IOException, FontFormatException
    {
        // Main panel
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(4, 1));

        // Header panel
        buildHeaderPanel();
        mainPnl.add(headerPnl);

        // Board
        game = new BattleshipGameLogic();
        boardPnl = new BattleshipGameBoard(game);
        mainPnl.add(boardPnl);

        // Status
        statuses = new ArrayList<>();
        buildStatusPanel();
        mainPnl.add(statusPnl);

        // Quit
        buildQuitPanel();
        mainPnl.add(quitPnl);

        // Finalize Main Panel
        add(mainPnl);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize((screenSize.width / 4) * 3, (screenSize.height / 4) * 3);
        setLocation(screenSize.width / 8, screenSize.height / 8);
        setTitle("Battleship Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buildHeaderPanel() throws IOException, FontFormatException
    {
        headerPnl = new JPanel();
        titleLbl = new JLabel("Battleship", JLabel.CENTER);
        File headerFontFile = new File("src/fonts/KiwiSoda.ttf");
        Font headerFont = Font.createFont(Font.TRUETYPE_FONT, headerFontFile).deriveFont(Font.PLAIN, 72f);
        titleLbl.setFont(headerFont);
        titleLbl.setForeground(Color.decode("#ff0000"));
        headerPnl.add(titleLbl);
    }

    private void buildStatusPanel() throws IOException, FontFormatException
    {
        statusPnl = new JPanel();
        statusPnl.setLayout(new GridLayout(2, 4));

        File statusFontFile = new File("src/fonts/KiwiSoda.ttf");
        Font statusFont = Font.createFont(Font.TRUETYPE_FONT, statusFontFile).deriveFont(Font.PLAIN, 36f);

        missTitleLbl = new JLabel("MISS");
        statuses.add(missTitleLbl);
        strikeTitleLbl = new JLabel("STRIKE");
        statuses.add(strikeTitleLbl);
        totalMissTitleLbl = new JLabel("TOTAL MISS");
        statuses.add(totalMissTitleLbl);
        totalHitTitleLbl = new JLabel("TOTAL HIT");
        statuses.add(totalHitTitleLbl);
        missNumberLbl = new JLabel("0");
        statuses.add(missNumberLbl);
        strikeNumberLbl = new JLabel("0");
        statuses.add(strikeNumberLbl);
        totalMissNumberLbl = new JLabel("0");
        statuses.add(totalMissNumberLbl);
        totalHitNumberLbl = new JLabel("0");
        statuses.add(totalHitNumberLbl);

        for (JLabel status : statuses)
        {
            status.setFont(statusFont);
            status.setForeground(Color.decode("#0000ff"));
            statusPnl.add(status);
        }
    }

    private void buildQuitPanel() throws IOException, FontFormatException
    {
        quitPnl = new JPanel();
        quitBtn = new JButton("Quit");
        File quitFontFile = new File("src/fonts/KiwiSoda.ttf");
        Font quitFont = Font.createFont(Font.TRUETYPE_FONT, quitFontFile).deriveFont(Font.PLAIN, 36f);
        quitBtn.setFont(quitFont);
        quitBtn.addActionListener((ActionEvent ae) ->
        {
            int userChoice = JOptionPane.showConfirmDialog(mainPnl, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
        quitPnl.add(quitBtn);
    }
}
