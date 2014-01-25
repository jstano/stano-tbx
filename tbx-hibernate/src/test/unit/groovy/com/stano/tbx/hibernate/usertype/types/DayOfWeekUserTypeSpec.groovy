package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.DayOfWeekUserTypeDelegate
import spock.lang.Specification

public class DayOfWeekUserTypeSpec extends Specification {

   void testDayOfWeekUserType() {

      def dayOfWeekUserType = new DayOfWeekUserType()

      expect:
      dayOfWeekUserType.userTypeDelegate instanceof DayOfWeekUserTypeDelegate
   }
}
