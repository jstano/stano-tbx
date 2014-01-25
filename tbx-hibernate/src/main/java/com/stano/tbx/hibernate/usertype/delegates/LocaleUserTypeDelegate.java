package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Locale;

public class LocaleUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {

      return Locale.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String value = resultSet.getString(names[0]);

      if (value != null) {
         return parseLocale(value.trim());
      }

      return null;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
      }
      else if (value instanceof Locale) {
         preparedStatement.setString(index, value.toString());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }

   private Locale parseLocale(String localeStr) {

      if (localeStr == null || localeStr.isEmpty()) {
         return null;
      }

      if (localeStr.contains("_")) {
         String[] parts = localeStr.split("_");

         return new Locale(parts[0], parts[1]);
      }

      return new Locale(localeStr);
   }
}
