package com.stano.tbx.core.encryption;

/**
 * Tiny Encryption Algorithm - Variant.
 * <P>
 * (The following description is from the web page for the C and Assembler source
 * code at <A HREF="http://vader.brad.ac.uk/tea/tea.shtml"> University of Bradford
 * Yorkshire, England - The Cryptography &amp; Computer Communications Security
 * Group</A>) The description is used with the permission of the authors,
 * Dr S J Shepherd and D A G Gillies.
 * <P>
 * The Tiny Encryption Algorithm is one of the fastest and most efficient
 * cryptographic algorithms in existence. It was developed by David
 * Wheeler and Roger Needham at the Computer Laboratory of Cambridge
 * University. It is a Feistel cipher which uses operations from mixed
 * (orthogonal) algebraic groups - XORs and additions in this case. It
 * encrypts 64 data bits at a time using a 128-bit key. It seems highly
 * resistant to differential cryptanalysis, and achieves complete
 * diffusion (where a one bit difference in the plaintext will cause
 * approximately 32 bit differences in the ciphertext) after only six
 * rounds. Performance on a modern desktop computer or workstation is
 * very impressive.
 * <P>
 * TEA takes 64 bits of data in v[0] and v[1], and 128 bits of key in
 * k[0] - k[3]. The result is returned in w[0] and w[1]. Returning the
 * result separately makes implementation of cipher modes other than
 * Electronic Code Book a little bit easier.
 * <P>
 * TEA can be operated in any of the modes of DES.
 * <P>
 * n is the number of iterations. 32 is ample, 16 is sufficient, as few
 * as eight should be OK for most applications, especially ones where
 * the data age quickly (real-time video, for example). The algorithm
 * achieves good dispersion after six iterations. The iteration count
 * can be made variable if required.
 *
 * @author Translated by Michael Lecuyer (mjl@theorem.com) from the C Language.
 * @version 1.0 Sep 13, 2000
 * @since JDK1.1
 */

public class TEAV {

   private int _key[];   // The 128 bit key.
   private byte _keyBytes[];   // original key as found
   private int _padding;      // amount of padding added in byte --> integer conversion.

