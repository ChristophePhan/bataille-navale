package bataille_navale;

import javax.swing.ImageIcon;

/**
 * CaseVide
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class CaseVide extends Case {
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public CaseVide() {
        
        ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
        this.setIcon(bateauImage);
        
    } // CaseVide()

    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public Bateau getBateau() {
        
        return null;
            
    } // getBateau()

        
} // clsse CaseVide
