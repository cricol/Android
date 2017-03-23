package com.gsb.suividevosfrais;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chris on 28/07/2016.
 */
public class AccesDistant implements AsyncResponse {

    // propriétés
    private static final String SERVERADDR = "http://cricol.fr/Android/synchrodonnees.php" ;

    /**
     * Constructeur
     */
    public AccesDistant() {
        super();
    }

    @Override
    public void processFinish(String output) {
        // affichage console du retour du serveur, pour contrôler
        Log.d("serveur", "************" + output);
        // split output (1ere aprtie = instruction, 2eme partie = valeur à traiter)
        String[] message = output.split("%");
        // appel des bons traitements en fonction du retour du serveur
        if (message.length > 1) {
            // le serveur a eu un problème d'accès à la base de données
            if (message[0].equals("connecter")) {
                Log.d("serveur", "************connect :" + message[1]);
            } else if (message[0].equals("enreg")) {
                Log.d("serveur", "************enregistrer :" + message[1]);
            } else if (message[0].equals("Erreur !")) {
                Log.d("serveur", "************Erreur :" + message[1]);
            }

        }
    }

    /**
     * Envoi vers le serveur distant (vers la page PHP)
     * @param operation
     * @param lesDonneesJSON
     */
    public void envoi(String operation, JSONArray lesDonneesJSON) {
        // création de l'objet d'accès à distance
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;

        // ajout des données en paramêtre
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        // envoi
        accesDonnees.execute(SERVERADDR);

    }


}
