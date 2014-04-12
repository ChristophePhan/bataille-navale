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

/**
 * IntelligenceArtificielleMoyen
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class IntelligenceArtificielleMoyen extends IntelligenceArtificielle {

    public IntelligenceArtificielleMoyen(Parametre parametre) {
        super(parametre);
    }

    @Override
    public Case getCaseForIA(Joueur joueurAdverse) {
        Random rand = new Random();
        Case caseTouchee = null;
        if (this.listeCaseATester.isEmpty()) {
            int x = rand.nextInt(this._parametre.getNbCaseX());
            int y = rand.nextInt(this._parametre.getNbCaseY());
            while (((Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()))).isEtat()) {
                x = rand.nextInt(this._parametre.getNbCaseX());
                y = rand.nextInt(this._parametre.getNbCaseY());
            }
            caseTouchee = (Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()));
            if (caseTouchee.getClass().getSimpleName().equalsIgnoreCase("CaseBateau")) {
                casesATester(joueurAdverse, caseTouchee);
            }
        } else {
            caseTouchee = tester(joueurAdverse);
        }
        System.out.println("Case touchée " + caseTouchee.getClass().getSimpleName() + " : " + caseTouchee.getAbs() + ", " + caseTouchee.getOrd());
        display();
        return caseTouchee;
    }

    private void casesATester(Joueur joueurAdverse, Case caseTouchee) {
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

    private Case tester(Joueur joueurAdverse) {
        Random random = new Random();
        int n = random.nextInt(listeCaseATester.size());
        Case caseTestee = this.listeCaseATester.get(n);
        if (caseTestee.getClass().getSimpleName().equalsIgnoreCase("CaseBateau")) {
            casesATester(joueurAdverse, caseTestee);
        }
        this.listeCaseATester.remove(caseTestee);

        return caseTestee;
    }

}
