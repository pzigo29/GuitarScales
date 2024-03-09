package sk.fri.uniza.stupnice;

/**
 * Stupnica/Arppegio Minor
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public class Minor extends Stupnica {

    /**
     * Vytvorí postupnosť stupnice/arppegia
     */
    public Minor() {
        super("Minor");
        int[] scaleFormula = new int[]{2, 1, 2, 2, 1, 2, 2};
        this.setScaleFormula(scaleFormula);
        int[] arpFormula = new int[]{3, 4, 5};
        this.setArpFormula(arpFormula);
        this.setStupnica(new Ton[scaleFormula.length]);
        this.setArpeggio(new Ton[arpFormula.length]);
    }

    @Override
    public String toString() {
        return this.getNazov();
    }
}
