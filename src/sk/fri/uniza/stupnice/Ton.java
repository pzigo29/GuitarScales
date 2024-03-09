package sk.fri.uniza.stupnice;

/**
 * Enum na všetky možné tóny
 *
 * @author (Pavol Žigo)
 * @version (1.1)
 */
public enum Ton {
    A("A", 0),
    A_SHARP("A#", 1),
    B("B", 2),
    C("C", 3),
    C_SHARP("C#", 4),
    D("D", 5),
    D_SHARP("D#", 6),
    E("E", 7),
    F("F", 8),
    F_SHARP("F#", 9),
    G("G", 10),
    G_SHARP("G#", 11);

    private final String nazov;
    private final int poradie;

    /**
     * Nastaví (String)názov a poradie pre vypočítavanie postupnosti stupnice
     * @param nazov názov tónu
     * @param poradie očíslovanie od A
     */
    Ton(String nazov, int poradie) {
        this.nazov = nazov;
        this.poradie = poradie;
    }

    public int getPoradie() {
        return this.poradie;
    }

    @Override
    public String toString() {
        return this.nazov;
    }
}
