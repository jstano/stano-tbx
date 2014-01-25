package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateWithRadiusUserTypeDelegate
import spock.lang.Specification

public class GeoCoordinateWithRadiusUserTypeSpec extends Specification {

   void testGeoCoordinateWithAccuracyUserType() {

      def geoCoordinateWithRadiusUserType = new GeoCoordinateWithRadiusUserType()

      expect:
      geoCoordinateWithRadiusUserType.compositeUserTypeDelegate instanceof GeoCoordinateWithRadiusUserTypeDelegate
   }
}
