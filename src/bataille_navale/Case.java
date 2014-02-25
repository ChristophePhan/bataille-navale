package bataille_navale;

/**
 * Case
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Case {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////

    
    private boolean etat;
    
    
    ////////////////////////////// CONSTRUCTEUR ///////////////////////////////
    
    
    public Case() {
        
        this.etat = false;
        
    } // Case()
    
    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    

    /**
     * Permet de retourner le bateau associe a la case pour une case Bateau
     * @return le Bateau associe a la case pour une case Bateau, null sinon
     */
    public abstract Bateau getBateau();
    
    
    /***** GETTER/SETTER *****/
    

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
    
    
} // abstract class Case 