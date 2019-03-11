/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glowne;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Obsługa algorytmu genetycznego
 * 
 */
public class Genetyczny {
   
    public static List < Gen > populacja;
    private int liczbaPrzejscGenetyczny;
    private int wielkoscPopulacji;
    private double prawdopodobienstwoMutacji;
    private int wielkoscPrzedzialu;
    private int wielkoscTurnieju;
    private boolean pierwszePrzejscie;
    
    // dla warunku stopu -> czas
    private static long start;
    private static long stop;
    public static void start() { start = System.currentTimeMillis();}
    public static void stop() { stop = System.currentTimeMillis();}
    public static long Time() { return stop - start;}
    

    public Genetyczny() {
        this.liczbaPrzejscGenetyczny = 15000;
        this.wielkoscPopulacji = 200;
        this.prawdopodobienstwoMutacji = 0.3;
        this.wielkoscPrzedzialu = Main.iloscZadan/10;
        this.wielkoscTurnieju = 2;  
    }

    public static List < Gen > nowaPopulacja;
    
    /**
     * Metoda odpowiadająca za krzyżowanie
     */
    public void krzyzowanie() {          
        Random random = new Random();
        
        // nowa populacja będzie tworzona dopóki nie osiągnie zadanej wielkości populacji 
        while(nowaPopulacja.size() < wielkoscPopulacji){
            
            // losowo wybieramy dwóch rodziców
            Gen genA = populacja.get(random.nextInt(populacja.size()));
            Gen genB = populacja.get(random.nextInt(populacja.size()));
            
            // potomkowie
            Gen temp1 = new Gen();
            Gen temp2 = new Gen();
            
            // dodajemy operacje na maszyny nowych genów, tyle ile wynosi parametr wielkości przedziału
            for(int i = 0; i < wielkoscPrzedzialu; i++) {
                temp1.getMaszyna0().add(genA.getMaszyna0().get(i));
                temp1.getMaszyna1().add(genA.getMaszyna1().get(i));
                temp2.getMaszyna0().add(genB.getMaszyna0().get(i));
                temp2.getMaszyna1().add(genB.getMaszyna1().get(i));
            }
            
            // dodajemy brakujące operacje na maszyny w tej kolejności w jakiej były ustawione na drugim rodzicu
            for(int i = 0; i < Main.iloscZadan; i++) {
                // jeśli nie zawiera tej operacji to dodaj
                if(!temp1.getMaszyna0().contains(genB.getMaszyna0().get(i))) {
                    temp1.getMaszyna0().add(genB.getMaszyna0().get(i));
                }
                
                if(!temp1.getMaszyna1().contains(genB.getMaszyna1().get(i))) {
                    temp1.getMaszyna1().add(genB.getMaszyna1().get(i));
                }
                
                if(!temp2.getMaszyna0().contains(genA.getMaszyna0().get(i))) {
                    temp2.getMaszyna0().add(genA.getMaszyna0().get(i));
                }
                
                if(!temp2.getMaszyna1().contains(genA.getMaszyna1().get(i))) {
                    temp2.getMaszyna1().add(genA.getMaszyna1().get(i));
                }
            }
            
            /*
            genA.odczyt();
            System.out.println("****");
            
            genB.odczyt();
            System.out.println("****");
            temp1.odczyt();
            System.out.println("****");
            
            temp2.odczyt();
            */
            
            // próbujemy zmutować nowopowstałe geny
            temp1.mutacja(prawdopodobienstwoMutacji);
            temp2.mutacja(prawdopodobienstwoMutacji);
            
            // dodajemy nowopowstałe geny do nowej populacji
            nowaPopulacja.add(temp1);
            nowaPopulacja.add(temp2);
        }
    }
    
