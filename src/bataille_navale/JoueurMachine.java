package bataille_navale;

import java.util.Iterator;
import java.util.Random;

/**
 * JoueurMachine
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class JoueurMachine extends Joueur {

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public JoueurMachine(Partie partie, String nom) {
        super(partie,nom);
        
    } // JoueurMachine()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    @Override
    public void positionnementAleatoire() {
        
        int difficulte = this._partie.getParametre().getDifficulte();
        Iterator iterator = this._partie.getParametre().getBateaux(this._partie.getParametre().getEpoque()).keySet().iterator();
        while(iterator.hasNext()) {
            
            Random rand = new Random();
            int sens = rand.nextInt(3)+1;
            Bateau bateau = (Bateau) iterator.next();
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
                        CaseBateau caseBateau = new CaseBateau(bateau);
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
                        CaseBateau caseBateau = new CaseBateau(bateau);
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
    public void jouerCase(Case c) {

    } // jouerCase(Case c)

    
} // class JoueurMachine
