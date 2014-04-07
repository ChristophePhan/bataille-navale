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
import javax.swing.JDialog;
import javax.swing.JLabel;
import window.vue.VuePartie;

/**
 * JouerCaseController
 * @author Tristan
 */
public class JouerCaseController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private VuePartie _vuePartie;
    private JDialog _popupVictoire;
    private JLabel _titrePopup;
    private Partie _partie;
    private Case _caseTouche;
    private Joueur _joueurCourant;
    private Joueur _joueurAdverse;

    
    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class JoueurCaseController
     * @param vuePartie fenetre de la partie
     * @param popupVictoire popup de fin de partie
     * @param titrePopup titre de la fenetre de popup
     * @param partie partie ne cours
     * @param caseTouche case choisie par le joueur
     * @param joueurCourant joueur ayant choisi la case
     * @param joueurAdverse joueur vise
     */
    public JouerCaseController(VuePartie vuePartie, JDialog popupVictoire, JLabel titrePopup, Partie partie
            , Case caseTouche, Joueur joueurCourant, Joueur joueurAdverse) {
        
        super();
        this._vuePartie = vuePartie;
        this._popupVictoire = popupVictoire;
        this._titrePopup = titrePopup;
        this._partie = partie;
        this._caseTouche = caseTouche;
        this._joueurCourant = joueurCourant;
        this._joueurAdverse = joueurAdverse;
        
    } // JouerCaseController(VuePartie vuePartie, JDialog popupVictoire, JLabel titrePopup, Partie partie
            //, Case caseTouche, Joueur joueurCourant, Joueur joueurAdverse)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        if(this._partie.jouerCase(this._joueurCourant, this._joueurAdverse, this._caseTouche)) {
            
            // Le joueur a gagne
            this._titrePopup.removeAll();
            this._titrePopup.setText("Félicitations ! Vous avez gagné la partie !");
            this._titrePopup.updateUI();
            this._popupVictoire.setLocationRelativeTo(null);
            this._popupVictoire.setVisible(true);
            this._vuePartie.setEnabled(false);
            
        } else {
            
            // Au joueur adverse
            if(this._partie.jouerCase(this._joueurAdverse, this._joueurCourant, null)) {
                
                // Le joueur adverse a gagne
                this._titrePopup.removeAll();
                this._titrePopup.setText("Oups... Vous avez perdu !");
                this._titrePopup.updateUI();
                this._popupVictoire.setLocationRelativeTo(null);
                this._popupVictoire.setVisible(true);
                this._vuePartie.setEnabled(false);
                
            }

        }
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class SuppressionPartieController