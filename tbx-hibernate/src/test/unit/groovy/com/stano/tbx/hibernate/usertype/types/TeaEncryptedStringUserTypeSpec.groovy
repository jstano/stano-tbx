package com.stano.tbx.hibernate.usertype.types

import com.stano.tbx.hibernate.usertype.delegates.TeaEncryptedStringUserTypeDelegate
import spock.lang.Specification

public class TeaEncryptedStringUserTypeSpec
   extends Specification {

   void testTeaEncryptedStringUserType() {

      def teaEncryptedStringUserType = new TeaEncryptedStringUserType()

      expect:
      teaEncryptedStringUserType.userTypeDelegate instanceof TeaEncryptedStringUserTypeDelegate
   }
}
