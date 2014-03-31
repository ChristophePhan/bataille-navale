package bataille_navale;

import java.util.ArrayList;

/**
 * Joueur
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Joueur {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    private String _nom;
    private int _nbTirsGagnant;
    private int _nbTirsPerdant;
    private ArrayList<Case> cases;

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Joueur(String nom) {
        
        this._nom = nom;
        this._nbTirsGagnant = 0;
        this._nbTirsPerdant = 0;
        
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
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public int getNbTirsGagnant() {
        return _nbTirsGagnant;
    }

    public void setNbTirsGagnant(int nbTirsGagnant) {
        this._nbTirsGagnant = nbTirsGagnant;
    }

    public int getNbTirsPerdant() {
        return _nbTirsPerdant;
    }

    public void setNbTirsPerdant(int nbTirsPerdant) {
        this._nbTirsPerdant = nbTirsPerdant;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }
    
} // abstract class Joueur
