package bataille_navale;

import java.util.Iterator;
import java.util.Random;

/**
 * JoueurMachine
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class JoueurMachine extends Joueur {

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public JoueurMachine(Parametre parametre, String nom) {
        super(parametre,nom);
        
    } // JoueurMachine()
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////

    
    @Override
    public void positionnementAleatoire() {
        
        int difficulte = this._parametre.getDifficulte();
        Iterator iterator = this._parametre.getBateaux(this._parametre.getEpoque()).keySet().iterator();
        while(iterator.hasNext()) {
            
            Random rand = new Random();
            int sens = rand.nextInt(3)+1;
            Bateau bateau = (Bateau) iterator.next();
            int xDepart = rand.nextInt(this._parametre.getNbCaseX()-1-bateau.getLongueur());
            int yDepart = rand.nextInt(this._parametre.getNbCaseY()-1-bateau.getLongueur());
            switch (sens) {
                
                case 1:
                    // Place le bateau horizontalement
                    while(!this.testPositionBateau(bateau.getLongueur(), sens, xDepart, yDepart)) {
                        // On cherche des cases libres pour le bateau
                        xDepart = rand.nextInt(this._parametre.getNbCaseX()-1-bateau.getLongueur());
                        yDepart = rand.nextInt(this._parametre.getNbCaseY()-1-bateau.getLongueur());
                    }
                    for(int i=0;i<bateau.getLongueur();i++) {
                        // On place le bateau 
                        CaseBateau caseBateau = new CaseBateau(bateau);
                        caseBateau.setImage((String) bateau.getImagesBateau().get(i+1));
                        this._cases.set(xDepart+i+yDepart*this._parametre.getNbCaseX(), caseBateau);
                    }
                    break;
                    
                case 2:
                    // Place le bateau verticalement
                    while(!this.testPositionBateau(bateau.getLongueur(), sens, xDepart, yDepart)) {
                        // On cherche des cases libres pour le bateau
                        xDepart = rand.nextInt(this._parametre.getNbCaseX()-1-bateau.getLongueur());
                        yDepart = rand.nextInt(this._parametre.getNbCaseY()-1-bateau.getLongueur());
                    }
                    for(int i=0;i<bateau.getLongueur();i++) {
                        // On place le bateau 
                        CaseBateau caseBateau = new CaseBateau(bateau);
                        caseBateau.setImage((String) bateau.getImagesBateau().get(i+1));
                        this._cases.set(xDepart+(yDepart+i)*this._parametre.getNbCaseX(), caseBateau);
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
