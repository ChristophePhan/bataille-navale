package bataille_navale;

import java.util.HashMap;

/**
 * Epoque
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Epoque {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////

    
    private String id;
    private String nom;
    private String epoque;
    private String image;
    private HashMap<String, Bateau> listBateaux;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Epoque() {
        
    } // Epoque()

    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
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

    public String getEpoque() {
        return epoque;
    }

    public void setEpoque(String epoque) {
        this.epoque = epoque;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HashMap getListBateaux() {
        return listBateaux;
    }

    public void setListBateaux(HashMap listBateaux) {
        this.listBateaux = listBateaux;
    }
} // class Epoque