   /**
    * Array of hex char mappings.
    * hex[0] = '0', hex[15] = 'F'.
    */
   protected static final char hex[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   /**
    * Accepts key for enciphering/deciphering.
    *
    * @param key 128 bit (16 byte) key.
    * @throws ArrayIndexOutOfBoundsException if the key isn't the correct length.
    */
   public TEAV(byte key[]) {

      int klen = key.length;
      _key = new int[4];

      // Incorrect key length throws exception.
      if (klen != 16) {
         throw new ArrayIndexOutOfBoundsException(this.getClass().getName() + ": Key is not 16 bytes");
      }

      int j, i;
      for (i = 0, j = 0; j < klen; j += 4, i++) {
         _key[i] = (key[j] << 24) | (((key[j + 1]) & 0xff) << 16) | (((key[j + 2]) & 0xff) << 8) | ((key[j + 3]) & 0xff);
      }

      _keyBytes = key;   // save for toString.
   }

   /**
    * Representation of TEA class
    */
   public String toString() {

      String tea = this.getClass().getName();
      tea += ": Tiny Encryption Algorithm (TEA)  key: " + getHex(_keyBytes);
      return tea;
   }

   public int[] encipher(int v[]) {

      int y = v[0];
      int z = v[1];
      int sum = 0;
      int delta = 0x9E3779B9;

      int n = 32;

      while (n-- > 0) {
         y += ((z << 4) ^ (z >>> 5)) + (z ^ sum) + _key[(sum & 3)];
         sum += delta;
         z += ((y << 4) ^ (y >>> 5)) + (y ^ sum) + _key[(sum >>> 11) & 3];
      }

      int w[] = new int[2];
      w[0] = y;
      w[1] = z;

      return w;
   }

   public int[] decipher(int v[]) {

      int y = v[0];
      int z = v[1];
      int sum = 0xC6EF3720;
      int delta = 0x9E3779B9;

      int n = 32;

      // sum = delta<<5, in general sum = delta * n

      while (n-- > 0) {
         z -= ((y << 4) ^ (y >>> 5)) + (y ^ sum) + _key[(sum >>> 11) & 3];
         sum -= delta;
         y -= ((z << 4) ^ (z >>> 5)) + (z ^ sum) + _key[sum & 3];
      }

      int w[] = new int[2];
      w[0] = y;
      w[1] = z;

      return w;
   }

   public int[] encode(byte b[], int count) {

      int j, i;
      int bLen = count;
      byte bp[] = b;

      _padding = bLen % 8;
      if (_padding != 0)   // Add some padding, if necessary.
      {
         _padding = 8 - (bLen % 8);
         bp = new byte[bLen + _padding];
         System.arraycopy(b, 0, bp, 0, bLen);
         bLen = bp.length;
      }

      int intCount = bLen / 4;
      int r[] = new int[2];
      int out[] = new int[intCount];

      for (i = 0, j = 0; j < bLen; j += 8, i += 2) {
         r[0] = (bp[j] << 24) | (((bp[j + 1]) & 0xff) << 16) | (((bp[j + 2]) & 0xff) << 8) | ((bp[j + 3]) & 0xff);
         r[1] = (bp[j + 4] << 24) | (((bp[j + 5]) & 0xff) << 16) | (((bp[j + 6]) & 0xff) << 8) | ((bp[j + 7]) & 0xff);
         r = encipher(r);
         out[i] = r[0];
         out[i + 1] = r[1];
      }

      return out;
   }

   public byte[] decode(int b[]) {
      // create the large number and start stripping ints out, two at a time.
      int intCount = b.length;

      byte outb[] = new byte[intCount * 4];
      int tmp[] = new int[2];

      // decipher all the ints.
      int i, j;
      for (j = 0, i = 0; i < intCount; i += 2, j += 8) {
         tmp[0] = b[i];
         tmp[1] = b[i + 1];
         tmp = decipher(tmp);
         outb[j] = (byte)(tmp[0] >>> 24);
         outb[j + 1] = (byte)(tmp[0] >>> 16);
         outb[j + 2] = (byte)(tmp[0] >>> 8);
         outb[j + 3] = (byte)(tmp[0]);
         outb[j + 4] = (byte)(tmp[1] >>> 24);
         outb[j + 5] = (byte)(tmp[1] >>> 16);
         outb[j + 6] = (byte)(tmp[1] >>> 8);
         outb[j + 7] = (byte)(tmp[1]);
      }

      return outb;
   }

   /**
    * Convert a string into an integer array form suitable for decoding.
    *
    * @param hexStr String of hexadecimal digits.
    * @return integer array.
    * @throws ArrayIndexOutOfBoundsException if the string length is not divisible into integer length pieces.
    */
   public int[] hexToBin(String hexStr) throws ArrayIndexOutOfBoundsException {

      int hexStrLen = hexStr.length();

      // Decode a hex string into a collection of ints.
      if ((hexStrLen % 8) != 0) {
         throw new ArrayIndexOutOfBoundsException("Hex string has incorrect length, required to be divisible by eight: " + hexStrLen);
      }

      int outLen = hexStrLen / 8;
      int out[] = new int[outLen];
      byte nibble[] = new byte[2];
      byte b[] = new byte[4];
      int posn = 0;
      for (int i = 0; i < outLen; i++) {
         for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 2; k++) {
               switch (hexStr.charAt(posn++)) {
                  case '0':
                     nibble[k] = (byte)0;
                     break;
                  case '1':
                     nibble[k] = (byte)1;
                     break;
                  case '2':
                     nibble[k] = (byte)2;
                     break;
                  case '3':
                     nibble[k] = (byte)3;
                     break;
                  case '4':
                     nibble[k] = (byte)4;
                     break;
                  case '5':
                     nibble[k] = (byte)5;
                     break;
                  case '6':
                     nibble[k] = (byte)6;
                     break;
                  case '7':
                     nibble[k] = (byte)7;
                     break;
                  case '8':
                     nibble[k] = (byte)8;
                     break;
                  case '9':
                     nibble[k] = (byte)9;
                     break;
                  case 'A':
                     nibble[k] = (byte)0xA;
                     break;
                  case 'B':
                     nibble[k] = (byte)0xB;
                     break;
                  case 'C':
                     nibble[k] = (byte)0xC;
                     break;
                  case 'D':
                     nibble[k] = (byte)0xD;
                     break;
                  case 'E':
                     nibble[k] = (byte)0xE;
                     break;
                  case 'F':
                     nibble[k] = (byte)0xF;
                     break;
                  case 'a':
                     nibble[k] = (byte)0xA;
                     break;
                  case 'b':
                     nibble[k] = (byte)0xB;
                     break;
                  case 'c':
                     nibble[k] = (byte)0xC;
                     break;
                  case 'd':
                     nibble[k] = (byte)0xD;
                     break;
                  case 'e':
                     nibble[k] = (byte)0xE;
                     break;
                  case 'f':
                     nibble[k] = (byte)0xF;
                     break;
               }
            }

            b[j] = (byte)(nibble[0] << 4 | nibble[1]);
         }
         out[i] = (b[0] << 24) | (((b[1]) & 0xff) << 16) | (((b[2]) & 0xff) << 8) | ((b[3]) & 0xff);
      }

