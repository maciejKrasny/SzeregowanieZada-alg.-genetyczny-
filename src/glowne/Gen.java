/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glowne;

import ogolne.Zadanie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Gen - pojedyncze rozwiązanie
 */
public class Gen {
    
    // lista operacji na pierwszej maszynie
    private List <Integer> maszyna0;
    // lista operacji na drugiej maszynie
    private List <Integer> maszyna1;
    // wartość funkcji celu
    private int funkcjaCelu;

    public Gen(List<Integer> maszyna0, List<Integer> maszyna1) {
        this.maszyna0 = maszyna0;
        this.maszyna1 = maszyna1;
        this.funkcjaCelu = -1;
    }
    
    public Gen() {
        this.maszyna0 = new ArrayList<>();
        this.maszyna1 = new ArrayList<>();
        this.funkcjaCelu = -1;
    }
    
    /**
     * Przeliczenie wartości funkcji celu
     * @return wartość funkcji celu
     */
    public int ustawFunkcjeCelu()  {
        
        // jeśli funkcja celu była już wcześniej wyliczona to nie licz ponownie
        if(funkcjaCelu != -1) return this.funkcjaCelu;
        
        this.funkcjaCelu = 0;
        
        // lista zawierająca numer zadania i czas zakończenia dla pierwszej operacji
        List <Integer[]> listaOperacjiMaszyna0 = new ArrayList<>();
        Zadanie tempZad = null;
        int czasAktualny = 0;
        int nrPrzerwania = 0;
        
        //dodawanie operacji na pierwszą maszyne
        for (Integer nrZadania : maszyna0) {
            tempZad = Main.zadania.get(nrZadania);
            
            // jeśli czas rozpoczęcia zadania (readytime) jest większy niż czas aktualny to czas aktualny zmienia się w czas rozpoczęcia zadania
            czasAktualny = Math.max(tempZad.getCzasRozpoczecia(), czasAktualny);
            
            while(Main.przerwania.get(nrPrzerwania).getNumerMaszyny() == 0) {
                
            // jeśli przerwanie zaczyna się w czasie wykonywania operacji to zadanie dodajemy za przerwaniem
                if(Main.przerwania.get(nrPrzerwania).getCzasStartu() 
                        < czasAktualny + tempZad.getCzasOp0()) {
                        czasAktualny = Math.max(Main.przerwania.get(nrPrzerwania).getCzasStartu() 
                                + Main.przerwania.get(nrPrzerwania).getCzas(),czasAktualny);                                                nrPrzerwania++;
                    }
                else break;
            }
            czasAktualny += tempZad.getCzasOp0();
            listaOperacjiMaszyna0.add(new Integer[]{nrZadania, czasAktualny});
            this.funkcjaCelu += czasAktualny;

        }
        
        // sortujemy liste operacji na pierwszej maszynie, żeby ułatwić sprawdzanie czasów zakończenia operacji pierwszych przy układaniu operacji na drugiej maszynie
        Collections.sort(listaOperacjiMaszyna0, new Main.KomparatorOperacji());
        czasAktualny = 0;

        // dodawanie operacji na drugą maszynę
        for (Integer nrZadania : maszyna1) {
            tempZad = Main.zadania.get(nrZadania);
                    
            while (nrPrzerwania < Main.przerwania.size()) { 
                    
                    // jeśli przerwanie zaczyna się w czasie wykonywania operacji to zadanie dodajemy za przerwaniem
                    if(Main.przerwania.get(nrPrzerwania).getCzasStartu() 
                        < czasAktualny + tempZad.getCzasOp1()) {
                        czasAktualny = Math.max(Main.przerwania.get(nrPrzerwania).getCzasStartu() 
                                + Main.przerwania.get(nrPrzerwania).getCzas(), czasAktualny);                                                    nrPrzerwania++;
                        if(nrPrzerwania >= Main.przerwania.size()) break;            
                    }
                    
                    else break;
                }
                
            // jeśli czas zakończenia pierwszej operacji jest większy niż czas aktualny to czas aktualny zmienia się w czas zakończenia pierwszej operacji
            czasAktualny = Math.max(listaOperacjiMaszyna0.get(nrZadania)[1], czasAktualny);
            

            czasAktualny += tempZad.getCzasOp1();
            this.funkcjaCelu += czasAktualny;
        }
        
        return this.funkcjaCelu;
    }
   
    /**
     * mutowanie genu
     * @param prawdopodobienstwoMutacji
     */
    public void mutacja(double prawdopodobienstwoMutacji) {
        Random r = new Random();
        // decyzja czy mutujemy
        if(r.nextDouble() > prawdopodobienstwoMutacji) return;
        
        Gen nowyGen = new Gen(this.getMaszyna0(),this.getMaszyna1());
        
        // wybór maszyny na której zamieniamy operacje
        if( r.nextInt(2) == 0 ) {
            Collections.swap(nowyGen.getMaszyna0(), 
                    r.nextInt(Main.iloscZadan), r.nextInt(Main.iloscZadan));  
        }
        
        else {
            Collections.swap(nowyGen.getMaszyna1(), 
                r.nextInt(Main.iloscZadan), r.nextInt(Main.iloscZadan));
        }
        
        // dodanie zmutowanego genu do nowej populacji
        Genetyczny.nowaPopulacja.add(nowyGen);
    }

    /**
     * Odczytanie kolejności operacji na maszynach
     */
    public void odczyt() {
        for (int i = 0; i < maszyna0.size(); i++) {
            System.out.println(maszyna0.get(i) + ";" + maszyna1.get(i) );            
        }
    }
    
    public List<Integer> getMaszyna0() {
        return maszyna0;
    }

    public void setMaszyna0(List<Integer> maszyna0) {
        this.maszyna0 = maszyna0;
    }

    public List<Integer> getMaszyna1() {
        return maszyna1;
    }

    public void setMaszyna1(List<Integer> maszyna1) {
        this.maszyna1 = maszyna1;
    }

    public int getFunkcjaCelu() {
        return funkcjaCelu;
    }

    public void setFunkcjaCelu(int funkcjaCelu) {
        this.funkcjaCelu = funkcjaCelu;
    }
    
    
 
}