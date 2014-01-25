package com.stano.tbx.core.geolocation

import spock.lang.Specification

public class GeoCoordinateWithAccuracySpec
   extends Specification {

   def testGeneralThings() {

      double latitude = 39.760000
      double longitude = -106.340000
      double accuracy = 100.2567
      def geoCoordinateWithAccuracy = GeoCoordinateWithAccuracy.of(latitude, longitude, accuracy)

      expect:
      geoCoordinateWithAccuracy.geoCoordinate.latitude == latitude
      geoCoordinateWithAccuracy.geoCoordinate.longitude == longitude
      geoCoordinateWithAccuracy.clone() == geoCoordinateWithAccuracy
      geoCoordinateWithAccuracy.toString() == "latitude=39.760000, longitude=-106.340000, accuracy=100.256700"
      geoCoordinateWithAccuracy.hashCode() == computeHashCodeForGeoCoordinate(geoCoordinateWithAccuracy)
   }

   def testEquals() {

      def geoCoordinate1 = GeoCoordinateWithAccuracy.of(39.760000d, -106.340000d, 100.0)
      def geoCoordinate2 = GeoCoordinateWithAccuracy.of(39.760000d, -106.340000d, 100.0)
      def geoCoordinate3 = GeoCoordinateWithAccuracy.of(39.775500d, -106.350500d, 100.0)
      def geoCoordinate4 = GeoCoordinateWithAccuracy.of(39.760000d, -106.340000d, 200.0)

      expect:
      geoCoordinate1.equals(geoCoordinate1)
      geoCoordinate1.equals(geoCoordinate2)
      !geoCoordinate1.equals(geoCoordinate3)
      !geoCoordinate1.equals(geoCoordinate4)
      !geoCoordinate1.equals(null)
      !geoCoordinate1.equals("ABC")
   }

   private int computeHashCodeForGeoCoordinate(GeoCoordinateWithAccuracy geoCoordinateWithAccuracy) {

      int result = geoCoordinateWithAccuracy.geoCoordinate.hashCode();
      long temp = Double.doubleToLongBits(geoCoordinateWithAccuracy.accuracy);
      result = 31 * result + (int)(temp ^ (temp >>> 32));
      return result;
   }
}
