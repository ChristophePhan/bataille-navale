package bataille_navale;

import java.awt.Image;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * JoueurHumain
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class JoueurHumain extends Joueur {

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public JoueurHumain() {
        
    } // JoueurHumain()
    
    
    public JoueurHumain(Partie partie, String nom) {
        super(partie,nom);
        
    } // JoueurHumain(Partie partie, String nom)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    @Override
    public void positionnementAleatoire() {
        
        Iterator iterator = this._partie.getParametre().getBateaux(this._partie.getParametre().getEpoque()).keySet().iterator();
        while(iterator.hasNext()) {
            
            Random rand = new Random();
            int sens = rand.nextInt(2)+1;
            Bateau bateau = new Bateau((Bateau) this._partie.getParametre().getBateaux(this._partie.getParametre().getEpoque()).get(iterator.next()));
            bateau.setNbCasesNonTouchees(bateau.getLongueur());
            int xDepart = rand.nextInt(this._partie.getParametre().getNbCaseX()-1-bateau.getLongueur());
            int yDepart = rand.nextInt(this._partie.getParametre().getNbCaseY()-1-bateau.getLongueur());
            switch (sens) {
                
                case 1:
                    // Place le bateau horizontalement
                    while(!this.testPositionBateau(bateau.getLongueur(), sens, xDepart, yDepart)) {
                        // On cherche des cases libres pour le bateau
                        xDepart = rand.nextInt(this._partie.getParametre().getNbCaseX()-1-bateau.getLongueur());
                        yDepart = rand.nextInt(this._partie.getParametre().getNbCaseY()-1-bateau.getLongueur());
                    }
                    for(int i=0;i<bateau.getLongueur();i++) {
                        // On place le bateau 
                        bateau.setOrientation(1);
                        CaseBateau caseBateau = new CaseBateau(bateau,this._partie);
                        caseBateau.setImage((String) bateau.getImagesBateau().get(i+1));
                        this._cases.set(xDepart+i+yDepart*this._partie.getParametre().getNbCaseX(), caseBateau);
                    }
                    break;
                    
                case 2:
                    // Place le bateau verticalement
                    while(!this.testPositionBateau(bateau.getLongueur(), sens, xDepart, yDepart)) {
                        // On cherche des cases libres pour le bateau
                        xDepart = rand.nextInt(this._partie.getParametre().getNbCaseX()-1-bateau.getLongueur());
                        yDepart = rand.nextInt(this._partie.getParametre().getNbCaseY()-1-bateau.getLongueur());
                    }
                    for(int i=0;i<bateau.getLongueur();i++) {
                        // On place le bateau 
                        bateau.setOrientation(2);
                        CaseBateau caseBateau = new CaseBateau(bateau,this._partie);
                        caseBateau.setImage((String) bateau.getImagesBateau().get(i+1));
                        this._cases.set(xDepart+(yDepart+i)*this._partie.getParametre().getNbCaseX(), caseBateau);
                    }
                    break;
                    
                case 3:
                    // Place le bateau a la diagonale (bas gauche -> haut droit)
                    bateau.setOrientation(3);
                    break;
                
                case 4:
                    // Place le bateau a la diagonale (haut gauche -> bas droit)
                    bateau.setOrientation(4);
                    break;
                    
                default:
                    break;
                
            }
            
        }
        
    } // positionnementAleatoire()

    
    @Override
    public boolean jouerCase(Case c) {
       
        ImageIcon bateauImage = null;
        boolean res = false;
        if(c.getBateau() == null) {
            
            // Tir dans le vide
            this._nbTirsPerdant++;
            bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Croix.png"))
                .getImage().getScaledInstance(c.getWidth(), c.getHeight(), Image.SCALE_DEFAULT));
            
        } else {
            
            // Batteau touche
            this._nbTirsGagnant++;
            res = true;
            bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_rouge.png"))
                .getImage().getScaledInstance(c.getWidth(), c.getHeight(), Image.SCALE_DEFAULT));
            c.getBateau().setNbCasesNonTouchees(c.getBateau().getNbCasesNonTouchees()-1);

        }
        c.setEtat(true);
        c.setEnabled(false);
        c.setDisabledIcon(bateauImage);
        
        return res;
        
    } // jouerCase(Case c)

        
} // class JoueurHumain
