package sk.fri.uniza.hudobneNastroje;

import sk.fri.uniza.mains.Menu;
import sk.fri.uniza.stupnice.Stupnica;
import sk.fri.uniza.stupnice.Ton;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Trieda ktorá zobrazuje frame potomkov(gitara, piano).
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public abstract class HudobnyNastroj {
    private JFrame okno;
    private boolean isArpeggio;
    private Stupnica generator;
    private boolean jeNakreslene;
    private JComboBox<String> typVyber;
    private JComboBox<Stupnica> scaleModVyber;
    private JComboBox<Stupnica> arpModVyber;
    private JComboBox<Ton>  zaciatocnyTonVyber;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JPanel northCenterPanel;
    private String colorFirstNote;
    private String colorOtherNotes;
    private String pocetKlavesov;
    private String pocetPrazcov;


    /**
     * Konštruktor, v ktorom načítavam zo súboru nastavenia pre zobrazenie obrázkov(farba nôt, počet pražcov/klávesov)
     * V prípade prázdneho súboru nastavím default nastavenie a zapíšem ho do súboru
     */
    public HudobnyNastroj() {

        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Specify the path to the configuration directory within the home directory
        String configDirPath = userHome + File.separator + ".guitarScalesSettings";
        // Create the configuration directory if it doesn't exist
        File configDir = new File(configDirPath);
        if (!configDir.exists()) {
            if (configDir.mkdirs()) {
                System.out.println("Configuration directory created: " + configDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create configuration directory.");
                return;
            }
        }

        // Specify the path to the configuration file within the configuration directory
        String configFile = configDirPath + File.separator + "setting.txt";

        if (!new File(configFile).exists()) {
            // Write default settings to the configuration file
            try {
                var subor = new File(configFile);
                var stream = new FileOutputStream(subor);
                var writer = new DataOutputStream(stream);
                writer.writeUTF("");
                writer.writeUTF("");
                writer.writeUTF("");
                writer.writeUTF("");
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        try {
            var subor = new File(configFile);
            FileInputStream stream = new FileInputStream(subor);
            DataInputStream citac = new DataInputStream(stream);

            this.colorFirstNote = citac.readUTF();
            this.colorOtherNotes = citac.readUTF();
            this.pocetPrazcov = citac.readUTF();
            this.pocetKlavesov = citac.readUTF();
        } catch (EOFException eofException) {
            this.colorFirstNote = "Blue";
            this.colorOtherNotes = "Green";
            this.pocetPrazcov = "12";
            this.pocetKlavesov = "12";

            try {
                var subor = new File(configFile);
                var stream = new FileOutputStream(subor);
                var writer = new DataOutputStream(stream);
                writer.writeUTF(this.colorFirstNote);
                writer.writeUTF(this.colorOtherNotes);
                writer.writeUTF(this.pocetPrazcov);
                writer.writeUTF(this.pocetKlavesov);
                writer.close();
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }
        } catch (Exception io) {
            System.out.println(io.getMessage());
        }
    }

    /**
     * Nastavuje hlavný panel v okne
     * @return JPanel - hlavný panel
     */
    public JPanel nastavMainPanel() {
        this.centerPanel = new JPanel();
        this.northCenterPanel = new JPanel(new BorderLayout());
        this.southPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel northWestPanel = new JPanel(new BorderLayout());
        JPanel northEastPanel = new JPanel(new BorderLayout());
        JPanel southWestPanel = new JPanel(new BorderLayout());
        JPanel southEastPanel = new JPanel(new BorderLayout());

        northWestPanel.add(new JLabel("Typ:  "), BorderLayout.WEST);
        this.typVyber = new JComboBox<>(new String[]{"Stupnica", "Arpeggio"});
        northWestPanel.add(this.typVyber , BorderLayout.CENTER);

        northPanel.add(northWestPanel, BorderLayout.WEST);

        JLabel zaciatocnyTon = new JLabel(" Začiatočný tón:  ");
        List<Ton> tonyList = Arrays.asList(Ton.values());
        Ton[] tony = new Ton[tonyList.size()];
        for (int i = 0; i < tonyList.size(); i++) {
            tony[i] = tonyList.get(i);
        }
        this.zaciatocnyTonVyber = new JComboBox<>(tony);
        northEastPanel.add(zaciatocnyTon, BorderLayout.WEST);
        northEastPanel.add(this.zaciatocnyTonVyber, BorderLayout.CENTER);
        northPanel.add(northEastPanel, BorderLayout.EAST);

        this.northCenterPanel.add(new JLabel(" Mód:  "), BorderLayout.WEST);
        ArrayList<Stupnica> arpsArrayList = new ArrayList<>();
        ArrayList<Stupnica> stupnicaArrayList = new ArrayList<>();
//        try {

        String tempDir = System.getProperty("java.io.tmpdir");
        String fileName = tempDir + File.separator + "arps.dat";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            // Read the object from the file
            while (true) {
                arpsArrayList.add((Stupnica) inputStream.readObject());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading object from the file: " + e.getMessage());
        }

            tempDir = System.getProperty("java.io.tmpdir");
            fileName = tempDir + File.separator + "scales.dat";

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
                // Read the object from the file
                while (true) {
                    stupnicaArrayList.add((Stupnica) inputStream.readObject());
                }
            } catch (EOFException ignored) {
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading object from the file: " + e.getMessage());
            }
//            var subor = new File("resources/settings/scales.dat");
//            FileInputStream stream = new FileInputStream(subor);
//            ObjectInputStream citac = new ObjectInputStream(stream);
//
//            while (true) {
//                stupnicaArrayList.add((Stupnica)citac.readObject());
//            }
//        } catch (EOFException ignored) {
//
//        } catch (IOException | ClassNotFoundException io) {
//            System.out.println(io.getMessage());
//        }

        Stupnica[] stupnice = new Stupnica[stupnicaArrayList.size()];
        Stupnica[] arpMods = new Stupnica[arpsArrayList.size()];
        var sizeStupnice = stupnicaArrayList.size();
        var sizeArps = arpsArrayList.size();
        for (int i = 0; i < sizeStupnice; i++) {
            stupnice[i] = stupnicaArrayList.remove(stupnicaArrayList.size() - 1);
        }
        for (int i = 0; i < sizeArps; i++) {
            arpMods[i] = arpsArrayList.remove(arpsArrayList.size() - 1);
        }

        this.arpModVyber = new JComboBox<>(arpMods);
        this.scaleModVyber = new JComboBox<>(stupnice);
        this.northCenterPanel.add(this.scaleModVyber, BorderLayout.CENTER);

        northPanel.add(this.northCenterPanel, BorderLayout.CENTER);

        JButton spatButton = new JButton("Späť");


        southWestPanel.add(spatButton, BorderLayout.WEST);
        this.southPanel.add(southWestPanel, BorderLayout.WEST);

        JButton generuj = new JButton("Generuj");

        southEastPanel.add(generuj, BorderLayout.CENTER);
        this.southPanel.add(southEastPanel, BorderLayout.EAST);

        mainPanel.add(this.southPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(this.centerPanel, BorderLayout.CENTER);

        spatButton.addActionListener(e -> {
            Menu menu = new Menu();
            HudobnyNastroj.this.getOkno().dispose();
        });

        this.getTypVyber().addActionListener(e -> {
            if (this.getTypVyber().getSelectedItem().equals("Arpeggio")) {
                this.getNorthCenterPanel().remove(this.getScaleModVyber());
                this.getNorthCenterPanel().add(this.getArpModVyber(), BorderLayout.CENTER);
                this.getOkno().revalidate();
            } else {
                this.getNorthCenterPanel().remove(this.getArpModVyber());
                this.getNorthCenterPanel().add(this.getScaleModVyber(), BorderLayout.CENTER);
                this.getOkno().revalidate();
            }
        });

        generuj.addActionListener(e -> {
            // polymorfizmus
            if (HudobnyNastroj.this instanceof Gitara) {
                this.generuj(this.colorFirstNote, this.colorOtherNotes, Integer.parseInt(this.pocetPrazcov));
            } else {
                this.generuj(this.colorFirstNote, this.colorOtherNotes, Integer.parseInt(this.pocetKlavesov));
            }

        });

        return mainPanel;
    }

    public String getColorFirstNote() {
        return this.colorFirstNote;
    }

    public String getColorOtherNotes() {
        return this.colorOtherNotes;
    }

    public String getPocetKlavesov() {
        return this.pocetKlavesov;
    }

    public String getPocetPrazcov() {
        return this.pocetPrazcov;
    }

    public JPanel getSouthPanel() {
        return this.southPanel;
    }

    public JPanel getNorthCenterPanel() {
        return this.northCenterPanel;
    }

    public JPanel getCenterPanel() {
        return this.centerPanel;
    }

    public JFrame getOkno() {
        return this.okno;
    }

    public boolean isArpeggio() {
        return this.isArpeggio;
    }

    public Stupnica getGenerator() {
        return this.generator;
    }

    public boolean isJeNakreslene() {
        return this.jeNakreslene;
    }

    public void setOkno(JFrame okno) {
        this.okno = okno;
    }

    public void setArpeggio(boolean arpeggio) {
        this.isArpeggio = arpeggio;
    }

    public void setGenerator(Stupnica generator) {
        this.generator = generator;
    }

    public void setJeNakreslene(boolean jeNakreslene) {
        this.jeNakreslene = jeNakreslene;
    }

    public JComboBox<String> getTypVyber() {
        return this.typVyber;
    }

    public JComboBox<Stupnica> getScaleModVyber() {
        return this.scaleModVyber;
    }

    public JComboBox<Stupnica> getArpModVyber() {
        return this.arpModVyber;
    }

    public JComboBox<Ton> getZaciatocnyTonVyber() {
        return this.zaciatocnyTonVyber;
    }

    /**
     * Nastaví panel s výberom farby a počtu pražcov(Gitara)/počtu klávesov(Piano)
     * @param southPanel južný panel - tam sú ComboBoxy na výber
     * @param centerPanel center panel - panel kde sa nastaví obrázok - metóda(nastavObrazok)
     * @param pocetPrazcovAleboKlavesov počet načítaný zo súboru - nastaví sa do ComboBoxu ako práve vybratý
     */
    public abstract void nastavSouthCenterPanel(JPanel southPanel, JPanel centerPanel, int pocetPrazcovAleboKlavesov, String farba);

    /**
     * Nastaví obrázok nástroja
     * @param panel na ňom sa obrázok zobrazí
     * @param pocetPrazcovAleboKlavesov podľa neho sa vyberie príslušný obrázok
     * @param farba hmatník (len pri Gitare)
     */
    public abstract void nastavObrazok(JPanel panel, String pocetPrazcovAleboKlavesov, String farba);

    /**
     * Po stlačení tlačidla vygeneruje príslušnú stupnicu/arppegio
     * @param colorFirstNote farba začiatočného tónu
     * @param colorOtherNotes farba ostatných tónov
     * @param pocetPrazcovAleboKlavesov pre výber obrázku
     */
    public abstract void generuj(String colorFirstNote, String colorOtherNotes, int pocetPrazcovAleboKlavesov);

    /**
     * Zobrazí jeden tón na všetkých miestach na nástroji
     * @param panel naň sa zobrazí
     * @param nota názov tónu
     * @param farba farba obrázku tónu
     */
    public abstract void showNote(JPanel panel, Ton nota, String farba);

    /**
     * Nakrelí celú stupnicu/arppegio
     * @param panel naň nakreslí
     * @param isArpeggio ak true - kreslí arppegio, inak stupnicu
     * @param zaciatocnyTon prvý tón stupnice
     * @param colorFirstNote farba prvého tónu
     * @param colorOtherNotes farba ostatných tónov
     */
    public void nakresli(JPanel panel, boolean isArpeggio, Ton zaciatocnyTon, String colorFirstNote, String colorOtherNotes) {
        // polymorfizmus
        this.showNote(panel, zaciatocnyTon, colorFirstNote);
        if (isArpeggio) {
            this.getGenerator().vytvorArpeggio(zaciatocnyTon, this.getGenerator().getArpFormula());

            for (int i = 0; i < this.getGenerator().getArpeggio().length; i++) {
                Ton ton = this.getGenerator().getArpeggio()[i];
                this.showNote(panel, ton, colorOtherNotes);
            }
        } else {
            this.getGenerator().vytvorStupnicu(zaciatocnyTon , this.getGenerator().getScaleFormula());

            for (int i = 1; i < this.getGenerator().getStupnica().length; i++) {
                Ton ton = this.getGenerator().getStupnica()[i];
                this.showNote(panel, ton, colorOtherNotes);
            }
        }
    }

    private BufferedImage resize(BufferedImage img) {
        Image image = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        BufferedImage bImage = new BufferedImage(35, 35, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bImage;
    }

    /**
     * Nastaví obrázok tónu na Label
     * @param nota názov noty
     * @param farba farba obrázku noty
     * @return JLabel - label tónu
     */
    public JLabel setLabel(String nota, String farba) {

        Border margin = new EmptyBorder(20, 40, 20, 20);
        try {
            var imageUrl = this.getClass().getResourceAsStream("/img/notes_" + farba.toLowerCase() + "/" + nota.toUpperCase() + ".png");
            BufferedImage image = ImageIO.read(imageUrl);
            image = this.resize(image);
            JLabel eLabel = new JLabel(new ImageIcon(image));
            Border border = eLabel.getBorder();

            eLabel.setBorder(new CompoundBorder(border, margin));
            return eLabel;
        } catch (IOException io) {
            System.out.println(io.getMessage());
            return null;
        }

    }


}
