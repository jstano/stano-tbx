package com.stano.tbx.core.encryption

import spock.lang.Specification

public class TEAVSpec
   extends Specification {

   void testEncryptDecryptString() {

      expect:
      TEAV.decryptString(TEAV.encryptString("ABC123")) == "ABC123"
      TEAV.encryptString("") == ""
      TEAV.encryptString(null) == null
      TEAV.decryptString("") == ""
      TEAV.decryptString(null) == null
   }

   void testHexToBin() {

      TEAV teav = TEAV.getTEA()

      expect:
      teav.hexToBin("1234567890ABCDEF") == [305_419_896, -1_867_788_817L]
      teav.hexToBin("1234567890abcdef") == [305_419_896, -1_867_788_817L]
   }

   void testHexToBinInvalidStringLength() {

      TEAV teav = TEAV.getTEA()

      when:
      teav.hexToBin("1234567890ABCD")

      then:
      thrown ArrayIndexOutOfBoundsException
   }

   void testBinToHexInvalidArrayLength() {

      TEAV teav = TEAV.getTEA()

      when:
      teav.binToHex([1, 2, 3] as int[])

      then:
      thrown ArrayIndexOutOfBoundsException
   }

   void testToString() {

      TEAV teav = TEAV.getTEA()

      expect:
      teav.toString() == "com.stano.tbx.core.encryption.TEAV: Tiny Encryption Algorithm (TEA)  key: 0E8396D58F8F9A9B908C87CB89AD959D"
   }

   void testInvalidConstructorKeyLength() {

      when:
      new TEAV()

      then:
      thrown ArrayIndexOutOfBoundsException
   }
}
