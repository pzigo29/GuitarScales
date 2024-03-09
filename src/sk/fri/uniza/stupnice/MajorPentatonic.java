package sk.fri.uniza.stupnice;

/**
 * Stupnica Major Pentatonic
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class MajorPentatonic extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice
     */
    public MajorPentatonic() {
        super("Major Pentatonic");
        int[] scaleFormula = new int[]{2, 2, 3, 2, 3};
        this.setScaleFormula(scaleFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
