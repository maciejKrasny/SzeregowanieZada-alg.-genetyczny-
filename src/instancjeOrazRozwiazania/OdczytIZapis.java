package instancjeOrazRozwiazania;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ogolne.Element;
import ogolne.Przerwanie;
import ogolne.Zadanie;
import glowne.Main;
import glowne.Main;

/**
 *
 * @author PC
 */
public class OdczytIZapis {
    
    private List <Element> rozwiazanieMaszyna0 = new ArrayList<>();
    private List <Element> rozwiazanieMaszyna1 = new ArrayList<>();
    
    private int liczbaPrzerwM1 = 0;
    private int czasPrzerwM1 = 0;
    private int liczbaPrzerwM2 = 0;
    private int czasPrzerwM2 = 0;
    
    private int liczbaPrzerwIdleM1 = 0;
    private int czasPrzerwIdleM1 = 0;
    private int liczbaPrzerwIdleM2 = 0;
    private int czasPrzerwIdleM2 = 0;
    
    /**
     * odczytanie instancji
     * @param nrInstancji numer odczytywanej instancji
     * @throws IOException
     */
    public void odczytaj(int nrInstancji) throws IOException {
        String plik = "instancje/strojenie/instancja" + nrInstancji + ".problem";
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(plik));
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        
        String linia;
        String liniaSplit[];
        in.readLine();
        Main.iloscZadan = Integer.parseInt(in.readLine());
        
        // odczytywanie zadań
        for(int nrZad = 0; nrZad < Main.iloscZadan; nrZad++) {
            linia = in.readLine();
            linia = linia.replaceAll("\\s","");
            liniaSplit = linia.split(";");
            Zadanie zad = new Zadanie(nrZad, 
                    Integer.parseInt(liniaSplit[0]),
                    Integer.parseInt(liniaSplit[1]),
                    Integer.parseInt(liniaSplit[4]));
            Main.zadania.add(zad);          
        }
        
        // odczytywanie przerwań
        while(!(linia = in.readLine()).equals("*** EOF ***")) {
            linia = linia.replaceAll("\\s","");
            liniaSplit = linia.split(";");
            Przerwanie przerwa = new Przerwanie(
                    Integer.parseInt(liniaSplit[0]), 
                    Integer.parseInt(liniaSplit[1]), 
                    Integer.parseInt(liniaSplit[2]), 
                    Integer.parseInt(liniaSplit[3]));
            Main.przerwania.add(przerwa);
        }    
        in.close();
        
