package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class NullableDoubleUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.DOUBLE};
   }

   @Override
   public Class returnedClass() {

      return Double.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      double value = resultSet.getDouble(names[0]);

      if (resultSet.wasNull()) {
         return 0.0;
      }

      return value;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null || value.equals(0.0)) {
         preparedStatement.setNull(index, Types.DOUBLE);
      }
      else if (value instanceof Double) {
         preparedStatement.setDouble(index, (Double)value);
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
