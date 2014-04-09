package bataille_navale;

import intelligenceArtificielle.IntelligenceArtificielle;
import intelligenceArtificielle.IntelligenceArtificielleFacile;
import java.util.Observable;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.TransferHandler;
import stockage.DAOFactory;

/**
 * Partie
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Partie extends Observable {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private String _id;
    private Parametre _parametre;
    private Joueur _j1;
    private Joueur _j2;
    private boolean _automatique;
    
    private String _message;
    private String _messageFinPartie;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Partie() {

    } // Partie()
    

    public Partie(Parametre parametre, Joueur j1, Joueur j2, boolean automatique) {
        
        this._id = "partie" + parametre.hashCode() + j1.hashCode() + j2.hashCode();
        this._parametre = parametre;
        this._j1 = j1;
        this._j2 = j2;
        this._automatique = automatique;
        
    } // Partie(Parametre parametre, Joueur j1, Joueur j2, boolean automatique)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    /**
     * Permet de lancer la partie
     */
    public void jouerPartie() {
        
        setChanged();
        notifyObservers("start");
        
    } // jouerPartie()
    
    
    /**
     * Permet d'initialiser la portee des cases
     */
    public void initialisationPorteeCases() {
        
        int x = this._parametre.getNbCaseX();
        int y = this._parametre.getNbCaseY();
        
        // Permet d'afficher les cases a portee de tir
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
            
                if(this._j1.getCases().get(i+j*x).getBateau() != null) {
           
                    // On parcours les cases autour du bateau pour les activer
                    int portee = this._j1.getCases().get(i+j*x).getBateau().getPortee();
                    for(int W=i-portee;W<(i-portee+2*portee)+1;W++) {
                        for(int H=j-portee;H<(j-portee+2*portee)+1;H++) {
                            
                            if(W >= 0 && W < x && H >= 0 && H < y) {
                
                                ((Case)(this._j2.getCases().get(W+H*x))).setPortee(true);
                                
                            }
                            
                        }
                    }

                }
            
            }
        }
        
    } // initialisationPorteeCases()

    
    /**
     * Permet de savoir s'il reste des bateaux au joueur adverse ou non
     * @param joueur joueur sur lequel on test son nombre de bateaux restants
     * @return TRUE si le joueur ne possede plus de cases, FALSE sinon
     */
    public boolean testVictoire(Joueur joueur) {
        
        for(Case c : joueur.getCases()) {
        
            if(c.getBateau() != null && !c.getBateau().testBateauCoule()) {
                
                return false;
                
            }
            
        }
        return true;
            
    } // testVictoire(Joueur joueur)
    
    
    /**
     * Permet de savoir s'il reste encore des cases a jouer dans la partie 
     * @return TRUE s'il n'y a plus de cases a jouer, FALSE sinon
     */
    public boolean testEgalite() {
        
        return false;
        
    } // testEgalite()

    
    /**
     * Permet de cloturer la partie
     */
    public void clorePartie() {

    } // clorePartie()

    
    /**
     * Permet de sauvegarder la partie d'un profil
     * @param profil profil dont on souhaite sauvegarder la partie
     */
    public void sauvegarderPartie(Profil profil) {
        
        DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(profil);

    } // sauvegarderPartie(Profil profil)
    
    
    /**
     * Permet d'autoriser ou non le Drag & Drop sur les cases su joueur
     * @param autorisation TRUE si on autorise, FALSE sinon
     */
    public void autoriserDragDropJoueur(boolean autorisation) {
        
        for(Case c : this._j1.getCases()) {
            
            //if(c.getBateau() != null && autorisation) {
                
                // On autorise le Drag & Drop
                /*TransferHandler transfer = new TransferHandler("text");
                c.setTransferHandler(transfer);
                c.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e){

                        Case c = (Case)e.getSource();
                        TransferHandler handle = c.getTransferHandler();
                        handle.exportAsDrag(c, e, TransferHandler.COPY);
                        
                    }
                });*/
                new DropTarget(c,c);
                c.setTransferHandler(new TransferHandler("text"));
                final MouseListener listener = new MouseAdapter() {
                    @Override
                    public void mousePressed(final MouseEvent me) {
                        final Case cDD = (Case) me.getSource();
                        System.out.println(cDD);

                        final TransferHandler handler = cDD.getTransferHandler();
                        handler.exportAsDrag(cDD, me, TransferHandler.COPY);
                    }
                };
                c.addMouseListener(listener);
                
            /*} else if(c.getBateau() != null && !autorisation) {
                
                // On desactive le Drag & Drop
                for (MouseListener mouseListener : c.getMouseListeners()) {
                    c.removeMouseListener(mouseListener);
                }
                
            } */
            
        }
        
    } // autoriserDragDropJoueur(boolean autorisation)

    
    /**
     * Permet au joueur de faire tourner un de ses bateaux
     * @param bateau bateau a faire tourner
     */
    public void rotationBateau(Bateau bateau) {

    } // rotationBateau(Bateau bateau)

    
    /**
     * Permet au joueur de positionner un de ses bateaux sur la grille
     * @param bateau bateau a positioner sur la grille
     * @param c case sur laquelle on positionne le bateau
     */
    public void positionnerBateau(Bateau bateau, Case c) {

    } // positionnerBateau(Bateau bateau, Case c)
    

    /**
     * Permet au joueur de tirer sur une case
     * @param joueurCourant joueur ayant tire sur une case
     * @param joueurAdverse joueur adverse du joueur ayant tire
     * @param c case sur laquelle on souhaite tirer
     * @return TRUE si le joueur a gagne, FALSE sinon
     */
    public boolean jouerCase(Joueur joueurCourant, Joueur joueurAdverse, Case c) {

        if(c != null) {
        
            // Le joueur physique joueur
            if(joueurCourant.jouerCase(c)) {
                
                // Le joueur a touche un bateau
                this.afficherMessage("Vous avez touché votre adversaire !",true);
                
            } else {
                
                // Le joueur tire dans le vide
                this.afficherMessage("Votre tir a échoué !",true);
                
            }
        
        } else {
            
            // La machine joue
            if(joueurCourant.jouerCase(this.getCaseForIA(joueurAdverse))) {
                
                // La machine a touche un bateau
                this.afficherMessage("Votre adversaire vous a touché !",false);
                
            } else {
                
                
                // La machine tire dans le vide
                this.afficherMessage("Le tir de votre adversaire a échoué !",false);
                
            }
            
        }
        setChanged();
        notifyObservers("tir");
        
        return this.testVictoire(joueurAdverse);
        
    } // jouerCase(Joueur joueurCourant, Joueur joueurAdverse, Case c)
    
    
    /**
     * Permet de recuperer une case a jouer pour l'IA
     * @param joueurAdverse joueur physique
     * @return la case a jouer pour l'IA
     */
    public Case getCaseForIA(Joueur joueurAdverse) {
        
//        Random rand = new Random();
//        int x = rand.nextInt(this._parametre.getNbCaseX());
//        int y = rand.nextInt(this._parametre.getNbCaseY());
//        while(((Case)(joueurAdverse.getCases().get(x+y*this._parametre.getNbCaseX()))).isEtat()) {
//            
//            x = rand.nextInt(this._parametre.getNbCaseX());
//            y = rand.nextInt(this._parametre.getNbCaseY());
//            
//        }
//        
//        return ((Case)(joueurAdverse.getCases().get(x+y*this._parametre.getNbCaseX())));
        IntelligenceArtificielle intelligenceArtificielle = new IntelligenceArtificielleFacile(_parametre);
        return intelligenceArtificielle.getCaseForIA(joueurAdverse);
        
    } // getCaseForIA(Joueur joueurAdverse)
    

    /**
     * Permet d'afficher un message au joueur
     * @param mess message a afficher
     * @param joueur si TRUE joueur courant, si FALSE joueur adverse
     */
    public void afficherMessage(String mess, boolean joueur) {

        this._message = mess;
        setChanged();
        String label = joueur ? "messageJ1" : "messageJ2";
        notifyObservers(label);
        
    } // afficherMessage(String mess, boolean joueur)
    
    
    /**
     * Permet d'afficher un message au joueur a la fin de la partie
     * @param mess message a afficher
     */
    public void afficherMessageFinPartie(String mess) {

        this._messageFinPartie = mess;
        setChanged();
        notifyObservers("resultat");
        
    } // afficherMessageFinPartie(String mess)

    
    /**** GETTER/SETTER *****/
    
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public Parametre getParametre() {
        return _parametre;
    }

    public void setParametre(Parametre _parametre) {
        this._parametre = _parametre;
    }

    public Joueur getJ1() {
        return _j1;
    }

    public void setJ1(Joueur _j1) {
        this._j1 = _j1;
    }

    public Joueur getJ2() {
        return _j2;
    }

    public void setJ2(Joueur _j2) {
        this._j2 = _j2;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String _message) {
        this._message = _message;
    }

    public String getMessageFinPartie() {
        return _messageFinPartie;
    }

    public void setMessageFinPartie(String _messageFinPartie) {
        this._messageFinPartie = _messageFinPartie;
    }

    public boolean isAutomatique() {
        return _automatique;
    }

    public void setAutomatique(boolean _automatique) {
        this._automatique = _automatique;
    }

    
} // class Partie
