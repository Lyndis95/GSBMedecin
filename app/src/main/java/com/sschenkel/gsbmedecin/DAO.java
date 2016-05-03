package com.sschenkel.gsbmedecin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Stéphane on 2/4/2016.
 */
public class DAO {

    public static Document getDocUrl(String url){
        URL myURL = null;
        try{
            myURL = new URL(url);
        }catch(MalformedURLException me){
        }

        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try{
            db = dbf.newDocumentBuilder();
        }catch(ParserConfigurationException parseex){
        }

        try{
            doc = db.parse(myURL.openStream());
        }catch(IOException ioe){
        }catch(SAXException sax){
        }
        return doc;
    }

    public static List<String> getLesDeps(){
        String url = "http://gaemedecins.appspot.com/Controleur/medParDep/listeDep";

        List<String> laListe = new ArrayList<String>();
        Document doc = DAO.getDocUrl(url);
        Element racine = doc.getDocumentElement();

        NodeList listeDep = racine.getElementsByTagName("Departement");
        for (int i = 0; i < listeDep.getLength(); i++){
            Node departement = listeDep.item(i);
            NodeList lesProprietes = departement.getChildNodes();
            String num = "";
            for (int j = 0; j < lesProprietes.getLength(); j++){
                if(lesProprietes.item(j).getNodeName().equals("num")){
                    num += lesProprietes.item(j).getTextContent().trim();
                }
            }
            laListe.add(num);
        }
        return laListe;
    }

    public static List<Medecin> getLesMeds(String numDep){
        String url = "http://gaemedecins.appspot.com/Controleur/medParDep/listeMed/" + numDep;

        List<Medecin> laListe = new ArrayList<Medecin>();
        Document doc = DAO.getDocUrl(url);
        Element racine = doc.getDocumentElement();

        NodeList listeMed = racine.getElementsByTagName("Medecin");
        for (int i = 0; i < listeMed.getLength(); i++){
            Node medecin = listeMed.item(i);
            NodeList lesProprietes = medecin.getChildNodes();
            String nom = "";
            String prenom = "";
            String tel = "";
            String specialite = "";
            String adresse = "";
            for (int j = 0; j < lesProprietes.getLength(); j++){
                if(lesProprietes.item(j).getNodeName().equals("nom")){
                    nom += lesProprietes.item(j).getTextContent().trim();
                }
                if(lesProprietes.item(j).getNodeName().equals("prenom")){
                    prenom += lesProprietes.item(j).getTextContent().trim();
                }
                if(lesProprietes.item(j).getNodeName().equals("tel")){
                    tel += lesProprietes.item(j).getTextContent().trim();
                }
                if(lesProprietes.item(j).getNodeName().equals("specialite")){
                    specialite += lesProprietes.item(j).getTextContent().trim();
                }
                if(lesProprietes.item(j).getNodeName().equals("adresse")){
                    adresse += lesProprietes.item(j).getTextContent().trim();
                }
            }
            laListe.add(new Medecin(nom, prenom, adresse, tel, specialite));
        }
        return laListe;
    }
}
