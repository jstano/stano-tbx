package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.LocalDateTimeUserTypeDelegate
import spock.lang.Specification

public class LocalDateTimeUserTypeSpec extends Specification {

   void testLocalDateTimeUserType() {

      def localeDateTimeUserType = new LocalDateTimeUserType()

      expect:
      localeDateTimeUserType.userTypeDelegate instanceof LocalDateTimeUserTypeDelegate
   }
}
