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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * złożenie wyników wielu instancji w jeden plik
 */
public class ZlozenieWynikow {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String plikZapisu = "rozwiazania/roznicaDlugosci/roznica0/zbiorczeWyniki.txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plikZapisu)));        
        for(int nrInstancji = 0; nrInstancji< 20; nrInstancji++) {
            String plik = "rozwiazania/roznicaDlugosci/roznica0/instancja" + nrInstancji + ".rozwiazanie";
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(plik));
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));

            in.readLine();
            String linia = in.readLine();
            String liniaSplit[];
            linia = linia.replaceAll("\\s","");
            liniaSplit = linia.split(";");
            out.write(liniaSplit[0] + "\n");
            in.close();
        }
        out.close();
        
    }
    
}
