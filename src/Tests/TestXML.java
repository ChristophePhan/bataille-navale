/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import bataille_navale.Epoque;
import java.util.HashMap;
import stockage.DAO_Configuration;

/**
 *
 * @author Samy
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
    }

}
