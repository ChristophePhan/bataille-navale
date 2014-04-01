/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Partie;
import bataille_navale.Profil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import stockage.DAOFactory;

/**
 * NouvellePartieController
 * @author Tristan
 */
public class NouvellePartieController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private Partie _partie;

    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class NouvellePartieController
     */
    public NouvellePartieController(Partie partie) {
        
        super();
        this._partie = partie;
        
    } // NouvellePartieController(Partie partie)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class NouvellePartieController