package bataille_navale;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    public CaseVide(Partie partie) {
        super (partie);
        
        ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_blanc.png"));
        this.setIcon(bateauImage);
        this.setDisabledIcon(bateauImage);
        
    } // CaseVide(Partie partie)

    
    /////////////////////////////// FONCTIONS /////////////////////////////////
    

    @Override
    public Bateau getBateau() {
        
        return null;
            
    } // getBateau()

    
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
        
        try {
            
            // Recuperation des coordonnees de la case d'origine
            final String coord = (String) dtde.getTransferable().getTransferData(
                    new DataFlavor("application/x-java-jvm-local-objectref; class=java.lang.String"));
            int x = Integer.parseInt(coord.split("x")[0]);
            int y = Integer.parseInt(coord.split("x")[1]);
            this._partie.positionnerBateau(x, y, this);
            
        } catch (UnsupportedFlavorException | IOException | ClassNotFoundException ex) {
            Logger.getLogger(CaseVide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } // drop(DropTargetDropEvent dtde)

    @Override
    public void setBateau(Bateau b) {
        
    }
      
} // clsse CaseVide
