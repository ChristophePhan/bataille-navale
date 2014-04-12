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
import window.main.BatailleNavale;

/**
 * ChargerPartieController
 * @author Christophe
 */
public class ChargerPartieController implements ActionListener {

    private Partie p;
    private BatailleNavale bn;
    private Jeu j;
    private Profil pr;

    public ChargerPartieController(Profil pr, Partie p, BatailleNavale bn, Jeu j) {
        
        this.p = p;
        this.bn = bn;
        this.j = j;
        this.pr = pr;
        
    } // ChargerPartieController(Profil pr,Partie p, BatailleNavale bn, Jeu j)

    @Override
    public void actionPerformed(ActionEvent e) {
        
        bn.getPopupPartie().setVisible(false);
        this.j.setPartieCourante(p);
        this.j.setProfilCourant(pr);
        p.addObserver(bn);
        p.jouerPartie();
        
    } // actionPerformed(ActionEvent e)

} // class ChargerPartieController
