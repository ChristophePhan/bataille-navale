package bataille_navale;

/**
 * Partie
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Partie {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private String _id;
    private Parametre _parametre;
    private Joueur _j1;
    private Joueur _j2;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////


    public Partie(Parametre parametre, Joueur j1, Joueur j2) {
        
        this._id = "partie" + parametre.hashCode() + j1.hashCode() + j2.hashCode();
        this._parametre = parametre;
        this._j1 = j1;
        this._j2 = j2;
        
    } // Partie(Partie(Parametre parametre, Joueur j1, Joueur j2))
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    

    /**
     * Permet de lancer la partie
     */
    public void jouerPartie() {

    } // jouerPartie()

    
    /**
     * Permet de savoir si la partie est finie ou non
     * @return TRUE si la partie est finie, FALSE sinon
     */
    public boolean testFinPartie() {
        
            return false;
            
    } // testFinPartie()

    
    /**
     * Permet de cloturer la partie
     */
    public void clorePartie() {

    } // clorePartie()

    
    /**
     * Permet de sauvegarder la partie
     */
    public void sauvegarderPartie() {

    } // sauvegarderPartie()

    
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
     * @param c case sur laquelle on souhaite tirer
     */
    public void jouerCase(Case c) {

    } // jouerCase(Case c)
    

    /**
     * Permet d'afficher un message au joueur
     * @param mess message a afficher
     */
    public void afficherMessage(String mess) {

    } // afficherMessage(String mess)

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

    
} // class Partie
