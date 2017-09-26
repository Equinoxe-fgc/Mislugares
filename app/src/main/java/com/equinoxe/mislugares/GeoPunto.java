package com.equinoxe.mislugares;

/**
 * Created by PC-C2 on 26/09/2017.
 */

public class GeoPunto {
    private double longitud, latitud;

    public GeoPunto(double longitud, double latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "GeoPunto{" +
                "longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }

    public double distancia(GeoPunto punto) {
        final double RADIO_TIERRA = 6371000;

        double dLat = Math.toRadians(latitud - punto.latitud);
        double dLon = Math.toRadians(longitud - punto.longitud);
        double lat1 = Math.toRadians(punto.latitud);
        double lat2 = Math.toRadians(latitud);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.sin(lat1) * Math.sin(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return c * RADIO_TIERRA;
    }
}
