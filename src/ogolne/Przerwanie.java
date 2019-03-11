/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogolne;


/**
 * Przerwanie
 */
public class Przerwanie {
    private int nrPrzerwania;
    private int numerMaszyny;
    private int czas;
    private int czasStartu;

    public Przerwanie(int nrPrzerwania, int numerMaszyny, int czas, int czasStartu) {
        this.numerMaszyny = numerMaszyny;
        this.czas = czas;
        this.czasStartu = czasStartu;
        this.nrPrzerwania = nrPrzerwania;
    }

    public int getNumerMaszyny() {
        return numerMaszyny;
    }

    public void setNumerMaszyny(int numerMaszyny) {
        this.numerMaszyny = numerMaszyny;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public int getCzasStartu() {
        return czasStartu;
    }

    public void setCzasStartu(int czasStartu) {
        this.czasStartu = czasStartu;
    }

    public int getNrPrzerwania() {
        return nrPrzerwania;
    }

    public void setNrPrzerwania(int nrPrzerwania) {
        this.nrPrzerwania = nrPrzerwania;
    }
    
    

    @Override
    public String toString() {
        return "Przerwanie{" + "numerMaszyny=" + numerMaszyny + ", czas=" + czas + ", czasStartu=" + czasStartu + '}';
    }
    
    
    
}
