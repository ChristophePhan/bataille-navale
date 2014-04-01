package stockage;

import java.util.List;
import bataille_navale.Profil;
import bataille_navale.Partie;
import java.util.HashMap;
import java.util.Iterator;

/**
 * DAO_Sauvegarde
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAO_Sauvegarde {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////
    
    
    String path = "fich_sauv.xml";
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Sauvegarde() {
        
    } // DAO_Configuration()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet de recuperer la liste de tous les profils disponibles
     * @return la liste des profils disponibles
     */
    public HashMap getAllProfils() {
        
        Profil p1 = new Profil("Tristan");
        Profil p2 = new Profil("Christophe");
        Profil p3 = new Profil("Samy");
        HashMap<String,Profil> liste = new HashMap<>();
        liste.put("Tristan", p1);
        liste.put("Christophe", p2);
        liste.put("Samy", p3);
        return liste;
        
    } // getAllProfils()

    
    /**
     * Permet de recuperer la liste de toutes les parties disponibles pour
     * un profil donne
     * @param profil profil a partir duquel on souhaite recuperer les parties
     * @return la liste des parties du profil donne
     */
    public List getParties(Profil profil) {
        
        return null;
        
    } // getParties(Profil profil)
    
    
    /**************************** PROFIL *************************************/

    
    /**
     * Permet de creer un nouveau profil
     * @param profil nouveau profil a enregistrer
     */
    public void createProfil(Profil profil) {

    } // createProfil(Profil profil)
    
    
    /**
     * Permet de savoir si un profil du meme nom existe deja
     * @param nom nom a tester
     * @return TRUE si un profil ayant le meme nom existe deja, FALSE sinon
     */
    public boolean isExistingProfil(String nom) {
        
        Iterator iterator = this.getAllProfils().keySet().iterator();
        while(iterator.hasNext()) {
            
            if(((Profil)this.getAllProfils().get(iterator.next())).getNom().equals(nom)) {
                
                return true;
                
            }
            
        }
        
        return false;
        
    } // isExistingProfil(String nom)

    
    /**
     * Permet de mettre a jour un profil
     * @param profil profil a mettre a jour
     */
    public void updateProfil(Profil profil) {

    } // updateProfil(Profil profil)

    
    /**
     * Permet de supprimer un profil
     * @param id identifiant du profil a supprimer
     */
    public void removeProfil(String id) {

    } // removeProfil(String id)
    
    
    /**************************** PARTIE *************************************/

    
    /**
     * Permet de d'ajouter une partie a un profil
     * @param profil profil auquel on souhaite ajouter la partie
     * @param partie partie a ajouter au profil
     */
    public void createPartie(Profil profil, Partie partie) {

    } // createPartie(Profil profil, Partie partie) 

    
    /**
     * Permet de mettre a jour une partie
     * @param partie partie a mettre a jour
     */
    public void updatePartie(Partie partie) {

    } // updatePartie(Partie partie)

    
    /**
     * Permet de supprimer une partie 
     * @param id identifiant de la partie a supprimer
     */
    public void removePartie(String id) {

    } // removePartie(int id)
    

} // class DAO_Sauvegarde
