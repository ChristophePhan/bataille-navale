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
    
    
    public CaseBateau() {
        
    } // CaseBateau()
    
    
    public CaseBateau(Bateau bateau, Partie partie) {
        super(partie);
        
        this._bateau = bateau;
        ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_gris.png"));
        this.setIcon(bateauImage);
        
    } // CaseBateau(Bateau bateau, Partie partie)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////


    @Override
    public Bateau getBateau() {

        return this._bateau;

    } // getBateau()
    
    @Override
    public void setBateau(Bateau b){
        this._bateau = b;
    }
     
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    
    } // dragEnter(DropTargetDragEvent dtde)

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
      
    } // dragOver(DropTargetDragEvent dtde)

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        
    } // dropActionChanged(DropTargetDragEvent dtde)

    @Override
    public void dragExit(DropTargetEvent dte) {

    } // dragExit(DropTargetEvent dte)

    @Override
    public void drop(DropTargetDropEvent dtde) {
        
        // On ne peut pas deplacer un bateau sur un autre bateau
        
    } // drop(DropTargetDropEvent dtde)
    
    
    /***** GETTER/SETTER *****/

    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
} // class CaseBateau
