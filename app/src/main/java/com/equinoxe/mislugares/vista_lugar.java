package com.equinoxe.mislugares;

import android.icu.text.DateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        TextView direccion = (TextView)findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());

        TextView telefono = (TextView)findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));

        TextView url = (TextView)findViewById(R.id.url);
        url.setText(lugar.getUrl());

        TextView comentario = (TextView)findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());

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
}
