package sk.fri.uniza.stupnice;

/**
 * Stupnica/Arppegio Major
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Major extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice/arppegia
     */
    public Major() {
        super("Major");
        int[] scaleFormula = new int[]{2, 2, 1, 2, 2, 2, 1};
        this.setScaleFormula(scaleFormula);
        int[] arpFormula = new int[]{4, 3, 5};
        this.setArpFormula(arpFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
