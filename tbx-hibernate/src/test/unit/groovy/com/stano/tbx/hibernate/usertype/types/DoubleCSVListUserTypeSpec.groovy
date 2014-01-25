package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.DoubleCSVListUserTypeDelegate
import spock.lang.Specification

public class DoubleCSVListUserTypeSpec extends Specification {

   void testDelegate() {

      def doubleCSVListUserType = new DoubleCSVListUserType()

      expect:
      doubleCSVListUserType.userTypeDelegate instanceof DoubleCSVListUserTypeDelegate
   }
}
