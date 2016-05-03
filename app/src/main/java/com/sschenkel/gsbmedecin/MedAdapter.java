package com.sschenkel.gsbmedecin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stéphane on 2/4/2016.
 */
public class MedAdapter extends BaseAdapter {

    private List<Medecin> lesMeds;
    private Context c;

    MedAdapter(List<Medecin> data, Context c){
        this.lesMeds = data;
        this.c = c;
    }

    @Override
    public int getCount() {
        return lesMeds.size();
    }

    @Override
    public Object getItem(int position) {
        return lesMeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.medrow, null);

        TextView nom = (TextView) v.findViewById(R.id.nom);
        TextView prenom = (TextView) v.findViewById(R.id.prenom);
        TextView adresse = (TextView) v.findViewById(R.id.adresse);
        TextView tel = (TextView) v.findViewById(R.id.tel);
        TextView specialite = (TextView) v.findViewById(R.id.specialite);

        Medecin leMed = lesMeds.get(position);
        nom.setText(leMed.getNom());
        prenom.setText(leMed.getPrenom());
        adresse.setText(leMed.getAdresse());
        tel.setText(leMed.getTel());
        specialite.setText(leMed.getSpecialite());

        return v;
    }
}