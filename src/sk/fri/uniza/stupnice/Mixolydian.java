package sk.fri.uniza.stupnice;

/**
 * Stupnica Mixolydian
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Mixolydian extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public Mixolydian() {
        super("Mixolydian");
        int[] scaleFormula = new int[]{2, 2, 1, 2, 2, 1, 2};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}