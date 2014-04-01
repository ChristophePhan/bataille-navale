/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Profil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * SuppressionPartieController
 * @author Tristan
 */
public class SuppressionPartieController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////

    
    private Profil _profil;
    private String _idPartie;

    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class SuppressionPartieController
     * @param profil profil dont on souhaite supprimer la partie
     * @param idPartie id de la partie a supprimer
     */
    public SuppressionPartieController(Profil profil, String idPartie) {
        
        super();
        this._profil = profil;
        this._idPartie = idPartie;
        
    } // SuppressionPartieController(Profil profil, String idPartie)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous réellement supprimer la partie ?");
        if(dialogResult == JOptionPane.YES_OPTION){
        
            this._profil.supprimerPartie(this._idPartie);
            
        }
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class SuppressionPartieController