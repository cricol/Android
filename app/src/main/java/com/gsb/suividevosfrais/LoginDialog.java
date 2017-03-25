package com.gsb.suividevosfrais;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by chris on 16/03/17.
 */

public class LoginDialog extends Activity {
    private EditText login;
    private EditText mdp;
    private ArrayList listFrais = new ArrayList();
    private ArrayList listFraisHf = new ArrayList();

    private static AccesDistant accesDistant ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        login = (EditText)findViewById(R.id.txtLogin);
        mdp = (EditText)findViewById(R.id.txtPassword);
        accesDistant = new AccesDistant();
        cmdValider_clic() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.etape, menu);
        return true;
    }

    /**
     * Sur le clic du bouton valider : Envoi sur le Serveur Distant
     */
    private void cmdValider_clic() {
        ((Button)findViewById(R.id.cmdLoginValider)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Iterator iter;
                iter = Global.listFraisMois.keySet().iterator();

                    while (iter.hasNext()) {
                        Object key = iter.next();

                        //Ajout des Frais Hors Forfait dans la base de donnee pour chaque mois ajouter dans l'appli
                        Integer i = 0;
                        for (i = 0; i < Global.listFraisMois.get(key).getLesFraisHf().size(); i++) {
                            listFraisHf.add(Global.listFraisMois.get(key).getLesFraisHf().get(i).getJour().toString());
                            listFraisHf.add(Global.listFraisMois.get(key).getMois().toString());
                            listFraisHf.add(Global.listFraisMois.get(key).getAnnee().toString());
                            listFraisHf.add(Global.listFraisMois.get(key).getLesFraisHf().get(i).getMontant().toString());
                            listFraisHf.add(Global.listFraisMois.get(key).getLesFraisHf().get(i).getMotif().toString());
                            listFraisHf.add("cbedos");
                            listFraisHf.add("gmhxd");
                            JSONArray fraisMoisHf = new JSONArray(listFraisHf);

                            accesDistant.envoi("fraismoisHf", fraisMoisHf);
                            listFraisHf.clear();

                        }
                        //Ajout des frais du mois dans la base de donnee pour chaque mois ajouter dans l'appli
                        listFrais.add(Global.listFraisMois.get(key).getEtape().toString());
                        listFrais.add(Global.listFraisMois.get(key).getKm().toString());
                        listFrais.add(Global.listFraisMois.get(key).getNuitee().toString());
                        listFrais.add(Global.listFraisMois.get(key).getRepas().toString());
                        listFrais.add(Global.listFraisMois.get(key).getAnnee().toString());
                        listFrais.add(Global.listFraisMois.get(key).getMois().toString());
                        listFrais.add(Global.listFraisMois.get(key).getTypeVehicule().toString());
                        listFrais.add("cbedos");
                        listFrais.add("gmhxd");

                        JSONArray fraisMois = new JSONArray(listFrais);

                        accesDistant.envoi("fraismois", fraisMois);
                        listFrais.clear();
                    }


                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(LoginDialog.this, MainActivity.class) ;
        startActivity(intent) ;
    }

}
