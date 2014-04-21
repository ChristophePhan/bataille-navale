package bataille_navale;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * CaseBateau
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class CaseBateau extends Case {
    

    ////////////////////////////// VARIABLES //////////////////////////////////


    private Bateau _bateau;
    private String _image;


    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public CaseBateau() {
        
    } // CaseBateau()
    
    
    public CaseBateau(Bateau bateau, Partie partie) {
        super(partie);
        
        this._bateau = bateau;
        //ImageIcon bateauImage = new ImageIcon(getClass().getResource("/stockage/images/Fond_gris.png"));
        if(this._image != null) {
            ImageIcon bateauImage = new ImageIcon(this._image);
            this.setIcon(bateauImage);
        }
        
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
    
    
     /**
     * *** GETTER/SETTER ****
     * @return 
     */

    public String getImage() {
        return _image;
    }

    public void setImage(String image) {
        
        int width = (300+this._partie.getParametre().getNbCaseX()+1)/this._partie.getParametre().getNbCaseX();
        int height = (300+this._partie.getParametre().getNbCaseY()+1)/this._partie.getParametre().getNbCaseY();
        this._image = image;
        String path = "stockage/"+this._partie.getParametre().getEpoque().getNom()+"/";
        String extension = "";
        int i = image.lastIndexOf('.');
        if (i > 0) {
            extension = "."+image.substring(i+1);
        }
        image = image.replace(extension, "");
        if(this.getBateau().getOrientation() == 1){
            image = image+"H"+extension;
            if(new File(path+image).exists()){
                ImageIcon ii= new ImageIcon(new ImageIcon(path+image).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                this.setIcon(ii);
            }
        }else {
            image = image+"V"+extension;
            if(new File(path+image).exists()){
                ImageIcon ii= new ImageIcon(new ImageIcon(path+image).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                this.setIcon(ii);
            }
        }
        
    }
    
} // class CaseBateau
