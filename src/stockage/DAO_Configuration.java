package stockage;

import bataille_navale.Bateau;
import bataille_navale.Epoque;
import java.io.File;
import java.io.IOException;
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
    
    
    private final String path = getClass().getResource("fich_config.xml").getFile();
    private Document document;
    private final Element racine;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Configuration() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            File f = new File(path);  
            document = sxb.build(f);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
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
        List listeEpoquesXML = racine.getChildren("epoque");
        HashMap<String, Epoque> listeEpoques = new HashMap();
        Iterator i = listeEpoquesXML.iterator();
        while (i.hasNext()) {
            Epoque ep = new Epoque();
            Element courant = (Element) i.next();
            ep.setEpoque(courant.getChildText("siecle"));
            ep.setId(courant.getChildText("id"));
            ep.setImage(courant.getChildText("image"));
            ep.setNom(courant.getChildText("nom"));
            Element bateaux = courant.getChild("bateaux");
            Iterator i2 = bateaux.getChildren().iterator();
            HashMap<String, Bateau> bateauxHM = new HashMap<>();
            while (i2.hasNext()) {
                Bateau b = new Bateau();
                Element courant2 = (Element) i2.next();
                b.setNom(courant2.getChildText("nom"));
                b.setLongueur(Integer.parseInt(courant2.getChildText("longueur")));
                b.setPortee(Integer.parseInt(courant2.getChildText("portee")));
                Element images = courant2.getChild("images");
                Iterator i3 = images.getChildren("image").iterator();
                HashMap<Integer, String> imagesL = new HashMap<>();
                while (i3.hasNext()) {
                    Element courant3 = (Element) i3.next();
                    imagesL.put(Integer.parseInt(courant3.getAttributeValue("id")), courant3.getText());
                }
                b.setImagesBateau(imagesL);
                bateauxHM.put(courant2.getChildText("nom"), b);
            }
            ep.setListBateaux(bateauxHM);
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
