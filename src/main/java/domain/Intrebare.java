package domain;

public class Intrebare extends Entity<Integer> {
    private int nrintrebare;
    private String descriere;
    private String raspunsCorect;
    private int punctaj;
    private String[] variante;

    public void setNrintrebare(int nrintrebare) {
        this.nrintrebare = nrintrebare;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setRaspunsCorect(String raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    public String[] getVariante() {
        return variante;
    }

    public int getNrintrebare() {
        return nrintrebare;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getRaspunsCorect() {
        return raspunsCorect;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public Intrebare(int nrintrebare, String descriere, String raspunsCorect, int punctaj,String[] variante) {
        this.nrintrebare = nrintrebare;
        this.descriere = descriere;
        this.raspunsCorect = raspunsCorect;
        this.punctaj = punctaj;
        this.variante= variante;
        setId(nrintrebare);
    }
}
