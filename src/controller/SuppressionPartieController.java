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
 * SuppressionPartieController
 * @author Tristan
 */
public class SuppressionPartieController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private String _idPartie;

    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class SuppressionPartieController
     * @param idPartie id de la partie a supprimer
     */
    public SuppressionPartieController(String idPartie) {
        
        super();
        this._idPartie = idPartie;
        
    } // SuppressionPartieController(String idPartie)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous réellement supprimer la partie ?");
        if(dialogResult == JOptionPane.YES_OPTION){
        
            DAOFactory.getInstance().getDAO_Sauvegarde().removePartie(this._idPartie);
            
        }
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class SuppressionPartieController