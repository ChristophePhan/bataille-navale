package bataille_navale;

import java.awt.Image;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import stockage.DAOFactory;

/**
 * JoueurMachine
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class JoueurMachine extends Joueur {

    
    private final List difficultes = DAOFactory.getInstance().getDAO_Parametre().getDifficultees();
    private String difficulte;
    
    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public JoueurMachine() {
        
    } // JoueurMachine()
    
    
    public JoueurMachine(Partie partie, String nom) {
        super(partie,nom);
        
    } // JoueurMachine(Partie partie, String nom)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    @Override
    public void positionnementAleatoire() {
        
        this.difficulte = this._partie.getParametre().getDifficulte();
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
                        CaseBateau caseBateau = new CaseBateau(bateau,this._partie);
                        caseBateau.setImage((String) bateau.getImagesBateau().get(i+1));
                        this._cases.set(xDepart+(yDepart+i)*this._partie.getParametre().getNbCaseX(), caseBateau);
                    }
                    break;
                    
                case 3:
                    // Place le bateau a la diagonale
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
            bateauImage = new ImageIcon(new ImageIcon(getClass().getResource("/stockage/images/Rond_rouge_gris.png"))
                .getImage().getScaledInstance(c.getWidth(), c.getHeight(), Image.SCALE_DEFAULT));
            c.getBateau().setNbCasesNonTouchees(c.getBateau().getNbCasesNonTouchees()-1);
            
        }
        c.setEtat(true);
        c.setEnabled(false);
        c.setDisabledIcon(bateauImage);
        
        return res;

    } // jouerCase(Case c)
    
    
    /**** GETTER/SETTER *****/

    
    public String getDifficulte() {
        return difficulte;
    }

    
    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

} // class JoueurMachine