      return out;
   }


   /**
    * Convert an array of ints into a hex string.
    *
    * @param enc Array of integers.
    * @return String hexadecimal representation of the integer array.
    * @throws ArrayIndexOutOfBoundsException if the array doesn't contain pairs of integers.
    */
   public String binToHex(int enc[]) throws ArrayIndexOutOfBoundsException {
      // The number of ints should always be a multiple of two as required by TEA (64 bits).
      if ((enc.length % 2) == 1) {
         throw new ArrayIndexOutOfBoundsException("Odd number of ints found: " + enc.length);
      }

      StringBuilder sb = new StringBuilder();
      byte outb[] = new byte[8];

      for (int i = 0; i < enc.length; i += 2) {
         outb[0] = (byte)(enc[i] >>> 24);
         outb[1] = (byte)(enc[i] >>> 16);
         outb[2] = (byte)(enc[i] >>> 8);
         outb[3] = (byte)(enc[i]);
         outb[4] = (byte)(enc[i + 1] >>> 24);
         outb[5] = (byte)(enc[i + 1] >>> 16);
         outb[6] = (byte)(enc[i + 1] >>> 8);
         outb[7] = (byte)(enc[i + 1]);

         sb.append(getHex(outb));
      }

      return sb.toString();
   }

   // Display some bytes in HEX.
   //
   public String getHex(byte b[]) {

      StringBuilder r = new StringBuilder();

      for (int i = 0; i < b.length; i++) {
         int c = ((b[i]) >>> 4) & 0xf;
         r.append(hex[c]);
         c = ((int)b[i] & 0xf);
         r.append(hex[c]);
      }

      return r.toString();
   }

   public String padPlaintext(String str, char pc) {

      StringBuilder sb = new StringBuilder(str);
      int padding = sb.length() % 8;
      for (int i = 0; i < padding; i++) {
         sb.append(pc);
      }

      return sb.toString();
   }

   public String padPlaintext(String str) {

      return padPlaintext(str, ' ');
   }

   /**
    * Encrypt the specified plaintext.
    *
    * @param plaintext Plain text string.
    * @return Encrypted text
    */
   public static String encryptString(String plaintext) {

      if (plaintext == null || plaintext.length() == 0) {
         return plaintext;
      }

      TEAV tea = getTEA();

      String padStr = tea.padPlaintext(plaintext);
      byte[] plainBytes = padStr.getBytes();
      int[] encBytes = tea.encode(plainBytes, plainBytes.length);

      return tea.binToHex(encBytes);
   }

   /**
    * Decrpyt the encrypted text.
    *
    * @param encryptedText Encrypted text
    * @return Plain text
    */
   public static String decryptString(String encryptedText) {

      if (encryptedText == null || encryptedText.length() == 0) {
         return encryptedText;
      }

      TEAV tea = getTEA();

      int[] encBytes = tea.hexToBin(encryptedText);
      byte[] rawBytes = tea.decode(encBytes);

      return new String(rawBytes).trim();
   }

   public static TEAV getTEA() {

      return new TEAV(new java.math.BigInteger("e8396d58f8f9a9b908c87cb89ad959d", 16).toByteArray());
   }
}
