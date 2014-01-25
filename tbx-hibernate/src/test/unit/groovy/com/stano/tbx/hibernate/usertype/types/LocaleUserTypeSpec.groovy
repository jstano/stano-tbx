package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.LocaleUserTypeDelegate
import spock.lang.Specification

public class LocaleUserTypeSpec extends Specification {

   void testLocaleUserType() {

      def localeUserType = new LocaleUserType()

      expect:
      localeUserType.userTypeDelegate instanceof LocaleUserTypeDelegate
   }
}
