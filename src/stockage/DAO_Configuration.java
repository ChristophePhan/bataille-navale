package stockage;

import bataille_navale.Bateau;
import bataille_navale.Epoque;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * DAO_Configuration
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAO_Configuration {

    ////////////////////////////// VARIABLES //////////////////////////////////
    private String path = "fich_config.xml";
    private Document document;
    private Element racine;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    public DAO_Configuration() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            document = sxb.build(new File(path));
        } catch (JDOMException | IOException e) {
        }
        racine = document.getRootElement();
    } // DAO_Configuration()

    ////////////////////////////// FONCTIONS //////////////////////////////////
    /**
     * Permet de recuperer la liste des epoques disponibles dans le fichier de
     * configuration
     *
     * @return la liste des epoques disponible
     */
    public HashMap<String, Epoque> getAllEpoques() {
        List listeEpoquesXML = racine.getChildren("epoques");
        HashMap<String, Epoque> listeEpoques = new HashMap();
        Iterator i = listeEpoquesXML.iterator();
        while (i.hasNext()) {
            Epoque ep = new Epoque();
            Element courant = (Element) i.next();
            ep.setEpoque(courant.getChildText("epoque"));
            ep.setId(courant.getChildText("id"));
            ep.setImage(courant.getChildText("image"));
            ep.setNom(courant.getChildText("nom"));
            
            List bateauxXML = courant.getChildren("bateaux");
            Iterator i2 = bateauxXML.iterator();
            HashMap<String, Bateau> bateaux = new HashMap<String, Bateau>();
            while (i2.hasNext()) {
                Bateau b = new Bateau();
                Element courant2 = (Element)i2.next();
                b.setNom(courant2.getChildText("nom"));
                b.setLongueur(Integer.parseInt(courant2.getChildText("longueur")));
                b.setPortee(Integer.parseInt(courant2.getChildText("portee")));
                List imagesBateauXML = courant2.getChildren("images");
                Iterator i3 = imagesBateauXML.iterator();
                List images = new ArrayList();
                while(i3.hasNext()){
                    Element courant3 = (Element)i3.next();
                    images.add(courant3.getChildText("image"));
                }
                b.setImagesBateau(images);
                bateaux.put(courant2.getChildText("nom"), b);
            }
            ep.setListBateaux(bateaux);
            listeEpoques.put(courant.getChildText("nom"), ep);
        }
        return listeEpoques;
    } // getAllEpoques()

    /**
     * Permet de recuperer la liste des bateaux disponibles pour une epoque
     * donnee dans le fichier de configuration
     *
     * @param epoque epoque dont on souhaite recuperer les bateaux
     * @return la liste des bateaux disponibles pour l'epoque donnee
     */
    public HashMap getAllBateaux(Epoque epoque) {
        return epoque.getListBateaux();

    } // getAllBateaux(Epoque epoque)

} // class DAO_Configuration
