package com.sschenkel.gsbmedecin;

/**
 * Created by Stéphane on 2/4/2016.
 */
public class Medecin {

    private String nom;
    private String prenom;
    private String adresse;
    private String tel;
    private String specialite;

    Medecin(String nom, String prenom, String adresse, String tel, String specialite){
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        if(specialite.equals("")){
            specialite = "Non renseigné";
        }
        this.specialite = specialite;
    }

    public String getNom() { return this.nom; }
    public String getPrenom() { return this.prenom; }
    public String getAdresse() { return this.adresse; }
    public String getTel() { return this.tel; }
    public String getSpecialite() { return this.specialite; }

    static public String serialize(Medecin leMed){
        String seriaMed = leMed.getNom() + "_" + leMed.getPrenom() + "_" + leMed.getAdresse() + "_" + leMed.getTel() + "_" + leMed.getSpecialite();
        return seriaMed;
    }

    static public Medecin deserialize(String seriaMed){
        String[] splitSeria = seriaMed.split("_");
        return new Medecin(splitSeria[0], splitSeria[1], splitSeria[2], splitSeria[3], splitSeria[4]);
    }

}
