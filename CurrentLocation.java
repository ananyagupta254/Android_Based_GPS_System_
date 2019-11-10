package edu.angupta.myapplication;

public class CurrentLocation {

    double lat = 0;
    double lon = 0;
    String busID = "";

    public CurrentLocation(){}

    public CurrentLocation (double la, double ln, String id)
    {
        lat = la;
        lon = ln;
        busID = id;
    }
}
