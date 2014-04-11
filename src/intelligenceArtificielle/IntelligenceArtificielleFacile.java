/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligenceArtificielle;

import bataille_navale.Case;
import bataille_navale.Joueur;
import bataille_navale.Parametre;
import java.util.Random;
import java.util.Stack;

/**
 * IntelligenceArtificielleFacile
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class IntelligenceArtificielleFacile extends IntelligenceArtificielle {

    private final Stack<Case> listeCaseATester;

    public IntelligenceArtificielleFacile(Parametre parametre) {
        super(parametre);
        listeCaseATester = new Stack<>();
    }

    @Override
    public Case getCaseForIA(Joueur joueurAdverse) {
        Random rand = new Random();
        Case caseTouchee;
        int x = rand.nextInt(this._parametre.getNbCaseX());
        int y = rand.nextInt(this._parametre.getNbCaseY());
        while (((Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()))).isEtat()) {
            x = rand.nextInt(this._parametre.getNbCaseX());
            y = rand.nextInt(this._parametre.getNbCaseY());
        }
        caseTouchee = joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX());
        if (caseTouchee.getClass().getSimpleName().equalsIgnoreCase("CaseBateau")) {
            this.listeCaseATester.push(caseTouchee);
        }
        display();
        return caseTouchee;
    }

    private void display() {
        for (int i = 0; i < listeCaseATester.size(); i++) {
            System.out.println("Case touchée " + i + " : " + listeCaseATester.get(i).getAbs() + ", " + listeCaseATester.get(i).getOrd());
        }
//        System.out.println(listeCaseATester.toString());
    }
}
