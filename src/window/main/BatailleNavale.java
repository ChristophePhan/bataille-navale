/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package window.main;

import bataille_navale.Jeu;
import bataille_navale.Joueur;
import bataille_navale.JoueurHumain;
import bataille_navale.JoueurMachine;
import bataille_navale.Parametre;
import bataille_navale.Partie;
import bataille_navale.Profil;
import bataille_navale.TailleGrille;
import controller.AfficherPartiesController;
import controller.ChargerPartieController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import stockage.DAOFactory;
import window.vue.VuePartie;

/**
 * BatailleNavale
 * @author Tristan
 */
public class BatailleNavale extends javax.swing.JFrame implements Observer {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////
    
    
    protected Jeu _jeu;
    public static int w = 800;
    public static int h = 600;
    private final ArrayList<TailleGrille> TailleGrilles = (ArrayList<TailleGrille>) DAOFactory.getInstance().getDAO_Parametre().getTaillesGrille();
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    /**
     * Creates new form BatailleNavale
     */
    public BatailleNavale() {
        initComponents();
        
        this._jeu = new Jeu();
        this._jeu.addObserver(this);
        this.initialisation();
        this.setLocationRelativeTo(null);
           
    } // BatailleNavale()
    
    
    /////////////////////////////// FONCTIONS /////////////////////////////////

    
    /**
     * Permet d'intialiser le jeu a partir des fichier de configuration
     * et de sauvegarde
     */
    public void initialisation() {
        
        this.getContentPane().setBackground(Color.WHITE);
        this.popupParties.getContentPane().setBackground(Color.WHITE);
        this.popupNouveauProfil.getContentPane().setBackground(Color.WHITE);
        this.popupParametres.getContentPane().setBackground(Color.WHITE);
        if(DAOFactory.getInstance().getDAO_Sauvegarde().getAllProfils() == null 
                || DAOFactory.getInstance().getDAO_Sauvegarde().getAllProfils().isEmpty()) {
   
            this.listeProfils.setVisible(false);
            this.labelAucunProfil.setVisible(true);
        
        } else {
            
            // Masque le label qui signal qu'aucun profil n'est disponible
            this.labelAucunProfil.setVisible(false);
            // Affichage des profils disponibles
            int nbProfils = DAOFactory.getInstance().getDAO_Sauvegarde().getAllProfils().size();
            FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
            this.listeProfils.removeAll();
            this.listeProfils.setPreferredSize(new Dimension(w,h/2));
            this.listeProfils.setLayout(fl);
            
            int nbProfil = 0;
            Iterator iterator = DAOFactory.getInstance().getDAO_Sauvegarde()
                    .getAllProfils().keySet().iterator();
            while(iterator.hasNext()) {
                
                nbProfil++;
                JPanel profilPanel = new JPanel();
                JPanel legendePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                profilPanel.setBackground(Color.WHITE);
                profilPanel.setPreferredSize(new Dimension(w/(nbProfils+1),h/2-5));
                legendePanel.setBackground(Color.WHITE);
                
                // Profil
                final Profil p = (Profil)DAOFactory.getInstance().getDAO_Sauvegarde().getAllProfils().get(iterator.next());
                JButton profil = new JButton();
                ImageIcon iconImageBleu = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/smiley_bleu.png"))
                    .getImage().getScaledInstance(w/(nbProfils+1)-20, w/(nbProfils+1)-20, Image.SCALE_DEFAULT));
                ImageIcon iconImageGris = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/smiley_gris.png"))
                    .getImage().getScaledInstance(w/(nbProfils+1)-20, w/(nbProfils+1)-20, Image.SCALE_DEFAULT));
                profil.setIcon(iconImageBleu);
                profil.setSelectedIcon(iconImageGris);
                profil.setPreferredSize(new Dimension(w/(nbProfils+1),h/2-100));
                profil.addActionListener(new AfficherPartiesController(this._jeu,p));
                profil.setHorizontalAlignment(SwingConstants.CENTER);
                profil.setBorder(BorderFactory.createEmptyBorder());
                profil.setContentAreaFilled(false);
                profilPanel.add(profil);
                
                // Bouton permettant de supprimer le profil
                JButton supr= new JButton();
                ImageIcon suprImageNoir = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/poubelle_noir.png"))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                ImageIcon suprImageGris = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/poubelle_gris.png"))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                supr.setIcon(suprImageNoir);
                supr.setSelectedIcon(suprImageGris);
                supr.setPreferredSize(new Dimension((w/(nbProfils+1))/5,30));
                supr.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DAOFactory.getInstance().getDAO_Sauvegarde().removeProfil(p);
                        initialisation();
                    }
                });
                supr.setBorder(BorderFactory.createEmptyBorder());
                supr.setContentAreaFilled(false);
                legendePanel.add(supr);
                
                // Nom du profil
                JLabel labelNom = new JLabel(p.getNom());
                labelNom.setHorizontalAlignment(SwingConstants.CENTER);
                labelNom.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
                labelNom.setForeground(Color.DARK_GRAY);
                legendePanel.add(labelNom);
                
                profilPanel.add(legendePanel);
                this.listeProfils.add(profilPanel);
                
            }
            
            // On limite le nombre de profils a 5
            if(nbProfil >= 5) {
                
                this.buttonAjouterProfil.setEnabled(false);
                this.buttonAjouterProfil.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
                this.buttonAjouterProfil.setForeground(Color.LIGHT_GRAY);
                
            } else {
                
                this.buttonAjouterProfil.setEnabled(true);
                this.buttonAjouterProfil.setBorder(new LineBorder(new Color(102,153,255),1));
                this.buttonAjouterProfil.setForeground(new Color(102,153,255));
                
            }
            
            this.listeProfils.updateUI();
            this.listeProfils.setVisible(true);
            
        }
    } // initialisation()

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupNouveauProfil = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        saisieNomProfil = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        erreurNomProfil = new javax.swing.JLabel();
        popupParties = new javax.swing.JDialog();
        nomProfil = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        buttonAjouterPartie = new javax.swing.JButton();
        panelParties = new javax.swing.JPanel();
        popupParametres = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxTailleGrilles = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxEpoques = new javax.swing.JComboBox();
        jComboBoxDifficultees = new javax.swing.JComboBox();
        jRadioButtonManuel = new javax.swing.JRadioButton();
        jRadioButtonAleatoire = new javax.swing.JRadioButton();
        jButtonJouer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonAjouterProfil = new javax.swing.JButton();
        labelAucunProfil = new javax.swing.JLabel();
        listeProfils = new javax.swing.JPanel();

        popupNouveauProfil.setMinimumSize(new java.awt.Dimension(500, 265));
        popupNouveauProfil.setResizable(false);
        popupNouveauProfil.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupNouveauProfilWindowClosing(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 153, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nom du nouveau profil");

        saisieNomProfil.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        saisieNomProfil.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        saisieNomProfil.setToolTipText("");
        saisieNomProfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saisieNomProfilActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 153, 255));
        jButton2.setText("Ajouter le profil");
        jButton2.setAlignmentX(0.5F);
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        erreurNomProfil.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        erreurNomProfil.setForeground(new java.awt.Color(255, 102, 102));
        erreurNomProfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        erreurNomProfil.setText("Désolé, ce nom de profil est déjà utilisé.");

        javax.swing.GroupLayout popupNouveauProfilLayout = new javax.swing.GroupLayout(popupNouveauProfil.getContentPane());
        popupNouveauProfil.getContentPane().setLayout(popupNouveauProfilLayout);
        popupNouveauProfilLayout.setHorizontalGroup(
            popupNouveauProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(saisieNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupNouveauProfilLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupNouveauProfilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(erreurNomProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        popupNouveauProfilLayout.setVerticalGroup(
            popupNouveauProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(saisieNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(erreurNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        popupParties.setSize(new java.awt.Dimension(500, 350));
        popupParties.setFocusTraversalPolicyProvider(true);
        popupParties.setMinimumSize(new java.awt.Dimension(500, 350));
        popupParties.setResizable(false);
        popupParties.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupPartiesWindowClosing(evt);
            }
        });

        nomProfil.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        nomProfil.setForeground(new java.awt.Color(102, 153, 255));
        nomProfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomProfil.setText("NOM");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 153, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Mes parties en cours");

        buttonAjouterPartie.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        buttonAjouterPartie.setForeground(new java.awt.Color(102, 153, 255));
        buttonAjouterPartie.setText("Nouvelle partie");
        buttonAjouterPartie.setAlignmentX(0.5F);
        buttonAjouterPartie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        buttonAjouterPartie.setContentAreaFilled(false);
        buttonAjouterPartie.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonAjouterPartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAjouterPartieActionPerformed(evt);
            }
        });

        panelParties.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelPartiesLayout = new javax.swing.GroupLayout(panelParties);
        panelParties.setLayout(panelPartiesLayout);
        panelPartiesLayout.setHorizontalGroup(
            panelPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPartiesLayout.setVerticalGroup(
            panelPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout popupPartiesLayout = new javax.swing.GroupLayout(popupParties.getContentPane());
        popupParties.getContentPane().setLayout(popupPartiesLayout);
        popupPartiesLayout.setHorizontalGroup(
            popupPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupPartiesLayout.createSequentialGroup()
                .addGap(0, 179, Short.MAX_VALUE)
                .addComponent(buttonAjouterPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
            .addGroup(popupPartiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(popupPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelParties, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        popupPartiesLayout.setVerticalGroup(
            popupPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupPartiesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(nomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(panelParties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAjouterPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        popupParametres.setSize(new java.awt.Dimension(500, 350));
        popupParametres.setResizable(false);
        popupParametres.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupParametresWindowClosing(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 28)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Paramètres de la nouvelle partie");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Nombre de cases");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Époque");

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Difficultée");

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Placement des cases");

        jRadioButtonManuel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jRadioButtonManuel.setForeground(new java.awt.Color(51, 51, 51));
        jRadioButtonManuel.setText("Manuel");

        jRadioButtonAleatoire.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jRadioButtonAleatoire.setForeground(new java.awt.Color(51, 51, 51));
        jRadioButtonAleatoire.setText("Aleatoire");

        jButtonJouer.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jButtonJouer.setForeground(new java.awt.Color(102, 153, 255));
        jButtonJouer.setText("Jouer");
        jButtonJouer.setAlignmentX(0.5F);
        jButtonJouer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        jButtonJouer.setContentAreaFilled(false);
        jButtonJouer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonJouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJouerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout popupParametresLayout = new javax.swing.GroupLayout(popupParametres.getContentPane());
        popupParametres.getContentPane().setLayout(popupParametresLayout);
        popupParametresLayout.setHorizontalGroup(
            popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupParametresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(popupParametresLayout.createSequentialGroup()
                        .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(jButtonJouer, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxTailleGrilles, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupParametresLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupParametresLayout.createSequentialGroup()
                                        .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)))
                                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxEpoques, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxDifficultees, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(popupParametresLayout.createSequentialGroup()
                                        .addComponent(jRadioButtonManuel)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonAleatoire, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        popupParametresLayout.setVerticalGroup(
            popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupParametresLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTailleGrilles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxEpoques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxDifficultees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jRadioButtonManuel)
                    .addComponent(jRadioButtonAleatoire))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jButtonJouer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 72)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BATTLESHIP");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Devenez le maître des océans !");

        buttonAjouterProfil.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        buttonAjouterProfil.setForeground(new java.awt.Color(102, 153, 255));
        buttonAjouterProfil.setText("Ajouter un profil");
        buttonAjouterProfil.setAlignmentX(0.5F);
        buttonAjouterProfil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        buttonAjouterProfil.setContentAreaFilled(false);
        buttonAjouterProfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonAjouterProfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAjouterProfilActionPerformed(evt);
            }
        });

        labelAucunProfil.setFont(new java.awt.Font("Helvetica Neue", 0, 50)); // NOI18N
        labelAucunProfil.setForeground(new java.awt.Color(204, 204, 204));
        labelAucunProfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAucunProfil.setText("Aucun profil disponible");

        listeProfils.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout listeProfilsLayout = new javax.swing.GroupLayout(listeProfils);
        listeProfils.setLayout(listeProfilsLayout);
        listeProfilsLayout.setHorizontalGroup(
            listeProfilsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 794, Short.MAX_VALUE)
        );
        listeProfilsLayout.setVerticalGroup(
            listeProfilsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listeProfils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(buttonAjouterProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelAucunProfil, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(listeProfils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(buttonAjouterProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(217, 217, 217)
                    .addComponent(labelAucunProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(226, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Permet d'afficher la fenetre permettant de creer un nouveau profil
     * @param evt 
     */
    private void buttonAjouterProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAjouterProfilActionPerformed
        
        this.popupNouveauProfil.setLocationRelativeTo(null);
        this.erreurNomProfil.setText("\n");
        this.setEnabled(false);
        this.popupNouveauProfil.setVisible(true);
        this.saisieNomProfil.requestFocus();
        
    }//GEN-LAST:event_buttonAjouterProfilActionPerformed

    
    /********************* JDIALOG - NOUVEAU PROFIL **************************/
    
    
    /**
     * Zone de saisie du nouveau profil
     * @param evt 
     */
    private void saisieNomProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saisieNomProfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saisieNomProfilActionPerformed

    
    /**
     * Permet d'ajouter le nouveau profil
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        if (DAOFactory.getInstance().getDAO_Sauvegarde().isExistingProfil(this.saisieNomProfil.getText())) {

            // Affichage du message d'erreur
            this.erreurNomProfil.setText("Désolé, ce nom de profil est déjà utilisé.");
            this.erreurNomProfil.setVisible(true);

        } else if (this.saisieNomProfil.getText().equals("") || this.saisieNomProfil.getText() == null) {
            
            // Affichage du message d'erreur
            this.erreurNomProfil.setText("Le nom de profil ne peut être vide.");
            this.erreurNomProfil.setVisible(true);
            
        } else {
          
            // Creation et sauvegrade du profil
            Profil newProfil = new Profil(this.saisieNomProfil.getText());
            DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(newProfil);
            this.initialisation();
            this.popupNouveauProfil.dispose();
            this.saisieNomProfil.setText(null);
            this.setEnabled(true);
            this.setVisible(true);
      }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    
    /************************ JDIALOG - PARTIES ******************************/
    
    
    /**
     * Permet d'afficher le popup des parties avec les informations du
     * profil
     * @param profil profil dont on souhaite afficher les parties
     */
    public void afficherPopupParties(final Profil profil) {
        
        // Empeche de cliquer sur la fenetre principale
        this.setEnabled(false);
        
        // Mise a jour du nom du joueur
        this.nomProfil.setText(profil.getNom());
        
        FlowLayout fl = new FlowLayout();
        this.panelParties.removeAll();
        this.panelParties.setLayout(fl);
        this.panelParties.setMinimumSize(new Dimension(this.popupParties.getWidth(),this.popupParties.getHeight()/2-50));
        this.panelParties.setMaximumSize(new Dimension(this.popupParties.getWidth(),this.popupParties.getHeight()/2-50));
        
        if(profil.getParties() == null || profil.getParties().isEmpty()) {
           
            // Affichage d'un message signalant qu'aucune partie n'est disponible
            JLabel none = new JLabel("Aucune partie disponible");
            none.setFont(new Font("Helvetica Neue", Font.PLAIN, 36));
            none.setForeground(Color.GRAY);
            this.panelParties.add(none);
            
        } else {
            
            // On affiche toutes les parties
            HashMap parties = profil.getParties();
            Iterator iterator = parties.keySet().iterator();
            int num = 1;
            while(iterator.hasNext()) {
                
                JPanel panelPartie = new JPanel();
                panelPartie.setBackground(Color.WHITE);
                
                // Bouton permettant de supprimer la partie
                JButton remove = new JButton();
                ImageIcon suprImageNoir = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/poubelle_noir.png"))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                ImageIcon suprImageGris = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/poubelle_gris.png"))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                remove.setIcon(suprImageNoir);
                remove.setSelectedIcon(suprImageGris);
                remove.setBorder(BorderFactory.createEmptyBorder());
                remove.setContentAreaFilled(false);
                
                final Partie p = DAOFactory.getInstance().getDAO_Sauvegarde().getPartie(((Partie)parties.get(iterator.next())).getId(),profil);
                p.addObserver(this);
                remove.addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous réellement supprimer la partie ?");
                        if(dialogResult == JOptionPane.YES_OPTION){

                            profil.supprimerPartie(p.getId());
                            DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(profil);
                            afficherPopupParties(profil);

                        }
                        
                    }
                    
                });
                remove.setPreferredSize(new Dimension(this.panelParties.getWidth()/5,30));
                panelPartie.add(remove);
                
                // Bouton permettant d'acceder a la partie
                JButton partie = new JButton("Partie " + num + " - " + p.getDate());
                partie.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
                partie.setPreferredSize(new Dimension((this.panelParties.getWidth()/5)*3-10,30));
                partie.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
                partie.setContentAreaFilled(false);
                partie.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                partie.addActionListener(new ChargerPartieController(profil, p, _jeu));
                panelPartie.add(partie);

                this.panelParties.add(panelPartie);
                num++;
                
            }
         
            // On limite a 3 le nombre de parties
            if(parties.size() >= 3) {
                
                this.buttonAjouterPartie.setEnabled(false);
                this.buttonAjouterPartie.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
                this.buttonAjouterPartie.setForeground(Color.LIGHT_GRAY);
                
            } else {
                
                this.buttonAjouterPartie.setEnabled(true);
                this.buttonAjouterPartie.setBorder(new LineBorder(new Color(102,153,255),1));
                this.buttonAjouterPartie.setForeground(new Color(102,153,255));
                
            }
            
        }
        
        // Affichage du popup
        this.panelParties.updateUI();
        this.popupParties.setLocationRelativeTo(null);
        this.popupParties.setVisible(true);
        
    } // afficherPopupParties(final Profil profil)
    
    
    /**
     * Permet d'acceder au choix des parametres de la nouvelle partie
     * @param evt 
     */
    private void buttonAjouterPartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAjouterPartieActionPerformed

       this.choisirParametres();
        
    }//GEN-LAST:event_buttonAjouterPartieActionPerformed

    
    /**
     * Permet de choisir les parametres de la nouvelle partie
     */
    public void choisirParametres() {
        
        // Initialisation des tailles de grille
        this.jComboBoxTailleGrilles.removeAllItems();
        for(TailleGrille tg : this.TailleGrilles){
            this.jComboBoxTailleGrilles.addItem(tg.getX()+"x"+tg.getY());
        }
        
        // Initialisation des epoques
        this.jComboBoxEpoques.removeAllItems();
        Iterator i = DAOFactory.getInstance().getDAO_Configuration().getAllEpoques().keySet().iterator();
        while (i.hasNext()) {
            this.jComboBoxEpoques.addItem(i.next());
        }
        
        // Initialisation des difficultees
        this.jComboBoxDifficultees.removeAllItems();
        for(Object diff : DAOFactory.getInstance().getDAO_Parametre().getDifficultees()){
            this.jComboBoxDifficultees.addItem((String)diff);
        }
        
        // Initialisation du mode de placement
        ButtonGroup bgroup = new ButtonGroup();
        this.jRadioButtonAleatoire.setSelected(true);
        bgroup.add(this.jRadioButtonAleatoire);
        bgroup.add(this.jRadioButtonManuel);
        
        this.popupParametres.setLocationRelativeTo(null);
        this.setEnabled(false);
        this.popupParametres.setVisible(true);
        this.popupParties.dispose();
        
    } // choisirParametres()
    
    
    /**
     * Permet de reautoriser les actions sur la fenetre principale
     * @param evt 
     */
    private void popupPartiesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupPartiesWindowClosing
        
        this.popupParties.dispose();
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupPartiesWindowClosing

    
    /**
     * Reautorise le clique sur la fenetre prinicpale
     * @param evt 
     */
    private void popupNouveauProfilWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupNouveauProfilWindowClosing
       
        this.popupNouveauProfil.dispose();
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupNouveauProfilWindowClosing

    
    /**
     * Bouton permettant de demarrer la nouvelle partie
     * @param evt 
     */
    private void jButtonJouerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJouerActionPerformed

        // Recuperation de la taille du tableau choisie
        String[] taille = ((String)this.jComboBoxTailleGrilles.getSelectedItem()).split("x");
        int x = Integer.parseInt(taille[0]);
        int y = Integer.parseInt(taille[1]);
      
        // Recuperation de l'epoque choisie
        String epoque = (String)this.jComboBoxEpoques.getSelectedItem();

        // Recuperation de la difficultee choisie
        String diff = (String)this.jComboBoxDifficultees.getSelectedItem();

        // Recuperation du mode de placement
        Parametre parametre = new Parametre(x, y, diff, DAOFactory.getInstance().getDAO_Configuration().getAllEpoques().get(epoque));
        Partie partie = null;
        if(this.jRadioButtonAleatoire.isSelected()) {
            
            // Placement aleatoire
            partie = new Partie(parametre, true);
            Joueur humain = new JoueurHumain(partie, this._jeu.getProfilCourant().getNom());
            Joueur IA = new JoueurMachine(partie, "Skynet");
            partie.setJ1(humain);
            partie.setJ2(IA);
            humain.positionnementAleatoire();
            IA.positionnementAleatoire();
            partie.initialisationPorteeCases();
            
        } else {
            
            // Placement manuel
            partie = new Partie(parametre, false);
            Joueur humain = new JoueurHumain(partie, this._jeu.getProfilCourant().getNom());
            Joueur IA = new JoueurMachine(partie, "Skynet");
            partie.setJ1(humain);
            partie.setJ2(IA);
            humain.positionnementAleatoire();
            IA.positionnementAleatoire();
            
        }
        
        //this._jeu.getProfilCourant().ajouterNouvellePartie(partie);
        partie.addObserver(this);
        this._jeu.setPartieCourante(partie);
        DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(this._jeu.getProfilCourant());
        // Permet d'afficher les cases a portee de tir et de lancer la partie
        partie.jouerPartie();
        
    }//GEN-LAST:event_jButtonJouerActionPerformed

    
    /**
     * Reautorise le clique sur la fenetre prinicpale
     * @param evt 
     */
    private void popupParametresWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupParametresWindowClosing
       
        this.popupParametres.dispose();
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupParametresWindowClosing

    
    /******************************* MAIN ************************************/ 
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BatailleNavale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BatailleNavale().setVisible(true);
            }
        });
        
    } // main(String args[])
    
    
    //////////////////////// VARIABLES GRAPHIQUES /////////////////////////////
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAjouterPartie;
    private javax.swing.JButton buttonAjouterProfil;
    private javax.swing.JLabel erreurNomProfil;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonJouer;
    private javax.swing.JComboBox jComboBoxDifficultees;
    private javax.swing.JComboBox jComboBoxEpoques;
    private javax.swing.JComboBox jComboBoxTailleGrilles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButtonAleatoire;
    private javax.swing.JRadioButton jRadioButtonManuel;
    private javax.swing.JLabel labelAucunProfil;
    private javax.swing.JPanel listeProfils;
    private javax.swing.JLabel nomProfil;
    private javax.swing.JPanel panelParties;
    private javax.swing.JDialog popupNouveauProfil;
    private javax.swing.JDialog popupParametres;
    private javax.swing.JDialog popupParties;
    private javax.swing.JTextField saisieNomProfil;
    // End of variables declaration//GEN-END:variables

    
    /************** GESTION DE LA MISE A JOUR DE LA FENETRE ******************/
    
    
    @Override
    public void update(Observable o, Object arg) {
      
        switch (arg.toString()) {
            
            case "parties":
                this.popupParties.setLocationRelativeTo(null);
                this.popupParties.setVisible(true);
                this.afficherPopupParties(this._jeu.getProfilCourant());
                break;
                
            case "start": 
                VuePartie plateau = new VuePartie(this, this._jeu, this._jeu.getProfilCourant(), this._jeu.getPartieCourante());
                this._jeu.getPartieCourante().addObserver(plateau);
                // Affiche le plateau de jeu
                plateau.setVisible(true);
                this.popupParties.dispose();
                this.popupParametres.dispose();
                this.dispose();
                break;
            
        }
        
    } // update(Observable o, Object arg)


} // class BatailleNavale
