package sk.fri.uniza.stupnice;

/**
 * Stupnica Melodic Minor
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class MelodicMinor extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public MelodicMinor() {
        super("Melodic Minor");
        int[] scaleFormula = new int[]{2, 1, 2, 2, 2, 2, 1};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    public String toString() {
        return this.getNazov();
    }
}
