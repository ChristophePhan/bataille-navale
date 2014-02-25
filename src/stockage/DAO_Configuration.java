package stockage;

import java.util.List;
import bataille_navale.Epoque;

/**
 * DAO_Configuration
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAO_Configuration {
    
    
    ////////////////////////////// VARIABLES //////////////////////////////////
    
    
    String path = "fich_config.xml";
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Configuration() {
        
    } // DAO_Configuration()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    

    /**
     * Permet de recuperer la liste des epoques disponibles dans le fichier
     * de configuration
     * @return la liste des epoques disponible
     */
    public List getAllEpoques() {
        
        return null;
        
    } // getAllEpoques()

    
    /**
     * Permet de recuperer la liste des bateaux disponibles pour une epoque 
     * donnee dans le fichier de configuration
     * @param epoque epoque dont on souhaite recuperer les bateaux
     * @return la liste des bateaux disponibles pour l'epoque donnee
     */
    public List getAllBateaux(Epoque epoque) {
        
        return null;
            
    } // getAllBateaux(Epoque epoque)

    
} // class DAO_Configuration
