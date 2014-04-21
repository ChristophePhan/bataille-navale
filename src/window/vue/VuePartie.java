/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package window.vue;

import bataille_navale.Case;
import bataille_navale.Jeu;
import bataille_navale.Partie;
import bataille_navale.Profil;
import controller.JouerCaseController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import window.main.BatailleNavale;

/**
 * VuePartie
 * @author Tristan
 */
public class VuePartie extends javax.swing.JFrame implements Observer, KeyListener {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////
    
    
    public BatailleNavale _batailleNavale;
    public Jeu _jeu;
    public Profil _profil;
    public Partie _partie;
    public JPanel etatBateaux;
    public JPanel bateaux;
    public boolean manuel;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////

    
    /**
     * Creates new form VuePartie
     * @param batailleNavale Fenetre d'accueil
     * @param jeu main
     * @param profil profil du joueur qui joue la partie
     * @param partie partie jouee par le joueur
     */
    public VuePartie(BatailleNavale batailleNavale, Jeu jeu, Profil profil, final Partie partie) {
        
        try {
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("stockage/"+partie.getParametre().getEpoque().getNom()+"/"+partie.getParametre().getEpoque().getImage())))));
        } catch (IOException ex) {
            System.err.println("Aucune image de fond n'a ete trouvee");
        }
        
        initComponents();
        this._batailleNavale = batailleNavale;
        this._jeu = jeu;
        this._profil = profil;
        this._partie = partie;
        this.updateBoutonsDifficultee(partie.getParametre().getDifficulte());
        
        this.etatBateaux = new JPanel();
        this.etatBateaux.setOpaque(false);
        this.etatBateaux.setLayout(new BoxLayout(this.etatBateaux, BoxLayout.Y_AXIS));
        this.bateaux = new JPanel();
        this.bateaux.setOpaque(false);
        this.bateaux.setLayout(new GridLayout(this._partie.getParametre().getBateaux(this._partie.getParametre().getEpoque()).size(),1));
        
        this.getContentPane().setBackground(Color.WHITE);
        this.popupQuitterPartie.getContentPane().setBackground(Color.WHITE);
        this.popupVictoire.getContentPane().setBackground(Color.WHITE);
        this.labelEpoque.setText(this._partie.getParametre().getEpoque().getNom());
        this.labelTirsReussis.setText(this._partie.getJ1().getNbTirsGagnant() + "");
        this.labelTirsRates.setText(this._partie.getJ1().getNbTirsPerdant() + "");
        
        // On test si la partie a deja commence ou non
        int num = 0;
        boolean test = false;
        while(num < partie.getJ2().getCases().size() && !test) {
            
            if(partie.getJ2().getCases().get(num).isAPortee()) {
                test = true;
            }
            num++;
            
        }
        
        if(partie.isAutomatique() || test) { 
            
            // Le positionnement est aleatoire, on lance la partie
            this.panelInfosBateauxJoueur.setLayout(new BoxLayout(this.panelInfosBateauxJoueur, BoxLayout.X_AXIS));
            this.buttonJouer.setEnabled(false);
            this.buttonJouer.setVisible(false);
            this.labelRotation1.setVisible(false);
            this.labelRotation2.setVisible(false);
            this.initialisation(); 
            
        } else {
            
            // Le positionnement est manuel, on laisse le joueur placer ses bateaux
            this.initialisation();
            this.labelInstructionsJoueur.setText("Vous pouvez déplacer vos bateaux sur la grille de droite.");
            this.labelInstructionsAdversaire.setText("Cliquez sur 'Jouer !' pour commencer la partie.");
            partie.autoriserDragDropJoueur(true);
            this.buttonJouer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panelInfosBateauxJoueur.setLayout(new BoxLayout(panelInfosBateauxJoueur, BoxLayout.X_AXIS));
                    for(KeyListener kl : getKeyListeners()) {
                        removeKeyListener(kl);
                    }
                    partie.autoriserDragDropJoueur(false);
                    partie.initialisationPorteeCases();
                    initialisation();
                    buttonJouer.setEnabled(false);
                    buttonJouer.setVisible(false);
                    labelRotation1.setVisible(false);
                    labelRotation2.setVisible(false);
                    manuel = true;
                    miseAJourEtatsBateaux();
                }
            });
            // Activation du listener du clavier
            this.addKeyListener(this);
            this.setFocusable(true);
            this.updatePorteeManuel();
            
        }
        
        this.panelInfosBateauxJoueur.add(this.bateaux);
        this.panelInfosBateauxJoueur.add(this.etatBateaux);
        manuel = true;
        this.miseAJourEtatsBateaux();
        this.popupQuitterPartie.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        
    } // VuePartie(BatailleNavale batailleNavale, Jeu jeu, Profil profil, Partie partie)
    
    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    
    
    /**
     * Permet d'initialiser la partie
     */
    public void initialisation() {
        
        this.miseAJourEtatsBateaux();
        
        // Initialisation des instructions 
        this.labelInstructionsJoueur.setText("À vous de jouer ! Cliquez sur une case de la grille adverse.");
        this.labelInstructionsAdversaire.setText("Votre adversaire attend son tour...");
        
        // Initialisation des deux grilles de jeu
        GridLayout gl = new GridLayout(this._partie.getParametre().getNbCaseX(),this._partie.getParametre().getNbCaseY());
        gl.setHgap(0);
        gl.setVgap(0);
        this.plateauJoueurAdverse.removeAll();
        this.plateauJoueurCourant.removeAll();
        this.plateauJoueurAdverse.setLayout(gl);
        this.plateauJoueurCourant.setLayout(gl);

        int numC = 0;
        for(int i=0;i<this._partie.getParametre().getNbCaseX();i++) {
            for(int j=0;j<this._partie.getParametre().getNbCaseY();j++) {
                
                ////////// Grille du joueur
                if(this._partie.getJ1().getCases().get(numC).isEtat() && this._partie.getJ1().getCases().get(numC).getBateau() != null) {
                    
                    // Case bateau touche
                    ImageIcon bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_rouge.png"))
                        .getImage().getScaledInstance(this.plateauJoueurCourant.getWidth()/this._partie.getParametre().getNbCaseX()+1
                                , this.plateauJoueurCourant.getHeight()/this._partie.getParametre().getNbCaseY()+1, Image.SCALE_DEFAULT));
                    this._partie.getJ1().getCases().get(numC).setIcon(bateauImage);
                    
                } else if(this._partie.getJ1().getCases().get(numC).isEtat()) {
                    
                    // Case vide non touchee
                    ImageIcon bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Croix.png"))
                        .getImage().getScaledInstance(this.plateauJoueurCourant.getWidth()/this._partie.getParametre().getNbCaseX()+1
                                , this.plateauJoueurCourant.getHeight()/this._partie.getParametre().getNbCaseY()+1, Image.SCALE_DEFAULT));
                    this._partie.getJ1().getCases().get(numC).setIcon(bateauImage);
                    
                }
                this._partie.getJ1().getCases().get(numC).setCoordonnees(j, i);
                this.plateauJoueurCourant.add(this._partie.getJ1().getCases().get(numC));
                // On resinitialise les cases a portee de tir sur la grille du joueur
                if (this._partie.getJ1().getCases().get(i + j *this._partie.getParametre().getNbCaseX()).getBateau() == null 
                        && !this._partie.getJ1().getCases().get(i + j *this._partie.getParametre().getNbCaseX()).isEtat()) {

                    ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
                    this._partie.getJ1().getCases().get(i + j *this._partie.getParametre().getNbCaseX()).setEnabled(false);
                    this._partie.getJ1().getCases().get(i + j *this._partie.getParametre().getNbCaseX()).setDisabledIcon(bateauImage);

                }
                
                ////////// Grille adverse
                for(ActionListener ac : this._partie.getJ2().getCases().get(numC).getActionListeners()) {
                    this._partie.getJ2().getCases().get(numC).removeActionListener(ac);
                }
                this._partie.getJ2().getCases().get(numC).addActionListener(new JouerCaseController(this._partie, 
                        this._partie.getJ2().getCases().get(numC), this._partie.getJ1(), this._partie.getJ2()));
                this._partie.getJ2().getCases().get(numC).setCoordonnees(j, i);
                
                // On signal que la case est a portee de tir si c'est le cas,
                // sinon on ne peut pas cliquer sur la case
                if(this._partie.getJ2().getCases().get(numC).isAPortee() && !this._partie.getJ2().getCases().get(numC).isEtat()) {
                    
                    // Case a porte
                    ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Case_a_portee.png"));
                    this._partie.getJ2().getCases().get(numC).setIcon(bateauImage);
                    this._partie.getJ2().getCases().get(numC).setDisabledIcon(bateauImage);
                    this._partie.getJ2().getCases().get(numC).setEnabled(true);
                    this._partie.getJ2().getCases().get(numC).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    
                } else if(!this._partie.getJ2().getCases().get(numC).isAPortee()) {
                    
                    // Case hors de porte
                    this._partie.getJ2().getCases().get(numC).setEnabled(false);
                    ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
                    this._partie.getJ2().getCases().get(numC).setDisabledIcon(bateauImage);
                    
                } else if(this._partie.getJ2().getCases().get(numC).isAPortee() && this._partie.getJ2().getCases().get(numC).isEtat()
                        && this._partie.getJ2().getCases().get(numC).getBateau() != null) {
                    
                    // Case bateau touche
                    ImageIcon bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_rouge.png"))
                        .getImage().getScaledInstance(this.plateauJoueurAdverse.getWidth()/this._partie.getParametre().getNbCaseX()+1
                                , this.plateauJoueurAdverse.getHeight()/this._partie.getParametre().getNbCaseY()+1, Image.SCALE_DEFAULT));
                    this._partie.getJ2().getCases().get(numC).setIcon(bateauImage);
                    
                } else {
                    
                    // Case vide touche
                    ImageIcon bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Croix.png"))
                        .getImage().getScaledInstance(this.plateauJoueurAdverse.getWidth()/this._partie.getParametre().getNbCaseX()+1
                                , this.plateauJoueurAdverse.getHeight()/this._partie.getParametre().getNbCaseY()+1, Image.SCALE_DEFAULT));
                    this._partie.getJ2().getCases().get(numC).setIcon(bateauImage);
                    
                }
                this.plateauJoueurAdverse.add(this._partie.getJ2().getCases().get(numC));
                numC++;
                
            }
        }
        this.plateauJoueurAdverse.updateUI();
        this.plateauJoueurCourant.updateUI();
        
        // On empeche les eventuels bateaux coules de tirer si l'utilisateur
        // en a fait le choix
        if(this._partie.getParametre().isMajPortee() && !this.buttonJouer.isEnabled()) {
            
            this.updatePortee();
            
        } 
        
    } // initialisation()
    
    
    /**
     * Permet de mettre a jour la portee des cases lorsqu'un bateau est coule
     * si l'option est activee
     */
    public void updatePortee() {
        
        int x = this._partie.getParametre().getNbCaseX();
        int y = this._partie.getParametre().getNbCaseY();
        
        // Si une case n'est plus a portee de tir, on la desactive
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                
                if (this._partie.getJ1().getCases().get(i + j * x).getBateau() != null 
                        && this._partie.getJ1().getCases().get(i + j * x).getBateau().testBateauCoule()) {

                    // On parcours les cases autour du bateau pour les desactiver
                    int portee = this._partie.getJ1().getCases().get(i + j * x).getBateau().getPortee();
                    for (int W = i - portee; W < (i - portee + 2 * portee) + 1; W++) {
                        for (int H = j - portee; H < (j - portee + 2 * portee) + 1; H++) {

                            if (W >= 0 && W < x && H >= 0 && H < y && !((Case) (this._partie.getJ2().getCases().get(W + H * x))).isEtat()) {

                                ((Case) (this._partie.getJ2().getCases().get(W + H * x))).setPortee(false);
                                ((Case) (this._partie.getJ2().getCases().get(W + H * x))).setEnabled(false);
                                ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
                                    this._partie.getJ2().getCases().get(W + H * x).setDisabledIcon(bateauImage);

                            }

                        }
                    }

                }
                
            }
        }
        
        // On reactive les cases qui ont etes desactivee inutilement
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                
                if (this._partie.getJ1().getCases().get(i + j * x).getBateau() != null 
                        && !this._partie.getJ1().getCases().get(i + j * x).getBateau().testBateauCoule()) {

                    // On parcours les cases autour du bateau pour les activer
                    int portee = this._partie.getJ1().getCases().get(i + j * x).getBateau().getPortee();
                    for (int W = i - portee; W < (i - portee + 2 * portee) + 1; W++) {
                        for (int H = j - portee; H < (j - portee + 2 * portee) + 1; H++) {

                            if (W >= 0 && W < x && H >= 0 && H < y && !((Case) (this._partie.getJ2().getCases().get(W + H * x))).isEtat() 
                                    && !((Case) (this._partie.getJ2().getCases().get(W + H * x))).isAPortee()) {

                                ((Case) (this._partie.getJ2().getCases().get(W + H * x))).setPortee(true);
                                ((Case) (this._partie.getJ2().getCases().get(W + H * x))).setEnabled(true);
                                ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Case_a_portee.png"));
                                    this._partie.getJ2().getCases().get(W + H * x).setIcon(bateauImage);

                            }

                        }
                    }

                }
                
            }
        }
        
    } // updatePortee()
    
    
    /**
     * Permet de mettre a jour la portee des cases lors du placement manuel des 
     * bateaux par le joueur
     */
    public void updatePorteeManuel() {
        
        int x = this._partie.getParametre().getNbCaseX();
        int y = this._partie.getParametre().getNbCaseY();
        
        // Si une case n'est plus a portee de tir, on la desactive
        /*for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                
                if (this._partie.getJ1().getCases().get(i + j * x).getBateau() == null) {

                    ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
                    this._partie.getJ1().getCases().get(i + j * x).setEnabled(false);
                    this._partie.getJ1().getCases().get(i + j * x).setDisabledIcon(bateauImage);

                }
                
            }
        }*/
        
        // On reactive les cases qui ont etes desactivee inutilement
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                
                if (this._partie.getJ1().getCases().get(i + j * x).getBateau() != null) {

                    // On parcours les cases autour du bateau pour les activer
                    int portee = this._partie.getJ1().getCases().get(i + j * x).getBateau().getPortee();
                    for (int W = i - portee; W < (i - portee + 2 * portee) + 1; W++) {
                        for (int H = j - portee; H < (j - portee + 2 * portee) + 1; H++) {

                            if (W >= 0 && W < x && H >= 0 && H < y && !((Case) (this._partie.getJ2().getCases().get(W + H * x))).isEtat() 
                                    && !((Case) (this._partie.getJ2().getCases().get(W + H * x))).isAPortee() 
                                    && ((Case) (this._partie.getJ1().getCases().get(W + H * x))).getBateau() == null) {

                                ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Case_a_portee.png"));
                                this._partie.getJ1().getCases().get(W + H * x).setEnabled(false);
                                this._partie.getJ1().getCases().get(W + H * x).setDisabledIcon(bateauImage);

                            }

                        }
                    }

                }
                
            }
        }
        
    } // updatePorteeManuel()
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupQuitterPartie = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        buttonAnnuler = new javax.swing.JButton();
        buttonNouvellePartie = new javax.swing.JButton();
        buttonEnregistrer = new javax.swing.JButton();
        buttonQuitter = new javax.swing.JButton();
        popupVictoire = new javax.swing.JDialog();
        titrePopupVictoire = new javax.swing.JLabel();
        buttonFinQuitter = new javax.swing.JButton();
        sousTitrePopupVictoire = new javax.swing.JLabel();
        labelEpoque = new javax.swing.JLabel();
        labelInstructionsJoueur = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        plateauJoueurAdverse = new javax.swing.JPanel();
        plateauJoueurCourant = new javax.swing.JPanel();
        labelBattleship = new javax.swing.JLabel();
        labelTitreEpoque = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelFlotteJoueur = new javax.swing.JLabel();
        labelFlotteAdverse = new javax.swing.JLabel();
        flecheFlotteJoueur = new javax.swing.JLabel();
        flecheFlotteAdverse = new javax.swing.JLabel();
        labelInstructionsAdversaire = new javax.swing.JLabel();
        panelInfosBateauxJoueur = new javax.swing.JPanel();
        buttonJouer = new javax.swing.JButton();
        labelRotation1 = new javax.swing.JLabel();
        labelRotation2 = new javax.swing.JLabel();
        labelTitreTirsReussis = new javax.swing.JLabel();
        labelTitreTirsRates = new javax.swing.JLabel();
        labelTirsReussis = new javax.swing.JLabel();
        labelTirsRates = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        buttonFacile = new javax.swing.JButton();
        buttonNormal = new javax.swing.JButton();
        buttonDifficile = new javax.swing.JButton();

        popupQuitterPartie.setSize(new java.awt.Dimension(500, 180));
        popupQuitterPartie.setAlwaysOnTop(true);
        popupQuitterPartie.setMinimumSize(new java.awt.Dimension(500, 180));
        popupQuitterPartie.setResizable(false);
        popupQuitterPartie.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupQuitterPartieWindowClosing(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Êtes-vous sûr de vouloir quitter la partie ?");

        buttonAnnuler.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        buttonAnnuler.setForeground(new java.awt.Color(102, 153, 255));
        buttonAnnuler.setText("Annuler");
        buttonAnnuler.setAlignmentX(0.5F);
        buttonAnnuler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        buttonAnnuler.setContentAreaFilled(false);
        buttonAnnuler.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAnnulerActionPerformed(evt);
            }
        });

        buttonNouvellePartie.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        buttonNouvellePartie.setForeground(new java.awt.Color(102, 102, 102));
        buttonNouvellePartie.setText("Nouvelle partie");
        buttonNouvellePartie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        buttonNouvellePartie.setContentAreaFilled(false);
        buttonNouvellePartie.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonNouvellePartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNouvellePartieActionPerformed(evt);
            }
        });

        buttonEnregistrer.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        buttonEnregistrer.setForeground(new java.awt.Color(102, 102, 102));
        buttonEnregistrer.setText("Enregistrer et quitter");
        buttonEnregistrer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        buttonEnregistrer.setContentAreaFilled(false);
        buttonEnregistrer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEnregistrerActionPerformed(evt);
            }
        });

        buttonQuitter.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        buttonQuitter.setForeground(new java.awt.Color(102, 102, 102));
        buttonQuitter.setText("Quitter");
        buttonQuitter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        buttonQuitter.setContentAreaFilled(false);
        buttonQuitter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout popupQuitterPartieLayout = new javax.swing.GroupLayout(popupQuitterPartie.getContentPane());
        popupQuitterPartie.getContentPane().setLayout(popupQuitterPartieLayout);
        popupQuitterPartieLayout.setHorizontalGroup(
            popupQuitterPartieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupQuitterPartieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(popupQuitterPartieLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(buttonNouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonEnregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupQuitterPartieLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(198, 198, 198))
        );
        popupQuitterPartieLayout.setVerticalGroup(
            popupQuitterPartieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupQuitterPartieLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(popupQuitterPartieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEnregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonNouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(buttonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        popupVictoire.setAlwaysOnTop(true);
        popupVictoire.setMinimumSize(new java.awt.Dimension(600, 205));
        popupVictoire.setResizable(false);
        popupVictoire.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                popupVictoireWindowClosing(evt);
            }
        });

        titrePopupVictoire.setFont(new java.awt.Font("Helvetica Neue", 1, 30)); // NOI18N
        titrePopupVictoire.setForeground(new java.awt.Color(51, 153, 255));
        titrePopupVictoire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titrePopupVictoire.setText("Félicitations !");
        titrePopupVictoire.setToolTipText("");
        titrePopupVictoire.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        buttonFinQuitter.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        buttonFinQuitter.setForeground(new java.awt.Color(102, 102, 102));
        buttonFinQuitter.setText("Quitter");
        buttonFinQuitter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        buttonFinQuitter.setContentAreaFilled(false);
        buttonFinQuitter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonFinQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFinQuitterActionPerformed(evt);
            }
        });

        sousTitrePopupVictoire.setFont(new java.awt.Font("Helvetica Neue", 0, 30)); // NOI18N
        sousTitrePopupVictoire.setForeground(new java.awt.Color(102, 153, 255));
        sousTitrePopupVictoire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sousTitrePopupVictoire.setText("Vous avez gagné la partie !");
        sousTitrePopupVictoire.setAlignmentX(0.5F);

        javax.swing.GroupLayout popupVictoireLayout = new javax.swing.GroupLayout(popupVictoire.getContentPane());
        popupVictoire.getContentPane().setLayout(popupVictoireLayout);
        popupVictoireLayout.setHorizontalGroup(
            popupVictoireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupVictoireLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(popupVictoireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titrePopupVictoire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sousTitrePopupVictoire, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(popupVictoireLayout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(buttonFinQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        popupVictoireLayout.setVerticalGroup(
            popupVictoireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupVictoireLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titrePopupVictoire, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sousTitrePopupVictoire)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(buttonFinQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        labelEpoque.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelEpoque.setForeground(new java.awt.Color(255, 255, 255));
        labelEpoque.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEpoque.setText("Epoque");
        labelEpoque.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelInstructionsJoueur.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelInstructionsJoueur.setForeground(new java.awt.Color(102, 153, 255));
        labelInstructionsJoueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstructionsJoueur.setText("Instructions joueur");

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Quitter");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        plateauJoueurAdverse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 2));
        plateauJoueurAdverse.setMaximumSize(new java.awt.Dimension(500, 500));
        plateauJoueurAdverse.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout plateauJoueurAdverseLayout = new javax.swing.GroupLayout(plateauJoueurAdverse);
        plateauJoueurAdverse.setLayout(plateauJoueurAdverseLayout);
        plateauJoueurAdverseLayout.setHorizontalGroup(
            plateauJoueurAdverseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );
        plateauJoueurAdverseLayout.setVerticalGroup(
            plateauJoueurAdverseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );

        plateauJoueurCourant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255), 2));
        plateauJoueurCourant.setMaximumSize(new java.awt.Dimension(300, 300));
        plateauJoueurCourant.setMinimumSize(new java.awt.Dimension(300, 300));
        plateauJoueurCourant.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout plateauJoueurCourantLayout = new javax.swing.GroupLayout(plateauJoueurCourant);
        plateauJoueurCourant.setLayout(plateauJoueurCourantLayout);
        plateauJoueurCourantLayout.setHorizontalGroup(
            plateauJoueurCourantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        plateauJoueurCourantLayout.setVerticalGroup(
            plateauJoueurCourantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        labelBattleship.setFont(new java.awt.Font("Helvetica Neue", 0, 60)); // NOI18N
        labelBattleship.setForeground(new java.awt.Color(102, 153, 255));
        labelBattleship.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBattleship.setText("Battleship ");
        labelBattleship.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        labelBattleship.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        labelTitreEpoque.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        labelTitreEpoque.setForeground(new java.awt.Color(51, 153, 255));
        labelTitreEpoque.setText("Époque:");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Vos bateaux");

        labelFlotteJoueur.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelFlotteJoueur.setForeground(new java.awt.Color(255, 255, 255));
        labelFlotteJoueur.setText("Votre flotte");

        labelFlotteAdverse.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelFlotteAdverse.setForeground(new java.awt.Color(255, 255, 255));
        labelFlotteAdverse.setText("Flotte adverse");

        flecheFlotteJoueur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Fleche_haut.png"))); // NOI18N
        flecheFlotteJoueur.setMaximumSize(new java.awt.Dimension(20, 20));
        flecheFlotteJoueur.setMinimumSize(new java.awt.Dimension(20, 20));
        flecheFlotteJoueur.setPreferredSize(new java.awt.Dimension(20, 20));

        flecheFlotteAdverse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Fleche_haut.png"))); // NOI18N

        labelInstructionsAdversaire.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        labelInstructionsAdversaire.setForeground(new java.awt.Color(255, 255, 255));
        labelInstructionsAdversaire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstructionsAdversaire.setText("Instructions adversaire");

        panelInfosBateauxJoueur.setBackground(new java.awt.Color(255, 255, 255));
        panelInfosBateauxJoueur.setMaximumSize(new java.awt.Dimension(300, 115));
        panelInfosBateauxJoueur.setMinimumSize(new java.awt.Dimension(300, 115));
        panelInfosBateauxJoueur.setOpaque(false);

        buttonJouer.setBackground(new java.awt.Color(102, 153, 255));
        buttonJouer.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        buttonJouer.setForeground(new java.awt.Color(255, 255, 255));
        buttonJouer.setText("Jouer !");
        buttonJouer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonJouer.setContentAreaFilled(false);
        buttonJouer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonJouer.setOpaque(true);

        labelRotation1.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelRotation1.setForeground(new java.awt.Color(255, 255, 255));
        labelRotation1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRotation1.setText("Sélectionnez un bateau puis cliquez sur les flèches");

        labelRotation2.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelRotation2.setForeground(new java.awt.Color(255, 255, 255));
        labelRotation2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRotation2.setText("droite/gauche de votre clavier pour le faire tourner.");

        javax.swing.GroupLayout panelInfosBateauxJoueurLayout = new javax.swing.GroupLayout(panelInfosBateauxJoueur);
        panelInfosBateauxJoueur.setLayout(panelInfosBateauxJoueurLayout);
        panelInfosBateauxJoueurLayout.setHorizontalGroup(
            panelInfosBateauxJoueurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosBateauxJoueurLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfosBateauxJoueurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelRotation1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelRotation2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosBateauxJoueurLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonJouer, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        panelInfosBateauxJoueurLayout.setVerticalGroup(
            panelInfosBateauxJoueurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosBateauxJoueurLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelRotation1)
                .addGap(0, 0, 0)
                .addComponent(labelRotation2)
                .addGap(18, 18, 18)
                .addComponent(buttonJouer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        labelTitreTirsReussis.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelTitreTirsReussis.setForeground(new java.awt.Color(102, 153, 255));
        labelTitreTirsReussis.setText("Tirs réussis");

        labelTitreTirsRates.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelTitreTirsRates.setForeground(new java.awt.Color(102, 153, 255));
        labelTitreTirsRates.setText("Tirs ratés");

        labelTirsReussis.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        labelTirsReussis.setForeground(new java.awt.Color(51, 102, 255));
        labelTirsReussis.setText("10");

        labelTirsRates.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        labelTirsRates.setForeground(new java.awt.Color(51, 102, 255));
        labelTirsRates.setText("10");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Case_a_portee.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Case à portée de tir");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Case hors de portée de tir");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        buttonFacile.setBackground(new java.awt.Color(102, 153, 255));
        buttonFacile.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        buttonFacile.setForeground(new java.awt.Color(255, 255, 255));
        buttonFacile.setText("Facile");
        buttonFacile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonFacile.setContentAreaFilled(false);
        buttonFacile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonFacile.setOpaque(true);
        buttonFacile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changerDifficultee(evt);
            }
        });

        buttonNormal.setBackground(new java.awt.Color(102, 153, 255));
        buttonNormal.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        buttonNormal.setForeground(new java.awt.Color(255, 255, 255));
        buttonNormal.setText("Normal");
        buttonNormal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonNormal.setContentAreaFilled(false);
        buttonNormal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonNormal.setOpaque(true);
        buttonNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changerDifficultee(evt);
            }
        });

        buttonDifficile.setBackground(new java.awt.Color(102, 153, 255));
        buttonDifficile.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        buttonDifficile.setForeground(new java.awt.Color(255, 255, 255));
        buttonDifficile.setText("Difficile");
        buttonDifficile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonDifficile.setContentAreaFilled(false);
        buttonDifficile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonDifficile.setOpaque(true);
        buttonDifficile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changerDifficultee(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(240, 240, 240)
                        .addComponent(labelFlotteAdverse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flecheFlotteAdverse))
                    .addComponent(plateauJoueurAdverse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelInstructionsJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelInstructionsAdversaire, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonFacile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonDifficile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBattleship, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(labelTitreEpoque)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelEpoque, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelTitreTirsReussis)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelTirsReussis, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(labelTitreTirsRates)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(labelTirsRates, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(plateauJoueurCourant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(flecheFlotteJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(labelFlotteJoueur))
                                .addComponent(panelInfosBateauxJoueur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelInstructionsJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(labelInstructionsAdversaire))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitreEpoque, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelEpoque, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelBattleship, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(plateauJoueurCourant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(flecheFlotteJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(labelFlotteJoueur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelInfosBateauxJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(plateauJoueurAdverse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelTitreTirsReussis)
                                        .addComponent(labelTirsReussis, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelTitreTirsRates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelTirsRates))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonFacile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(1, 1, 1))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 1, Short.MAX_VALUE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(flecheFlotteAdverse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelFlotteAdverse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonNormal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonDifficile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /******************* JDIALOG - GESTION DE LA PARTIE **********************/
    
    
    /**
     * Permet d'afficher les options pour quitter la partie
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        this.setEnabled(false);
        
        // On limite le nombre de parties a 3
        if(this._profil.getParties().size() >= 3) {
            
            this.buttonNouvellePartie.setEnabled(false);
            this.buttonNouvellePartie.setForeground(Color.LIGHT_GRAY);
            this.buttonNouvellePartie.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
            
        } else {
            
            this.buttonNouvellePartie.setEnabled(true);
            this.buttonNouvellePartie.setForeground(Color.DARK_GRAY);
            this.buttonNouvellePartie.setBorder(new LineBorder(Color.DARK_GRAY,1));
            
        }
        
        // Si la partie n'a pas commencee, on ne peut pas l'enregistrer
        if(this.buttonJouer.isEnabled()) {
            
            this.buttonEnregistrer.setEnabled(false);
            this.buttonEnregistrer.setForeground(Color.LIGHT_GRAY);
            this.buttonEnregistrer.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
            
        } else {
            
            this.buttonEnregistrer.setEnabled(true);
            this.buttonEnregistrer.setForeground(Color.DARK_GRAY);
            this.buttonEnregistrer.setBorder(new LineBorder(Color.DARK_GRAY,1));
            
        }
        
        this.popupQuitterPartie.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    /**
     * Permet de quitter definitivement la partie
     * @param evt 
     */
    private void buttonQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitterActionPerformed
       
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupQuitterPartie.dispose();
        this.dispose();
        
    }//GEN-LAST:event_buttonQuitterActionPerformed

    
    /**
     * Permet de revenir a la partie
     * @param evt 
     */
    private void buttonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAnnulerActionPerformed
        
        this.setEnabled(true);
        this.popupQuitterPartie.dispose();
        
    }//GEN-LAST:event_buttonAnnulerActionPerformed

    
    /***************** JDIALOG - SAUVEGARDE DE LA PARTIE *********************/
    
    
    /**
     * Permet d'enregistrer la partie et de quitter
     * @param evt 
     */
    private void buttonEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEnregistrerActionPerformed

        // Mise a jour de la date de la partie
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();
        this._partie.setDate(dateFormat.format(cal.getTime()));
        
        this._jeu.getProfilCourant().ajouterNouvellePartie(this._partie);
        this._partie.sauvegarderPartie(this._profil);
        this._batailleNavale.setVisible(true);
        this._batailleNavale.setEnabled(true);
        this.dispose();
        this.popupQuitterPartie.dispose();
        
    }//GEN-LAST:event_buttonEnregistrerActionPerformed

    
    /**
     * Permet de reautoriser le clique sur la fenetre principale
     * @param evt 
     */
    private void popupQuitterPartieWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupQuitterPartieWindowClosing
       
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupQuitterPartieWindowClosing

    
    /**
     * Permet de quitter la partie une fois celle-ci terminee
     * (popupVictoire)
     * @param evt 
     */
    private void buttonFinQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFinQuitterActionPerformed
        
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupVictoire.dispose();
        this._profil.supprimerPartie(this._partie.getId());
        this.dispose();
        this.setEnabled(true);
        
    }//GEN-LAST:event_buttonFinQuitterActionPerformed

    
    /**
     * Quitte definitivement la partie si on ferme le popup de victoire
     * @param evt 
     */
    private void popupVictoireWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupVictoireWindowClosing
        
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupVictoire.dispose();
        this._profil.supprimerPartie(this._partie.getId());
        this.dispose();
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupVictoireWindowClosing

    
    /**
     * Permet de creer une nouvelle partie en cours de jeu
     * @param evt 
     */
    private void buttonNouvellePartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNouvellePartieActionPerformed
        
        this.popupQuitterPartie.dispose();
        this.dispose();
        this._batailleNavale.setVisible(true);
        this._batailleNavale.choisirParametres();
        
    }//GEN-LAST:event_buttonNouvellePartieActionPerformed

    
    /**
     * Permet de modifier la difficultee de la partie en cours
     * @param evt 
     */
    private void changerDifficultee(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changerDifficultee
        
        this.updateBoutonsDifficultee(evt.getActionCommand());
        this._partie.changerDifficultee(evt.getActionCommand());
        
    }//GEN-LAST:event_changerDifficultee

    
    /**
     * Met a jour la vue des boutons de difficultee
     * @param diff 
     */
    public void updateBoutonsDifficultee(String diff) {
        
        switch(diff) {
         
            case "Facile":
                // On change la partie en facile
                this.buttonFacile.setEnabled(false);
                this.buttonFacile.setForeground(new Color(102,153,255));
                this.buttonFacile.setBorder(new LineBorder(new Color(102,153,255),1));
                this.buttonFacile.setBackground(Color.WHITE);
                this.buttonNormal.setEnabled(true);
                this.buttonNormal.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonNormal.setForeground(Color.WHITE);
                this.buttonNormal.setBackground(new Color(102,153,255));
                this.buttonDifficile.setEnabled(true);
                this.buttonDifficile.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonDifficile.setForeground(Color.WHITE);
                this.buttonDifficile.setBackground(new Color(102,153,255));
                break;
                
            case "Normal":
                // On change la partie en facile
                this.buttonFacile.setEnabled(true);
                this.buttonFacile.setForeground(Color.WHITE);
                this.buttonFacile.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonFacile.setBackground(new Color(102,153,255));
                this.buttonNormal.setEnabled(false);
                this.buttonNormal.setBorder(new LineBorder(new Color(102,153,255),1));
                this.buttonNormal.setForeground(new Color(102,153,255));
                this.buttonNormal.setBackground(Color.WHITE);
                this.buttonDifficile.setEnabled(true);
                this.buttonDifficile.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonDifficile.setForeground(Color.WHITE);
                this.buttonDifficile.setBackground(new Color(102,153,255));
                break;
                
            case "Difficile":
                // On change la partie en facile
                this.buttonFacile.setEnabled(true);
                this.buttonFacile.setForeground(Color.WHITE);
                this.buttonFacile.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonFacile.setBackground(new Color(102,153,255));
                this.buttonNormal.setEnabled(true);
                this.buttonNormal.setBorder(new LineBorder(Color.WHITE,1));
                this.buttonNormal.setForeground(Color.WHITE);
                this.buttonNormal.setBackground(new Color(102,153,255));
                this.buttonDifficile.setEnabled(false);
                this.buttonDifficile.setBorder(new LineBorder(new Color(102,153,255),1));
                this.buttonDifficile.setForeground(new Color(102,153,255));
                this.buttonDifficile.setBackground(Color.WHITE);
                break;
            
        }
        
    } // updateBoutonsDifficultee(String diff)
    
    
    /**
     * Permet de mettre a jour l'affichage du nombre de caseBateau restant au
     * joueur
     */
    public void miseAJourEtatsBateaux() {
        
        this.etatBateaux.setVisible(manuel || this._partie.isAutomatique());
        this.etatBateaux.removeAll();
        this.bateaux.setVisible(manuel || this._partie.isAutomatique());
        this.bateaux.removeAll();
        Iterator ite = this._partie.getCasesBateaux().keySet().iterator();
        while(ite.hasNext()){
            
            Case c = this._partie.getCasesBateaux().get((String)ite.next());
            int nbCasesTouche = c.getBateau().getLongueur()-c.getBateau().getNbCasesNonTouchees();
            int nbCasesNonTouche = c.getBateau().getNbCasesNonTouchees();
            JPanel panelEtat = new JPanel();
            panelEtat.setOpaque(false);
            panelEtat.setLayout(new BoxLayout(panelEtat, BoxLayout.X_AXIS));

            // Ajout des nom des bateaux
            JLabel nomBateau = new JLabel(c.getBateau().getNom());
            nomBateau.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            nomBateau.setFont(new java.awt.Font("Helvetica Neue", 0, 20));
            nomBateau.setForeground(Color.WHITE);
            nomBateau.setOpaque(false);
            this.bateaux.add(nomBateau);
            
            // Image pour une caseBateau non touchee
            for(int i = 0; i < nbCasesNonTouche;i++) {
                
                JLabel nonTouche = new JLabel();
                nonTouche.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_plein.png")).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
                panelEtat.add(nonTouche);
                
            }
            
            // Image pour une caseBateau touchee
            for(int i = 0; i < nbCasesTouche;i++) {
                 
                JLabel touche = new JLabel();
                touche.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_vide.png")).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
                panelEtat.add(touche);
                
            }
            this.etatBateaux.add(panelEtat);
            
        }
        
    } // miseAJourEtatsBateaux()
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAnnuler;
    private javax.swing.JButton buttonDifficile;
    private javax.swing.JButton buttonEnregistrer;
    private javax.swing.JButton buttonFacile;
    private javax.swing.JButton buttonFinQuitter;
    private javax.swing.JButton buttonJouer;
    private javax.swing.JButton buttonNormal;
    private javax.swing.JButton buttonNouvellePartie;
    private javax.swing.JButton buttonQuitter;
    private javax.swing.JLabel flecheFlotteAdverse;
    private javax.swing.JLabel flecheFlotteJoueur;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel labelBattleship;
    private javax.swing.JLabel labelEpoque;
    private javax.swing.JLabel labelFlotteAdverse;
    private javax.swing.JLabel labelFlotteJoueur;
    private javax.swing.JLabel labelInstructionsAdversaire;
    private javax.swing.JLabel labelInstructionsJoueur;
    private javax.swing.JLabel labelRotation1;
    private javax.swing.JLabel labelRotation2;
    private javax.swing.JLabel labelTirsRates;
    private javax.swing.JLabel labelTirsReussis;
    private javax.swing.JLabel labelTitreEpoque;
    private javax.swing.JLabel labelTitreTirsRates;
    private javax.swing.JLabel labelTitreTirsReussis;
    private javax.swing.JPanel panelInfosBateauxJoueur;
    private javax.swing.JPanel plateauJoueurAdverse;
    private javax.swing.JPanel plateauJoueurCourant;
    private javax.swing.JDialog popupQuitterPartie;
    private javax.swing.JDialog popupVictoire;
    private javax.swing.JLabel sousTitrePopupVictoire;
    private javax.swing.JLabel titrePopupVictoire;
    // End of variables declaration//GEN-END:variables

    
    /************************** LISTENER DE CLAVIER **************************/
    
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        // On fais tourner le bateau selectionner si c'est possible
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            
            this._partie.rotationBateau();
            
        }
        
    } // keyPressed(KeyEvent e)
    
    
    /************** GESTION DE LA MISE A JOUR DE LA FENETRE ******************/
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        switch (arg.toString()) {
            
            case "reinitialiser":
                this.initialisation();
                this.labelInstructionsJoueur.setText("Vous pouvez déplacer vos bateaux sur la grille de droite.");
                this.labelInstructionsAdversaire.setText("Cliquez sur 'Jouer !' pour commencer la partie.");
                this.updatePorteeManuel();
                break;
                
            case "focus":
                this.requestFocus();
                break;
            
            case "messageJ1":
                this.labelInstructionsJoueur.setText(this._partie.getMessage());
                break;
                
            case "messageJ2":
                this.labelInstructionsAdversaire.setText(this._partie.getMessage());
                break;
               
            case "tir":
                this.miseAJourEtatsBateaux();
                this.labelTirsReussis.setText(this._partie.getJ1().getNbTirsGagnant() + "");
                this.labelTirsRates.setText(this._partie.getJ1().getNbTirsPerdant() + "");
                // On empeche les eventuels bateaux coules de tirer si l'utilisateur
                // en a fait le choix
                if(this._partie.getParametre().isMajPortee()) {
                    this.updatePortee();
                }
                break;
                
            case "resultat":
                this.titrePopupVictoire.setText("Résultats");
                this.sousTitrePopupVictoire.setText(this._partie.getMessageFinPartie());
                this.popupVictoire.setLocationRelativeTo(null);
                this.popupVictoire.setVisible(true);
                this.setEnabled(false);
                break;
                
        }
        
    } // update(Observable o, Object arg)

    
} // class VuePartie
