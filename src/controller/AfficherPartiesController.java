/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Partie;
import bataille_navale.Profil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import window.main.BatailleNavale;

/**
 * AfficherPartiesController
 * @author Tristan
 */
public class AfficherPartiesController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private BatailleNavale _batailleNavale;
    private Profil _profil;
    private BatailleNavale _frame;
    private JDialog _popup;
    private JPanel _panel;
    private JLabel _nom;


    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class AfficherPartiesController
     * @param batailleNavale page d'accueil
     * @param profil profil contenant les parties a afficher
     * @param frame
     * @param popup fenetre sur laquelle afficher les parties
     * @param panel scroll panel sur lequel afficher la liste des parties
     * @param nom nom du profil
     */
    public AfficherPartiesController(BatailleNavale batailleNavale, Profil profil, BatailleNavale frame, 
            JDialog popup, JPanel panel, JLabel nom) {
        
        super();
        this._batailleNavale = batailleNavale;
        this._profil = profil;
        this._frame = frame;
        this._popup = popup;
        this._panel = panel;
        this._nom = nom;
        
    } // AfficherPartiesController(BatailleNavale batailleNavale, Profil profil, BatailleNavale frame, 
            //JDialog popup, JPanel panel, JLabel nom)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        // Memorisation du profil courant
        this._batailleNavale.setProfilCourant(this._profil);
        
        // Empeche de cliquer sur la fenetre principale
        this._frame.setEnabled(false);
        
        // Mise a jour du nom du joueur
        FlowLayout fl = new FlowLayout();
        this._panel.removeAll();
        this._panel.setLayout(fl);
        this._nom.setText(this._profil.getNom());
        
        if(this._profil.getParties() == null || this._profil.getParties().isEmpty()) {
           
            // Affichage d'un message signalant qu'aucune partie n'est disponible
            JLabel none = new JLabel("Aucune partie disponible");
            none.setFont(new Font("Helvetica Neue", Font.PLAIN, 36));
            none.setForeground(Color.GRAY);
            this._panel.add(none);
            
        } else {
            
            // On affiche toutes les parties
            HashMap parties = this._profil.getParties();
            Iterator iterator = parties.keySet().iterator();
            int num = 1;
            while(iterator.hasNext()) {
                
                // Bouton permettant de supprimer la partie
                JButton remove = new JButton("SUPR");
                
                Partie p = (Partie) parties.get(iterator.next());
                remove.addActionListener(new SuppressionPartieController(this._profil,p.getId()));
                remove.setPreferredSize(new Dimension(this._panel.getWidth()/5,30));
                this._panel.add(remove);
                
                // Bouton permettant d'acceder a la partie
                JButton partie = new JButton("Partie " + num);
                partie.addActionListener(this);
                partie.setPreferredSize(new Dimension(this._panel.getWidth()/5,30));
                this._panel.add(partie);
                
                num++;
                
            }
            
        }
        
        // Affichage du popup
        this._panel.updateUI();
        this._popup.setLocationRelativeTo(null);
        this._popup.setVisible(true);
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class AfficherPartiesController