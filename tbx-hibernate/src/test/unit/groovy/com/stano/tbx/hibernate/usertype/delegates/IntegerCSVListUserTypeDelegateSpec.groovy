package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

public class IntegerCSVListUserTypeDelegateSpec
   extends Specification {

   def testParseValueValidValue() {

      def integerCSVListUserTypeDelegate = new IntegerCSVListUserTypeDelegate()

      expect:
      integerCSVListUserTypeDelegate.parseValue("123") == 123
   }

   def testParseValueLegacyNull() {

      def integerCSVListUserTypeDelegate = new IntegerCSVListUserTypeDelegate()

      expect:
      integerCSVListUserTypeDelegate.parseValue("null") == null
   }

   def testParseValueNotANumber() {

      def integerCSVListUserTypeDelegate = new IntegerCSVListUserTypeDelegate()

      expect:
      integerCSVListUserTypeDelegate.parseValue("ABC") == 0
   }
}
