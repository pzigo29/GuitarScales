package sk.fri.uniza.stupnice;

/**
 * Stupnica Minor Pentatonic
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class MinorPentatonic extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public MinorPentatonic() {
        super("Minor Pentatonic");
        int[] scaleFormula = new int[]{3, 2, 2, 3, 2};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);

    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
