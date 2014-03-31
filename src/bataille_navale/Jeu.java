package bataille_navale;

import stockage.DAOFactory;

/**
 * Jeu
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Jeu {

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Jeu() {
        
    } // Jeu()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet de creer un nouveau profil
     */
    public void createProfil() {

    } // createProfil()

    
    /**
     * Permet de sauvegarder un profil
     * @param profil profil a sauvegarder
     */
    public void saveProfil(Profil profil) {
        
        DAOFactory.getInstance().getDAO_Sauvegarde().createProfil(profil);

    } // saveProfil(Profil profil)
    
    
    /**
     * Permet de mettre a jour un profil
     * @param profil profil a mettre a jour
     */
    public void updateProfil(Profil profil) {

        DAOFactory.getInstance().getDAO_Sauvegarde().updateProfil(profil);
        
    } // updateProfil(Profil profil)
    

    /**
     * Permet de supprimer un profil
     * @param id identifiant du profil a supprimer
     */
    private void removeProfil(int id) {
        
        DAOFactory.getInstance().getDAO_Sauvegarde().removeProfil(id);

    } // removeProfil(int id)
    

} // class Jeu
