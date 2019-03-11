/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instancjeOrazRozwiazania;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Generowanie instancji
 */
public class GeneratorInstancji {
    
    // ilość instancji
    private static final int liczbaInstancji = 20;
    private static final Random random = new Random();
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        for (int nrInstancji = 0; nrInstancji < liczbaInstancji; nrInstancji++ ) {
            
            // parametry instancji
            String plik = "instancje/roznicaDlugosci/roznica30/instancja" + nrInstancji + ".problem";
            int liczbaZadan = 50;
            int minLiczbaPrzerwan = liczbaZadan/10;
            int maxLiczbaPrzerwan = minLiczbaPrzerwan;    // + minLiczbaPrzerwan
            int maxDlugoscOperacji = 30;
            int minDlugoscOperacji = 1;
            int maxCzasRozpoczecia = 5;
            int maxCzasPrzerwania = 10;
            int minCzasPrzerwania = 5;
            
            int liczbaPrzerwanPierwsza = random.nextInt(maxLiczbaPrzerwan) + minLiczbaPrzerwan;
            int liczbaPrzerwanDruga = random.nextInt(maxLiczbaPrzerwan) + minLiczbaPrzerwan;
            int liczbaPrzerwan = liczbaPrzerwanPierwsza + liczbaPrzerwanDruga;
            
            int czasZadanMinPierwszaMaszyna = 0;
            int czasZadanMinDrugaMaszyna = 0;
            
            
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
            out.write("**** " + Integer.toString(nrInstancji) + " ****\n");
            out.write(Integer.toString(liczbaZadan) + "\n");
            
            // losowanie parametrów zadań
            for( int nrZadania = 0; nrZadania < liczbaZadan; nrZadania++) {
                
                System.out.println("zad:" + nrZadania);
                int dlugoscOperacji1 = random.nextInt(maxDlugoscOperacji)+minDlugoscOperacji;
                czasZadanMinPierwszaMaszyna += dlugoscOperacji1;
                int dlugoscOperacji2 = random.nextInt(maxDlugoscOperacji)+minDlugoscOperacji;
                czasZadanMinDrugaMaszyna += dlugoscOperacji2;
                int czasRozpoczeciaZadania = random.nextInt(maxCzasRozpoczecia) + 1;
                
                out.write(dlugoscOperacji1 + "; " + dlugoscOperacji2 + "; 0; 1; " + czasRozpoczeciaZadania + "; \n");          
            }
            
            boolean tablica1[] = new boolean[czasZadanMinPierwszaMaszyna];
            boolean tablica2[] = new boolean[(int)(czasZadanMinDrugaMaszyna*1.2)];
            
            int nrPrzerwania = 0;

            
            
            
            // losowanie przerwan dla pierwszej maszyny
            while(nrPrzerwania < liczbaPrzerwanPierwsza) {
                System.out.println("nrPrzerwania start " + nrPrzerwania);
            
                int startPrzerwania = random.nextInt(tablica1.length) + 1;
                int czasTrwaniaPrzerwy = random.nextInt(maxCzasPrzerwania) + minCzasPrzerwania;
                
                if(startPrzerwania + czasTrwaniaPrzerwy > tablica1.length ) {
                    System.out.println("test");
                    continue;
                    
                }
                
                System.out.println("wylosowano czas" + startPrzerwania);
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
                    System.out.println("wchodze do while");
                    while(tempCzas < startPrzerwania+czasTrwaniaPrzerwy) {
                        System.out.println("jestem w while");
                        tablica1[tempCzas] = true;
                        if(tempCzas < tablica2.length - 1) {
                                tablica2[tempCzas] = true;
                        }
                        tempCzas++;
                    }
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
