package domain;

public class Raspuns {
    private int nr;
    private String nume;
    private int puncte;

    public int getNr() {
        return nr;
    }

    public String getNume() {
        return nume;
    }

    public int getPuncte() {
        return puncte;
    }

    public Raspuns(int nr, String nume, int puncte) {
        this.nr = nr;
        this.nume = nume;
        this.puncte = puncte;
    }
}
