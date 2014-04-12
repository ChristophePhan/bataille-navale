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
import java.util.Random;

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

    public void display(String s) {
        System.out.println("*******Debut List " + s + "*******");
        for (int i = 0; i < listeCaseATester.size(); i++) {
            System.out.println("Case " + i + " : " + listeCaseATester.get(i).getAbs() + ", " + listeCaseATester.get(i).getOrd());
        }
        System.out.println("*******Fin List " + s + "*******");
    }

    public void casesATester(Joueur joueurAdverse, Case caseTouchee) {
        Case case1 = caseTouchee.getAbs() != 0 ? joueurAdverse.getCases().get((caseTouchee.getAbs() - 1) + this._parametre.getNbCaseX() * caseTouchee.getOrd()) : caseTouchee;
        Case case3 = caseTouchee.getOrd() != 0 ? joueurAdverse.getCases().get(caseTouchee.getAbs() + this._parametre.getNbCaseX() * (caseTouchee.getOrd() - 1)) : caseTouchee;

        Case case2 = caseTouchee.getAbs() != (this._parametre.getNbCaseX() - 1) ? joueurAdverse.getCases().get((caseTouchee.getAbs() + 1) + this._parametre.getNbCaseX() * caseTouchee.getOrd()) : caseTouchee;
        Case case4 = caseTouchee.getOrd() != (this._parametre.getNbCaseY() - 1) ? joueurAdverse.getCases().get(caseTouchee.getAbs() + this._parametre.getNbCaseX() * (caseTouchee.getOrd() + 1)) : caseTouchee;

        Case[] tableau = {case1, case2, case3, case4};

        for (Case caseTableau : tableau) {
            if (!caseTableau.isEtat() && !this.listeCaseATester.contains(caseTableau) && !caseTableau.equals(caseTouchee)) {
                this.listeCaseATester.add(caseTableau);
            }
        }

    }

    public Case tester(Joueur joueurAdverse) {
        Random random = new Random();
        int n = random.nextInt(listeCaseATester.size());
        Case caseTestee = this.listeCaseATester.get(n);
        if (caseTestee.getClass().getSimpleName().equalsIgnoreCase("CaseBateau")) {
            casesATester(joueurAdverse, caseTestee);
        }
        this.listeCaseATester.remove(caseTestee);

        return caseTestee;
    }

    
    /***** GETTER/SETTER *****/
    
    
    public List<Case> getListeCaseATester() {
        return listeCaseATester;
    }

    public void setListeCaseATester(List<Case> listeCaseATester) {
        this.listeCaseATester = listeCaseATester;
    }
 
    
}
