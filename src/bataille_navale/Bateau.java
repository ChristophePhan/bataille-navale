package bataille_navale;

import java.util.HashMap;

/**
 * Bateau
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Bateau {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////

  
    private String _nom;
    private int _longueur;
    private int _portee;
    private int _nbCasesNonTouchees;
    /*
     * Integer : position de l'image sur le bateau (1 -> ...)
     * String  : URL de l'image correspondante 
     */
    private HashMap<Integer,String> _imagesBateau;
    /*
     * 1 - Horizontal
     * 2 - Verticale
     * 3 - Diagonale (bas gauche -> haut droit)
     * 4 - Diagonale (haut gauche -> bas droit)
     */
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
        
        return (this._nbCasesNonTouchees <= 0);
            
    } // testBateauCoule()

    
    /***** GETTER/SETTER *****/
    
    
    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public int getLongueur() {
        return _longueur;
    }

    public void setLongueur(int longueur) {
        this._longueur = longueur;
    }

    public int getPortee() {
        return _portee;
    }

    public void setPortee(int portee) {
        this._portee = portee;
    }

    public int getNbCasesNonTouchees() {
        return _nbCasesNonTouchees;
    }

    public void setNbCasesNonTouchees(int nbCasesNonTouchees) {
        this._nbCasesNonTouchees = nbCasesNonTouchees;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public HashMap<Integer, String> getImagesBateau() {
        return _imagesBateau;
    }

    public void setImagesBateau(HashMap<Integer, String> _imagesBateau) {
        this._imagesBateau = _imagesBateau;
    }

} // class Bateau
