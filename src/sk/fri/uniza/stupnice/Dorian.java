package sk.fri.uniza.stupnice;

/**
 * Stupnica Dorian
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Dorian extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public Dorian() {
        super("Dorian");
        int[] scaleFormula = new int[]{2, 1, 2, 2, 2, 1, 2};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
