package com.stano.tbx.core.geolocation

import spock.lang.Specification

import static com.stano.tbx.core.geolocation.GeoCoordinateTestUtils.*

public class GeoCoordinateWithRadiusSpec
   extends Specification {

   def testGeneralThings() {

      double latitude = 39.760000
      double longitude = -106.340000
      double radius = 100.2567
      def geoCoordinateWithRadius = GeoCoordinateWithRadius.of(latitude, longitude, radius)

      expect:
      geoCoordinateWithRadius.geoCoordinate.latitude == latitude
      geoCoordinateWithRadius.geoCoordinate.longitude == longitude
      geoCoordinateWithRadius.clone() == geoCoordinateWithRadius
      geoCoordinateWithRadius.toString() == "latitude=39.760000, longitude=-106.340000, radius=100.256700"
      geoCoordinateWithRadius.hashCode() == computeHashCodeForGeoCoordinate(geoCoordinateWithRadius)
   }

   def testEquals() {

      def geoCoordinate1 = GeoCoordinateWithRadius.of(39.760000d, -106.340000d, 100.0)
      def geoCoordinate2 = GeoCoordinateWithRadius.of(39.760000d, -106.340000d, 100.0)
      def geoCoordinate3 = GeoCoordinateWithRadius.of(39.775500d, -106.350500d, 100.0)
      def geoCoordinate4 = GeoCoordinateWithRadius.of(39.760000d, -106.340000d, 200.0)

      expect:
      geoCoordinate1.equals(geoCoordinate1)
      geoCoordinate1.equals(geoCoordinate2)
      !geoCoordinate1.equals(geoCoordinate3)
      !geoCoordinate1.equals(geoCoordinate4)
      !geoCoordinate1.equals(null)
      !geoCoordinate1.equals("ABC")
   }

   def "withinBounds should return true if the coordinate is within the radius"(){

      def mobileCoordinate = GeoCoordinate.of(mobileLatitude, mobileLongitude)
      def timeClockCoordinate = GeoCoordinateWithRadius.of(TIME_CLOCK_LATITUDE, TIME_CLOCK_LONGITUDE, radius)

      expect:
      timeClockCoordinate.withinBounds(mobileCoordinate) == expectedWithinBounds

      where:
      mobileLatitude         | mobileLongitude         | radius | expectedWithinBounds
      BLONDIES_LATITUDE      | BLONDIES_LONGITUDE      | 75     | false
      BLONDIES_LATITUDE      | BLONDIES_LONGITUDE      | 85     | true
      CHUCKY_CHEESE_LATITUDE | CHUCKY_CHEESE_LONGITUDE | 230    | false
      CHUCKY_CHEESE_LATITUDE | CHUCKY_CHEESE_LONGITUDE | 232    | true
   }

   private int computeHashCodeForGeoCoordinate(GeoCoordinateWithRadius geoCoordinateWithRadius) {

      int result = geoCoordinateWithRadius.geoCoordinate.hashCode();
      long temp = Double.doubleToLongBits(geoCoordinateWithRadius.radius);
      result = 31 * result + (int)(temp ^ (temp >>> 32));
      return result;
   }
}
