package bataille_navale;

/**
 * Profil
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Profil {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    private String id;
    private String nom;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Profil() {
        
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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
} // class Profil
