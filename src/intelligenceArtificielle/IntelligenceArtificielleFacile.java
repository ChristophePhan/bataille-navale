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
 * IntelligenceArtificielleFacile
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class IntelligenceArtificielleFacile extends IntelligenceArtificielle {

    public IntelligenceArtificielleFacile(Parametre parametre) {
        super(parametre);
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
            casesATester(joueurAdverse, caseTouchee);
        }
        System.out.println("Case touchée " + caseTouchee.getClass().getSimpleName() + " : " + caseTouchee.getAbs() + ", " + caseTouchee.getOrd());
        display("Facile");
        return caseTouchee;
    }

}
