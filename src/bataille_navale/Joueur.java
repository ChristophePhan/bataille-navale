package bataille_navale;

import java.util.ArrayList;

/**
 * Joueur
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class Joueur {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    

    protected Partie _partie;
    protected String _nom;
    protected int _nbTirsGagnant;
    protected int _nbTirsPerdant;
    protected ArrayList<Case> _cases;

    
    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public Joueur() {
        
    } // Joueur()
    
    
    public Joueur(Partie partie, String nom) {
        
        this._partie = partie;
        this._nom = nom;
        this._nbTirsGagnant = 0;
        this._nbTirsPerdant = 0;
        
        // Remplit la liste de Case du joueur
        this._cases = new ArrayList<>();
        for(int i=0;i<partie.getParametre().getNbCaseX();i++) {
            for(int j=0;j<partie.getParametre().getNbCaseY();j++) {
                
                this._cases.add(new CaseVide(partie));
                
            }
        }

    } // Joueur(Parametre parametre, String nom)
    
    
    ////////////////////////////// FONCTIONS //////////////////////////////////
    

    /**
     * Permet de positionner aleatoirement les bateaux du joueur 
     */
    public abstract void positionnementAleatoire();
    
    
    /**
     * Permet de savoir si on peut placer le bateau a l'endroit desire
     * @param longueur taille du bateau a placer
     * @param sens sens dans lequel on souhaite placer le bateau
     * 1 - horizontal
     * 2 - vertical
     * 3 - diagonale
     * @param x position en abscisse a tester
     * @param y position en ordonnee a tester
     * @return TRUE si le bateau peut etre placer, FALSE sinon
     */
    public boolean testPositionBateau(int longueur, int sens, int x, int y) {
        
        int pos = 0;
        for(int i=0;i<longueur;i++) {
            
            switch(sens) {

                case 1:
                    // Horizontale
                    pos = x+i+y*this._partie.getParametre().getNbCaseX();
                    if(this._cases.get(pos).getBateau() != null) {

                        return false;

                    }
                    break;

                case 2:
                    // Verticale
                    pos = x+(y+i)*this._partie.getParametre().getNbCaseX();
                    if(this._cases.get(pos).getBateau() != null) {

                        return false;

                    }
                    break;

                case 3:
                    // Diagonale
                    break;

                default:
                    break;

            }
            
        }
        
        return true;
        
    } // testPositionBateau(int longueur, int sens)
    

    /**
     * Permet au joueur de tirer sur une case
     * @param c case sur laquelle le joueur souhaite tirer
     * @return TRUE si ub bateau a ete touche, FALSE sinon
     */
    public abstract boolean jouerCase(Case c);

    
    //**** GETTER/SETTER *****//
    
     
    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public int getNbTirsGagnant() {
        return _nbTirsGagnant;
    }

    public void setNbTirsGagnant(int nbTirsGagnant) {
        this._nbTirsGagnant = nbTirsGagnant;
    }

    public int getNbTirsPerdant() {
        return _nbTirsPerdant;
    }

    public void setNbTirsPerdant(int nbTirsPerdant) {
        this._nbTirsPerdant = nbTirsPerdant;
    }

    public ArrayList<Case> getCases() {
        return _cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this._cases = cases;
    }

    public Partie getPartie() {
        return this._partie;
    }

    public void setPartie(Partie partie) {
        this._partie = partie;
    }
    
    
} // abstract class Joueur
