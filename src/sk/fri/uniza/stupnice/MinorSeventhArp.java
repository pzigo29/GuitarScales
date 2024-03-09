package sk.fri.uniza.stupnice;

/**
 * Arppegio Minor Seventh
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class MinorSeventhArp extends Stupnica {

    /**
     * Vytvorí postupnosť arppegia
     */
    public MinorSeventhArp() {
        super("Minor 7th");
        int[] arpFormula = new int[]{3, 4, 3, 2};
        this.setArpFormula(arpFormula);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
