package bataille_navale;

/**
 * CaseBateau
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class CaseBateau extends Case {
    

    ////////////////////////////// VARIABLES //////////////////////////////////


    private Bateau _bateau;
    private String image;


    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public CaseBateau(Bateau bateau) {
        
        this._bateau = bateau;
        
    } // CaseBateau(Bateau _bateau)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////


    @Override
    public Bateau getBateau() {

        return this._bateau;

    } // getBateau()
    
    
    /***** GETTER/SETTER *****/

    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
} // class CaseBateau
