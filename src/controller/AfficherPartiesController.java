/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Jeu;
import bataille_navale.Profil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AfficherPartiesController
 * @author Tristan
 */
public class AfficherPartiesController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private Jeu _jeu;
    private Profil _profil;


    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class AfficherPartiesController
     * @param jeu main
     * @param profil profil contenant les parties a afficher
     */
    public AfficherPartiesController(Jeu jeu, Profil profil) {
        
        super();
        this._jeu = jeu;
        this._profil = profil;
        
    } // AfficherPartiesController(Jeu jeu, Profil profil)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(final ActionEvent ae) {
       
        this._jeu.afficherParties(this._profil);
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class AfficherPartiesController