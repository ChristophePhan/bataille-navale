/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligenceArtificielle;

import bataille_navale.Case;
import bataille_navale.Joueur;
import bataille_navale.Parametre;
import java.util.Stack;

/**
 * IntelligenceArtificielle
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public abstract class IntelligenceArtificielle {

    protected Parametre _parametre;
    protected Stack listeCaseATester;

    public IntelligenceArtificielle(Parametre parametre) {
        this._parametre = parametre;
    }

    public abstract Case getCaseForIA(Joueur joueurAdverse);

    public void testerCase(Joueur joueurAdverse, Case caseTouchee) {

    }
}
