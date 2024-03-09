package sk.fri.uniza.stupnice;

/**
 * Stupnica Blues
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Blues extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public Blues() {
        super("Blues");
        int[] scaleFormula = new int[]{3, 2, 1, 1, 3, 2};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
