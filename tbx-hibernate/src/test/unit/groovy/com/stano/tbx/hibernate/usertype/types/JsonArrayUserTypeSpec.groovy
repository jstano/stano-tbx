package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.JsonArrayUserTypeDelegate
import spock.lang.Specification

public class JsonArrayUserTypeSpec extends Specification {

   void testDelegate() {

      def jsonArrayUserType = new JsonArrayUserType()

      expect:
      jsonArrayUserType.userTypeDelegate instanceof JsonArrayUserTypeDelegate
   }
}
