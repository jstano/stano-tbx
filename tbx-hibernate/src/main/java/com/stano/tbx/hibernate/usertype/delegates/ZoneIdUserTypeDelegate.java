package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.ZoneId;

public class ZoneIdUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {

      return ZoneId.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String value = resultSet.getString(names[0]);

      if (value != null) {
         return ZoneId.of(value.trim());
      }

      return null;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
      }
      else if (value instanceof ZoneId) {
         preparedStatement.setString(index, value.toString());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
