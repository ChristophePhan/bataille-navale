package bataille_navale;

import java.awt.Color;
import java.awt.dnd.DropTargetListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Case
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Case extends JButton implements DropTargetListener {

    /////////////////////////////// VARIABLES /////////////////////////////////
    protected boolean _etat;
    protected boolean _aPortee;
    protected int _abs;
    protected int _ord;
    protected Partie _partie;

    ////////////////////////////// CONSTRUCTEUR ///////////////////////////////
    public Case() {

        this._etat = false;
        this._aPortee = false;
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

    } // Case()

    public Case(Partie partie) {

        this._partie = partie;
        this._etat = false;
        this._aPortee = false;
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

    } // Case(Partie partie)

    /////////////////////////////// FONCTIONS /////////////////////////////////
    /**
     * Permet de retourner le bateau associe a la case pour une case Bateau
     *
     * @return le Bateau associe a la case pour une case Bateau, null sinon
     */
    public abstract Bateau getBateau();

    /**
     * Met a jour les coordonnees de la case dans la grille de jeu
     *
     * @param abs coordonnee en abscisse de la case
     * @param ord coordonnee en ordonnee de la case
     */
    public void setCoordonnees(int abs, int ord) {

        this._abs = abs;
        this._ord = ord;

    } // setCoordonnees(int abs, int ord)

    /**
     * *** GETTER/SETTER ****
     */
    public boolean isEtat() {
        return _etat;
    }

    public void setEtat(boolean etat) {
        this._etat = etat;
//        if(this.isEtat()){
//            this.setIcon(new ImageIcon(getClass().getResource("/stockage/images/Croix.png")));
//        } 
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

    public Partie getPartie() {
        return _partie;
    }

    public void setPartie(Partie _partie) {
        this._partie = _partie;
    }

    @Override
    public String toString() {
        return "Case : " + _abs + ", " + _ord;
    }

} // abstract class Case 
