package sk.fri.uniza.mains;


import sk.fri.uniza.stupnice.AugmentedArp;
import sk.fri.uniza.stupnice.Blues;
import sk.fri.uniza.stupnice.DominantArp;
import sk.fri.uniza.stupnice.Dorian;
import sk.fri.uniza.stupnice.FullyDiminishedArp;
import sk.fri.uniza.stupnice.HalfDiminishedArp;
import sk.fri.uniza.stupnice.HarmonicMinor;
import sk.fri.uniza.stupnice.Locrian;
import sk.fri.uniza.stupnice.Lydian;
import sk.fri.uniza.stupnice.Major;
import sk.fri.uniza.stupnice.MajorPentatonic;
import sk.fri.uniza.stupnice.MajorSeventhArp;
import sk.fri.uniza.stupnice.MelodicMinor;
import sk.fri.uniza.stupnice.Minor;
import sk.fri.uniza.stupnice.MinorPentatonic;
import sk.fri.uniza.stupnice.MinorSeventhArp;
import sk.fri.uniza.stupnice.Mixolydian;
import sk.fri.uniza.stupnice.Phrygian;
import sk.fri.uniza.stupnice.Stupnica;

import java.io.*;

/**
 * Main
 *
 * @author (Pavol Žigo)
 * @version (1.0)
 */
public class Main {

    /**
     * Spustí aplikáciu - zobrazí menu.
     * Vytvorí pole stupníc a akordov na výber
     * @param args null
     */
    public static void main(String[] args) {
        Stupnica[] stupnice = new Stupnica[]{new Locrian(), new Mixolydian(), new Lydian(), new Phrygian(), new Dorian(), new MelodicMinor(), new Blues(), new HarmonicMinor(), new MinorPentatonic(),  new Minor(), new MajorPentatonic(), new Major()};
        Stupnica[] arpMods = new Stupnica[]{new AugmentedArp(), new HalfDiminishedArp(), new FullyDiminishedArp(), new MinorSeventhArp(), new MajorSeventhArp(), new DominantArp(), new Minor(), new Major()};

        String tempDir = System.getProperty("java.io.tmpdir");
        String fileName = tempDir + File.separator + "scales.dat";
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Stupnica stupnica : stupnice) {
                writer.writeObject(stupnica);
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
//            var subor = new File("resources/settings/scales.dat");
//            var stream = new FileOutputStream(subor);
//            var writer = new ObjectOutputStream(stream);
//            for (Stupnica stupnica : stupnice) {
//                writer.writeObject(stupnica);
//            }
//            writer.close();

        tempDir = System.getProperty("java.io.tmpdir");
        fileName = tempDir + File.separator + "arps.dat";
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Stupnica stupnica : arpMods) {
                writer.writeObject(stupnica);
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        Menu menu = new Menu();
    }
}