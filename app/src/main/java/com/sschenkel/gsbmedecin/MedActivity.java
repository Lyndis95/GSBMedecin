package com.sschenkel.gsbmedecin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stéphane on 2/4/2016.
 */
public class MedActivity extends AppCompatActivity {

    private String numDep;
    private TextView title;
    private EditText filterBox;
    private List<Medecin> listMedBase;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);

        Intent inter = getIntent();
        numDep = inter.getStringExtra("numDep");

        list = (ListView)findViewById(R.id.list);

        title = (TextView)findViewById(R.id.title);
        title.setText(title.getText() + " " + numDep + " :");

        filterBox = (EditText)findViewById(R.id.filterBox);
        filterBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(listMedBase != null){
                    List<Medecin> listMedFiltered = new ArrayList<Medecin>();
                    for(Medecin leMed : listMedBase){
                        String nomprenom = leMed.getNom().toLowerCase();
                        if(nomprenom.contains(s.toString().toLowerCase())){
                            listMedFiltered.add(leMed);
                        }
                    }
                    affichList(listMedFiltered);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Medecin leMed = (Medecin)parent.getItemAtPosition(position);
                Intent inter = new Intent(MedActivity.this, MedInfoActivity.class);
                inter.putExtra("leMed", Medecin.serialize(leMed));
                startActivity(inter);
            }
        });

        new ShowMed().execute();
    }

    private void getMed(List<Medecin> listMed){
        listMedBase = new ArrayList<Medecin>(listMed);
        affichList(listMedBase);
    }

    private void affichList(List<Medecin> laListe){
        MedAdapter adapter = new MedAdapter(laListe, MedActivity.this);
        list.setAdapter(adapter);
    }

    private class ShowMed extends AsyncTask<Void, Void, Void> {

        List<Medecin> lesMeds;

        protected Void doInBackground(Void... params){
            lesMeds = DAO.getLesMeds(numDep);
            return null;
        }

        protected void onPostExecute(Void result){
            getMed(lesMeds);
        }
    }
}
