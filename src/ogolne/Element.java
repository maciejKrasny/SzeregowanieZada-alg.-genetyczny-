/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogolne;

/**
 * Klasa używana przy zapisywaniu wyników do pliku
 */
public class Element {
    
    private String nazwa;
    private int id;
    private int czasStartu;
    private int czasOperacji;

    public Element(String nazwa, int id, int czasStartu, int czasOperacji) {
        this.nazwa = nazwa;
        this.id = id;
        this.czasStartu = czasStartu;
        this.czasOperacji = czasOperacji;
    }

    
    
    
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCzasStartu() {
        return czasStartu;
    }

    public void setCzasStartu(int czasStartu) {
        this.czasStartu = czasStartu;
    }

    public int getCzasOperacji() {
        return czasOperacji;
    }

    public void setCzasOperacji(int czasOperacji) {
        this.czasOperacji = czasOperacji;
    }

    @Override
    public String toString() {
        return "Element{" + "nazwa=" + nazwa + ", id=" + id + ", czasStartu=" + czasStartu + ", czasOperacji=" + czasOperacji + '}';
    }
    
    
    
}
