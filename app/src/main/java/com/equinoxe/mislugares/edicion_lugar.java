package com.equinoxe.mislugares;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class edicion_lugar extends AppCompatActivity {
    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    private Spinner tipo;

    private long id;
    private Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_lugar);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);

        lugar = Lugares.elemento((int) id);

        nombre = (EditText) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());

        tipo = (Spinner)findViewById(R.id.tipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());

        direccion = (EditText) findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());

        telefono = (EditText) findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));

        url = (EditText) findViewById(R.id.url);
        url.setText(lugar.getUrl());

        comentario = (EditText) findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
    }
}
