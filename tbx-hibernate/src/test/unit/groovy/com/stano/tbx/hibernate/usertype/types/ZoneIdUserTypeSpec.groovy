package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.ZoneIdUserTypeDelegate
import spock.lang.Specification

public class ZoneIdUserTypeSpec extends Specification {

   void testZoneIdUserType() {

      def zoneIdUserType = new ZoneIdUserType()

      expect:
      zoneIdUserType.userTypeDelegate instanceof ZoneIdUserTypeDelegate
   }
}
