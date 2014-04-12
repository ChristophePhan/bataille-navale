/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe pour l'IA Difficile avec l'algo en diagonale en Stand Bye
 * @author Samy
 */
public class IADifficile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        DAO_Configuration dAO_Configuration = new DAO_Configuration();
//        HashMap<String, Epoque> mapEpoque = dAO_Configuration.getAllEpoques();
//        Parametre parametre = new Parametre(10, 10, "Facile", mapEpoque.get("Future"));
//        Partie partie = new Partie(parametre, true);
//        Joueur joueurHumain = new JoueurHumain(partie, "Stan");
//        Joueur joueurMachine = new JoueurMachine(partie, "IA");
//
//        joueurHumain.positionnementAleatoire();
//        Case[] liste = new Case[joueurHumain.getCases().size()];
//        joueurHumain.getCases().toArray(liste);
//        System.out.println(joueurHumain.getCases());
//        for (int i = 0; i < parametre.getNbCaseX(); i++) {
//            for (int j = 0; j < parametre.getNbCaseY(); j++) {
//                System.out.print("(" + liste[i + j * parametre.getNbCaseX()].getAbs() + ", " + liste[i + j * parametre.getNbCaseX()].getOrd() + ") ");
//            }
//            System.out.print("\n");
//        }

        int[] tableau = new int[100];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tableau[i + j * 10] = ((i + j) % 2 == 0) ? 0 : 1;
//                System.out.print("[(" + i + ", " + j + ") = " + tableau[i + j * 10] + "] ");
                System.out.print(tableau[i + j * 10] + " ");
            }
            System.out.print("\n");
        }

        Random rand = new Random();
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        int type = tableau[x + y * 10];
        List<Integer> liste = new ArrayList<>();
        for (int i : tableau) {
            if (i == type) {
                liste.add(i);
            }
        }

        while (!liste.isEmpty()) {
            int n = rand.nextInt(liste.size());
            int select = liste.get(n);
            
        }
    }

}
