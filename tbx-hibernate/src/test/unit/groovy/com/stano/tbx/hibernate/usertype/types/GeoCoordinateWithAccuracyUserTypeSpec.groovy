package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateWithAccuracyUserTypeDelegate
import spock.lang.Specification

public class GeoCoordinateWithAccuracyUserTypeSpec extends Specification {

   void testGeoCoordinateWithAccuracyUserType() {

      def geoCoordinateWithAccuracyUserType = new GeoCoordinateWithAccuracyUserType()

      expect:
      geoCoordinateWithAccuracyUserType.compositeUserTypeDelegate instanceof GeoCoordinateWithAccuracyUserTypeDelegate
   }
}
