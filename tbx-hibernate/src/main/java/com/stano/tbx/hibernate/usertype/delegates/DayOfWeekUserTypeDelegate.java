package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.DayOfWeek;

public class DayOfWeekUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.INTEGER};
   }

   @Override
   public Class returnedClass() {

      return DayOfWeek.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      Object usDayNumber = resultSet.getObject(names[0]);

      if (usDayNumber == null) {
         return null;
      }

      return DateTimeUtil.usDayNumberToDayOfWeek(((Number)usDayNumber).intValue());
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.INTEGER);
      }
      else if (value instanceof DayOfWeek) {
         preparedStatement.setInt(index, DateTimeUtil.dayOfWeekToUsDayNumber((DayOfWeek)value));
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
