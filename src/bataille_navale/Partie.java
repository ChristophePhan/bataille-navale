package bataille_navale;

/**
 * Partie
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Partie {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private String id;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////


    public Partie() {

    } // Partie()
    
    
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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
} // class Partie
