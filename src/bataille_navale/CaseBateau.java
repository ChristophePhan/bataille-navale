package bataille_navale;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import javax.swing.ImageIcon;

/**
 * CaseBateau
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class CaseBateau extends Case {
    

    ////////////////////////////// VARIABLES //////////////////////////////////


    private Bateau _bateau;
    private String image;


    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    public CaseBateau(){
        
    }
    
    public CaseBateau(Bateau bateau) {
        
        this._bateau = bateau;
        ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_gris.png"));
        this.setIcon(bateauImage);
        
    } // CaseBateau(Bateau _bateau)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////


    @Override
    public Bateau getBateau() {

        return this._bateau;

    } // getBateau()
    
    
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("ENTER");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        System.out.println("OVER");
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("CHANGED");
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        System.out.println("EXIT");
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        System.out.println("DROP");
    }
    
    
    /***** GETTER/SETTER *****/

    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
} // class CaseBateau
