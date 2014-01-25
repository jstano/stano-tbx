package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.core.encryption.EncryptionServices;

public class EncryptedStringUserTypeDelegate extends AbstractStringUserTypeDelegate {

   private final EncryptionServices encryptionServices = new EncryptionServices();

   @Override
   protected Object convertFromString(String text) {

      return encryptionServices.decryptString(text);
   }

   @Override
   protected String convertToString(Object object) {

      return encryptionServices.encryptString(object.toString());
   }
}
