/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glowne;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generator rozwiązań losowych
 * 
 */
public class GeneratorRozwiazan {
    
    /**
     * Generowanie losowego rozwiązania
     * @return wylosowane rozwiązanie
     */
    public Gen generujLosowe() {
        
        // ustawione operacje na pierwszej maszynie
        List<Integer> maszyna0 = new ArrayList<>();
        // ustawione operacje na drugiej maszynie
        List<Integer> maszyna1 = new ArrayList<>();

        List <Integer> numeryZadan = new ArrayList<>();
        for (int i = 0; i < Main.iloscZadan; i++) {
            numeryZadan.add(i);   
        }

        List <Integer> listaMaszyna0 = new ArrayList<>();
        List <Integer> listaMaszyna1 = new ArrayList<>();
        Random random = new Random();
        int ind;
        
        // losowanie operacji dla maszyny pierwszej
        for( int i = 0; i < Main.iloscZadan; i++) {
            ind = random.nextInt(numeryZadan.size());
            listaMaszyna0.add(numeryZadan.get(ind));
            numeryZadan.remove(ind);
        }
        
        for (int i = 0; i < Main.iloscZadan; i++) {
            numeryZadan.add(i);   
        }
        
        //losowanie operacji dla maszyny drugiej
        for( int i = 0; i < Main.iloscZadan; i++) {
            ind = random.nextInt(numeryZadan.size());
            listaMaszyna1.add(numeryZadan.get(ind));
            numeryZadan.remove(ind);
        }
        
        Gen rozwLosowe = new Gen(listaMaszyna0, listaMaszyna1);
        
        return rozwLosowe;
    }     
}
