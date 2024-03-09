package sk.fri.uniza.stupnice;

/**
 * Stupnica Lydian
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Lydian extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public Lydian() {
        super("Lydian");
        int[] scaleFormula = new int[]{2, 2, 2, 1, 2, 2, 1};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
