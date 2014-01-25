package com.stano.tbx.core.encryption

import org.jasypt.exceptions.EncryptionOperationNotPossibleException
import spock.lang.Specification

public class EncryptionServicesSpec
   extends Specification {

   void testEncryptionServices() {

      def encryptionServices = new EncryptionServices()

      expect:
      encryptionServices.decryptString(encryptionServices.encryptString("ABC123")) == "ABC123"
      encryptionServices.decryptString(encryptionServices.encryptString("")) == ""

      encryptionServices.decryptString(null) == null

      encryptionServices.encryptString(null) == null

      encryptionServices.passwordMatches("ABC123", encryptionServices.encryptPassword("ABC123"));
      !encryptionServices.passwordMatches("XYZ987", encryptionServices.encryptPassword("ABC123"));
      !encryptionServices.passwordMatches("", encryptionServices.encryptPassword("ABC123"));
      !encryptionServices.passwordMatches(null, encryptionServices.encryptPassword("ABC123"));
   }

   void testDecryptEmptyString() {

      def encryptionServices = new EncryptionServices()

      when:
      encryptionServices.decryptString("")

      then:
      thrown EncryptionOperationNotPossibleException
   }

   void testTeaEncryptionServices() {

      def encryptionServices = new EncryptionServices()

      expect:
      encryptionServices.decryptStringTea(encryptionServices.encryptStringTea("ABC123")) == "ABC123"
      encryptionServices.encryptStringTea("") == ""
      encryptionServices.encryptStringTea(null) == null
      encryptionServices.decryptStringTea("") == ""
      encryptionServices.decryptStringTea(null) == null
   }
}
