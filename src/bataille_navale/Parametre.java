package bataille_navale;

import java.util.HashMap;
import stockage.DAOFactory;

/**
 * Parametre
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Parametre {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private int _nbCaseX;
    private int _nbCaseY;
    private String _difficulte;
    private boolean _majPortee;
    private Epoque _epoque;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Parametre() {
        
    } // Parametre()
    
    
    public Parametre(int nbCaseX, int nbCaseY, String difficulte, boolean majPortee, Epoque epoque) {
        
        this._nbCaseX = nbCaseX;
        this._nbCaseY = nbCaseY;
        this._difficulte = difficulte;
        this._majPortee = majPortee;
        this._epoque = epoque;
        
    } // Parametre(int nbCaseX, int nbCaseY, String difficulte, boolean majPortee, Epoque epoque)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////


    /**
     * Permet de recuper la liste des epoques disponibles
     * @return la liste des epoques disponibles
     */
    public HashMap getEpoques() {
        
        return DAOFactory.getInstance().getDAO_Configuration().getAllEpoques();
            
    } // getEpoques()
    

    /**
     * Permet de recuperer la liste des bateaux disponibles pour une epoque
     * donnee
     * @param epoque epoque dont on souhaite recuperer les bateaux
     * @return la liste des bateaux de l'epoque
     */
    public HashMap getBateaux(Epoque epoque) {
        
        return DAOFactory.getInstance().getDAO_Configuration().getAllBateaux(epoque);
            
    } // getBateaux(Epoque epoque)

    
    /**
     * *** GETTER/SETTER ****
     * @return 
     */
       
    public int getNbCaseX() {
        return _nbCaseX;
    }

    public void setNbCaseX(int nbCaseX) {
        this._nbCaseX = nbCaseX;
    }

    public int getNbCaseY() {
        return _nbCaseY;
    }

    public void setNbCaseY(int nbCaseY) {
        this._nbCaseY = nbCaseY;
    }

    public String getDifficulte() {
        return _difficulte;
    }

    public void setDifficulte(String difficulte) {
        this._difficulte = difficulte;
    }

    public boolean isMajPortee() {
        return _majPortee;
    }

    public void setMajPortee(boolean _majPortee) {
        this._majPortee = _majPortee;
    }
    
    public Epoque getEpoque() {
        return _epoque;
    }

    public void setEpoque(Epoque _epoque) {
        this._epoque = _epoque;
    }

    
} // class Parametre
