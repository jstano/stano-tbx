package com.stano.tbx.core.geolocation

import spock.lang.Specification

import static com.stano.tbx.core.geolocation.GeoCoordinateTestUtils.*

public class GeoCoordinateSpec
   extends Specification {

   def testGeneralThings() {

      double latitude = 39.760000
      double longitude = -106.340000
      def geoCoordinate = GeoCoordinate.of(latitude, longitude)

      expect:
      geoCoordinate.latitude == latitude
      geoCoordinate.longitude == longitude
      geoCoordinate.clone() == geoCoordinate
      geoCoordinate.toString() == "latitude=39.760000, longitude=-106.340000"
      geoCoordinate.hashCode() == computeHashCodeForGeoCoordinate(geoCoordinate)
   }

   def testEquals() {

      def geoCoordinate1 = GeoCoordinate.of(39.760000d, -106.340000d)
      def geoCoordinate2 = GeoCoordinate.of(39.760000d, -106.340000d)
      def geoCoordinate3 = GeoCoordinate.of(39.775500d, -106.350500d)
      def geoCoordinate4 = GeoCoordinate.of(39.760000d, -106.350500d)
      def geoCoordinate5 = GeoCoordinate.of(39.775500d, -106.340000d)

      expect:
      geoCoordinate1.equals(geoCoordinate1)
      geoCoordinate1.equals(geoCoordinate2)
      !geoCoordinate1.equals(geoCoordinate3)
      !geoCoordinate1.equals(geoCoordinate4)
      !geoCoordinate1.equals(geoCoordinate5)
      !geoCoordinate1.equals(null)
      !geoCoordinate1.equals("ABC")
   }

   def "test that the distance between two latitudes/longitudes is within tolerance"() {

      def mobileCoordinate = GeoCoordinate.of(mobileLatitude, mobileLongitude)
      def virtualTimeClockCoordinate = GeoCoordinate.of(TIME_CLOCK_LATITUDE, TIME_CLOCK_LONGITUDE)

      when:
      def distance = mobileCoordinate.distanceBetweenInMeters(virtualTimeClockCoordinate)

      then:
      withinTolerableDistance(distance, expectedDistance)

      where:
      mobileLatitude                  | mobileLongitude                  | expectedDistance
      BLONDIES_LATITUDE               | BLONDIES_LONGITUDE               | EXPECTED_BLONDIES_DISTANCE
      CHUCKY_CHEESE_LATITUDE          | CHUCKY_CHEESE_LONGITUDE          | EXPECTED_CHUCKY_CHEESE_DISTANCE
      COLORADO_STATE_CAPITOL_LATITUDE | COLORADO_STATE_CAPITOL_LONGITUDE | EXPECTED_COLORADO_STATE_CAPITOL_DISTANCE
   }

   private int computeHashCodeForGeoCoordinate(GeoCoordinate geoCoordinate) {

      long temp = Double.doubleToLongBits(geoCoordinate.latitude)
      int result = (int)(temp ^ (temp >>> 32))
      temp = Double.doubleToLongBits(geoCoordinate.longitude)
      result = 31 * result + (int)(temp ^ (temp >>> 32))
      return result
   }
}
