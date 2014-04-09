package bataille_navale;

import java.awt.Color;
import java.awt.dnd.DropTargetListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Case
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Case extends JButton implements DropTargetListener {
    
    
    /////////////////////////////// VARIABLES /////////////////////////////////

    
    private boolean _etat;
    private boolean _aPortee;
    private int _abs;
    private int _ord;
    
    
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
    
    
    /**
     * Met a jour les coordonnees de la case dans la grille de jeu
     * @param abs coordonnee en abscisse de la case
     * @param ord coordonnee en ordonnee de la case
     */
    public void setCoordonnees(int abs, int ord) {
        
        this._abs = abs;
        this._ord = ord;
        
    } // setCoordonnees(int abs, int ord)
  
    
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

    public int getAbs() {
        return _abs;
    }

    public void setAbs(int _abs) {
        this._abs = _abs;
    }

    public int getOrd() {
        return _ord;
    }

    public void setOrd(int _ord) {
        this._ord = _ord;
    }
    
    
} // abstract class Case 
