package com.sschenkel.gsbmedecin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class DepActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep);

        list = (ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inter = new Intent(DepActivity.this, MedActivity.class);
                inter.putExtra("numDep", parent.getItemAtPosition(position).toString());
                startActivity(inter);
            }
        });

        new ShowDep().execute();
    }

    private class ShowDep extends AsyncTask<Void, Void, Void> {

        List<String> lesDeps;

        protected Void doInBackground(Void... params){
            lesDeps = DAO.getLesDeps();
            return null;
        }

        protected void onPostExecute(Void result){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepActivity.this, android.R.layout.simple_list_item_1, lesDeps);
            list.setAdapter(adapter);
        }
    }
}