    /**
     * Metoda odpowiadająca za selekcję metodą turnieju
     */
    public void selekcja() {
        int wartosc;
        
        // dodajemy do populacji całą nową populację - w ten sposób zwiększamy pulę genów
        populacja.addAll(nowaPopulacja);
        nowaPopulacja = new ArrayList<>();
        
        // jeśli jest to pierwsze przejście to tylko nadajemy genom funkcje celu, nie dokonujemy selekcji
        if(pierwszePrzejscie == true) {
            Main.sredniLosowy = 0;
            for (Gen gen : populacja) {
                wartosc = gen.ustawFunkcjeCelu();
                Main.sredniLosowy += wartosc;
                if(wartosc < Main.najlepszeRozw.getFunkcjaCelu()) {
                    Main.najlepszeRozw = gen;
                }
            }
            // średnia wartość wylosowanych rozwiązań przez generator
            Main.sredniLosowy = Main.sredniLosowy/populacja.size();
            pierwszePrzejscie = false;
        }
        
        else {
            // ewaluacja funkcji celu
            for (Gen gen : populacja) {
                wartosc = gen.ustawFunkcjeCelu();
                if(wartosc < Main.najlepszeRozw.getFunkcjaCelu()) {
                    Main.najlepszeRozw = gen;
                }
            }
            
            // przenosimy najlepsze rozwiązanie
            nowaPopulacja.add(Main.najlepszeRozw);
            populacja.remove(Main.najlepszeRozw);
            
            // selekcja metodą turnieju
            Random random = new Random();
            Gen genMin, genB;
            while( nowaPopulacja.size() < wielkoscPopulacji && populacja.size() > wielkoscTurnieju ) {

                genMin = populacja.get(random.nextInt(populacja.size()));
                populacja.remove(genMin);

                for(int i = 0; i < wielkoscTurnieju - 1; i++) {

                    genB = populacja.get(random.nextInt(populacja.size()));
                    populacja.remove(genB);

                    if(genMin.getFunkcjaCelu() > genB.getFunkcjaCelu()) {
                        genMin = genB;
                    }
                }
                
                // lepsze rozwiązanie zostaje dodane do nowej populacji
                nowaPopulacja.add(genMin);    
            }
        populacja = new ArrayList<>(nowaPopulacja);   
        nowaPopulacja = new ArrayList<>();  
        }
        
        
        
    }
    
    
    public int genetyczny() {
        start();
        GeneratorRozwiazan generator = new GeneratorRozwiazan();
        Main.najlepszeRozw = null;
        populacja = new ArrayList<>();
        nowaPopulacja = new ArrayList<>();
        this.wielkoscPrzedzialu = Main.iloscZadan/10;
        
        System.out.println("liczbaPrzejscGenetyczny = " + liczbaPrzejscGenetyczny + "\n" +
            "wielkoscPopulacji = " + wielkoscPopulacji + "\n" +
            "prawdopodobienstwoMutacji = " + prawdopodobienstwoMutacji + "\n" +
            "wielkoscPrzedzialu = " + wielkoscPrzedzialu + "\n" +
            "wielkoscTurnieju = " + wielkoscTurnieju);  
        
        // tworzymy pierwszą populację z rozwiązań losowych
        for (int i = 0; i < wielkoscPopulacji; i++) {
            nowaPopulacja.add(generator.generujLosowe());
        }
        pierwszePrzejscie = true;
        Main.najlepszeRozw = nowaPopulacja.get(0);
                
        int stareNajlepsze = Main.najlepszeRozw.getFunkcjaCelu();
        
        
        int dzialanie = 0;
        // algorytm działa dopóki nie wykona określonej liczby iteracji
        // while(dzialanie < liczbaPrzejscGenetyczny) {
        long czas = 0;
        while(czas < 1000) {
            selekcja();  
            krzyzowanie();
            
            // warunek stopu - jeśli po 3000 przejść rozwiązanie nie ulegnie zmianie, zakończ działanie
//            if(dzialanie%3000 == 0 || dzialanie == 0) {
//                //System.out.println(Main.najlepszeRozw.getFunkcjaCelu());
//                if(stareNajlepsze == Main.najlepszeRozw.getFunkcjaCelu()) {
//                    return Main.najlepszeRozw.getFunkcjaCelu();
//                }
//                stareNajlepsze = Main.najlepszeRozw.getFunkcjaCelu();
//            }
            
            dzialanie++;
            stop();
            czas = Time();
        }
        
        return Main.najlepszeRozw.getFunkcjaCelu();
        
        
    }
    
    

    public double getPrawdopodobienstwoMutacji() {
        return prawdopodobienstwoMutacji;
    }

    public void setPrawdopodobienstwoMutacji(double prawdopodobienstwoMutacji) {
        this.prawdopodobienstwoMutacji = prawdopodobienstwoMutacji;
    }

    public static List<Gen> getPopulacja() {
        return populacja;
    }

    public void setPopulacja(List<Gen> populacja) {
        Genetyczny.populacja = populacja;
    }

    public int getLiczbaPrzejscGenetyczny() {
        return liczbaPrzejscGenetyczny;
    }

    public void setLiczbaPrzejscGenetyczny(int liczbaPrzejscGenetyczny) {
        this.liczbaPrzejscGenetyczny = liczbaPrzejscGenetyczny;
    }

    public int getWielkoscPopulacji() {
        return wielkoscPopulacji;
    }

    public void setWielkoscPopulacji(int wielkoscPopulacji) {
        this.wielkoscPopulacji = wielkoscPopulacji;
    }

    public int getWielkoscPrzedzialu() {
        return wielkoscPrzedzialu;
    }

    public void setWielkoscPrzedzialu(int wielkoscPrzedzialu) {
        this.wielkoscPrzedzialu = wielkoscPrzedzialu;
    }

    public int getWielkoscTurnieju() {
        return wielkoscTurnieju;
    }

    public void setWielkoscTurnieju(int wielkoscTurnieju) {
        this.wielkoscTurnieju = wielkoscTurnieju;
    }

}
