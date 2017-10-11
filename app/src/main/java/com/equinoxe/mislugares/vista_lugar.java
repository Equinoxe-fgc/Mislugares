package com.equinoxe.mislugares;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

public class vista_lugar extends AppCompatActivity {
    private long id;
    private Lugar lugar;
    private ImageView imageView;
    private Uri uriFoto;

    final static int RESULTADO_EDITAR  = 1;
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO    = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id", -1);

        lugar = Lugares.elemento((int)id);

        imageView = (ImageView) findViewById(R.id.imageViewFoto);

        actualizarVistas();
    }

    public void tomarFoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File fileFoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "img_" + (System.currentTimeMillis() / 1000) + ".jpg");
        uriFoto = Uri.fromFile(fileFoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);
    }

    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, lugar.getNombre() + " - " + lugar.getUrl());
                startActivity(intent);
                return true;
            case R.id.accion_llegar:
                verMapa(null);
                return true;
            case R.id.accion_editar:
                Intent i = new Intent(this, edicion_lugar.class);
                i.putExtra("id", id);
                startActivityForResult(i, RESULTADO_EDITAR);
                return true;
            case R.id.accion_borrar:
                borrarLugar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULTADO_EDITAR) {
            actualizarVistas();
            findViewById(R.id.scrollViewVistaLugar).invalidate();
        } else if (requestCode == RESULTADO_GALERIA && resultCode == Activity.RESULT_OK) {
            lugar.setFoto(data.getDataString());
            ponerFoto(imageView, lugar.getFoto());
        } else if (requestCode == RESULTADO_FOTO && resultCode == Activity.RESULT_OK) {
            lugar.setFoto(uriFoto.toString());
            ponerFoto(imageView, lugar.getFoto());
        }
    }

    protected void ponerFoto (ImageView imageView, String uri) {
        if (uri != null)
            imageView.setImageURI(Uri.parse(uri));
        else
            imageView.setImageBitmap(null);
    }

    public void verMapa(View view) {
        Uri uri;
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();

        if (lat != 0 || lon != 0) {
            uri = Uri.parse("geo:" + lat + "," + lon);
        } else {
            uri = Uri.parse("geo:0,0?q=" + lugar.getDireccion());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ lugar.getTelefono())));
    }

    public void pgWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lugar.getUrl())));
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

    public void actualizarVistas() {
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

        ponerFoto(imageView, lugar.getFoto());

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
