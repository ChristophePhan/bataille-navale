package stockage;

import bataille_navale.Profil;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO_Sauvegarde
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAO_Sauvegarde {

    ////////////////////////////// VARIABLES //////////////////////////////////
    //private final String path = "fich_sauv.xml";
    private XMLEncoder encoder;
    private XMLDecoder decoder;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    public DAO_Sauvegarde() {
        File f = new File("users");
        if (!f.exists()) {
            f.mkdir();
        }
    }

    ////////////////////////////// FONCTIONS //////////////////////////////////
    /**
     * Permet de recuperer la liste de tous les profils disponibles
     *
     * @return la liste des profils disponibles
     */
    public HashMap getAllProfils() {

        HashMap<String, Profil> liste = new HashMap<>();
        File folder = new File("users");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    String s = "users"+File.separator+listOfFiles[i].getName();
                    decoder = new XMLDecoder(new FileInputStream(s));
                    Profil p = (Profil) decoder.readObject();
                    liste.put(p.getNom(), p);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // fermeture du decodeur
                    decoder.close();
                }
            }
        }
        return liste;

    } // getAllProfils()

    /**
     * ************************** PROFIL ************************************
     */
    /**
     * Permet de creer un nouveau profil
     *
     * @param profil nouveau profil a enregistrer
     */
    public void saveProfil(Profil profil) {
        try {
            String file = "users" + File.separator + profil.getNom() + ".xml";
            encoder = new XMLEncoder(new FileOutputStream(file));
            // serialisation de l'objet
            encoder.writeObject(profil);
            encoder.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }
    } // createProfil(Profil profil)
    
    /**
     * Permet de supprimer un profil
     * @param profil profil a supprimer
     */
    public void removeProfil(Profil profil) {
        String s = "users"+File.separator+profil.getNom()+".xml";
        File f = new File(s);
        f.delete();
    } // removeProfil(int id)

    /**
     * Permet de savoir si un profil du meme nom existe deja
     *
     * @param nom nom a tester
     * @return TRUE si un profil ayant le meme nom existe deja, FALSE sinon
     */
    public boolean isExistingProfil(String nom) {

        Iterator iterator = this.getAllProfils().keySet().iterator();
        while (iterator.hasNext()) {

            if (((Profil) this.getAllProfils().get(iterator.next())).getNom().equals(nom)) {

                return true;

            }

        }

        return false;

    } // isExistingProfil(String nom)

//    /**
//     * Permet de mettre a jour un profil
//     * @param profil profil a mettre a jour
//     */
//    public void updateProfil(Profil profil) {
//    } // updateProfil(Profil profil)
//    /**************************** PARTIE *************************************/
//
//        /**
//     * Permet de recuperer la liste de toutes les parties disponibles pour
//     * un profil donne
//     * @param profil profil a partir duquel on souhaite recuperer les parties
//     * @return la liste des parties du profil donne
//     */
//    public List getParties(Profil profil) {
//        
//        return null;
//        
//    } // getParties(Profil profil)
//    /**
//     * Permet de d'ajouter une partie a un profil
//     * @param profil profil auquel on souhaite ajouter la partie
//     * @param partie partie a ajouter au profil
//     */
//    public void createPartie(Profil profil, Partie partie) {
//
//    } // createPartie(Profil profil, Partie partie) 
//
//    
//    /**
//     * Permet de mettre a jour une partie
//     * @param partie partie a mettre a jour
//     */
//    public void updatePartie(Partie partie) {
//
//    } // updatePartie(Partie partie)
//
//    
//    /**
//     * Permet de supprimer une partie 
//     * @param id identifiant de la partie a supprimer
//     */
//    public void removePartie(int id) {
//
//    } // removePartie(int id)
} // class DAO_Sauvegarde
