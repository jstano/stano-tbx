package com.stano.tbx.hibernate.usertype.delegates;

public class IntegerCSVListUserTypeDelegate extends AbstractCSVListUserTypeDelegate<Integer> {

   private static final String LEGACY_NULL = "null";

   @Override
   protected Integer parseValue(String text) {

      if (text.equalsIgnoreCase(LEGACY_NULL)) {
         return null;
      }

      try {
         return new Integer(text);
      }
      catch (NumberFormatException x) {
         return 0;
      }
   }
}
