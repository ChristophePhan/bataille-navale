package stockage;

import java.util.List;
import bataille_navale.Profil;
import bataille_navale.Partie;

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
    public List getAllProfils() {
        
        return null;
        
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

    
    /**
     * Permet de creer un nouveau profil
     * @param profil nouveau profil a enregistrer
     */
    public void createProfil(Profil profil) {

    } // createProfil(Profil profil)

    
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
    public void removeProfil(int id) {

    } // removeProfil(int id)

    
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
    public void removePartie(int id) {

    } // removePartie(int id)
    

} // class DAO_Sauvegarde
