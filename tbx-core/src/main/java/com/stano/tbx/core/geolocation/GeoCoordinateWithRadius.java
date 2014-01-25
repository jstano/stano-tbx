package com.stano.tbx.core.geolocation;

import java.io.Serializable;

public final class GeoCoordinateWithRadius implements Serializable, Cloneable {

   private final GeoCoordinate geoCoordinate;
   private final double radius;

   public static GeoCoordinateWithRadius of(double latitude, double longitude, double radius) {

      return new GeoCoordinateWithRadius(latitude, longitude, radius);
   }

   public GeoCoordinate getGeoCoordinate() {

      return geoCoordinate;
   }

   public double getRadius() {

      return radius;
   }

   public boolean withinBounds(GeoCoordinate geoCoordinateToCheck) {

      return geoCoordinate.distanceBetweenInMeters(geoCoordinateToCheck) <= radius;
   }

   @Override
   public boolean equals(Object o) {

      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      GeoCoordinateWithRadius that = (GeoCoordinateWithRadius)o;

      if (Double.compare(that.radius, radius) != 0) {
         return false;
      }

      return geoCoordinate.equals(that.geoCoordinate);
   }

   @Override
   public int hashCode() {

      int result;
      long temp;
      result = geoCoordinate.hashCode();
      temp = Double.doubleToLongBits(radius);
      result = 31 * result + (int)(temp ^ (temp >>> 32));
      return result;
   }

   @Override
   public String toString() {

      return String.format("latitude=%f, longitude=%f, radius=%f", geoCoordinate.getLatitude(), geoCoordinate.getLongitude(), getRadius());
   }

   @Override
   public Object clone() throws CloneNotSupportedException {

      return super.clone();
   }

   private GeoCoordinateWithRadius(double latitude, double longitude, double radius) {

      this.geoCoordinate = GeoCoordinate.of(latitude, longitude);
      this.radius = radius;
   }
}
