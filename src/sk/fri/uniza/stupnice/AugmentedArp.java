package sk.fri.uniza.stupnice;

/**
 * Arppegio Augmented
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class AugmentedArp extends Stupnica {


    /**
     * Vytvorí postupnosť arppegia
     */
    public AugmentedArp() {
        super("Augmented");
        int[] arpFormula = new int[]{4, 4, 4};
        this.setArpFormula(arpFormula);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
