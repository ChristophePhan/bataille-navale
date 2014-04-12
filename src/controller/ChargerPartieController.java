/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bataille_navale.Jeu;
import bataille_navale.Partie;
import bataille_navale.Profil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import stockage.DAOFactory;

/**
 * ChargerPartieController
 * @author Christophe
 */
public class ChargerPartieController implements ActionListener {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////

    
    private Partie p;
    private Jeu j;
    private Profil pr;
    
    
    //////////////////////////// CONSTRUCTEURS ///////////////////////////////
    

    public ChargerPartieController(Profil pr, Partie p, Jeu j) {
        
        this.p = p;
        this.j = j;
        this.pr = pr;
        
    } // ChargerPartieController(Profil pr,Partie p, BatailleNavale bn, Jeu j)
    
    
    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        this.j.setPartieCourante(p);
        this.j.setProfilCourant(pr);
        p.jouerPartie();
        
    } // actionPerformed(ActionEvent e)

    
} // class ChargerPartieController
