package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

public class TeaEncryptedStringUserTypeDelegateSpec
   extends Specification {

   void testConvertFromToString() {

      def teaEncryptedStringUserTypeDelegate = new TeaEncryptedStringUserTypeDelegate()

      expect:
      teaEncryptedStringUserTypeDelegate.convertFromString(teaEncryptedStringUserTypeDelegate.convertToString("ABC123")) == "ABC123"
   }
}
