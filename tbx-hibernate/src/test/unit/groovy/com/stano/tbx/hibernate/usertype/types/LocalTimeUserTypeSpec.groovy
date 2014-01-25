package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.LocalTimeUserTypeDelegate
import spock.lang.Specification

public class LocalTimeUserTypeSpec extends Specification {

   void testLocalTimeUserType() {

      def localeTimeUserType = new LocalTimeUserType()

      expect:
      localeTimeUserType.userTypeDelegate instanceof LocalTimeUserTypeDelegate
   }
}
