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
import java.util.HashMap;

/**
 * Trieda ktorá zobrazuje hmatník gitary a stupnice na nej
 *
 * @author (Pavol Žigo)
 * @version (1.0)
 */
public class Gitara extends HudobnyNastroj {
    private String pocetPrazcov;
    private JComboBox<String> prazceVyber;
    private JComboBox<String> farbaVyber;
    private HashMap<String, String> prekladFarieb;
    private boolean isSelected;


    /**
     * Zobrazuje frame s gitarou
     */
    public Gitara() {

        this.setOkno(new JFrame("Gitara"));
        this.isSelected = false;

        JPanel mainPanel = this.nastavMainPanel();
        this.nastavSouthCenterPanel(this.getSouthPanel(), this.getCenterPanel(), Integer.parseInt(this.getPocetPrazcov()), "white");

        this.prazceVyber.addActionListener(e -> this.isSelected = this.farbaAleboPrazceVyber(this.getColorFirstNote(), this.getColorOtherNotes()));

        this.farbaVyber.addActionListener(e -> this.isSelected = this.farbaAleboPrazceVyber(this.getColorFirstNote(), this.getColorOtherNotes()));



        this.getOkno().setContentPane(mainPanel);

        this.getOkno().pack();
        this.getOkno().setResizable(false);
        this.getOkno().setVisible(true);

        this.getOkno().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * Na južný panel nastaví ComboBoxy na výber farby gitary a počtu pražcov
     * @param southPanel južný panel - tam sú ComboBoxy na výber
     * @param centerPanel center panel - panel kde sa nastaví obrázok - metóda(nastavObrazok)
     * @param pocetPrazcov počet načítaný zo súboru - nastaví sa do ComboBoxu ako práve vybratý
     */
    @Override
    public void nastavSouthCenterPanel(JPanel southPanel, JPanel centerPanel, int pocetPrazcov, String farba) {
        JPanel southCenterPanel = new JPanel(new BorderLayout());
        JPanel southCenterWestPanel = new JPanel(new BorderLayout());
        JPanel southCenterEastPanel = new JPanel(new BorderLayout());
        String[] prazce = new String[]{"12", "20", "21", "22", "24"};
        this.prazceVyber = new JComboBox<>(prazce);
        for (String s : prazce) {
            if (s.equals(String.valueOf(pocetPrazcov))) {
                this.prazceVyber.setSelectedItem(String.valueOf(pocetPrazcov).toLowerCase());
            }
        }
        southCenterEastPanel.add(new JLabel("Počet pražcov:  "), BorderLayout.WEST);
        southCenterEastPanel.add(this.prazceVyber, BorderLayout.CENTER);
        southCenterPanel.add(southCenterEastPanel, BorderLayout.EAST);

        this.prekladFarieb = new HashMap<>();
        this.prekladFarieb.put("Biela", "white");
        this.prekladFarieb.put("Hnedá", "brown");

        this.farbaVyber = new JComboBox<>(this.prekladFarieb.keySet().toArray(new String[0]));

        southCenterWestPanel.add(new JLabel("Farba hmatníku:  "), BorderLayout.WEST);
        southCenterWestPanel.add(this.farbaVyber, BorderLayout.CENTER);
        southCenterPanel.add(southCenterWestPanel, BorderLayout.WEST);

        southPanel.add(southCenterPanel, BorderLayout.CENTER);
        this.pocetPrazcov = (String)this.prazceVyber.getSelectedItem();
//        var farbaHmatniku = (String)this.farbaVyber.getSelectedItem();
        this.nastavObrazok(centerPanel, String.valueOf(pocetPrazcov), farba);
    }

    /**
     * Zobrazí obrázok hmatníku gitary
     * @param panel na ňom sa obrázok zobrazí
     * @param pocetPrazcov podľa neho sa vyberie príslušný obrázok
     * @param farba hmatník
     */
    @Override
    public void nastavObrazok(JPanel panel, String pocetPrazcov, String farba) {
        try {
            Border margin = new EmptyBorder(20, 0, 20, 10);
            var imageUrl = this.getClass().getResourceAsStream("/img/fretboard" + pocetPrazcov + farba.toLowerCase() + "head.png");
            BufferedImage hmatnik = ImageIO.read(imageUrl);
            JLabel hmatnikLabel = new JLabel(new ImageIcon(hmatnik));
            Border border = hmatnikLabel.getBorder();

            hmatnikLabel.setBorder(new CompoundBorder(border, margin));
            hmatnikLabel.setLayout(new OverlayLayout(hmatnikLabel));
            panel.add(hmatnikLabel);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Vytvorí a zobrazí stupnicu na gitare
     * @param colorFirstNote farba začiatočného tónu
     * @param colorOtherNotes farba ostatných tónov
     * @param vybratyPocetPrazcov pre výber obrázku
     */
    @Override
    public void generuj(String colorFirstNote, String colorOtherNotes, int vybratyPocetPrazcov) {
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

        String vybrataFarba = this.prekladFarieb.get((String)this.farbaVyber.getSelectedItem());
        String vybratePrazce;
        if (this.isSelected) {
            vybratePrazce = this.pocetPrazcov;
        } else {
            vybratePrazce = String.valueOf(vybratyPocetPrazcov);
        }
        this.nastavObrazok(this.getCenterPanel(), vybratePrazce, vybrataFarba);

        this.nakresli(this.getCenterPanel(), this.isArpeggio(), zaciatocnyTon1, colorFirstNote.toLowerCase(), colorOtherNotes.toLowerCase());
        this.setJeNakreslene(true);
        this.getOkno().pack();
        this.getOkno().revalidate();
    }

    private void showNote(JPanel panel, String nota, String farba, int prazec, int struna) {
        var fLabel = this.setLabel(nota, farba);
        if (fLabel == null) {
            return;
        }
        fLabel.setBounds(-14 + (prazec * 44), 185 - (struna * 30), 50, 35);
        // rozostupy x = 44; y = 30
        fLabel.repaint();
        var hmatnikLabel = (JLabel)panel.getComponent(0);
        hmatnikLabel.setLayout(null);
        hmatnikLabel.add(fLabel);
        panel.add(hmatnikLabel);
    }

    private boolean farbaAleboPrazceVyber(String colorFirstNote, String colorOtherNotes) {
        this.pocetPrazcov = (String)this.prazceVyber.getSelectedItem();
        this.getCenterPanel().removeAll();
        String selectedItem = (String)this.farbaVyber.getSelectedItem();
        this.nastavObrazok(this.getCenterPanel(), this.pocetPrazcov, this.prekladFarieb.get(selectedItem));
        if (this.isJeNakreslene()) {
            Ton zaciatocnyTon1 = (Ton)this.getZaciatocnyTonVyber().getSelectedItem();
            this.nakresli(this.getCenterPanel(), this.isArpeggio(), zaciatocnyTon1, colorFirstNote.toLowerCase(), colorOtherNotes.toLowerCase());
        }
        this.getOkno().pack();
        this.getOkno().revalidate();
        return true;
    }


    /**
     * Zobrazí tón na všetkých miestach na gitare
     * @param panel naň sa zobrazí
     * @param nota názov tónu
     * @param farba farba obrázku tónu
     */
    @Override
    public void showNote(JPanel panel, Ton nota, String farba) {
        switch (nota) {
            case A -> {
                this.showNote(panel, nota.toString(), farba, 0, 2);
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 2, 4);
                    this.showNote(panel, nota.toString(), farba, i + 5, 6);
                    this.showNote(panel, nota.toString(), farba, i + 5, 1);
                    this.showNote(panel, nota.toString(), farba, i + 7, 3);
                    this.showNote(panel, nota.toString(), farba, i + 10, 5);
                    this.showNote(panel, nota.toString(), farba, i + 12, 2);
                    i += 12;
                }
            }
            case A_SHARP -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 1, 2);
                    this.showNote(panel, nota.toString(), farba, i + 3, 4);
                    this.showNote(panel, nota.toString(), farba, i + 6, 6);
                    this.showNote(panel, nota.toString(), farba, i + 6, 1);
                    this.showNote(panel, nota.toString(), farba, i + 8, 3);
                    this.showNote(panel, nota.toString(), farba, i + 11, 5);
                    i += 12;
                }
            }
            case B -> {
                this.showNote(panel, nota.toString(), farba, 0, 5);
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 2, 2);
                    this.showNote(panel, nota.toString(), farba, i + 4, 4);
                    this.showNote(panel, nota.toString(), farba, i + 7, 6);
                    this.showNote(panel, nota.toString(), farba, i + 7, 1);
                    this.showNote(panel, nota.toString(), farba, i + 9, 3);
                    this.showNote(panel, nota.toString(), farba, i + 12, 5);
                    i += 12;
                }
            }
            case C -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 1, 5);
                    this.showNote(panel, nota.toString(), farba, i + 3, 2);
                    this.showNote(panel, nota.toString(), farba, i + 5, 4);
                    this.showNote(panel, nota.toString(), farba, i + 8, 6);
                    this.showNote(panel, nota.toString(), farba, i + 8, 1);
                    this.showNote(panel, nota.toString(), farba, i + 10, 3);
                    i += 12;
                }
            }
            case C_SHARP -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 2, 5);
                    this.showNote(panel, nota.toString(), farba, i + 4, 2);
                    this.showNote(panel, nota.toString(), farba, i + 6, 4);
                    this.showNote(panel, nota.toString(), farba, i + 9, 6);
                    this.showNote(panel, nota.toString(), farba, i + 9, 1);
                    this.showNote(panel, nota.toString(), farba, i + 11, 3);
                    i += 12;
                }
            }
            case D -> {
                this.showNote(panel, nota.toString(), farba, 0, 3);
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 3, 5);
                    this.showNote(panel, nota.toString(), farba, i + 5, 2);
                    this.showNote(panel, nota.toString(), farba, i + 7, 4);
                    this.showNote(panel, nota.toString(), farba, i + 10, 6);
                    this.showNote(panel, nota.toString(), farba, i + 10, 1);
                    this.showNote(panel, nota.toString(), farba, i + 12, 3);
                    i += 12;
                }
            }
            case D_SHARP -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 1, 3);
                    this.showNote(panel, nota.toString(), farba, i + 4, 5);
                    this.showNote(panel, nota.toString(), farba, i + 6, 2);
                    this.showNote(panel, nota.toString(), farba, i + 8, 4);
                    this.showNote(panel, nota.toString(), farba, i + 11, 6);
                    this.showNote(panel, nota.toString(), farba, i + 11, 1);
                    i += 12;
                }
            }
            case E -> {
                this.showNote(panel, nota.toString(), farba, 0, 6);
                this.showNote(panel, nota.toString(), farba, 0, 1);
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 2, 3);
                    this.showNote(panel, nota.toString(), farba, i + 5, 5);
                    this.showNote(panel, nota.toString(), farba, i + 7, 2);
                    this.showNote(panel, nota.toString(), farba, i + 9, 4);
                    this.showNote(panel, nota.toString(), farba, i + 12, 6);
                    this.showNote(panel, nota.toString(), farba, i + 12, 1);
                    i += 12;
                }
            }
            case F -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 1, 6);
                    this.showNote(panel, nota.toString(), farba, i + 1, 1);
                    this.showNote(panel, nota.toString(), farba, i + 3, 3);
                    this.showNote(panel, nota.toString(), farba, i + 6, 5);
                    this.showNote(panel, nota.toString(), farba, i + 8, 2);
                    this.showNote(panel, nota.toString(), farba, i + 10, 4);
                    i += 12;
                }
            }
            case F_SHARP -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 2, 6);
                    this.showNote(panel, nota.toString(), farba, i + 2, 1);
                    this.showNote(panel, nota.toString(), farba, i + 4, 3);
                    this.showNote(panel, nota.toString(), farba, i + 7, 5);
                    this.showNote(panel, nota.toString(), farba, i + 9, 2);
                    this.showNote(panel, nota.toString(), farba, i + 11, 4);
                    i += 12;
                }
            }
            case G -> {
                this.showNote(panel, nota.toString(), farba, 0, 4);
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 3, 6);
                    this.showNote(panel, nota.toString(), farba, i + 3, 1);
                    this.showNote(panel, nota.toString(), farba, i + 5, 3);
                    this.showNote(panel, nota.toString(), farba, i + 8, 5);
                    this.showNote(panel, nota.toString(), farba, i + 10, 2);
                    this.showNote(panel, nota.toString(), farba, i + 12, 4);
                    i += 12;
                }
            }
            case G_SHARP -> {
                int i = 0;
                while (i <= 12) {
                    this.showNote(panel, nota.toString(), farba, i + 1, 4);
                    this.showNote(panel, nota.toString(), farba, i + 4, 6);
                    this.showNote(panel, nota.toString(), farba, i + 4, 1);
                    this.showNote(panel, nota.toString(), farba, i + 6, 3);
                    this.showNote(panel, nota.toString(), farba, i + 9, 5);
                    this.showNote(panel, nota.toString(), farba, i + 11, 2);
                    i += 12;
                }
            }
        }
    }

}
