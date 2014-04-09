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
    protected Stack listeCaseATester = new Stack();

    public IntelligenceArtificielle(Parametre parametre) {
        this._parametre = parametre;
    }

    public abstract Case getCaseForIA(Joueur joueurAdverse);

    public void testerCase(Joueur joueurAdverse, Case caseTouchee) {
        Case case1 = joueurAdverse.getCases().get(caseTouchee.getAbs() - 1 + this._parametre.getNbCaseX()*caseTouchee.getOrd());
        Case case2 = joueurAdverse.getCases().get(caseTouchee.getAbs() + 1 + this._parametre.getNbCaseX()*caseTouchee.getOrd());
        Case case3 = joueurAdverse.getCases().get(caseTouchee.getAbs() + this._parametre.getNbCaseX()*(caseTouchee.getOrd() - 1));
        Case case4 = joueurAdverse.getCases().get(caseTouchee.getAbs() + this._parametre.getNbCaseX()*(caseTouchee.getOrd() + 1));
        
        Case[] caseTestees = {case1, case2, case3, case4};
        
        Case caseTemp;
        for (Case caseTestee : caseTestees) {
            caseTemp = caseTestee;
            if(caseTemp.getAbs() >= 0 && caseTemp.getAbs() < this._parametre.getNbCaseX() && caseTemp.getOrd()>= 0 && caseTemp.getOrd()< this._parametre.getNbCaseY()){
                boolean etat;
                etat = caseTemp.isEtat();
                if(etat){
                    this.listeCaseATester.push(caseTemp);
                }
            }
        }
    }
}
