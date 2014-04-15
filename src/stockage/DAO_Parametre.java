/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockage;

import bataille_navale.TailleGrille;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Christophe
 */
public class DAO_Parametre {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    
    
    private final String path = "stockage/fich_param.xml";
    private Document document;
    private final Element racine;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Parametre() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            File f = new File(path);
            File dossier = new File("stockage");
            if(!dossier.exists()){
                dossier.mkdir();
            }
            if(!f.exists()){
                this.ecrireFichParam();
            }
            document = sxb.build(f);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        racine = document.getRootElement();
    }

    
    /**
     * Permet de recuperer les tailles de grille disponible
     * @return une liste des tailles de grille disonible
     */
    public List getTaillesGrille() {
        List grillesXML = racine.getChildren("grille");
        ArrayList<TailleGrille> listeGrilles = new ArrayList<>();
        Iterator i = grillesXML.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            TailleGrille tg = new TailleGrille(Integer.parseInt(courant.getChildText("x")), Integer.parseInt(courant.getChildText("y")));
            listeGrilles.add(tg);
        }
        return listeGrilles;
    }
    
    
    public List getDifficultees() {
        Element difficulteXML = racine.getChild("difficulte");
        List modeXML = difficulteXML.getChildren("mode");
        ArrayList<String> listeDifficulte = new ArrayList<>();
        Iterator i = modeXML.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            String diff = courant.getText();
            listeDifficulte.add(diff);
        }
        return listeDifficulte;
    }
    
    private void ecrireFichParam(){
        try {
            URL s = getClass().getClassLoader().getResource("stockage/fich_param.xml");
            System.out.println(s);
            File f = new File(s.toURI());
            BufferedReader br = new BufferedReader(new FileReader(f));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            String line;
            while(( line = br.readLine()) != null){
                bw.append(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DAO_Parametre.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DAO_Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
