package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

public class EncryptedStringUserTypeDelegateSpec extends Specification {

   void testConvertFromToString() {

      def encryptedStringUserTypeDelegate = new EncryptedStringUserTypeDelegate()

      expect:
      encryptedStringUserTypeDelegate.convertFromString(encryptedStringUserTypeDelegate.convertToString("ABC123")) == "ABC123"
   }
}
