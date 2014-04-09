package bataille_navale;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
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
        this.setDisabledIcon(bateauImage);
        
    } // CaseVide()

    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public Bateau getBateau() {
        
        return null;
            
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

        
} // clsse CaseVide
