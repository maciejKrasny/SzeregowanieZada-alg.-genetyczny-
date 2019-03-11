/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glowne;

import instancjeOrazRozwiazania.OdczytIZapis;
import java.io.BufferedWriter;
import java.io.FileWriter;
import ogolne.Przerwanie;
import ogolne.Zadanie;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class Main {
    
    public static int iloscZadan;
    public static int iloscInstancji = 20;
    public static List <Zadanie> zadania;
    public static List <Przerwanie> przerwania;
    public static Gen najlepszeRozw = null;
    public static int najlepszyCzas;
    public static int sredniLosowy;
   
    public static void main(String[] args) throws IOException {
    
    //strojenieMutacji();
    //strojeniePrzedzialu();
    //strojeniePopulacji();
    
    Genetyczny algGen = new Genetyczny();
    OdczytIZapis odczyt = new OdczytIZapis();
    String sciezka;

    
  // ścieżka określa gdzie można znaleźć instancje jaką chcemy wykonać - i w folderze o tej samej nazwie tylko że w folderze rozwiązania zostanie utworzone rozwiązanie
  
//    sciezka = "roznicaDlugosci/roznica0/instancja";
//    for (int nrInstancji = 0; nrInstancji < iloscInstancji; nrInstancji++) {
//            zadania = new ArrayList<>();
//            przerwania = new ArrayList<>();            
//            odczyt.odczytajSciezka(sciezka, nrInstancji);
//            najlepszyCzas = algGen.genetyczny();
//            System.out.println("*****" + nrInstancji + " " + najlepszyCzas + " ******" );
//            odczyt.zapisSciezka(sciezka, nrInstancji);
//        }
    
    }
    
    /*
    * strojenie czasu
    */
//    private static void czas() throws IOException {
//        OdczytIZapis odczyt = new OdczytIZapis();
//        String plik = "strojenie/czas2.txt";
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
//        List <Integer> tablicaWynikow; 
//            
//            for (int i = 0; i < 10; i++) {
//                zadania = new ArrayList<>();
//                przerwania = new ArrayList<>();
//                odczyt.odczytaj(i);
//                Genetyczny algGen = new Genetyczny();
//               
//                
//                System.out.println(" instancja: " + i);
//            
//                tablicaWynikow = algGen.genetyczny();
//                for (Integer integer : tablicaWynikow) {
//                    out.write(integer + "; ");
//                }
//                tablicaWynikow.clear();
//                out.write("\n");
//            }
//
//        out.close();
//        
//    }
    
    
    /**
    * Strojenie parametru przedziału
    */
    private static void strojeniePrzedzialu() throws IOException {
        OdczytIZapis odczyt = new OdczytIZapis();
        String plik = "strojenie/przedzial_czas3.txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
        
        for(int parametr = 1; parametr < 10; parametr++) {
            System.out.println("************" + parametr);
            out.write(Integer.toString(parametr*5)+"; ");

            for (int i = 0; i < 20; i++) {
                zadania = new ArrayList<>();
                przerwania = new ArrayList<>();
                odczyt.odczytaj(i);
                Genetyczny algGen = new Genetyczny();
                algGen.setWielkoscPrzedzialu(parametr*5);
                
                System.out.println(algGen.getWielkoscPrzedzialu() + " instancja: " + i);
            
                najlepszyCzas = algGen.genetyczny();
                out.write(najlepszyCzas + "; ");
            }
            
            out.write("\n");
        }
        out.close();
    }
    
    /**
    * Strojenie parametru populacji
    */
    private static void strojeniePopulacji() throws IOException {
        OdczytIZapis odczyt = new OdczytIZapis();
        String plik = "strojenie/populacja_czas3.txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
        
        for(int parametr = 0; parametr < 11; parametr++) {
            System.out.println("**********" + parametr);
            out.write(Integer.toString(parametr*100)+"; ");
            
            
            for (int i = 0; i < 20; i++) {
                zadania = new ArrayList<>();
                przerwania = new ArrayList<>();
                odczyt.odczytaj(i);
                Genetyczny algGen = new Genetyczny();
                algGen.setWielkoscPopulacji(parametr*100);
                if(parametr == 0) {
                    algGen.setWielkoscPopulacji(50);
                }
                
                System.out.println(algGen.getWielkoscPopulacji() + " instancja: " + i);
            
                najlepszyCzas = algGen.genetyczny();
                out.write(najlepszyCzas + "; ");
            }
            
            out.write("\n");
        }
        out.close();
    }
       
    /**
    * Strojenie parametru mutacji
    */
    private static void strojenieMutacji() throws IOException {
        
        OdczytIZapis odczyt = new OdczytIZapis();
        String plik = "strojenie/mutacja_czas3.txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
        
        for(int parametr = 0; parametr < 16; parametr++) {
            System.out.println("************" + parametr);
            out.write(Double.toString((double)parametr/10)+"; ");
            
            for (int i = 0; i < 20; i++) {
                System.out.println(parametr + " instancja: " + i);
                zadania = new ArrayList<>();
                przerwania = new ArrayList<>();
                odczyt.odczytaj(i);
                Genetyczny algGen = new Genetyczny();
                
                algGen.setPrawdopodobienstwoMutacji((double)parametr/10);
                if(parametr > 10) {
                    algGen.setPrawdopodobienstwoMutacji((double)(parametr%10)*0.01);
                }

                System.out.println(algGen.getPrawdopodobienstwoMutacji() + " instancja: " + i);
                
                najlepszyCzas = algGen.genetyczny();
                
                
                
                out.write(najlepszyCzas + "; ");
            }
            
            out.write("\n");
        }
        out.close();
    }
    
    /**
    * Komparator do ustawiania operacji w kolejności ich numeru zadania - używany przy przeliczaniu funkcji celu
    */
    static class KomparatorOperacji implements Comparator<Integer[]> {
    @Override
    public int compare(Integer[] o1, Integer[] o2) {
        return o1[0] - o2[0];
    }
    }
}
