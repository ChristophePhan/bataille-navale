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
 * @author Tristan
 */
public class JouerCaseController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////

    
    private Partie _partie;
    private Case _caseTouche;
    private Joueur _joueurCourant;
    private Joueur _joueurAdverse;

    
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
       
        if(this._partie.jouerCase(this._joueurCourant, this._joueurAdverse, this._caseTouche)) {
            
            // Le joueur a gagne
            this._partie.afficherMessageFinPartie("Félicitations ! Vous avez gagné la partie !");
            
        } else {
            
            // Au joueur adverse
            if(this._partie.jouerCase(this._joueurAdverse, this._joueurCourant, null)) {
                
                // Le joueur adverse a gagne
                this._partie.afficherMessageFinPartie("Oups... Vous avez perdu !");
                
            }

        }
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class SuppressionPartieController