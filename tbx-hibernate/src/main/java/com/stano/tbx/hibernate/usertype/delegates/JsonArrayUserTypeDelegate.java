package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;
import org.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JsonArrayUserTypeDelegate extends AbstractMutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {

      return JSONArray.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String value = resultSet.getString(names[0]);

      if (value != null) {
         return new JSONArray(value);
      }

      return null;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
      }
      else if (value instanceof JSONArray) {
         preparedStatement.setString(index, value.toString());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
