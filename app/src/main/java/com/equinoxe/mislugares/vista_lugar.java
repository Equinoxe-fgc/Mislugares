package com.equinoxe.mislugares;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

public class vista_lugar extends AppCompatActivity {
    private long id;
    private Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);

        lugar = Lugares.elemento((int)id);

        TextView nombre = (TextView)findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());

        ImageView logo_tipo = (ImageView)findViewById(R.id.Logo_tipo);
        logo_tipo.setImageResource(lugar.getTipo().getRecurso());

        TextView tipo = (TextView)findViewById(R.id.tipo);
        tipo.setText(lugar.getTipo().getTexto());

        if (lugar.getDireccion().length() == 0)
            findViewById(R.id.LayoutDireccion).setVisibility(View.GONE);
        else {
            TextView direccion = (TextView)findViewById(R.id.direccion);
            direccion.setText(lugar.getDireccion());
        }

        if (lugar.getTelefono() == 0)
            findViewById(R.id.LayoutTelefono).setVisibility(View.GONE);
        else {
            TextView telefono = (TextView)findViewById(R.id.telefono);
            telefono.setText(Integer.toString(lugar.getTelefono()));
        }

        if (lugar.getUrl().length() == 0)
            findViewById(R.id.LayoutURL).setVisibility(View.GONE);
        else {
            TextView url = (TextView)findViewById(R.id.url);
            url.setText(lugar.getUrl());
        }

        if (lugar.getComentario().length() == 0)
            findViewById(R.id.LayoutComentario).setVisibility(View.GONE);
        else {
            TextView comentario = (TextView)findViewById(R.id.comentario);
            comentario.setText(lugar.getComentario());
        }

        TextView fecha = (TextView)findViewById(R.id.fecha);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fecha.setText(DateFormat.getDateInstance().format(new Date(lugar.getFecha())));
        }

        TextView hora = (TextView)findViewById(R.id.hora);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            hora.setText(DateFormat.getTimeInstance().format(new Date(lugar.getFecha())));
        }

        RatingBar valoracion = (RatingBar)findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        lugar.setValoracion(rating);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_compartir:
                return true;
            case R.id.accion_llegar:
                return true;
            case R.id.accion_editar:
                Intent i = new Intent(this, edicion_lugar.class);
                i.putExtra("id", id);
                startActivity(i);
                return true;
            case R.id.accion_borrar:
                borrarLugar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void  borrarLugar() {
        new AlertDialog.Builder(this)
                .setTitle("Borrado de lugar")
                .setMessage("Estás seguro de que quieres eliminar este lugar?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Lugares.borrar((int)id);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
