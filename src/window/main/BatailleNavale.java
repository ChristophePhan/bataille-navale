/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package window.main;

import bataille_navale.Case;
import bataille_navale.Jeu;
import bataille_navale.Joueur;
import bataille_navale.JoueurHumain;
import bataille_navale.JoueurMachine;
import bataille_navale.Parametre;
import bataille_navale.Partie;
import bataille_navale.Profil;
import bataille_navale.TailleGrille;
import controller.AfficherPartiesController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
            this.jLabel5.setVisible(true);
        
        } else {
            
            // Masque le label qui signal qu'aucun profil n'est disponible
            this.jLabel5.setVisible(false);
            // Affichage des profils disponibles
            int nbProfils = DAOFactory.getInstance().getDAO_Sauvegarde().getAllProfils().size();
            FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
            this.listeProfils.removeAll();
            this.listeProfils.setPreferredSize(new Dimension(w,h/2));
            this.listeProfils.setLayout(fl);
            
            Iterator iterator = DAOFactory.getInstance().getDAO_Sauvegarde()
                    .getAllProfils().keySet().iterator();
            while(iterator.hasNext()) {
                
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
        jButton3 = new javax.swing.JButton();
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
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        listeProfils = new javax.swing.JPanel();

        popupNouveauProfil.setMinimumSize(new java.awt.Dimension(400, 220));
        popupNouveauProfil.setResizable(false);
        popupNouveauProfil.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupNouveauProfilWindowClosing(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
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

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jButton2.setText("Ajouter le profil");
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
                .addGroup(popupNouveauProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(saisieNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(popupNouveauProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(erreurNomProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupNouveauProfilLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(125, 125, 125))
        );
        popupNouveauProfilLayout.setVerticalGroup(
            popupNouveauProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupNouveauProfilLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(saisieNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(erreurNomProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        popupParties.setSize(new java.awt.Dimension(500, 350));
        popupParties.setResizable(false);
        popupParties.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupPartiesWindowClosing(evt);
            }
        });

        nomProfil.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        nomProfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomProfil.setText("NOM");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Mes parties en cours");

        jButton3.setText("Nouvelle partie");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
            .addGroup(popupPartiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(popupPartiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelParties, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupPartiesLayout.createSequentialGroup()
                .addGap(0, 179, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
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
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        popupParametres.setSize(new java.awt.Dimension(500, 350));
        popupParametres.setResizable(false);
        popupParametres.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupParametresWindowClosing(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Paramètres de la nouvelle partie");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Nombre de cases");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Époque");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Difficultée");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Placement des cases");

        jRadioButtonManuel.setText("Manuel");

        jRadioButtonAleatoire.setText("Aleatoire");

        jButtonJouer.setText("Jouer");
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
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(popupParametresLayout.createSequentialGroup()
                        .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxTailleGrilles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131))
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxDifficultees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxEpoques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(popupParametresLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonManuel)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonAleatoire))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupParametresLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonJouer, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)))
                        .addGap(0, 21, Short.MAX_VALUE)))
                .addContainerGap())
        );
        popupParametresLayout.setVerticalGroup(
            popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupParametresLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTailleGrilles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxEpoques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxDifficultees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(popupParametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jRadioButtonManuel)
                    .addComponent(jRadioButtonAleatoire))
                .addGap(18, 18, 18)
                .addComponent(jButtonJouer)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 72)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Battleship");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Devenez le maître des océans !");

        jButton1.setText("Ajouter un profil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 50)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Aucun profil disponible");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(listeProfils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(47, 47, 47))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(217, 217, 217)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(218, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Permet d'afficher la fenetre permettant de creer un nouveau profil
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        this.popupNouveauProfil.setLocationRelativeTo(null);
        this.erreurNomProfil.setVisible(false);
        this.setEnabled(false);
        this.popupNouveauProfil.setVisible(true);
        this.saisieNomProfil.requestFocus();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
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
            this.erreurNomProfil.setText("Le nom de profil ne doit pas être vide.");
            this.erreurNomProfil.setVisible(true);
        } else {
          
            // Creation et sauvegrade du profil
            Profil newProfil = new Profil(this.saisieNomProfil.getText());
            DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(newProfil);
            this.initialisation();
            this.popupNouveauProfil.setVisible(false);
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
                
                // Bouton permettant de supprimer la partie
                JButton remove = new JButton("SUPR");
                
                final Partie p = (Partie) parties.get(iterator.next());
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
                this.panelParties.add(remove);
                
                // Bouton permettant d'acceder a la partie
                JButton partie = new JButton("Partie " + num);
                partie.addActionListener(new AfficherPartiesController(this._jeu,profil));
                partie.setPreferredSize(new Dimension(this.panelParties.getWidth()/5,30));
                this.panelParties.add(partie);

                num++;
                
            }
            
        }
        
        // Affichage du popup
        this.panelParties.updateUI();
        this.popupParties.setLocationRelativeTo(null);
        this.popupParties.setVisible(true);
        
    } // afficherPopupParties(final Profil profil)
    
    
    /**
     * Permet de choisir les parametres de la nouvelle partie
     * @param evt 
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

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
        this.popupParties.setVisible(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    
    /**
     * Permet de reautoriser les actions sur la fenetre principale
     * @param evt 
     */
    private void popupPartiesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupPartiesWindowClosing
        
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupPartiesWindowClosing

    
    /**
     * Reautorise le clique sur la fenetre prinicpale
     * @param evt 
     */
    private void popupNouveauProfilWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupNouveauProfilWindowClosing
       
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
        Joueur humain = new JoueurHumain(parametre, this._jeu.getProfilCourant().getNom());
        Joueur IA = new JoueurMachine(parametre, "Skynet");
        Partie partie = null;
        humain.positionnementAleatoire();
        IA.positionnementAleatoire();
        if(this.jRadioButtonAleatoire.isSelected()) {
            
            // Placement aleatoire
            partie = new Partie(parametre, humain, IA, true);
            partie.initialisationPorteeCases();
            
        } else {
            
            // Placement manuel
            partie = new Partie(parametre, humain, IA, false);
            
        }
        this._jeu.getProfilCourant().ajouterNouvellePartie(partie);
        partie.addObserver(this);
        this._jeu.setPartieCourante(partie);
        
        // Permet d'afficher les cases a portee de tir et de lancer la partie
        partie.jouerPartie();
        
    }//GEN-LAST:event_jButtonJouerActionPerformed

    
    /**
     * Reautorise le clique sur la fenetre prinicpale
     * @param evt 
     */
    private void popupParametresWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupParametresWindowClosing
       
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
    private javax.swing.JLabel erreurNomProfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonJouer;
    private javax.swing.JComboBox jComboBoxDifficultees;
    private javax.swing.JComboBox jComboBoxEpoques;
    private javax.swing.JComboBox jComboBoxTailleGrilles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButtonAleatoire;
    private javax.swing.JRadioButton jRadioButtonManuel;
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
                this.popupParametres.setVisible(false);
                this.setVisible(false);
                break;
            
        }
        
    } // update(Observable o, Object arg)


} // class BatailleNavale
