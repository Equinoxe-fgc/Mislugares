package com.equinoxe.mislugares;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-C2 on 26/09/2017.
 */

public class Lugares {
    protected static List<Lugar> vectorLugares = ejemploLugares();

    public Lugares() {
        vectorLugares = ejemploLugares();
    }

    static Lugar elemento(int id) {
        return vectorLugares.get(id);
    }

    static void anyade(Lugar lugar) {
        vectorLugares.add(lugar);
    }

    static int nuevo() {
        Lugar lugar = new Lugar();
        vectorLugares.add(lugar);
        return vectorLugares.size() - 1;
    }

    static void borrar(int id) {
        vectorLugares.remove(id);
    }

    static int size() {
        return vectorLugares.size();
    }

    public static ArrayList<Lugar> ejemploLugares() {
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();

        lugares.add(new Lugar("Escuela Técnica Superior de Gandía", "C/ De la escuela", -0.166093, 38.995656,TipoLugar.EDUCACION,962849300,"http://www.epsg.com", "Buen lugar",3));
        lugares.add(new Lugar("Picoteo Pitágoras", "Urb Gran Sol", 1.3454, 38.9898, TipoLugar.BAR, 952404067,"http://www.pitagoras.es", "Bueno para comer", 4));
        lugares.add(new Lugar("Hotel RinconSol","", 3.45, 65.34, TipoLugar.HOTEL, 0, "", "Junto al mar", 5));

        return lugares;
    }
}
