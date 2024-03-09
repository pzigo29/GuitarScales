package sk.fri.uniza.stupnice;

/**
 * Stupnica Harmonic Minor
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class HarmonicMinor extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public HarmonicMinor() {
        super("Harmonic Minor");
        int[] scaleFormula = new int[]{2, 1, 2, 2, 1, 3, 1};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
