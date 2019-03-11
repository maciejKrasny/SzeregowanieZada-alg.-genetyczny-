/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogolne;

import java.util.Random;

/**
 * Zadanie 
 */
public class Zadanie {
    
    private int id;
    private int czasOp0;
    private int czasOp1;
    private int czasRozpoczecia;
    private int zrobione;

    public Zadanie(int id, int czasOp0, int czasOp1, int czasRozpoczecia) {
        this.id = id;
        this.czasOp0 = czasOp0;
        this.czasOp1 = czasOp1;
        this.czasRozpoczecia = czasRozpoczecia;
        zrobione = 0;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCzasRozpoczecia() {
        return czasRozpoczecia;
    }

    public void setCzasRozpoczecia(int czasRozpoczecia) {
        this.czasRozpoczecia = czasRozpoczecia;
    }

    public int getCzasOp0() {
        return czasOp0;
    }

    public void setCzasOp0(int czasOp0) {
        this.czasOp0 = czasOp0;
    }

    public int getCzasOp1() {
        return czasOp1;
    }

    public void setCzasOp1(int czasOp1) {
        this.czasOp1 = czasOp1;
    }

    public int getZrobione() {
        return zrobione;
    }

    public void setZrobione(int zrobione) {
        this.zrobione = zrobione;
    }

    @Override
    public String toString() {
        return "Zadanie{" + "id=" + id + ", czasOp0=" + czasOp0 + ", czasOp1=" + czasOp1 + ", czasRozpoczecia=" + czasRozpoczecia + ", zrobione=" + zrobione + '}';
    }

    
    
}
