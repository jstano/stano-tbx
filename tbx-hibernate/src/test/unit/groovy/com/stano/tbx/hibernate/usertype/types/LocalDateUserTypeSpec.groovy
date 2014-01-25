package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.LocalDateUserTypeDelegate
import spock.lang.Specification

public class LocalDateUserTypeSpec extends Specification {

   void testLocalDateUserType() {

      def localeDateUserType = new LocalDateUserType()

      expect:
      localeDateUserType.userTypeDelegate instanceof LocalDateUserTypeDelegate
   }
}
