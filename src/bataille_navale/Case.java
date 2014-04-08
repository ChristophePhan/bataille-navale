package bataille_navale;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Case
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Case extends JButton {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////

    
    private boolean _etat;
    private boolean _aPortee;
    
    
    ////////////////////////////// CONSTRUCTEUR ///////////////////////////////
    
    
    public Case() {
        
        this._etat = false;
        this._aPortee = false;
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
    } // Case()
    
    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    

    /**
     * Permet de retourner le bateau associe a la case pour une case Bateau
     * @return le Bateau associe a la case pour une case Bateau, null sinon
     */
    public abstract Bateau getBateau();
    
    
    /***** GETTER/SETTER *****/
    

    public boolean isEtat() {
        return _etat;
    }

    public void setEtat(boolean etat) {
        this._etat = etat;
    }

    public boolean isAPortee() {
        return _aPortee;
    }

    public void setPortee(boolean _aPortee) {
        this._aPortee = _aPortee;
    }
    
    
} // abstract class Case 
