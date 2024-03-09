package sk.fri.uniza.mains;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Zobrazí frame s nastaveniami aplikácie a ukladá ich do súboru
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Settings {
    private JComboBox<String> farbaStartCombo;
    private JComboBox<String> farbaOtherCombo;
    private JLabel farbaStartLabel;
    private JLabel farbaOtherLabel;
    private JComboBox<String> pocetPrazcovCombo;
    private JLabel pocetPrazcovLabel;
    private JComboBox<String> pocetKlavesovCombo;
    private JLabel pocetKlavesovLabel;
    private JPanel mainPanel;
    private JButton confirmBtn;
    private final JFrame okno;
    private String colorFirstNote;
    private String colorOtherNotes;
    private String pocetPrazcov;
    private String pocetKlavesov;

    /**
     * Vytvorí frame, kde po stlačení tlačidla potvrdiť sa nastavenia uložia do súboru
     */
    public Settings() {
        this.okno = new JFrame("Nastavenia");
        this.okno.setContentPane(this.mainPanel);

        String[] farba = new String[]{"Red", "Green", "Blue"};
        for (String s : farba) {
            this.farbaStartCombo.addItem(s);
            this.farbaOtherCombo.addItem(s);
        }

        int[] prazce = new int[]{12, 20, 21, 22, 24};
        for (int i : prazce) {
            this.pocetPrazcovCombo.addItem(String.valueOf(i));
        }

        int[] klavesy = new int[]{12, 24, 36, 48};
        for (int i : klavesy) {
            this.pocetKlavesovCombo.addItem(String.valueOf(i));
        }

        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Specify the path to the configuration directory within the home directory
        String configDirPath = userHome + File.separator + ".guitarScalesSettings";
        // Create the configuration directory if it doesn't exist
        File configDir = new File(configDirPath);
        if (!configDir.exists()) {
            if (configDir.mkdir()) {
                System.out.println("Configuration directory created: " + configDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create configuration directory.");
                return;
            }
        }

        // Specify the path to the configuration file within the configuration directory
        String configFile = configDirPath + File.separator + "setting.txt";

        try {
            var subor = new File(configFile);
            FileInputStream stream = new FileInputStream(subor);
            DataInputStream citac = new DataInputStream(stream);

            this.colorFirstNote = citac.readUTF();
            this.colorOtherNotes = citac.readUTF();
            this.pocetPrazcov = citac.readUTF();
            this.pocetKlavesov = citac.readUTF();

        } catch (EOFException ex) {
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

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            this.pocetPrazcovCombo.setSelectedItem(this.pocetPrazcov);
            this.pocetKlavesovCombo.setSelectedItem(this.pocetKlavesov);
            this.farbaStartCombo.setSelectedItem(this.colorFirstNote);
            this.farbaOtherCombo.setSelectedItem(this.colorOtherNotes);
        }


        this.confirmBtn.addActionListener(e -> {
            Settings.this.colorFirstNote = (String)Settings.this.farbaStartCombo.getSelectedItem();
            Settings.this.colorOtherNotes = (String)Settings.this.farbaOtherCombo.getSelectedItem();
            Settings.this.pocetPrazcov = (String)Settings.this.pocetPrazcovCombo.getSelectedItem();
            Settings.this.pocetKlavesov = (String)Settings.this.pocetKlavesovCombo.getSelectedItem();

            try {
                var subor = new File(configFile);
                var stream = new FileOutputStream(subor);
                var writer = new DataOutputStream(stream);
                writer.writeUTF(Settings.this.colorFirstNote);
                writer.writeUTF(Settings.this.colorOtherNotes);
                writer.writeUTF(Settings.this.pocetPrazcov);
                writer.writeUTF(Settings.this.pocetKlavesov);
                writer.close();

            } catch (IOException io) {
                System.out.println(io.getMessage());
            }
            Menu menu = new Menu();

            Settings.this.okno.dispose();
        });

        this.okno.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    var subor = new File(configFile);
                    FileInputStream stream = new FileInputStream(subor);
                    DataInputStream citac = new DataInputStream(stream);

                    Settings.this.colorFirstNote = citac.readUTF();
                    Settings.this.colorOtherNotes = citac.readUTF();
                    Settings.this.pocetPrazcov = citac.readUTF();
                    Settings.this.pocetKlavesov = citac.readUTF();
                } catch (Exception io) {
                    System.out.println(io.getMessage());
                }
                Menu menu = new Menu();
            }
        });



        this.okno.pack();
        this.okno.setVisible(true);
    }
}
