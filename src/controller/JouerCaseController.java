/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Case;
import bataille_navale.Joueur;
import bataille_navale.Partie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JouerCaseController
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class JouerCaseController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////

    
    private final Partie _partie;
    private final Case _caseTouche;
    private final Joueur _joueurCourant;
    private final Joueur _joueurAdverse;

    
    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class JoueurCaseController
     * @param partie partie ne cours
     * @param caseTouche case choisie par le joueur
     * @param joueurCourant joueur ayant choisi la case
     * @param joueurAdverse joueur vise
     */
    public JouerCaseController(Partie partie, Case caseTouche, Joueur joueurCourant, Joueur joueurAdverse) {
        
        super();
        this._partie = partie;
        this._caseTouche = caseTouche;
        this._joueurCourant = joueurCourant;
        this._joueurAdverse = joueurAdverse;
        
    } // JouerCaseController(Partie partie, Case caseTouche, Joueur joueurCourant, Joueur joueurAdverse)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        boolean testPourEgalite = true;
        if(this._partie.jouerCase(this._joueurCourant, this._joueurAdverse, this._caseTouche)) {
            
            // Le joueur a gagne
            this._partie.afficherMessageFinPartie("Vous avez gagné la partie !");
            testPourEgalite = false;
            
        } else {
            
            // Au joueur adverse
            if(this._partie.jouerCase(this._joueurAdverse, this._joueurCourant, null)) {
                
                // Le joueur adverse a gagne
                this._partie.afficherMessageFinPartie("Oups... Votre flotte est coulée !");
                testPourEgalite = false;
                
            }

        }
        
        // On test l'egalite
        if(testPourEgalite && this._partie.testEgalite()) {
            
            // Egalite, fin de la partie
            this._partie.afficherMessageFinPartie("Flotte adverse hors de portée !");
            
        } 
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class SuppressionPartieController