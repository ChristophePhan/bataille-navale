package bataille_navale;

import java.util.HashMap;

/**
 * Profil
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Profil {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    private String _id;
    private String _nom;
    private HashMap<String,Partie> _parties;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    public Profil(){
        
    }
    
    public Profil(String nom) {
        
        this._id = nom + this.hashCode();
        this._nom = nom;
        this._parties = new HashMap<>();
        
    } // Profil()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet de creer une nouvelle partie
     * @param p
     */
    public void ajouterNouvellePartie(Partie p) {
        
        this._parties.put(p.getId(), p);
        
    } // nouvellePartie()

    
    /**
     * Permet de charger une nouvelle partie
     * @param partie partie a charger
     */
    public void chargerPartie(Partie partie) {

    } // chargerPartie(Partie partie)
    
    
    /**
     * Permet de supprimer une partie a partir de son id
     * @param id id de la partie a supprimer
     */
    public void supprimerPartie(String id) {
        
        this._parties.remove(id);
        
    } // supprimerPartie(String id)
    

    /**
     * *** GETTER/SETTER ****
     * @return 
     */
      
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

    public HashMap getParties() {
        return _parties;
    }

    public void setParties(HashMap _parties) {
        this._parties = _parties;
    }

    
} // class Profil
