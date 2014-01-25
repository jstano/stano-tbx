package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

public class DoubleCSVListUserTypeDelegateSpec
   extends Specification {

   def testParseValueValidValue() {

      def doubleCSVListUserTypeDelegate = new DoubleCSVListUserTypeDelegate()

      expect:
      doubleCSVListUserTypeDelegate.parseValue("123.456") == 123.456
   }

   def testParseValueNotANumber() {

      def doubleCSVListUserTypeDelegate = new DoubleCSVListUserTypeDelegate()

      expect:
      doubleCSVListUserTypeDelegate.parseValue("ABC") == 0.0
   }
}
