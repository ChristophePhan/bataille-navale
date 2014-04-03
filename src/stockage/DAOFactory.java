package stockage;

/**
 * DAOFactory
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAOFactory {
    
    
    /////////////////////////// VARIABLES //////////////////////////////////
    
    
    private static final DAOFactory INSTANCE = new DAOFactory();

    
    //////////////////////// INITAILAISATION ///////////////////////////////
    
    
    private DAOFactory() {

    
    } // DAOFactory()

    
    /** Point d'accès pour l'instance unique du singleton
      * @return une instance du singleton 
      */
    public static DAOFactory getInstance() {	
        
        return INSTANCE;
        
    } // getInstance()
    
    
    /////////////////////////// FONCTIONS //////////////////////////////////

    
    /**
     * Permet d'avoir acces au fichier de configuration
     * @return la classe permettant l'acces au fichier de configuration
     */
    public DAO_Configuration getDAO_Configuration() {
        
        return new DAO_Configuration();
        
    } // getDAO_Configuration()

    
    /**
     * Permet d'avoir acces au fichier de sauvegarde
     * @return la classe permettant l'acces au fichier de sauvegarde
     */
    public DAO_Sauvegarde getDAO_Sauvegarde() {
        
        return new DAO_Sauvegarde();
        
    } // getDAO_Sauvegarde()
    
    public DAO_Grille getDAO_Grille() {
        
        return new DAO_Grille();
        
    } // getDAO_Sauvegarde()

    
} // class DAOFactory
