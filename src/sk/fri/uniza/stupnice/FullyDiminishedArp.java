package sk.fri.uniza.stupnice;

/**
 * Arppegio Diminished
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class FullyDiminishedArp extends Stupnica {

    /**
     * Vytvorí postupnosť arppegia
     */
    public FullyDiminishedArp() {
        super("Full Diminished");
        int[] arpFormula = new int[]{3, 3, 3, 3};
        this.setArpFormula(arpFormula);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
