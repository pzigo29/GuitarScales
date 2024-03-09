package sk.fri.uniza.stupnice;

/**
 * Stupnica Locrian
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Locrian extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public Locrian() {
        super("Locrian");
        int[] scaleFormula = new int[]{1, 2, 2, 1, 2, 2, 2};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
