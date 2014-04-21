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
    private final String path = "stockage/fich_config.xml";
    private Document document;
    private final Element racine;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    public DAO_Configuration() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            File f = new File(path);
            File dossier = new File("stockage");
            if (!dossier.exists()) {
                dossier.mkdir();
            }
            if (!f.exists()) {
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

    private void ecrireFichConfig() {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!-- Fichier de configuration (Epoque/Bateaux)\n"
                + " L'id des images bateaux doit commencer par 1 et les id suivants des images doivent suivre dans \n"
                + " l'ordre croissant -->\n"
                + "<epoques>\n"
                + "    <epoque>\n"
                + "        <id>5678</id>\n"
                + "        <nom>Future</nom>\n"
                + "        <siecle>inconnu</siecle>\n"
                + "        <image>Future.jpg</image>\n"
                + "        <bateaux>\n"
                + "            <bateau>\n"
                + "                <nom>Prometheus</nom>\n"
                + "                <longueur>5</longueur>\n"
                + "                <portee>5</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b5a.png</image>\n"
                + "                    <image id=\"2\">b5b.png</image>\n"
                + "                    <image id=\"3\">b5c.png</image>\n"
                + "                    <image id=\"4\">b5d.png</image>\n"
                + "                    <image id=\"5\">b5e.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>BattlestarGalactica</nom>\n"
                + "                <longueur>4</longueur>\n"
                + "                <portee>4</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b4a.png</image>\n"
                + "                    <image id=\"2\">b4b.png</image>\n"
                + "                    <image id=\"3\">b4c.png</image>\n"
                + "                    <image id=\"4\">b4d.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Raptor</nom>\n"
                + "                <longueur>3</longueur>\n"
                + "                <portee>3</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b3a.png</image>\n"
                + "                    <image id=\"2\">b3b.png</image>\n"
                + "                    <image id=\"3\">b3c.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Viper</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b2a.png</image>\n"
                + "                    <image id=\"2\">b2b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Couleuvre</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b22a.png</image>\n"
                + "                    <image id=\"2\">b22b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "        </bateaux>\n"
                + "    </epoque>\n"
                + "    <epoque>\n"
                + "        <id>4678</id>\n"
                + "        <nom>Pocahontas</nom>\n"
                + "        <siecle>15eme</siecle>\n"
                + "        <image>Pocahontas.png</image>\n"
                + "        <bateaux>\n"
                + "            <bateau>\n"
                + "                <nom>Santa-Maria</nom>\n"
                + "                <longueur>5</longueur>\n"
                + "                <portee>5</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b5a.png</image>\n"
                + "                    <image id=\"2\">b5b.png</image>\n"
                + "                    <image id=\"3\">b5c.png</image>\n"
                + "                    <image id=\"4\">b5d.png</image>\n"
                + "                    <image id=\"5\">b5e.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Pinta</nom>\n"
                + "                <longueur>4</longueur>\n"
                + "                <portee>4</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b4a.png</image>\n"
                + "                    <image id=\"2\">b4b.png</image>\n"
                + "                    <image id=\"3\">b4c.png</image>\n"
                + "                    <image id=\"4\">b4d.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Nina</nom>\n"
                + "                <longueur>3</longueur>\n"
                + "                <portee>3</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b3a.png</image>\n"
                + "                    <image id=\"2\">b3b.png</image>\n"
                + "                    <image id=\"3\">b3c.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Caraque</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b2a.png</image>\n"
                + "                    <image id=\"2\">b2b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Caravelle</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b22a.png</image>\n"
                + "                    <image id=\"2\">b22b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "        </bateaux>\n"
                + "    </epoque>\n"
                + "    <epoque>\n"
                + "        <id>8678</id>\n"
                + "        <nom>StarWars</nom>\n"
                + "        <siecle>Inconnu</siecle>\n"
                + "        <image>StarWars.png</image>\n"
                + "        <bateaux>\n"
                + "            <bateau>\n"
                + "                <nom>Leviathan</nom>\n"
                + "                <longueur>4</longueur>\n"
                + "                <portee>4</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b4a.png</image>\n"
                + "                    <image id=\"2\">b4b.png</image>\n"
                + "                    <image id=\"3\">b4c.png</image>\n"
                + "                    <image id=\"4\">b4d.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Corvette</nom>\n"
                + "                <longueur>3</longueur>\n"
                + "                <portee>3</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b33a.png</image>\n"
                + "                    <image id=\"2\">b33b.png</image>\n"
                + "                    <image id=\"3\">b33c.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Cimeterre</nom>\n"
                + "                <longueur>3</longueur>\n"
                + "                <portee>3</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b3a.png</image>\n"
                + "                    <image id=\"2\">b3b.png</image>\n"
                + "                    <image id=\"3\">b3c.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Jedi Starfighter</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b2a.png</image>\n"
                + "                    <image id=\"2\">b2b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "            <bateau>\n"
                + "                <nom>Fighter</nom>\n"
                + "                <longueur>2</longueur>\n"
                + "                <portee>2</portee>\n"
                + "                <images>\n"
                + "                    <image id=\"1\">b22a.png</image>\n"
                + "                    <image id=\"2\">b22b.png</image>\n"
                + "                </images>\n"
                + "            </bateau>\n"
                + "        </bateaux>\n"
                + "    </epoque>\n"
                + "</epoques>";
        try {
            File f = new File("stockage/fich_config.xml");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(s);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DAO_Parametre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

} // class DAO_Configuration
