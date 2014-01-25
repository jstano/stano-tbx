package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.TIME};
   }

   @Override
   public Class returnedClass() {

      return LocalTime.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      Time sqlTime = resultSet.getTime(names[0], DateTimeUtil.getUTCCalender());

      if (sqlTime == null || resultSet.wasNull()) {
         return null;
      }

      return DateTimeUtil.sqlTimeToLocalTime(sqlTime);
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.TIME);
      }
      else if (value instanceof LocalTime) {
         preparedStatement.setTime(index, DateTimeUtil.localTimeToSqlTime((LocalTime)value), DateTimeUtil.getUTCCalender());
      }
      else if (value instanceof LocalDateTime) {
         preparedStatement.setTime(index, DateTimeUtil.localTimeToSqlTime(((LocalDateTime)value).toLocalTime()), DateTimeUtil.getUTCCalender());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
