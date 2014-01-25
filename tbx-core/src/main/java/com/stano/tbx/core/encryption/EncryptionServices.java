package com.stano.tbx.core.encryption;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;

public class EncryptionServices {

   private static final String PASSWORD = "Z0lt0n3718";

   private static final TextEncryptor textEncryptor = createTextEncryptor();
   private static final PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

   public String encryptString(String clearText) {

      return textEncryptor.encrypt(clearText);
   }

   public String decryptString(String encryptedText) {

      return textEncryptor.decrypt(encryptedText);
   }

   public String encryptStringTea(String clearText) {

      return TEAV.encryptString(clearText);
   }

   public String decryptStringTea(String encryptedText) {

      return TEAV.decryptString(encryptedText);
   }

   public String encryptPassword(String password) {

      return passwordEncryptor.encryptPassword(password);
   }

   public boolean passwordMatches(String plainPassword, String encryptedPassword) {

      return passwordEncryptor.checkPassword(plainPassword, encryptedPassword);
   }

   private static TextEncryptor createTextEncryptor() {

      BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
      textEncryptor.setPassword(PASSWORD);

      return textEncryptor;
   }
}
