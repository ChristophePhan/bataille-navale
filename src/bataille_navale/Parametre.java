package bataille_navale;

import java.util.ArrayList;
import java.util.List;

/**
 * Parametre
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Parametre {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private int nbCaseX;
    private int nbCaseY;
    private int difficulte;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Parametre() {
        
    } // Parametre()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////


    /**
     * Permet de recuper la liste des epoques disponibles
     * @return la liste des epoques disponibles
     */
    public ArrayList getEpoques() {
        
            return null;
            
    } // getEpoques()
    

    /**
     * Permet de recuperer la liste des bateaux disponibles pour une epoque
     * donnee
     * @param epoque epoque dont on souhaite recuperer les bateaux
     * @return la liste des bateaux de l'epoque
     */
    public List getBateaux(Epoque epoque) {
        
            return null;
            
    } // getBateaux(Epoque epoque)

    /***** GETTER/SETTER *****/
    
    
    public int getNbCaseX() {
        return nbCaseX;
    }

    public void setNbCaseX(int nbCaseX) {
        this.nbCaseX = nbCaseX;
    }

    public int getNbCaseY() {
        return nbCaseY;
    }

    public void setNbCaseY(int nbCaseY) {
        this.nbCaseY = nbCaseY;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    
} // class Parametre
