package com.stano.tbx.core.geolocation;

import java.io.Serializable;

public final class GeoCoordinateWithAccuracy implements Serializable, Cloneable {

   private final GeoCoordinate geoCoordinate;
   private final double accuracy;

   public static GeoCoordinateWithAccuracy of(double latitude, double longitude, double accuracy) {

      return new GeoCoordinateWithAccuracy(latitude, longitude, accuracy);
   }

   public GeoCoordinate getGeoCoordinate() {

      return geoCoordinate;
   }

   public double getAccuracy() {

      return accuracy;
   }

   @Override
   public boolean equals(Object o) {

      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      GeoCoordinateWithAccuracy that = (GeoCoordinateWithAccuracy)o;

      if (Double.compare(that.accuracy, accuracy) != 0) {
         return false;
      }

      return geoCoordinate.equals(that.geoCoordinate);
   }

   @Override
   public int hashCode() {

      int result;
      long temp;
      result = geoCoordinate.hashCode();
      temp = Double.doubleToLongBits(accuracy);
      result = 31 * result + (int)(temp ^ (temp >>> 32));
      return result;
   }

   @Override
   public String toString() {

      return String.format("latitude=%f, longitude=%f, accuracy=%f", geoCoordinate.getLatitude(), geoCoordinate.getLongitude(), getAccuracy());
   }

   @Override
   public Object clone() throws CloneNotSupportedException {

      return super.clone();
   }

   private GeoCoordinateWithAccuracy(double latitude, double longitude, double accuracy) {

      this.geoCoordinate = GeoCoordinate.of(latitude, longitude);
      this.accuracy = accuracy;
   }
}
