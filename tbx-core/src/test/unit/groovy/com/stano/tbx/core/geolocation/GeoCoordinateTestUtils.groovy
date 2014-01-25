package com.stano.tbx.core.geolocation

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
public class GeoCoordinateTestUtils extends Specification {

   static final TIME_CLOCK_LATITUDE = 39.65461d
   static final TIME_CLOCK_LONGITUDE = -104.99770d
   static final CHUCKY_CHEESE_LATITUDE = 39.65356d
   static final CHUCKY_CHEESE_LONGITUDE = -105.00003d
   static final EXPECTED_CHUCKY_CHEESE_DISTANCE = 231
   static final BLONDIES_LATITUDE = 39.65429d
   static final BLONDIES_LONGITUDE = -104.99855d
   static final EXPECTED_BLONDIES_DISTANCE = 81
   static final COLORADO_STATE_CAPITOL_LATITUDE = 39.73930d
   static final COLORADO_STATE_CAPITOL_LONGITUDE = -104.98463d
   static final EXPECTED_COLORADO_STATE_CAPITOL_DISTANCE = 9483
   static final DISTANCE_TOLERANCE_IN_METERS = 1

   static withinTolerableDistance(distance, expectedDistance) {

      def maxTolerableDistance = distance + DISTANCE_TOLERANCE_IN_METERS
      def minTolerableDistance = distance - DISTANCE_TOLERANCE_IN_METERS
      return expectedDistance >= minTolerableDistance && expectedDistance <= maxTolerableDistance
   }
}
