package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.JsonObjectUserTypeDelegate
import spock.lang.Specification

public class JsonObjectUserTypeSpec extends Specification {

   void testDelegate() {

      def jsonObjectUserType = new JsonObjectUserType()

      expect:
      jsonObjectUserType.userTypeDelegate instanceof JsonObjectUserTypeDelegate
   }
}
