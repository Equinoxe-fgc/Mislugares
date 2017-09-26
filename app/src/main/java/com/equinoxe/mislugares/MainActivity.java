package com.equinoxe.mislugares;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent i = new Intent(this, vista_lugar.class);
        i.putExtra("id", (long)0);
        startActivity(i);
    }
}
