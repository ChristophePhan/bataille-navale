package stockage;

import bataille_navale.Bateau;
import bataille_navale.Epoque;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    private final String path = "fich_config/fich_config.xml";
    private Document document;
    private final Element racine;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Configuration() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            File f = new File(path);  
            if(!f.exists()){
                this.ecrireFichConfig();
            }
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
    
    private void ecrireFichConfig(){
        File f = new File(path);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                "<!--\n" +
                                "exemple de configuration que le fichier xml doit avoir\n" +
                                "-->\n" +
                                "<epoques>\n" +
                                "    <epoque>\n" +
                                "        <id>1234</id>\n" +
                                "        <nom>Contemporaine</nom>\n" +
                                "        <siecle>XXIeme</siecle>\n" +
                                "        <image>nnnn</image>\n" +
                                "        <bateaux>\n" +
                                "            <bateau>\n" +
                                "                <nom>Porte-avions</nom>\n" +
                                "                <longueur>5</longueur>\n" +
                                "                <portee>5</portee>\n" +
                                "                <images>\n" +
                                "                    <image id=\"1\">aaaa</image>\n" +
                                "                    <image id=\"2\">bbbb</image>\n" +
                                "                    <image id=\"3\">cccc</image>\n" +
                                "                    <image id=\"4\">dddd</image>\n" +
                                "                    <image id=\"5\">eeee</image>\n" +
                                "                </images>\n" +
                                "            </bateau>\n" +
                                "        </bateaux>\n" +
                                "    </epoque>\n" +
                                "    \n" +
                                "    <epoque>\n" +
                                "        <id>5678</id>\n" +
                                "        <nom>Future</nom>\n" +
                                "        <siecle>inconnu</siecle>\n" +
                                "        <image>a</image>\n" +
                                "        <bateaux>\n" +
                                "            <bateau>\n" +
                                "                <nom>Enterprise</nom>\n" +
                                "                <longueur>5</longueur>\n" +
                                "                <portee>5</portee>\n" +
                                "                <images>\n" +
                                "                    <image id=\"2\">a</image>\n" +
                                "                    <image id=\"3\">a</image>\n" +
                                "                    <image id=\"4\">a</image>\n" +
                                "                    <image id=\"5\">a</image>\n" +
                                "                    <image id=\"6\">a</image>\n" +
                                "                </images>\n" +
                                "            </bateau>\n" +
                                "            <bateau>\n" +
                                "                <nom>BattlestarGalactica</nom>\n" +
                                "                <longueur>4</longueur>\n" +
                                "                <portee>4</portee>\n" +
                                "                <images>\n" +
                                "                    <image id=\"6\">a</image>\n" +
                                "                    <image id=\"7\">b</image>\n" +
                                "                    <image id=\"8\">c</image>\n" +
                                "                    <image id=\"9\">d</image>\n" +
                                "                </images>\n" +
                                "            </bateau>\n" +
                                "            <bateau>\n" +
                                "                <nom>Raptor</nom>\n" +
                                "                <longueur>3</longueur>\n" +
                                "                <portee>3</portee>\n" +
                                "                <images>\n" +
                                "                    <image id=\"5\">a</image>\n" +
                                "                    <image id=\"6\">a</image>\n" +
                                "                    <image id=\"7\">b</image>\n" +
                                "                </images>\n" +
                                "            </bateau>\n" +
                                "            <bateau>\n" +
                                "                <nom>Viper</nom>\n" +
                                "                <longueur>2</longueur>\n" +
                                "                <portee>2</portee>\n" +
                                "                <images>\n" +
                                "                    <image id=\"6\">a</image>\n" +
                                "                    <image id=\"7\">a</image>\n" +
                                "                </images>\n" +
                                "            </bateau>\n" +
                                "            \n" +
                                "        </bateaux>\n" +
                                "    </epoque>\n" +
                                "</epoques>";
            bw.write(param);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DAO_Parametre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

} // class DAO_Configuration