        // sortujemy przerwania - najpierw przerwania na pierwszej maszynie po kolei, potem te na drugiej
        Collections.sort(Main.przerwania, new KomparatorCzasStartuPrzerwania());
    }
    
    /**
     * odczytanie instancji za pomocą podanej ścieżki
     * @param sciezka gdzie jest plik
     * @param nrInstancji numer odczytywanej instancji
     * @throws IOException
     */
    public void odczytajSciezka(String sciezka, int nrInstancji) throws IOException {
        String plik = "instancje/" + sciezka + nrInstancji + ".problem";
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(plik));
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        
        String linia;
        String liniaSplit[];
        in.readLine();
        Main.iloscZadan = Integer.parseInt(in.readLine());
        
        // odczytywanie zadań
        for(int nrZad = 0; nrZad < Main.iloscZadan; nrZad++) {
            linia = in.readLine();
            linia = linia.replaceAll("\\s","");
            liniaSplit = linia.split(";");
            Zadanie zad = new Zadanie(nrZad, 
                    Integer.parseInt(liniaSplit[0]),
                    Integer.parseInt(liniaSplit[1]),
                    Integer.parseInt(liniaSplit[4]));
            Main.zadania.add(zad);          
        }
        
        // odczytywanie przerwań
        while(!(linia = in.readLine()).equals("*** EOF ***")) {
            linia = linia.replaceAll("\\s","");
            liniaSplit = linia.split(";");
            Przerwanie przerwa = new Przerwanie(
                    Integer.parseInt(liniaSplit[0]), 
                    Integer.parseInt(liniaSplit[1]), 
                    Integer.parseInt(liniaSplit[2]), 
                    Integer.parseInt(liniaSplit[3]));
            Main.przerwania.add(przerwa);
        }    
        in.close();
        Collections.sort(Main.przerwania, new KomparatorCzasStartuPrzerwania());
    }
    
    /**
     * zapis rozwiązania
     * @param nrInstancji numer instancji, której rozwiązanie zapisujemy
     * @throws IOException
     */
    public void zapis(int nrInstancji) throws IOException {
        
        rozwiazanieMaszyna0 = new ArrayList<>();
        rozwiazanieMaszyna1 = new ArrayList<>();
        liczbaPrzerwM1 = 0;
        czasPrzerwM1 = 0;
        liczbaPrzerwM2 = 0;
        czasPrzerwM2 = 0;
        liczbaPrzerwIdleM1 = 0;
        czasPrzerwIdleM1 = 0;
        liczbaPrzerwIdleM2 = 0;
        czasPrzerwIdleM2 = 0;    
        
        
         String plik = "rozwiazania/nowy/instancja" + nrInstancji + ".rozwiazanie";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
            out.write("**** " + Integer.toString(nrInstancji) + " ****\n");
            out.write(Integer.toString(Main.najlepszyCzas) + "; " 
                    + Integer.toString(Main.sredniLosowy) + "\n");
        
        ustawZadania();    
            
        out.write("M1: ");
        for (Element element1 : rozwiazanieMaszyna0) {
            out.write(element1.getNazwa());
            // jeśli element jest operacja to nazwę tworzymy troche inaczej
            if(element1.getId() >= 0) {
                out.write(Integer.toString(element1.getId()));                
            }
            out.write(", " + element1.getCzasStartu() + ", " + element1.getCzasOperacji() + "; ");
        }
        
        out.write("\nM2: ");
        for (Element element2 : rozwiazanieMaszyna1) {
            
            out.write(element2.getNazwa());
            // jeśli element jest operacja to nazwę tworzymy troche inaczej
            if(element2.getId() >= 0) {
                out.write(Integer.toString(element2.getId()));                
            }
            out.write(", " + element2.getCzasStartu() + ", " + element2.getCzasOperacji() + "; ");
        }
        out.write("\n");
        
        out.write(liczbaPrzerwM1 + ", " + czasPrzerwM1 + '\n');
        out.write(liczbaPrzerwM2 + ", " + czasPrzerwM2 + '\n');
        out.write(liczbaPrzerwIdleM1 + ", " + czasPrzerwIdleM1 + '\n');
        out.write(liczbaPrzerwIdleM2 + ", " + czasPrzerwIdleM2 + '\n');
        
        out.write("*** EOF ***");
        out.close();
                        
    }
    
    /**
     * zapis rozwiązania za pomocą podanej ścieżki
     * @param sciezka gdzie jest plik
     * @param nrInstancji numer instancji której rozwiązanie zapisujemy
     * @throws IOException
     */
    public void zapisSciezka(String sciezka, int nrInstancji) throws IOException {
        
        rozwiazanieMaszyna0 = new ArrayList<>();
        rozwiazanieMaszyna1 = new ArrayList<>();
        liczbaPrzerwM1 = 0;
        czasPrzerwM1 = 0;
        liczbaPrzerwM2 = 0;
        czasPrzerwM2 = 0;
        liczbaPrzerwIdleM1 = 0;
        czasPrzerwIdleM1 = 0;
        liczbaPrzerwIdleM2 = 0;
        czasPrzerwIdleM2 = 0;    
        

         String plik = "rozwiazania/" + sciezka + nrInstancji + ".rozwiazanie";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plik)));
            out.write("**** " + Integer.toString(nrInstancji) + " ****\n");
            out.write(Integer.toString(Main.najlepszyCzas) + "; " 
                    + Integer.toString(Main.sredniLosowy) + "\n");
        
        ustawZadania();    
            
        out.write("M1: ");
        for (Element element1 : rozwiazanieMaszyna0) {
            out.write(element1.getNazwa());
            // jeśli element jest operacja to nazwę tworzymy troche inaczej
            if(element1.getId() >= 0) {
                out.write(Integer.toString(element1.getId()));                
            }
            out.write(", " + element1.getCzasStartu() + ", " + element1.getCzasOperacji() + "; ");
        }
        
        out.write("\nM2: ");
        for (Element element2 : rozwiazanieMaszyna1) {
            
            out.write(element2.getNazwa());
            // jeśli element jest operacja to nazwę tworzymy troche inaczej
            if(element2.getId() >= 0) {
                out.write(Integer.toString(element2.getId()));                
            }
            out.write(", " + element2.getCzasStartu() + ", " + element2.getCzasOperacji() + "; ");
        }
        out.write("\n");
        
        out.write(liczbaPrzerwM1 + ", " + czasPrzerwM1 + '\n');
        out.write(liczbaPrzerwM2 + ", " + czasPrzerwM2 + '\n');
        out.write(liczbaPrzerwIdleM1 + ", " + czasPrzerwIdleM1 + '\n');
        out.write(liczbaPrzerwIdleM2 + ", " + czasPrzerwIdleM2 + '\n');
        
        out.write("*** EOF ***");
        out.close();
                        
    }

    /**
     * ustawienie operacji na maszynach wraz z dodaniem przerwań technicznych i przerw, gdy maszyna nic nie robi
     */
    public void ustawZadania() {
        
        int czasAktualny = 0;
        Zadanie tempZad;
        
        // najpierw dodajemy przerwania na maszyny
        for (Przerwanie przerwanie : Main.przerwania) {
            
            if( przerwanie.getNumerMaszyny() == 0 ) {
                rozwiazanieMaszyna0.add(new Element(
                        "maint"+przerwanie.getNrPrzerwania()+"_M1",
                        -1,
                        przerwanie.getCzasStartu(), 
                        przerwanie.getCzas()));
                liczbaPrzerwM1++;
                czasPrzerwM1 += przerwanie.getCzas();
            }
            
            else {
                rozwiazanieMaszyna1.add(new Element(
                        "maint"+przerwanie.getNrPrzerwania()+"_M2",
                        -1,
                        przerwanie.getCzasStartu(), 
                        przerwanie.getCzas()));
                liczbaPrzerwM2++;
                czasPrzerwM2 += przerwanie.getCzas();
                
            }
            
        }
                
        
        int nrPozycji = 0;
        // dodajemy operacje na maszynę pierwszą
        for (Integer nrZadania : Main.najlepszeRozw.getMaszyna0()) {
            tempZad = Main.zadania.get(nrZadania);
            
            // jeśli czas rozpoczęcia zadania (readytime) jest większy od czau aktualnego to staje się on czasem aktualnym
            if (czasAktualny < tempZad.getCzasRozpoczecia()) {
                czasAktualny = tempZad.getCzasRozpoczecia();
            }
            
            // jeśli na maszynie w obecnym miejscu byłoby pusto to po prostu operację dodajemy na listę
            while(rozwiazanieMaszyna0.size() != nrPozycji) { 
                
                // jeśli przerwanie zaczyna się w czasie wykonywania operacji to operacje dodajemy za przerwaniem
                if(rozwiazanieMaszyna0.get(nrPozycji).getCzasStartu() < czasAktualny + tempZad.getCzasOp0()) {
                    czasAktualny = rozwiazanieMaszyna0.get(nrPozycji).getCzasStartu() + rozwiazanieMaszyna0.get(nrPozycji).getCzasOperacji();
                    nrPozycji++;
                }
                else break;
            }
            
            // dodajemy operację pierwszą na pierwszą maszynę w wybranym miejscu jako element
            rozwiazanieMaszyna0.add(nrPozycji, new Element(
                    "o1_",
                    tempZad.getId(),
                    czasAktualny,
                    tempZad.getCzasOp0()));
            
            czasAktualny += tempZad.getCzasOp0();
            nrPozycji++;
        }
        
        czasAktualny = 0;
        nrPozycji = 0;
        
        // dodajemy operacje na drugą maszynę
        for (Integer nrZadania : Main.najlepszeRozw.getMaszyna1()) {
            tempZad = Main.zadania.get(nrZadania);
            
            // sprawdzamy kiedy zakończyła się operacja pierwsza zadania i jeśli po czasie aktualnym to staje się on nowym czasem aktualnym
               for (int j = 0; j < rozwiazanieMaszyna0.size(); j++){
                    if (rozwiazanieMaszyna0.get(j).getNazwa().equals("o1_") 
                            && tempZad.getId()== rozwiazanieMaszyna0.get(j).getId() ) {
                        czasAktualny = Math.max(czasAktualny, 
                                (rozwiazanieMaszyna0.get(j).getCzasStartu()
                                        +rozwiazanieMaszyna0.get(j).getCzasOperacji()));
                        break;
                     }
            
            }
             
            while (rozwiazanieMaszyna1.size() != nrPozycji) { 
                
                // jeśli przerwanie zaczyna się przed skończeniem wykonywania operacji
                if(rozwiazanieMaszyna1.get(nrPozycji).getCzasStartu() < czasAktualny + tempZad.getCzasOp1()) {
                    // czas aktualny może zmienić wartość na czas zakończenia przerwania jeśli przerwanie wystąpiło w trakcie (mogło mieć miejsce o wiele wcześniej niż kiedy zaczęła się operacja)
                    czasAktualny = Math.max(czasAktualny, 
                            rozwiazanieMaszyna1.get(nrPozycji).getCzasStartu() + 
                                    rozwiazanieMaszyna1.get(nrPozycji).getCzasOperacji());
                    nrPozycji++;
                }
                else break;
            }
            
            // dodanie operacji drugiej na maszynę
            rozwiazanieMaszyna1.add(nrPozycji, new Element(
                    "o2_",
                    tempZad.getId(),
                    czasAktualny,
                    tempZad.getCzasOp1()));
        }
        
    
        
        
    // dodanie czasów gdy maszyna nic nie robi (idle) na maszynę pierwszą
    int czasTrwania = 0;
    nrPozycji = 0;
    czasAktualny = 0;
    while(nrPozycji < rozwiazanieMaszyna0.size()) {
        
        // jeśli czas aktualny nie zgadza się z czasem rozpoczęcia operacji / przerwania na maszynie to oznacza to, że maszyna nic w tym czasie nie robiła więc dodajemy przerwę idle
        if(czasAktualny < rozwiazanieMaszyna0.get(nrPozycji).getCzasStartu()) {
            czasTrwania = rozwiazanieMaszyna0.get(nrPozycji).getCzasStartu() - czasAktualny;
            rozwiazanieMaszyna0.add(nrPozycji, new Element(
                    "idle" + liczbaPrzerwIdleM1 + "_M1", 
                    -2,
                    czasAktualny, 
                    czasTrwania));
            liczbaPrzerwIdleM1++;
            czasPrzerwIdleM1 += czasTrwania;
            czasAktualny += czasTrwania;
            nrPozycji++;
            continue;
        }
        
        czasAktualny += rozwiazanieMaszyna0.get(nrPozycji).getCzasOperacji();
        nrPozycji++;
        
        
    }
    
    nrPozycji = 0;
    czasAktualny = 0;
    // dodanie czasów gdy maszyna nic nie robi (idle) na maszynę drugą
    while(nrPozycji < rozwiazanieMaszyna1.size()) {
        
        // jeśli czas aktualny nie zgadza się z czasem rozpoczęcia operacji / przerwania na maszynie to oznacza to, że maszyna nic w tym czasie nie robiła więc dodajemy przerwę idle
        if(czasAktualny < rozwiazanieMaszyna1.get(nrPozycji).getCzasStartu()) {
            czasTrwania = rozwiazanieMaszyna1.get(nrPozycji).getCzasStartu() - czasAktualny;
            rozwiazanieMaszyna1.add(nrPozycji, new Element(
                    "idle" + (liczbaPrzerwIdleM1 + liczbaPrzerwIdleM2) + "_M2", 
                    -2,
                    czasAktualny, 
                    czasTrwania));
            liczbaPrzerwIdleM2++;
            czasPrzerwIdleM2 += czasTrwania;
            czasAktualny += czasTrwania;
            nrPozycji++;
            continue;
        }
        czasAktualny += rozwiazanieMaszyna1.get(nrPozycji).getCzasOperacji();
        nrPozycji++;
    }
    }
    
    
}

    /**
    * Komparator do ustawiania przerwań - najpierw przerwania na pierwszej maszynie po kolei, potem te na drugiej
    */
    class KomparatorCzasStartuPrzerwania implements Comparator<Przerwanie> {
    @Override
    public int compare(Przerwanie p1, Przerwanie p2) {
        if(p1.getNumerMaszyny() == p2.getNumerMaszyny())
            return  p1.getCzasStartu() - p2.getCzasStartu();
        else return p1.getNumerMaszyny() - p2.getNumerMaszyny();
    }
}
