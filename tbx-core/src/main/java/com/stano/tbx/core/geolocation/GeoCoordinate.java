package com.stano.tbx.core.geolocation;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import java.io.Serializable;

public final class GeoCoordinate implements Serializable, Cloneable {

   private final double latitude;
   private final double longitude;

   public static GeoCoordinate of(double latitude, double longitude) {

      return new GeoCoordinate(latitude, longitude);
   }

   public double getLatitude() {

      return latitude;
   }

   public double getLongitude() {

      return longitude;
   }

   public double distanceBetweenInMeters(GeoCoordinate geoCoordinate) {

      LatLng mobilePoint = new LatLng(geoCoordinate.getLatitude(), geoCoordinate.getLongitude());
      LatLng timeClockPoint = new LatLng(latitude, longitude);

      return LatLngTool.distance(mobilePoint, timeClockPoint, LengthUnit.METER);
   }

   @Override
   public boolean equals(Object o) {

      if (this == o) {
         return true;
      }

      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      GeoCoordinate that = (GeoCoordinate)o;

      if (Double.compare(that.latitude, latitude) != 0) {
         return false;
      }

      return Double.compare(that.longitude, longitude) == 0;
   }

   @Override
   public int hashCode() {

      int result;
      long temp;
      temp = Double.doubleToLongBits(latitude);
      result = (int)(temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(longitude);
      result = 31 * result + (int)(temp ^ (temp >>> 32));
      return result;
   }

   @Override
   public String toString() {

      return String.format("latitude=%f, longitude=%f", latitude, longitude);
   }

   @Override
   public Object clone() throws CloneNotSupportedException {

      return super.clone();
   }

   private GeoCoordinate(double latitude, double longitude) {

      this.latitude = latitude;
      this.longitude = longitude;
   }
}
