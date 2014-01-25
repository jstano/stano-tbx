package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class NullableIntUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.INTEGER};
   }

   @Override
   public Class returnedClass() {

      return Integer.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      int value = resultSet.getInt(names[0]);

      if (resultSet.wasNull()) {
         return 0;
      }

      return value;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null || value.equals(0)) {
         preparedStatement.setNull(index, Types.INTEGER);
      }
      else if (value instanceof Integer) {
         preparedStatement.setInt(index, (Integer)value);
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
