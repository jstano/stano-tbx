package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateUserTypeDelegate
import spock.lang.Specification

public class GeoCoordinateUserTypeSpec extends Specification {

   void testGeoCoordinateUserType() {

      def geoCoordinateUserType = new GeoCoordinateUserType()

      expect:
      geoCoordinateUserType.compositeUserTypeDelegate instanceof GeoCoordinateUserTypeDelegate
   }
}
