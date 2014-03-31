package bataille_navale;

import java.util.HashMap;
import java.util.List;

/**
 * Bateau
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Bateau {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////

  
    private String nom;
    private int longueur;
    private int portee;
    private int nbCasesNonTouchees;
    private List imagesBateau;
    private int orientation;
    
    
    ////////////////////////////// CONSTRUCTEUR ///////////////////////////////
    
    
    public Bateau() {
        
    } // Bateau()
    
    
    //////////////////////////////// FONCTIONS ////////////////////////////////

    
    /**
     * Permet de savoir si le bateau est coule ou non
     * @return TRUE si le bateau est coule, FALSE sinon
     */
    public boolean testBateauCoule() {
        
            return (this.nbCasesNonTouchees <= 0);
            
    } // testBateauCoule()

    
    /***** GETTER/SETTER *****/
    
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public int getNbCasesNonTouchees() {
        return nbCasesNonTouchees;
    }

    public void setNbCasesNonTouchees(int nbCasesNonTouchees) {
        this.nbCasesNonTouchees = nbCasesNonTouchees;
    }

    public List getImagesBateau() {
        return imagesBateau;
    }

    public void setImagesBateau(List imagesBateau) {
        this.imagesBateau = imagesBateau;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    
} // class Bateau
