/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intelligenceArtificielle;

import bataille_navale.Parametre;

/**
 *
 * @author Samy
 */
public class FactoryIA {
    /////////////////////////// VARIABLES //////////////////////////////////

    private static final FactoryIA INSTANCE = new FactoryIA();

    //////////////////////// INITAILAISATION ///////////////////////////////
    private FactoryIA() {

    } // DAOFactory()

    /**
     * Point d'accès pour l'instance unique du singleton
     *
     * @return une instance du singleton
     */
    public static FactoryIA getInstance() {

        return INSTANCE;

    } // getInstance()

    /////////////////////////// FONCTIONS //////////////////////////////////
    public IntelligenceArtificielle getIntelligenceArtificielle(Parametre parametre) {
        IntelligenceArtificielle intelligenceArtificielle = null;
        switch (parametre.getDifficulte()) {
            case "Facile":
                intelligenceArtificielle = new IntelligenceArtificielleFacile(parametre);
                break;
            case "Normal":
                intelligenceArtificielle = new IntelligenceArtificielleMoyen(parametre);
                break;
            case "Difficile":
                intelligenceArtificielle = new IntelligenceArtificielleDifficile(parametre);
                break;
        }
        return intelligenceArtificielle;
    }

}
