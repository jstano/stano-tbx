package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.NullableDoubleUserTypeDelegate
import spock.lang.Specification

public class NullableDoubleUserTypeSpec extends Specification {

   void testDelegate() {

      def nullableDoubleUserType = new NullableDoubleUserType()

      expect:
      nullableDoubleUserType.userTypeDelegate instanceof NullableDoubleUserTypeDelegate
   }
}
