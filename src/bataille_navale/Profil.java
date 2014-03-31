package bataille_navale;

import java.util.ArrayList;

/**
 * Profil
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Profil {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    private String _id;
    private String _nom;
    private ArrayList<Partie> _parties;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Profil(String nom) {
        
        this._id = nom + this.hashCode();
        this._nom = nom;
        this._parties = new ArrayList<>();
        
    } // Profil()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet de creer une nouvelle partie
     */
    public void nouvellePartie() {

    } // nouvellePartie()

    
    /**
     * Permet de charger une nouvelle partie
     * @param partie partie a charger
     */
    public void chargerPartie(Partie partie) {

    } // chargerPartie(Partie partie)
    

    /***** GETTER/SETTER *****/
    
    
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public ArrayList<Partie> getParties() {
        return _parties;
    }

    public void setParties(ArrayList<Partie> _parties) {
        this._parties = _parties;
    }

    
} // class Profil
