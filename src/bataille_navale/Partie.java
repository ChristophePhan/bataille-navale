package bataille_navale;

import intelligenceArtificielle.FactoryIA;
import intelligenceArtificielle.IntelligenceArtificielle;
import java.awt.Cursor;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import javax.swing.TransferHandler;
import stockage.DAOFactory;

/**
 * Partie
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Partie extends Observable {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    
    
    private String _id;
    private String _date;
    private Parametre _parametre;
    private Joueur _j1;
    private Joueur _j2;
    private boolean _automatique;
    private IntelligenceArtificielle intelligenceArtificielle;

    private String _message;
    private String _messageFinPartie;

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Partie() {

    } // Partie()

    
    public Partie(Parametre parametre, boolean automatique) {

        this._id = "partie" + parametre.hashCode();
        
        // Recuperation de la date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();
        
        this._date = dateFormat.format(cal.getTime());
        this._parametre = parametre;
        this._automatique = automatique;
        this.intelligenceArtificielle = FactoryIA.getInstance().getIntelligenceArtificielle(this._parametre);

    } // Partie(Parametre parametre, boolean automatique)

    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    /************************** INITIALISATION ******************************/
    
    
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
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                if (this._j1.getCases().get(i + j * x).getBateau() != null) {

                    // On parcours les cases autour du bateau pour les activer
                    int portee = this._j1.getCases().get(i + j * x).getBateau().getPortee();
                    for (int W = i - portee; W < (i - portee + 2 * portee) + 1; W++) {
                        for (int H = j - portee; H < (j - portee + 2 * portee) + 1; H++) {

                            if (W >= 0 && W < x && H >= 0 && H < y) {

                                ((Case) (this._j2.getCases().get(W + H * x))).setPortee(true);

                            }

                        }
                    }

                }

            }
        }

    } // initialisationPorteeCases()

    
    /**
     * Permet de cloturer la partie
     */
    public void clorePartie() {

    } // clorePartie()

    
    /**
     * Permet de sauvegarder la partie d'un profil
     *
     * @param profil profil dont on souhaite sauvegarder la partie
     */
    public void sauvegarderPartie(Profil profil) {

        DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(profil);

    } // sauvegarderPartie(Profil profil)

    
    /**
     * Permet d'autoriser ou non le Drag & Drop sur les cases su joueur
     *
     * @param autorisation TRUE si on autorise, FALSE sinon
     */
    public void autoriserDragDropJoueur(boolean autorisation) {

        if (autorisation) {

            // Activation du Drag & Drop
            for (Case c : this._j1.getCases()) {

                if (c.getBateau() != null) {

                    this.addMouseEvent(c, true);

                } else {

                    this.addMouseEvent(c, false);

                }

            }

        } else {

            // Desactivation du Drag & Drop
            for (Case c : this._j1.getCases()) {
                for (MouseListener ml : c.getMouseListeners()) {

                    c.removeMouseListener(ml);

                }

            }

        }

        // On desactive les actions sur les cases aadverses
        for (Case c : this._j2.getCases()) {
            for (ActionListener ac : c.getActionListeners()) {

                c.removeActionListener(ac);

            }
        }

    } // autoriserDragDropJoueur(boolean autorisation)

    
    /**
     * Permet au joueur de faire tourner un de ses bateaux
     *
     * @param bateau bateau a faire tourner
     */
    public void rotationBateau(Bateau bateau) {

    } // rotationBateau(Bateau bateau)

    
    /**
     * Permet au joueur de positionner un de ses bateaux sur la grille
     *
     * @param abs coordonnees en abscisse de la case de depart
     * @param ord coordonnees en ordonnee de la case de depart
     * @param cArrive case d'arrivee
     */
    public void positionnerBateau(int abs, int ord, Case cArrive) {

        Bateau bateau = this._j1.getCases().get(abs + ord * this._parametre.getNbCaseX()).getBateau();
        List<Case> liste = new ArrayList<>();

        // Recuperation de toutes les cases du bateau
        for (Case c : this.getJ1().getCases()) {
            if (c.getBateau() != null && c.getBateau().getNom().equals(bateau.getNom())) {
                liste.add(c);
            }
        }

        boolean test = this.testDeplacementBateau(bateau.getOrientation(), bateau.getLongueur(), cArrive);
        switch (bateau.getOrientation()) {

            case 1:
                // Horizontal (on change les cases de place si possible)
                if (test) {
                    for (int i = cArrive.getAbs(); i < (cArrive.getAbs() + bateau.getLongueur()); i++) {

                        Case b = new CaseBateau(bateau, this);
                        b = this.addMouseEvent(b, true);
                        this._j1.getCases().set(i + cArrive.getOrd() * this.getParametre().getNbCaseX(), b);

                    }
                }
                break;

            case 2:
                // Vertical (on change les cases de place si possible)
                if (test) {
                    for (int i = cArrive.getOrd(); i < (cArrive.getOrd() + bateau.getLongueur()); i++) {

                        Case b = new CaseBateau(bateau, this);
                        b = this.addMouseEvent(b, true);
                        this._j1.getCases().set(cArrive.getAbs() + i * this.getParametre().getNbCaseX(), b);

                    }
                }
                break;

        }

        // On met des cases vides a la place des cases bateaux si on a pu faire
        // le deplacement et on previent la fenetre
        if (test) {

            for (Case c : liste) {

                Case b = new CaseVide(this);
                b = this.addMouseEvent(b, false);
                this._j1.getCases().set(c.getAbs() + c.getOrd() * this.getParametre().getNbCaseX(), b);

            }
            this.autoriserDragDropJoueur(true);

            setChanged();
            notifyObservers("reinitialiser");

        }

    } // positionnerBateau(int x, int y, Case cArrive)

    
    /**
     * Permet de savoir si le bateau peut etre deplace ou non
     * @param sens orientation du bateau 1 - Horizontal 2 - Vertical
     * @param taille longueur du bateau a placer
     * @param cArrive premiere case de la nouvelle position du bateau
     * @return TRUE si le bateau peut etre deplace, FALSE sinon
     */
    public boolean testDeplacementBateau(int sens, int taille, Case cArrive) {

        switch (sens) {

            case 1:
                // Horizontal
                for (int i = cArrive.getAbs(); i < (cArrive.getAbs() + taille); i++) {
                    if (i >= this._parametre.getNbCaseX() || this.getJ1().getCases().get(i + cArrive.getOrd() * this.getParametre().getNbCaseX()).getBateau() != null) {

                        return false;

                    }
                }
                break;

            case 2:
                // Vertical
                for (int i = cArrive.getOrd(); i < (cArrive.getOrd() + taille); i++) {
                    if (i >= this._parametre.getNbCaseY() || this.getJ1().getCases().get(cArrive.getAbs() + i * this.getParametre().getNbCaseX()).getBateau() != null) {

                        return false;

                    }
                }
                break;

        }

        return true;

    } // testDeplacementBateau(int sens, Case cArrive)

    
    /**
     * Permet d'ajouter l'evenement de souris sur la case pour le Drag & Drop
     *
     * @param c case sur laquelle on souhaite autoriser l'evenement de souris
     * @param typeCase si CaseBateau on ajoute un evenement au clic
     * @return la case avec l'evenement de souris autorise
     */
    public Case addMouseEvent(Case c, boolean typeCase) {

        new DropTarget(c, c);
        c.setTransferHandler(new TransferHandler("text"));
        MouseListener listener = null;
        if (typeCase) {

            c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            listener = new MouseAdapter() {
                @Override
                public void mousePressed(final MouseEvent me) {

                    final Case cDD = (Case) me.getSource();
                    cDD.setText(cDD.getAbs() + "x" + cDD.getOrd());
                    final TransferHandler handler = cDD.getTransferHandler();
                    handler.exportAsDrag(cDD, me, TransferHandler.COPY);

                }
            };

        } else {

            c.setCursor(Cursor.getDefaultCursor());
            listener = new MouseAdapter() {
                @Override
                public void mousePressed(final MouseEvent me) {
                }
            };

        }
        c.addMouseListener(listener);

        return c;

    } // addMouseEvent(Case c)

    
    /********************** GESTION DE LA PARTIE ****************************/
    
    
    /**
     * Permet au joueur de tirer sur une case
     * @param joueurCourant joueur ayant tire sur une case
     * @param joueurAdverse joueur adverse du joueur ayant tire
     * @param c case sur laquelle on souhaite tirer
     * @return TRUE si le joueur a gagne, FALSE sinon
     */
    public boolean jouerCase(Joueur joueurCourant, Joueur joueurAdverse, Case c) {

        if (c != null) {

            // Le joueur physique joueur
            if (joueurCourant.jouerCase(c)) {

                // Le joueur a touche un bateau
                this.afficherMessage("Vous avez touché votre adversaire !", true);

            } else {

                // Le joueur tire dans le vide
                this.afficherMessage("Votre tir a échoué !", true);

            }

        } else {

            // La machine joue
            if (joueurCourant.jouerCase(this.getCaseForIA(joueurAdverse))) {

                // La machine a touche un bateau
                this.afficherMessage("Votre adversaire vous a touché !", false);

            } else {

                // La machine tire dans le vide
                this.afficherMessage("Le tir de votre adversaire a échoué !", false);

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

        return this.intelligenceArtificielle.getCaseForIA(joueurAdverse);

    } // getCaseForIA(Joueur joueurAdverse)

    
    /************************* TEST DE FIN DE PARTIE *************************/
    
    
    /**
     * Permet de savoir s'il reste des bateaux au joueur adverse ou non
     * @param joueur joueur sur lequel on test son nombre de bateaux restants
     * @return TRUE si le joueur ne possede plus de cases, FALSE sinon
     */
    public boolean testVictoire(Joueur joueur) {

        for (Case c : joueur.getCases()) {

            if (c.getBateau() != null && !c.getBateau().testBateauCoule()) {

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

        for (Case c : this._j2.getCases()) {

            if (c.isAPortee() && !c.isEtat()) {

                return false;

            }

        }

        return true;

    } // testEgalite()

    
    /**************************** MESSAGE ***********************************/
    
    
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

    
    /***** GETTER/SETTER *****/
    
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
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

    public IntelligenceArtificielle getIntelligenceArtificielle() {
        return intelligenceArtificielle;
    }

    public void setIntelligenceArtificielle(IntelligenceArtificielle intelligenceArtificielle) {
        this.intelligenceArtificielle = intelligenceArtificielle;
    }

    
} // class Partie
