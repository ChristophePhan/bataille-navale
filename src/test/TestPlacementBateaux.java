/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import bataille_navale.Epoque;
import bataille_navale.Joueur;
import bataille_navale.JoueurHumain;
import bataille_navale.JoueurMachine;
import bataille_navale.Parametre;
import java.util.HashMap;
import stockage.DAO_Configuration;

/**
 * TestPlacementBateaux
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class TestPlacementBateaux {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DAO_Configuration dAO_Configuration = new DAO_Configuration();
        HashMap<String, Epoque> mapEpoque = dAO_Configuration.getAllEpoques();
        Parametre parametre = new Parametre(10, 10, "Facile", mapEpoque.get("Future"));
        Joueur joueurHumain = new JoueurHumain(parametre, "Stan");
        Joueur joueurMachine = new JoueurMachine(parametre, "IA");
        joueurHumain.positionnementAleatoire();
        for (int i = 0; i < joueurHumain.getCases().size(); i++) {
            String s;
            s = "Case " + i + " : " + joueurHumain.getCases().get(i).getClass().getSimpleName();
            if (!joueurHumain.getCases().get(i).getClass().getSimpleName().equalsIgnoreCase("CaseVide")) {
                s += " | Nom : " + joueurHumain.getCases().get(i).getBateau().getNom();
            }
            System.out.println(s);
        }
    }
    
    // Problème avec des bateaux de taille superieur a 5, pour la grille 10x10

}
