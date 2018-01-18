package com.equinoxe.mislugares;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private long id;
    private Lugar lugar;
    public AdaptadorLugares adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = new AdaptadorLugares(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View vista, int posicion, long id) {
        Intent i = new Intent(this, vista_lugar.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate((R.menu.menu_main), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.acercaDe) {
            lanzarAcercaDe();
            return true;
        }
        if (id == R.id.config) {
            //lanzarConfig();
            return true;
        }
        if (id == R.id.menu_buscar) {
            lanzarVistaLugar(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void lanzarAcercaDe() {
        Intent i = new Intent(this, acercaDe.class);
        startActivity(i);
    }

    public void lanzarVistaLugar(View v) {
        /*Intent i = new Intent(this, vista_lugar.class);
        i.putExtra("id", (long)0);
        startActivity(i);*/
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("Indica su id:")
                .setView(entrada)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = Long.parseLong(entrada.getText().toString());
                        Intent i = new Intent(MainActivity.this, vista_lugar.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
