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
 * IntelligenceArtificielleDifficile
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class IntelligenceArtificielleDifficile extends IntelligenceArtificielle {

    public IntelligenceArtificielleDifficile(Parametre parametre) {
        super(parametre);
    }

    @Override
    public Case getCaseForIA(Joueur joueurAdverse) {
        Random rand = new Random();
        int x;
        int y;
        Case caseTouchee = null;
        if (listeCaseATester.isEmpty()) {
            do {
                x = rand.nextInt(this._parametre.getNbCaseX());
                y = rand.nextInt(this._parametre.getNbCaseY());
                while (((Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()))).isEtat()) {
                    x = rand.nextInt(this._parametre.getNbCaseX());
                    y = rand.nextInt(this._parametre.getNbCaseY());
                }
                if (x % 2 != 0) {
                    y++;
                } else if (y == -1) {
                    y = 1;
                }
                caseTouchee = (Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()));
            } while (((Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()))).isEtat());
        } /*else {
         caseTouchee = (Case) listeCaseATester.pop();
         if (caseTouchee.isEtat()) {
         //                testerCase(joueurAdverse, caseTouchee);
         }
         }*/

        return caseTouchee;
    }

}
