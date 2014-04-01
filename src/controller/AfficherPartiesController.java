/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bataille_navale.Profil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * AfficherPartiesController
 * @author Tristan
 */
public class AfficherPartiesController implements ActionListener  {
    
    
    ////////////////////////////// VARIABLES /////////////////////////////////


    private Profil _profil;
    private JDialog _popup;
    private JPanel _panel;
    private JLabel _nom;


    //////////////////////////// CONSTRUCTEURS ///////////////////////////////


    /**
     * Constructeur de la class AfficherPartiesController
     * @param profil profil contenant les parties a afficher
     * @param popup fenetre sur laquelle afficher les parties
     * @param panel scroll panel sur lequel afficher la liste des parties
     * @param nom nom du profil
     */
    public AfficherPartiesController(Profil profil, JDialog popup, JPanel panel, JLabel nom) {
        
        super();
        this._profil = profil;
        this._popup = popup;
        this._panel = panel;
        this._nom = nom;
      
    } // AfficherPartiesController(Profil profil, JDialog popup, JScrollPane panel)


    ////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        // Mise a jour du nom du joueur
        this._nom.setText(this._profil.getNom());
        FlowLayout fl = new FlowLayout();
        this._panel.setLayout(fl);
        
        if(this._profil.getParties() == null || this._profil.getParties().isEmpty()) {
           
            // Affichage d'un message signalant qu'aucune partie n'est disponible
            JLabel none = new JLabel("Aucune partie disponible");
            none.setFont(new Font("Helvetica Neue", Font.PLAIN, 36));
            none.setForeground(Color.GRAY);
            this._panel.add(none);
            
        } else {
            
            // On affiche toutes les parties
            for(int i=0;i<this._profil.getParties().size();i++) {
                
                // Bouton permettant de supprimer la partie
                JButton remove = new JButton("SUPR");
                remove.addActionListener(new SuppressionPartieController(this._profil.getParties().get(i).getId()));
                remove.setPreferredSize(new Dimension(this._panel.getWidth()/5,30));
                this._panel.add(remove);
                
                // Bouton permettant d'acceder a la partie
                JButton partie = new JButton("Partie " + i);
                partie.addActionListener(this);
                partie.setPreferredSize(new Dimension(this._panel.getWidth()/5,30));
                this._panel.add(partie);
                
            }
            
        }
        
        // Affichage du popup
        this._popup.setLocationRelativeTo(null);
        this._popup.setVisible(true);
        
    } // actionPerformed(ActionEvent ae)
    
    
} // class AfficherPartiesController