package sk.fri.uniza.stupnice;

/**
 * Arppegio Dominant
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class DominantArp extends Stupnica {

    /**
     * Vytvorí postupnosť arppegia
     */
    public DominantArp() {
        super("Dominant 7th");
        int[] arpFormula = new int[]{4, 3, 3, 2};
        this.setArpFormula(arpFormula);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
