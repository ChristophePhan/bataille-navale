package bataille_navale;

import java.util.Observable;
import stockage.DAOFactory;

/**
 * Jeu
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class Jeu extends Observable {
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    private Profil _profilCourant;
    private Partie _partieCourante;

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Jeu() {
        
    } // Jeu()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet de sauvegarder un nouveau profil
     * @param profil profil a sauvegarder
     */
    public void saveProfil(Profil profil) {
        
        DAOFactory.getInstance().getDAO_Sauvegarde().saveProfil(profil);

    } // saveProfil(Profil profil)
    
    
//    /**
//     * Permet de mettre a jour un profil
//     * @param profil profil a mettre a jour
//     */
//    public void updateProfil(Profil profil) {
//
//        DAOFactory.getInstance().getDAO_Sauvegarde().updateProfil(profil);
//        
//    } // updateProfil(Profil profil)
    

    /**
     * Permet de supprimer un profil
     * @param profil identifiant du profil a supprimer
     */
    private void removeProfil(Profil profil) {
        
        DAOFactory.getInstance().getDAO_Sauvegarde().removeProfil(profil);

    } // removeProfil(int id)
    
    
    /**
     * Permet d'afficher les parties d'un profil
     * @param profil profil dont on souhaite afficher les parties
     */
    public void afficherParties(Profil profil) {
        
        this._profilCourant = profil;
        setChanged();
        notifyObservers("parties");
        
    } // afficherParties(Profil profil)

    
    /***** GETTER/SETTER *****/
    
    
    public Profil getProfilCourant() {
        return _profilCourant;
    }
    
    public void setProfilCourant(Profil _profilCourant) {
        this._profilCourant = _profilCourant;
    }

    public Partie getPartieCourante() {
        return _partieCourante;
    }

    public void setPartieCourante(Partie _partieCourante) {
        this._partieCourante = _partieCourante;
    }

 
} // class Jeu
