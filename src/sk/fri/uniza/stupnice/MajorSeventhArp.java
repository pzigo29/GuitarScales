package sk.fri.uniza.stupnice;

/**
 * Arppegio Major Seventh
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class MajorSeventhArp extends Stupnica {

    /**
     * Vytvorí postupnosť arppegia
     */
    public MajorSeventhArp() {
        super("Major 7th");
        int[] arpFormula = new int[]{4, 3, 4, 1};
        this.setArpFormula(arpFormula);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
