package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.EncryptedStringUserTypeDelegate
import spock.lang.Specification

public class EncryptedStringUserTypeSpec extends Specification {

   void testEncryptedStringUserType() {

      def encryptedStringUserType = new EncryptedStringUserType()

      expect:
      encryptedStringUserType.userTypeDelegate instanceof EncryptedStringUserTypeDelegate
   }
}
