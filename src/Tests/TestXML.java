/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import bataille_navale.Epoque;
import bataille_navale.Partie;
import bataille_navale.Profil;
import java.util.ArrayList;
import java.util.HashMap;
import stockage.DAO_Configuration;
import stockage.DAO_Sauvegarde;

/**
 * TestXML
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class TestXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DAO_Configuration dAO_Configuration = new DAO_Configuration();
        HashMap<String, Epoque> mapEpoque = dAO_Configuration.getAllEpoques();
        System.out.println(mapEpoque.get("Contemporaine").getNom());
        System.out.println(mapEpoque.get("Contemporaine").getEpoque());
        System.out.println(mapEpoque.get("Contemporaine").getId());
        
        System.out.println(mapEpoque.get("Future").getNom());
        System.out.println(mapEpoque.get("Future").getEpoque());
        System.out.println(mapEpoque.get("Future").getId());
        
        DAO_Sauvegarde sauv = new DAO_Sauvegarde();
        
        Profil profil = new Profil();
        profil.setId("ddd");
        profil.setNom("zzzzz");
        ArrayList<Partie> p = new ArrayList<>();
        Partie pp = new Partie();
        pp.setId("bbbbb00");
        
        /*p.add(pp);
        profil.setParties(p);
        sauv.saveProfil(profil);
        Profil ppp = (Profil) sauv.getAllProfils().get("zzzzz");
        System.out.println(ppp.getId());
        sauv.removeProfil(profil);*/
    }

}
