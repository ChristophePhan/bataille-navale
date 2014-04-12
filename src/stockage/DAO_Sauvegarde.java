package stockage;

import bataille_navale.Bateau;
import bataille_navale.Case;
import bataille_navale.CaseBateau;
import bataille_navale.CaseVide;
import bataille_navale.Epoque;
import bataille_navale.Joueur;
import bataille_navale.JoueurHumain;
import bataille_navale.JoueurMachine;
import bataille_navale.Parametre;
import bataille_navale.Partie;
import bataille_navale.Profil;
import intelligenceArtificielle.FactoryIA;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * DAO_Sauvegarde
 *
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class DAO_Sauvegarde {

    
    ////////////////////////////// VARIABLES //////////////////////////////////
    
    
    //private final String path = "fich_sauv.xml";
    private XMLEncoder encoder;
    private XMLDecoder decoder;

    ///////////////////////////// CONSTRUCTEUR ////////////////////////////////
    
    
    public DAO_Sauvegarde() {
        
        File f = new File("users");
        if (!f.exists()) {
            f.mkdir();
        }
        
    } // DAO_Sauvegarde()

    ////////////////////////////// FONCTIONS //////////////////////////////////
    
    
    /**
     * Permet de recuperer la liste de tous les profils disponibles
     *
     * @return la liste des profils disponibles
     */
    public HashMap getAllProfils() {

        /*HashMap<String, Profil> liste = new HashMap<>();
        File folder = new File("users");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    String s = "users"+File.separator+listOfFiles[i].getName();
                    decoder = new XMLDecoder(new FileInputStream(s));
                    Profil p = (Profil) decoder.readObject();
                    liste.put(p.getNom(), p);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // fermeture du decodeur
                    decoder.close();
                }
            }
        }
        return liste;*/
        
        HashMap<String, Profil> liste = new HashMap<>();
        File folder = new File("users");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().equals(".DS_Store")) {
                try {

                    SAXBuilder builder = new SAXBuilder();
                    Document document = (Document) builder.build(listOfFiles[i]);
                    Element rootNode = document.getRootElement();
                    Profil p = new Profil();
                    
                    // ID
                    Element id = rootNode.getChild("id");
                    p.setId(id.getAttributeValue("id"));
                    
                    // Nom
                    Element nom = rootNode.getChild("nom");
                    p.setNom(nom.getAttributeValue("nom"));
                    
                    // Parties
                    List list = rootNode.getChildren("partie");
                    HashMap<String,Partie> parties = new HashMap<>();
                    for (int j = 0; j < list.size(); j++) {
                        
                        Element partie = (Element) list.get(j);
                        String idPartie = partie.getAttributeValue("id");
                        Partie laPartie = this.getPartie(idPartie, p);
                        parties.put(laPartie.getId(), laPartie);
                        
                    }
                    p.setParties(parties);
                    
                    liste.put(p.getNom(), p);
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        
        return liste;

    } // getAllProfils()

    
    /*************************** PROFIL ************************************/
     
    
    /**
     * Permet de creer un nouveau profil
     * @param profil nouveau profil a enregistrer
     */
    public void saveProfil(Profil profil) {
        /*try {
            String file = "users" + File.separator + profil.getNom() + ".xml";
            encoder = new XMLEncoder(new FileOutputStream(file));
            // serialisation de l'objet
            encoder.writeObject(profil);
            encoder.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }*/
        
        try {
 
            // Profil
            Element eProfil = new Element("profil");
            Document doc = new Document();
            doc.setRootElement(eProfil);

            // Id
            Element idProfil = new Element("id");
            idProfil.setAttribute(new Attribute("id", profil.getId()));
            doc.getRootElement().addContent(idProfil);

            // Nom
            Element nomProfil = new Element("nom");
            nomProfil.setAttribute(new Attribute("nom", profil.getNom()));
            doc.getRootElement().addContent(nomProfil);

            Iterator iterator = profil.getParties().keySet().iterator();
            while(iterator.hasNext()) {
                
                Partie partie = (Partie)profil.getParties().get(iterator.next());
                
                Element p = new Element("partie");
                p.setAttribute(new Attribute("id", partie.getId()));
                p.addContent(new Element("date").setText(partie.getDate()));
                String auto = partie.isAutomatique() ? "1" : "0";
                p.addContent(new Element("automatique").setText(auto));
                
                // Parametre
                Element parametre = new Element("parametre");
               
                parametre.addContent(new Element("nbCaseX").setText(partie.getParametre().getNbCaseX()+""));
                parametre.addContent(new Element("nbCaseY").setText(partie.getParametre().getNbCaseY()+""));
                parametre.addContent(new Element("difficulte").setText(partie.getParametre().getDifficulte()+""));
                parametre.addContent(new Element("nomEpoque").setText(partie.getParametre().getEpoque().getNom()));
                p.addContent(parametre);
                
                // J1
                Element joueur1 = new Element("joueur1");
                joueur1.setAttribute(new Attribute("num", 1+""));
                joueur1.addContent(new Element("nom").setText(partie.getJ1().getNom()+""));
                joueur1.addContent(new Element("nbTirsGagnant").setText(partie.getJ1().getNbTirsGagnant()+""));
                joueur1.addContent(new Element("nbTirsPerdant").setText(partie.getJ1().getNbTirsPerdant()+""));
                for(Case c : partie.getJ1().getCases()) {
                    
                    Element caseJ = new Element("case");
                    String etat = c.isEtat() ? "1" : "0";
                    caseJ.addContent(new Element("etat").setText(etat));
                    String aPortee = c.isAPortee() ? "1" : "0";
                    caseJ.addContent(new Element("aPortee").setText(aPortee));
                    caseJ.addContent(new Element("abs").setText(c.getAbs()+""));
                    caseJ.addContent(new Element("ord").setText(c.getOrd()+""));
                    caseJ.addContent(new Element("idPartie").setText(c.getPartie().getId()));
                    String bateau = (c.getBateau() == null) ? "null" : c.getBateau().getNom();
                    if(c.getBateau() == null) {
                        
                        // Case vide
                        caseJ.addContent(new Element("bateau").setText("null"));
                        caseJ.addContent(new Element("nbCasesNonTouchees").setText("0"));
                        caseJ.addContent(new Element("orientation").setText("0"));
                    
                    } else {
                        
                        // Case bateau
                        caseJ.addContent(new Element("bateau").setText(c.getBateau().getNom()));
                        caseJ.addContent(new Element("nbCasesNonTouchees").setText(c.getBateau().getNbCasesNonTouchees()+""));
                        caseJ.addContent(new Element("orientation").setText(c.getBateau().getOrientation()+""));
                        
                    }
                    joueur1.addContent(caseJ);
                    
                }
                p.addContent(joueur1);
                
                // J2
                Element joueur2 = new Element("joueur2");
                joueur2.setAttribute(new Attribute("num", 2+""));
                joueur2.addContent(new Element("nom").setText(partie.getJ2().getNom()+""));
                joueur2.addContent(new Element("nbTirsGagnant").setText(partie.getJ2().getNbTirsGagnant()+""));
                joueur2.addContent(new Element("nbTirsPerdant").setText(partie.getJ2().getNbTirsPerdant()+""));
                joueur2.addContent(new Element("difficulte").setText(((JoueurMachine)partie.getJ2()).getDifficulte()));
                for(Case c : partie.getJ2().getCases()) {
                    
                    Element caseJ = new Element("case");
                    String etat = c.isEtat() ? "1" : "0";
                    caseJ.addContent(new Element("etat").setText(etat));
                    String aPortee = c.isAPortee() ? "1" : "0";
                    caseJ.addContent(new Element("aPortee").setText(aPortee));
                    caseJ.addContent(new Element("abs").setText(c.getAbs()+""));
                    caseJ.addContent(new Element("ord").setText(c.getOrd()+""));
                    caseJ.addContent(new Element("idPartie").setText(c.getPartie().getId()));
                    String bateau = (c.getBateau() == null) ? "null" : c.getBateau().getNom();
                    caseJ.addContent(new Element("bateau").setText(bateau));
                    joueur2.addContent(caseJ);
                    
                }
                p.addContent(joueur2);
                
                doc.getRootElement().addContent(p);
                
            }

            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // Enregistre le fichier
            String file = "users" + File.separator + profil.getNom() + ".xml";
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(file));
 
        } catch (IOException io) {
              System.out.println(io.getMessage());
        }
      
    } // createProfil(Profil profil)
    
    
    /**
     * Permet de supprimer un profil
     * @param profil profil a supprimer
     */
    public void removeProfil(Profil profil) {
        String s = "users"+File.separator+profil.getNom()+".xml";
        File f = new File(s);
        f.delete();
    } // removeProfil(int id)

    
    /**
     * Permet de savoir si un profil du meme nom existe deja
     *
     * @param nom nom a tester
     * @return TRUE si un profil ayant le meme nom existe deja, FALSE sinon
     */
    public boolean isExistingProfil(String nom) {

        Iterator iterator = this.getAllProfils().keySet().iterator();
        while (iterator.hasNext()) {

            if (((Profil) this.getAllProfils().get(iterator.next())).getNom().equals(nom)) {

                return true;

            }

        }

        return false;

    } // isExistingProfil(String nom)

    
//    /**
//     * Permet de mettre a jour un profil
//     * @param profil profil a mettre a jour
//     */
//    public void updateProfil(Profil profil) {
//    } // updateProfil(Profil profil)
    
    
//    /**************************** PARTIE ***********************************/
//
//    /**
//     * Permet de recuperer la liste de toutes les parties disponibles pour
//     * un profil donne
//     * @param profil profil a partir duquel on souhaite recuperer les parties
//     * @return la liste des parties du profil donne
//     */
//    public List getParties(Profil profil) {
//        
//        return null;
//        
//    } // getParties(Profil profil)
    
    
    public Partie getPartie(String id, Profil profil) {
        
        Partie partie = new Partie();
        File folder = new File("users");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().replaceAll("\\..*", "").equals(profil.getNom())) {
                try {
                    
                    SAXBuilder builder = new SAXBuilder();
                    Document document = (Document) builder.build(listOfFiles[i]);
                    Element rootNode = document.getRootElement();
                    //Profil p = new Profil();
                    
                    List list = rootNode.getChildren("partie");
                    for (int j = 0; j < list.size(); j++) {
                        
                        Element partieElt = (Element) list.get(j);
                        String idPartie = partieElt.getAttributeValue("id");
                        
                        // On en recupere que la bonne partie
                        if(idPartie.equals(id)) {
                        
                            boolean auto = ("1".equals(partieElt.getChildText("automatique")));
                            partie.setId(idPartie);
                            partie.setAutomatique(auto);
                            partie.setDate(partieElt.getChildText("date"));

                            // Parametre
                            Element param = (Element) partieElt.getChild("parametre");
                            Parametre parametre = new Parametre();
                            parametre.setNbCaseX(Integer.parseInt(param.getChildText("nbCaseX")));
                            parametre.setNbCaseY(Integer.parseInt(param.getChildText("nbCaseY")));
                            parametre.setDifficulte(param.getChildText("difficulte"));
                            Epoque epoque = DAOFactory.getInstance().getDAO_Configuration().getAllEpoques().get(param.getChildText("nomEpoque"));
                            parametre.setEpoque(epoque);
                            partie.setParametre(parametre);
                            partie.setIntelligenceArtificielle(FactoryIA.getInstance().getIntelligenceArtificielle(parametre));

                            // J1
                            Element j1 = (Element) partieElt.getChild("joueur1");
                            Joueur joueur1 = new JoueurHumain();
                            joueur1.setPartie(partie);
                            joueur1.setNom(j1.getChildText("nom"));
                            joueur1.setNbTirsGagnant(Integer.parseInt(j1.getChildText("nbTirsGagnant")));
                            joueur1.setNbTirsPerdant(Integer.parseInt(j1.getChildText("nbTirsPerdant")));
                            List listCasesXML = j1.getChildren("case");
                            ArrayList<Case> cases = new ArrayList<>();
                            for (int k = 0; k < listCasesXML.size(); k++) {

                                Element caseElt = (Element) listCasesXML.get(k);
                                Case c = null;
                                if(caseElt.getChildText("bateau").equals("null")) {

                                    c = new CaseVide();
                                    c.setPartie(partie);

                                } else {

                                    c = new CaseBateau(((Bateau)DAOFactory.getInstance().getDAO_Configuration().getAllBateaux(epoque).get(caseElt.getChildText("bateau"))),partie);
                                    c.getBateau().setOrientation(Integer.parseInt(caseElt.getChildText("orientation")));
                                    c.getBateau().setNbCasesNonTouchees(Integer.parseInt(caseElt.getChildText("nbCasesNonTouchees")));
                                    
                                }
                                boolean etat = ("1".equals(caseElt.getChildText("etat")));
                                c.setEtat(etat);
                                c.setAbs(Integer.parseInt(caseElt.getChildText("abs")));
                                c.setOrd(Integer.parseInt(caseElt.getChildText("ord")));
                                boolean aPorte = ("1".equals(caseElt.getChildText("aPorte")));
                                c.setPortee(aPorte);
                                cases.add(c);

                            }
                            joueur1.setCases((ArrayList<Case>) cases);
                            partie.setJ1(joueur1);

                            // J2
                            Element j2 = (Element) partieElt.getChild("joueur2");
                            Joueur joueur2 = new JoueurMachine();
                            joueur2.setPartie(partie);
                            joueur2.setNom(j2.getChildText("nom"));
                            joueur2.setNbTirsGagnant(Integer.parseInt(j2.getChildText("nbTirsGagnant")));
                            joueur2.setNbTirsPerdant(Integer.parseInt(j2.getChildText("nbTirsPerdant")));
                            ((JoueurMachine)joueur2).setDifficulte(j2.getChildText("difficulte"));
                            List listCasesBisXML = j2.getChildren("case");
                            ArrayList<Case> casesBis = new ArrayList<>();
                            for (int k = 0; k < listCasesBisXML.size(); k++) {

                                Element caseElt = (Element) listCasesBisXML.get(k);
                                Case c = null;
                                if(caseElt.getChildText("bateau").equals("null")) {

                                    c = new CaseVide();
                                    c.setPartie(partie);

                                } else {

                                    c = new CaseBateau(((Bateau)DAOFactory.getInstance().getDAO_Configuration().getAllBateaux(epoque).get(caseElt.getChildText("bateau"))),partie);

                                }
                                boolean etat = ("1".equals(caseElt.getChildText("etat")));
                                c.setEtat(etat);
                                c.setAbs(Integer.parseInt(caseElt.getChildText("abs")));
                                c.setOrd(Integer.parseInt(caseElt.getChildText("ord")));
                                boolean aPorte = ("1".equals(caseElt.getChildText("aPorte")));
                                c.setPortee(aPorte);
                                casesBis.add(c);

                            }
                            joueur2.setCases((ArrayList<Case>) casesBis);
                            partie.setJ2(joueur2);

                        }
                        
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(DAO_Sauvegarde.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        partie.initialisationPorteeCases();
        return partie;
        
    } // getPartie(String id, Profil profil)
    
    
//    /**
//     * Permet de d'ajouter une partie a un profil
//     * @param profil profil auquel on souhaite ajouter la partie
//     * @param partie partie a ajouter au profil
//     */
//    public void createPartie(Profil profil, Partie partie) {
//
//    } // createPartie(Profil profil, Partie partie) 
//
//    
//    /**
//     * Permet de mettre a jour une partie
//     * @param partie partie a mettre a jour
//     */
//    public void updatePartie(Partie partie) {
//
//    } // updatePartie(Partie partie)
//
//    
//    /**
//     * Permet de supprimer une partie 
//     * @param id identifiant de la partie a supprimer
//     */
//    public void removePartie(int id) {
//
//    } // removePartie(int id)
    
    
} // class DAO_Sauvegarde
