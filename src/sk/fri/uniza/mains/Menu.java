package sk.fri.uniza.mains;

import sk.fri.uniza.hudobneNastroje.Gitara;
import sk.fri.uniza.hudobneNastroje.Piano;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Úvodná obrazovka - zobrazí nástroje možné na výber a nastavenia
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Menu {
    private final JFrame okno;

    /**
     * Vytvorí celý frame kde po kliknutí na príslušné tlačidlá sa spúšťa aplikácia
     */
    public Menu() {
        this.okno = new JFrame("Stupnice");
        this.okno.setLayout(null);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JPanel centerWestPanel = new JPanel(new BorderLayout());
        JPanel centerEastPanel = new JPanel(new BorderLayout());


        try {
            String nadpis = "<html>" + "<h1 style='margin-top:20; margin-bottom:30'>Vyberte si jeden z nástrojov</h1>" + "</html>";

            JLabel nadpisLabel = new JLabel(nadpis, SwingConstants.CENTER);
            nadpisLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            northPanel.add(nadpisLabel, BorderLayout.CENTER);

            JLabel settingsLabel = null;
            JLabel gitaraLabel = null;
            JLabel pianoLabel = null;
            try {
                var settingUrl = this.getClass().getResourceAsStream("/img/settings.png");
                BufferedImage settingsImg = ImageIO.read(settingUrl);
                settingsLabel = new JLabel(new ImageIcon(settingsImg));


                var gitaraUrl = this.getClass().getResourceAsStream("/img/sunburst_strat1.png");
                BufferedImage gitaraImg = ImageIO.read(gitaraUrl);
                gitaraLabel = new JLabel(new ImageIcon(gitaraImg));

                var pianoUrl = this.getClass().getResourceAsStream("/img/brown_piano.png");
                BufferedImage pianoImg = ImageIO.read(pianoUrl);
                pianoLabel = new JLabel(new ImageIcon(pianoImg));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            northPanel.add(settingsLabel, BorderLayout.EAST);

            mainPanel.add(northPanel, BorderLayout.NORTH);


            Border border = new EmptyBorder(0, 30, 0, 30);
            gitaraLabel.setBorder(border);
            pianoLabel.setBorder(border);
            centerWestPanel.add(gitaraLabel, BorderLayout.CENTER);
            centerEastPanel.add(pianoLabel, BorderLayout.CENTER);

            String gitaraNapis = "<html><p style='margin-top:20'>Gitara</p></html>";
            String pianoNapis = "<html><p style='margin-top:20'>Piano</p></html>";
            JLabel gitaraNapisLabel = new JLabel(gitaraNapis, SwingConstants.CENTER);
            JLabel pianoNapisLabel = new JLabel(pianoNapis, SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.ITALIC, 25);
            gitaraNapisLabel.setFont(font);
            pianoNapisLabel.setFont(font);
            centerWestPanel.add(gitaraNapisLabel, BorderLayout.NORTH);
            centerEastPanel.add(pianoNapisLabel, BorderLayout.NORTH);

            LineBorder lineBorder = new LineBorder(Color.BLACK, 2, true);
            centerEastPanel.setBorder(lineBorder);
            centerWestPanel.setBorder(lineBorder);

            Color color = new Color(234, 255, 255);
            centerWestPanel.setBackground(color);
            centerEastPanel.setBackground(color);
            centerPanel.add(centerWestPanel);
            centerPanel.add(centerEastPanel);

            centerWestPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Gitara gitara = new Gitara();
                    Menu.this.okno.dispose();
                }
            });

            centerEastPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Piano piano = new Piano();
                    Menu.this.okno.dispose();
                }
            });

            settingsLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Settings settings = new Settings();
                    Menu.this.okno.dispose();
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        mainPanel.add(centerPanel);

        this.okno.setContentPane(mainPanel);

        this.okno.pack();
        this.okno.setResizable(false);
        this.okno.setVisible(true);

        this.okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }
}
