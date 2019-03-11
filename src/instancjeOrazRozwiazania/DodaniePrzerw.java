/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instancjeOrazRozwiazania;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ogolne.Zadanie;

/**
 * Dodanie przerw do istniejących instancji - stworzenie instancji o tych samych parametrach zadań ale o różnej ilości przerw
 * 
 */
public class DodaniePrzerw {


    
    public static List <Zadanie> zadania;

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        
        for (int nrInstancji = 0; nrInstancji < 20; nrInstancji++) {
            zadania = new ArrayList<>();
            int liczbaZadan;
    
            
            //odczyt zadan instancji
            String plik = "instancje/iloscPrzerw/instancja" + nrInstancji + ".problem";
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(plik));
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));

            int maxCzasPrzerwania = 10;
            int minCzasPrzerwania = 5;
            
            //ustawienie ilosc przerw na wybranych maszynach
            int liczbaPrzerwanPierwsza = 14;
            int liczbaPrzerwanDruga = 14;
            int liczbaPrzerwan = liczbaPrzerwanPierwsza + liczbaPrzerwanDruga;
            
            int czasZadanMinPierwszaMaszyna = 0;
            int czasZadanMinDrugaMaszyna = 0;
            
            String plikZapis = "instancje/iloscPrzerw/14przerw/instancja" + nrInstancji + ".problem";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plikZapis)));
            out.write("**** " + Integer.toString(nrInstancji) + " ****\n");
            
            
            
            // w linii pliku musi być [ id, czas trwania, czas startu ] 
            String linia;
            String liniaSplit[];
            System.out.println(in.readLine());
            liczbaZadan = Integer.parseInt(in.readLine());
            out.write(Integer.toString(liczbaZadan) + "\n");

            for(int nrZad = 0; nrZad < liczbaZadan; nrZad++) {
                linia = in.readLine();
                out.write(linia+"\n");
                linia = linia.replaceAll("\\s","");
                liniaSplit = linia.split(";");
                Zadanie zad = new Zadanie(nrZad, 
                        Integer.parseInt(liniaSplit[0]),
                        Integer.parseInt(liniaSplit[1]),
                        Integer.parseInt(liniaSplit[4]));
                zadania.add(zad);
                czasZadanMinPierwszaMaszyna += zad.getCzasOp0();
                czasZadanMinDrugaMaszyna += zad.getCzasOp1();
                
            }
            
            // na podstawie tablicy sprawdzamy, żeby przerwy na siebie nie nachodziły
            boolean tablica1[] = new boolean[czasZadanMinPierwszaMaszyna];
            boolean tablica2[] = new boolean[(int)(czasZadanMinDrugaMaszyna*1.2)];
            
            int nrPrzerwania = 0;

            
            
            
            //losowanie przerwan dla pierwszej maszyny
            while(nrPrzerwania < liczbaPrzerwanPierwsza) {
                System.out.println("nrPrzerwania start " + nrPrzerwania);
            
                int startPrzerwania = random.nextInt(tablica1.length) + 1;
                int czasTrwaniaPrzerwy = random.nextInt(maxCzasPrzerwania) + minCzasPrzerwania;
                
                if(startPrzerwania + czasTrwaniaPrzerwy > tablica1.length ) {
                    continue;
                    
                }
                
                int tempCzas = startPrzerwania;
                boolean blad = false;
                // sprawdzamy czy wylosowane przerwanie nie nachodzi na inne
                while(tempCzas < startPrzerwania+czasTrwaniaPrzerwy) {
                    if(tempCzas > tablica2.length - 1 ) break;
                    if( tablica1[tempCzas] || tablica2[tempCzas] ) {
                        blad = true;
                        break;
                    }
                    tempCzas++;
                }
                
                // jeśli wylosowane przerwanie może znajdować się na maszynie to je dodajemy
                if(!blad) { 
                    tempCzas = startPrzerwania;
                    while(tempCzas < startPrzerwania+czasTrwaniaPrzerwy) {
                        tablica1[tempCzas] = true;
                        if(tempCzas < tablica2.length - 1) {
                                tablica2[tempCzas] = true;
                        }
                        tempCzas++;
                    }
                    System.out.println(nrPrzerwania);
                    out.write(nrPrzerwania + "; 0; " + czasTrwaniaPrzerwy + "; " + startPrzerwania + "; \n");
                    nrPrzerwania++;    
                    
                }
            }

             
            //losowanie przerwan dla drugiej maszyny
            while(nrPrzerwania < liczbaPrzerwan) {
                System.out.println("nrPrzerwania start2 " + nrPrzerwania);
                int startPrzerwania = random.nextInt(tablica2.length) + 1;
                int czasTrwaniaPrzerwy = random.nextInt(maxCzasPrzerwania) + minCzasPrzerwania;
                
                if(startPrzerwania + czasTrwaniaPrzerwy > tablica2.length ) {
                    continue;
                }
                
                System.out.println("dlugosc tab1: " + tablica1.length);
                System.out.println("dlugosc tab2: " + tablica2.length);
                int tempCzas = startPrzerwania;
                boolean blad = false;
                // sprawdzamy czy wylosowane przerwanie nie nachodzi na inne
                while(tempCzas < startPrzerwania+czasTrwaniaPrzerwy) {
                    if(tempCzas > tablica1.length - 1) break;
                    System.out.println(tempCzas);
                    if( tablica1[tempCzas] || tablica2[tempCzas] ) {
                        blad = true;
                        break;
                    }
                    tempCzas++;
                }
                
                // jeśli wylosowane przerwanie może znajdować się na maszynie to je dodajemy
                if(!blad) {
                    tempCzas = startPrzerwania;
                    while(tempCzas < startPrzerwania+czasTrwaniaPrzerwy) {
                        if(tempCzas < tablica1.length - 1) {
                                tablica1[tempCzas] = true;
                        }
                        tablica2[tempCzas] = true;
                        tempCzas++;
                    }
                    System.out.println(nrPrzerwania);
                    out.write(nrPrzerwania + "; 1; " + czasTrwaniaPrzerwy + "; " + startPrzerwania + "; \n");
                    nrPrzerwania++;    
                }
            } 
           
            out.write("*** EOF ***");
            out.close();
        }
    }
    
    
}
