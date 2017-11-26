package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;

/**
 * Created by Bülent SİYAH on 25.7.2016.
 */
public class EarthQuake {

    public String Subject ;
    public String Details ;
    public String Datetime ;
    public Double Siddeti ;
    public Double Latitude ;
    public Double Longitude ;

    public EarthQuake (){

    }

    public EarthQuake (String Subject,String Details,String Datetime,Double Siddeti,Double Latitude,Double Longitude){
        this.Subject=Subject;
        this.Details=Details;
        this.Datetime=Datetime;
        this.Siddeti=Siddeti;
        this.Latitude =Latitude;
        this.Longitude =Longitude;
    }

}