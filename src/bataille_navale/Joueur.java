package bataille_navale;

/**
 * Joueur
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Joueur {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    private String nom;
    private int nbTirsGagnant;
    private int nbTirsPerdant;

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Joueur() {
        
    } // Joueur()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    /**
     * Permet de tourner un bateau
     * @param bateau bateau a faire tourner
     */
    public void rotationBateau(Bateau bateau) {

    } // rotationBateau(Bateau bateau)

    
    /**
     * Permet de placer un bateau sur la grille
     * @param bateau bateau a placer
     * @param c case sur laquelle on place le bateau
     */
    public void positionnerBateau(Bateau bateau, Case c) {

    } //positionnerBateau(Bateau bateau, Case c)
    

    /**
     * Permet de positionner aleatoirement les bateaux du joueur 
     */
    public void positionnementAleatoire() {

    } // positionnementAleatoire()
    

    /**
     * Permet au joueur de tirer sur une case
     * @param c case sur laquelle le joueur souhaite tirer
     */
    public abstract void jouerCase(Case c);

    
    /***** GETTER/SETTER *****/
    
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbTirsGagnant() {
        return nbTirsGagnant;
    }

    public void setNbTirsGagnant(int nbTirsGagnant) {
        this.nbTirsGagnant = nbTirsGagnant;
    }

    public int getNbTirsPerdant() {
        return nbTirsPerdant;
    }

    public void setNbTirsPerdant(int nbTirsPerdant) {
        this.nbTirsPerdant = nbTirsPerdant;
    }

    
} // abstract class Joueur
