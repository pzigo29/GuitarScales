package sk.fri.uniza.stupnice;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Vytvára stupnice a arppegia
 *
 * @author (Pavol Žigo)
 * @version (2.0)
 */
public abstract class Stupnica implements Serializable {

    private final String nazov;
    private final List<Ton> tony;
    private Ton[] stupnica;
    private Ton[] arpeggio;
    private int[] scaleFormula;
    private int[] arpFormula;
    private HashMap<Integer, String> intervaly;

    /**
     * Inicializuje atribúty
     * @param nazov názov stupnice
     */
    public Stupnica(String nazov) {
        this.nazov = nazov;
        this.tony = Arrays.asList(Ton.values());
        this.stupnica = null;
        this.arpeggio = null;

        this.intervaly = new HashMap<>(12);
        this.intervaly.put(1, "1");
        this.intervaly.put(2, "2_FLAT");
        this.intervaly.put(3, "2");
        this.intervaly.put(4, "3_FLAT");
        this.intervaly.put(5, "3");
        this.intervaly.put(6, "4");
        this.intervaly.put(7, "5_FLAT");
        this.intervaly.put(8, "5");
        this.intervaly.put(9, "6_FLAT");
        this.intervaly.put(10, "6");
        this.intervaly.put(11, "7_FLAT");
        this.intervaly.put(12, "7");

    }

    public String getNazov() {
        return this.nazov;
    }

    public Ton[] getStupnica() {
        return this.stupnica;
    }

    public void setStupnica(Ton[] stupnica) {
        this.stupnica = stupnica;
    }

    public void setArpeggio(Ton[] arpeggio) {
        this.arpeggio = arpeggio;
    }

    public Ton[] getArpeggio() {
        return this.arpeggio;
    }

    public int[] getScaleFormula() {
        return this.scaleFormula;
    }

    public void setScaleFormula(int[] scaleFormula) {
        this.scaleFormula = scaleFormula;
    }

    public int[] getArpFormula() {
        return this.arpFormula;
    }

    public void setArpFormula(int[] arpFormula) {
        this.arpFormula = arpFormula;
    }

    /**
     * Vytvorí postupnosť tónov v akorde
     * @param zaciatocnyTon prvý tón v arppegiu
     * @param formula rozostupy medzi tónmi
     */
    public void vytvorArpeggio(Ton zaciatocnyTon, int[] formula) {
        int poradie = zaciatocnyTon.getPoradie();
        for (int i = 0; i < this.getArpeggio().length; i++) {
            if (poradie > 11) {
                poradie = poradie - 12;
            }
            this.getArpeggio()[i] = this.tony.get(poradie);
            poradie += formula[i];
        }
    }

    /**
     * Vytvorí postupnosť tónov v stupnici
     * @param zaciatocnyTon prvý tón v stupnici
     * @param formula rozostupy medzi tónmi
     */
    public void vytvorStupnicu(Ton zaciatocnyTon, int[] formula) {
        int poradie = zaciatocnyTon.getPoradie();
        for (int j = 0; j < this.getStupnica().length; j++) {
            if (poradie > 11) {
                poradie = poradie - 12;
            }
            this.getStupnica()[j] = this.tony.get(poradie);
            poradie += formula[j];
        }
    }







}
