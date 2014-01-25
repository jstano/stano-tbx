package com.stano.tbx.hibernate.usertype.delegates;

public class DoubleCSVListUserTypeDelegate extends AbstractCSVListUserTypeDelegate<Double> {

   @Override
   protected Double parseValue(String text) {

      try {
         return new Double(text);
      }
      catch (NumberFormatException x) {
         return 0.0;
      }
   }
}
