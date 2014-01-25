package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class AbstractStringUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {

      return String.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String text = resultSet.getString(names[0]);

      if (text == null) {
         return null;
      }

      return convertFromString(text);
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
      }
      else {
         preparedStatement.setString(index, convertToString(value.toString()));
      }
   }

   protected abstract Object convertFromString(String text);

   protected abstract String convertToString(Object object);
}
