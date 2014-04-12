/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package window.vue;

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
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import window.main.BatailleNavale;

/**
 * VuePartie
 * @author Tristan
 */
public class VuePartie extends javax.swing.JFrame implements Observer {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////
    
    
    public BatailleNavale _batailleNavale;
    public Jeu _jeu;
    public Profil _profil;
    public Partie _partie;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////

    
    /**
     * Creates new form VuePartie
     * @param batailleNavale Fenetre d'accueil
     * @param jeu main
     * @param profil profil du joueur qui joue la partie
     * @param partie partie jouee par le joueur
     */
    public VuePartie(BatailleNavale batailleNavale, Jeu jeu, Profil profil, final Partie partie) {
        initComponents();
        this._batailleNavale = batailleNavale;
        this._jeu = jeu;
        this._profil = profil;
        this._partie = partie;
        
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
            
            if(partie.getJ2().getCases().get(num).isEtat()) {
                test = true;
            }
            num++;
            
        }
        
        if(partie.isAutomatique() || test) { 
            
            // Le positionnement est aleatoire, on lance la partie
            this.buttonJouer.setEnabled(false);
            this.buttonJouer.setVisible(false);
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
                    
                    partie.autoriserDragDropJoueur(false);
                    partie.initialisationPorteeCases();
                    initialisation();
                    buttonJouer.setEnabled(false);
                    buttonJouer.setVisible(false);
                    
                }
            });
            
        }
        
        this.popupQuitterPartie.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        
    } // VuePartie(BatailleNavale batailleNavale, Jeu jeu, Profil profil, Partie partie)
    
    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    
    
    /**
     * Permet d'initialiser la partie
     */
    public void initialisation() {
        
        // Initialisation des instructions 
        this.labelInstructionsJoueur.setText("À vous de jouer ! Cliquez sur une case de la grille adverse.");
        this.labelInstructionsAdversaire.setText("");
        
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
                    ImageIcon bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_rouge_gris.png"))
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
        
    } // initialisation()
    

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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        popupVictoire = new javax.swing.JDialog();
        titrePopupVictoire = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
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
        labelTitreTirsReussis = new javax.swing.JLabel();
        labelTitreTirsRates = new javax.swing.JLabel();
        labelTirsReussis = new javax.swing.JLabel();
        labelTirsRates = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        popupQuitterPartie.setSize(new java.awt.Dimension(500, 180));
        popupQuitterPartie.setMaximumSize(new java.awt.Dimension(500, 180));
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

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 153, 255));
        jButton2.setText("Annuler");
        jButton2.setAlignmentX(0.5F);
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 102, 102));
        jButton3.setText("Nouvelle partie");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setText("Enregistrer et quitter");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setText("Quitter");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupQuitterPartieLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(198, 198, 198))
        );
        popupQuitterPartieLayout.setVerticalGroup(
            popupQuitterPartieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupQuitterPartieLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(popupQuitterPartieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

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

        jButton6.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(102, 102, 102));
        jButton6.setText("Rejouer");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton7.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 102, 102));
        jButton7.setText("Quitter");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
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
                    .addComponent(sousTitrePopupVictoire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupVictoireLayout.createSequentialGroup()
                .addContainerGap(180, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        popupVictoireLayout.setVerticalGroup(
            popupVictoireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupVictoireLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titrePopupVictoire, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sousTitrePopupVictoire)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(popupVictoireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        labelEpoque.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelEpoque.setForeground(new java.awt.Color(51, 102, 255));
        labelEpoque.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEpoque.setText("Epoque");
        labelEpoque.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelInstructionsJoueur.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelInstructionsJoueur.setForeground(new java.awt.Color(102, 153, 255));
        labelInstructionsJoueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstructionsJoueur.setText("Instructions joueur");

        jButton1.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 153, 255));
        jButton1.setText("Quitter");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Vos bateaux");

        labelFlotteJoueur.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelFlotteJoueur.setForeground(new java.awt.Color(102, 102, 102));
        labelFlotteJoueur.setText("Votre flotte");

        labelFlotteAdverse.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        labelFlotteAdverse.setForeground(new java.awt.Color(102, 102, 102));
        labelFlotteAdverse.setText("Flotte adverse");

        flecheFlotteJoueur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Fleche_haut.png"))); // NOI18N
        flecheFlotteJoueur.setMaximumSize(new java.awt.Dimension(20, 20));
        flecheFlotteJoueur.setMinimumSize(new java.awt.Dimension(20, 20));
        flecheFlotteJoueur.setPreferredSize(new java.awt.Dimension(20, 20));

        flecheFlotteAdverse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stockage/images/Fleche_haut.png"))); // NOI18N

        labelInstructionsAdversaire.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        labelInstructionsAdversaire.setForeground(new java.awt.Color(102, 102, 102));
        labelInstructionsAdversaire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstructionsAdversaire.setText("Instructions adversaire");

        panelInfosBateauxJoueur.setBackground(new java.awt.Color(255, 255, 255));

        buttonJouer.setText("Jouer !");

        javax.swing.GroupLayout panelInfosBateauxJoueurLayout = new javax.swing.GroupLayout(panelInfosBateauxJoueur);
        panelInfosBateauxJoueur.setLayout(panelInfosBateauxJoueurLayout);
        panelInfosBateauxJoueurLayout.setHorizontalGroup(
            panelInfosBateauxJoueurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosBateauxJoueurLayout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addComponent(buttonJouer)
                .addGap(91, 91, 91))
        );
        panelInfosBateauxJoueurLayout.setVerticalGroup(
            panelInfosBateauxJoueurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosBateauxJoueurLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(buttonJouer)
                .addGap(38, 38, 38))
        );

        labelTitreTirsReussis.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelTitreTirsReussis.setForeground(new java.awt.Color(51, 153, 255));
        labelTitreTirsReussis.setText("Tirs réussis");

        labelTitreTirsRates.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        labelTitreTirsRates.setForeground(new java.awt.Color(51, 153, 255));
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
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Case à portée de tir");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Case hors de portée de tir");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelFlotteAdverse)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(flecheFlotteAdverse))
                        .addComponent(plateauJoueurAdverse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelInstructionsJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelInstructionsAdversaire, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(labelTitreEpoque, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelEpoque, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelTitreTirsReussis)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelTirsReussis, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelTitreTirsRates)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelTirsRates, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(plateauJoueurCourant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(flecheFlotteJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(labelFlotteJoueur))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(19, 19, 19))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelBattleship, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panelInfosBateauxJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                        .addGap(19, 19, 19)
                        .addComponent(plateauJoueurCourant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(flecheFlotteJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(labelFlotteJoueur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelInfosBateauxJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitreTirsRates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTirsRates)
                            .addComponent(labelTirsReussis, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTitreTirsReussis)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(plateauJoueurAdverse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFlotteAdverse)
                            .addComponent(flecheFlotteAdverse, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14))
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
        this.popupQuitterPartie.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    /**
     * Permet de quitter definitivement la partie
     * @param evt 
     */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupQuitterPartie.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    
    /**
     * Permet de revenir a la partie
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        this.setEnabled(true);
        this.popupQuitterPartie.setVisible(false);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    
    /***************** JDIALOG - SAUVEGARDE DE LA PARTIE *********************/
    
    
    /**
     * Permet d'enregistrer la partie et de quitter
     * @param evt 
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        this._partie.sauvegarderPartie(this._profil);
        this.setVisible(false);
        this.popupQuitterPartie.setVisible(false);
        this._batailleNavale.setVisible(true);
        this._batailleNavale.setEnabled(true);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    
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
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupVictoire.setVisible(false);
        this.setVisible(false);
        this.setEnabled(true);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    
    /**
     * Quitte definitivement la partie si on ferme le popup de victoire
     * @param evt 
     */
    private void popupVictoireWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_popupVictoireWindowClosing
        
        this._batailleNavale.setEnabled(true);
        this._batailleNavale.setVisible(true);
        this.popupVictoire.setVisible(false);
        this.setVisible(false);
        this.setEnabled(true);
        
    }//GEN-LAST:event_popupVictoireWindowClosing

    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        this.popupQuitterPartie.setVisible(false);
        this.dispose();
        this._batailleNavale.setVisible(true);
        this._batailleNavale.getPopupPartie().setVisible(true);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonJouer;
    private javax.swing.JLabel flecheFlotteAdverse;
    private javax.swing.JLabel flecheFlotteJoueur;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
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

    
     /************** GESTION DE LA MISE A JOUR DE LA FENETRE ******************/
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        switch (arg.toString()) {
            
            case "reinitialiser":
                this.initialisation();
                break;
            
            case "messageJ1":
                this.labelInstructionsJoueur.setText(this._partie.getMessage());
                break;
                
            case "messageJ2":
                this.labelInstructionsAdversaire.setText(this._partie.getMessage());
                break;
               
            case "tir":
                this.labelTirsReussis.setText(this._partie.getJ1().getNbTirsGagnant() + "");
                this.labelTirsRates.setText(this._partie.getJ1().getNbTirsPerdant() + "");
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
