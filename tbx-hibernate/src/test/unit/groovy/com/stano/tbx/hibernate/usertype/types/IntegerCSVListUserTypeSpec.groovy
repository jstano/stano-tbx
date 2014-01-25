package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.IntegerCSVListUserTypeDelegate
import spock.lang.Specification

public class IntegerCSVListUserTypeSpec extends Specification {

   void testDelegate() {

      def integerCSVListUserType = new IntegerCSVListUserType()

      expect:
      integerCSVListUserType.userTypeDelegate instanceof IntegerCSVListUserTypeDelegate
   }
}
