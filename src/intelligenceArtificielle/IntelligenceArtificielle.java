/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligenceArtificielle;

import bataille_navale.Case;
import bataille_navale.Joueur;
import bataille_navale.Parametre;
import java.util.ArrayList;
import java.util.List;

/**
 * IntelligenceArtificielle
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class IntelligenceArtificielle {

    protected Parametre _parametre;
    protected List<Case> listeCaseATester;

    public IntelligenceArtificielle(Parametre parametre) {
        this._parametre = parametre;
        this.listeCaseATester = new ArrayList<>();
    }

    public abstract Case getCaseForIA(Joueur joueurAdverse);

    public void display() {
        System.out.println("*******Debut List*******");
        for (int i = 0; i < listeCaseATester.size(); i++) {
            System.out.println("Case " + i + " : " + listeCaseATester.get(i).getAbs() + ", " + listeCaseATester.get(i).getOrd());
        }
//        Set<Case> setCase = new HashSet<>(listeCaseATester);
//        Iterator iterator = setCase.iterator();
//        int i = 0;
//        while (iterator.hasNext()) {
//            System.out.println(i + " " + iterator.next());
//            i++;
//        }
        System.out.println("*******Fin List*******");

    }

    
    /***** GETTER/SETTER *****/
    
    
    public List<Case> getListeCaseATester() {
        return listeCaseATester;
    }

    public void setListeCaseATester(List<Case> listeCaseATester) {
        this.listeCaseATester = listeCaseATester;
    }
 
    
}
