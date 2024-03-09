package sk.fri.uniza.hudobneNastroje;

import sk.fri.uniza.stupnice.Stupnica;
import sk.fri.uniza.stupnice.Ton;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

/**
 * Trieda ktorá zobrazuje piano a stupnice na ňom
 *
 * @author (Pavol Žigo)
 * @version (1.0)
 */
public class Piano extends HudobnyNastroj {
    private String[] pocetKlavesov;
    private JComboBox<String> klavesyVyber;

    /**
     * Zobrazuje frame s pianom
     */
    public Piano() {
        this.setOkno(new JFrame("Piano"));
        JPanel mainPanel = this.nastavMainPanel();
        this.nastavSouthCenterPanel(this.getSouthPanel(), this.getCenterPanel(), Integer.parseInt(this.getPocetKlavesov()), null);
        this.getOkno().setContentPane(mainPanel);

        this.klavesyVyber.addActionListener(e -> {
            this.pocetKlavesov[0] = (String)this.klavesyVyber.getSelectedItem();
            this.getCenterPanel().removeAll();
            Piano.this.nastavObrazok(this.getCenterPanel(), this.pocetKlavesov[0], null);
            if (Piano.this.isJeNakreslene()) {
                Ton zaciatocnyTon1 = (Ton)this.getZaciatocnyTonVyber().getSelectedItem();
                Piano.this.nakresli(this.getCenterPanel(), Piano.this.isArpeggio(), zaciatocnyTon1,
                        this.getColorFirstNote().toLowerCase(), this.getColorOtherNotes().toLowerCase());
            }
            this.getOkno().pack();
            this.getOkno().revalidate();
        });

        this.getOkno().pack();
        this.getOkno().setResizable(false);
        this.getOkno().setVisible(true);

        this.getOkno().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    /**
     * Na južný panel nastaví ComboBox na výber počtu klávesov
     * @param southPanel južný panel - tam sú ComboBoxy na výber
     * @param centerPanel center panel - panel kde sa nastaví obrázok - metóda(nastavObrazok)
     * @param pocetKlavesov počet načítaný zo súboru - nastaví sa do ComboBoxu ako práve vybratý
     */
    @Override
    public void nastavSouthCenterPanel(JPanel southPanel, JPanel centerPanel, int pocetKlavesov, String farba) {
        JPanel southCenterPanel = new JPanel(new BorderLayout());
        southCenterPanel.add(new JLabel("Počet klávesov:  "), BorderLayout.WEST);
        String[] klavesy = new String[]{"12", "24", "36", "48"};
        this.klavesyVyber = new JComboBox<>(klavesy);
        for (String s : klavesy) {
            if (s.equals(String.valueOf(pocetKlavesov))) {
                this.klavesyVyber.setSelectedItem(String.valueOf(pocetKlavesov));
            }
        }
        southCenterPanel.add(this.klavesyVyber, BorderLayout.CENTER);
        southPanel.add(southCenterPanel, BorderLayout.CENTER);
        this.pocetKlavesov = new String[]{(String)this.klavesyVyber.getSelectedItem()};
        this.nastavObrazok(centerPanel, String.valueOf(pocetKlavesov), null);
    }

    /**
     * Zobrazí obrázok piana
     * @param panel na ňom sa obrázok zobrazí
     * @param pocetKlavesov podľa neho sa vyberie príslušný obrázok
     * @param farba null
     */
    @Override
    public void nastavObrazok(JPanel panel, String pocetKlavesov, String farba) {
        try {
            Border margin = new EmptyBorder(30, 10, 30, 10);
            var imageUrl = this.getClass().getResourceAsStream("/img/piano" + pocetKlavesov + ".png");
            BufferedImage klavir = ImageIO.read(imageUrl);
            JLabel klavirLabel = new JLabel(new ImageIcon(klavir));

            Border border = klavirLabel.getBorder();
            klavirLabel.setBorder(new CompoundBorder(border, margin));
            klavirLabel.setLayout(new OverlayLayout(klavirLabel));
            panel.add(klavirLabel);
        } catch (Exception io) {
            System.out.println(io.getMessage());
        }
    }

    /**
     * Vytvorí a zobrazí stupnicu na piane
     * @param colorFirstNote farba začiatočného tónu
     * @param colorOtherNotes farba ostatných tónov
     * @param vybratyPocetKlavesov pre výber obrázku
     */
    @Override
    public void generuj(String colorFirstNote, String colorOtherNotes, int vybratyPocetKlavesov) {
        if (this.getTypVyber().getSelectedItem().equals("Stupnica")) {
            this.setArpeggio(false);
            if (this.getScaleModVyber().getSelectedItem() == null) {
                this.setGenerator(this.getScaleModVyber().getItemAt(0));
            } else {
                this.setGenerator((Stupnica)this.getScaleModVyber().getSelectedItem());
            }
        } else {
            this.setArpeggio(true);
            if (this.getArpModVyber().getSelectedItem() == null) {
                this.setGenerator(this.getArpModVyber().getItemAt(0));
            } else {
                this.setGenerator((Stupnica)this.getArpModVyber().getSelectedItem());
            }
        }
        Ton zaciatocnyTon1 = (Ton)this.getZaciatocnyTonVyber().getSelectedItem();

        this.getCenterPanel().remove(0);
        this.nastavObrazok(this.getCenterPanel(), this.pocetKlavesov[0], null);

        this.nakresli(this.getCenterPanel(), this.isArpeggio(), zaciatocnyTon1, colorFirstNote.toLowerCase(), colorOtherNotes.toLowerCase());
        this.setJeNakreslene(true);
        this.getOkno().pack();
        this.getOkno().revalidate();
    }

    private void showNote(JPanel panel, Ton nota, String farba, int[] klavesy, String farbaKlavesu) {
        int x;
        int y;
        if (farbaKlavesu.equals("White")) {
            x = -45;
            y = 230;
        } else {
            x = -21;
            y = 120;
        }

        for (int klaves : klavesy) {
            var fLabel = this.setLabel(nota.toString(), farba);
            if (fLabel == null) {
                return;
            }
            fLabel.setBounds(x + (53 * klaves), y, 50, 35);
            // rozostupy x = 53; y =
            fLabel.repaint();
            var klavirLabel = (JLabel)panel.getComponent(0);
            klavirLabel.setLayout(null);
            klavirLabel.add(fLabel);
            panel.add(klavirLabel);
        }
    }

    /**
     * Zobrazí jeden tón vo všetkých možných oktávach na piane
     * @param panel naň sa zobrazí
     * @param nota názov tónu
     * @param farba farba obrázku tónu
     */
    @Override
    public void showNote(JPanel panel, Ton nota, String farba) {

        switch (nota) {
            case A -> {
                int[] klavesy = new int[]{6, 13, 20, 27};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case A_SHARP -> {
                int[] klavesy = new int[]{6, 13, 20, 27};
                this.showNote(panel, nota, farba, klavesy, "Black");
            }
            case B -> {
                int[] klavesy = new int[]{7, 14, 21, 28};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case C -> {
                int[] klavesy = new int[]{1, 8, 15, 22};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case C_SHARP -> {
                int[] klavesy = new int[]{1, 8, 15, 22};
                this.showNote(panel, nota, farba, klavesy, "Black");
            }
            case D -> {
                int[] klavesy = new int[]{2, 9, 16, 23};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case D_SHARP -> {
                int[] klavesy = new int[]{2, 9, 16, 23};
                this.showNote(panel, nota, farba, klavesy, "Black");
            }
            case E -> {
                int[] klavesy = new int[]{3, 10, 17, 24};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case F -> {
                int[] klavesy = new int[]{4, 11, 18, 25};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case F_SHARP -> {
                int[] klavesy = new int[]{4, 11, 18, 25};
                this.showNote(panel, nota, farba, klavesy, "Black");
            }
            case G -> {
                int[] klavesy = new int[]{5, 12, 19, 26};
                this.showNote(panel, nota, farba, klavesy, "White");
            }
            case G_SHARP -> {
                int[] klavesy = new int[]{5, 12, 19, 26};
                this.showNote(panel, nota, farba, klavesy, "Black");
            }
        }
    }
}
