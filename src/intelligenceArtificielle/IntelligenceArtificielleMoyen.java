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
        
//        if (!case1.isEtat() && !case1.equals(caseTouchee)) {
//            this.listeCaseATester.add(case1);
//        }
//        if (!case2.isEtat() && !case2.equals(caseTouchee)) {
//            this.listeCaseATester.add(case2);
//        }
//        if (!case3.isEtat() && !case3.equals(caseTouchee)) {
//            this.listeCaseATester.add(case3);
//        }
//        if (!case4.isEtat() && !case4.equals(caseTouchee)) {
//            this.listeCaseATester.add(case4);
//        }
        if (!case1.isEtat()) {
            this.listeCaseATester.add(case1);
        }
        if (!case2.isEtat()) {
            this.listeCaseATester.add(case2);
        }
        if (!case3.isEtat()) {
            this.listeCaseATester.add(case3);
        }
        if (!case4.isEtat()) {
            this.listeCaseATester.add(case4);
        }
    }

    private Case tester(Joueur joueurAdverse) {
        Random random = new Random();
        int n = random.nextInt(listeCaseATester.size());
        Case caseTestee = this.listeCaseATester.get(n);
        if (caseTestee.getClass().getSimpleName().equalsIgnoreCase("CaseBateau")) {
            casesATester(joueurAdverse, caseTestee);
            this.listeCaseATester.remove(caseTestee);
        } else {
            this.listeCaseATester.remove(caseTestee);
        }
        return caseTestee;
    }

}
