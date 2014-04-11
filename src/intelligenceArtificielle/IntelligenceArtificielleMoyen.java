/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligenceArtificielle;

import bataille_navale.Case;
import bataille_navale.Joueur;
import bataille_navale.Parametre;
import java.util.List;
import java.util.Random;

/**
 * IntelligenceArtificielleMoyen
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class IntelligenceArtificielleMoyen extends IntelligenceArtificielle {

    private List listeCaseATester;
        
    public IntelligenceArtificielleMoyen(Parametre parametre) {
        super(parametre);
    }

    @Override
    public Case getCaseForIA(Joueur joueurAdverse) {
        Random rand = new Random();
        Case caseTouchee = null;
        if (listeCaseATester.isEmpty()) {
            int x = rand.nextInt(this._parametre.getNbCaseX());
            int y = rand.nextInt(this._parametre.getNbCaseY());
            while (((Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()))).isEtat()) {
                x = rand.nextInt(this._parametre.getNbCaseX());
                y = rand.nextInt(this._parametre.getNbCaseY());
            }
            caseTouchee = (Case) (joueurAdverse.getCases().get(x + y * this._parametre.getNbCaseX()));
            if (caseTouchee.isEtat()) {
                listeCaseATester.add(caseTouchee);
            }
        } /*else {
            caseTouchee = (Case) listeCaseATester.get(0);
            listeCaseATester.remove(caseTouchee);
//            testerCase(joueurAdverse, caseTouchee);
        }*/
        System.out.println(listeCaseATester.toString());
        return caseTouchee;
    }

}
