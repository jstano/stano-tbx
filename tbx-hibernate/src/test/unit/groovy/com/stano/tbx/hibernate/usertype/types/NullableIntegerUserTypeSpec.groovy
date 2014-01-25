package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.NullableIntUserTypeDelegate
import spock.lang.Specification

public class NullableIntegerUserTypeSpec extends Specification {

   void testDelegate() {

      def nullableIntUserType = new NullableIntUserType()

      expect:
      nullableIntUserType.userTypeDelegate instanceof NullableIntUserTypeDelegate
   }
}
