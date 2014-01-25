package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.TIMESTAMP};
   }

   @Override
   public Class returnedClass() {

      return LocalDateTime.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      Timestamp sqlTimestamp = resultSet.getTimestamp(names[0], DateTimeUtil.getUTCCalender());

      if (sqlTimestamp == null || resultSet.wasNull()) {
         return null;
      }

      return DateTimeUtil.sqlTimestampToLocalDateTime(sqlTimestamp);
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.TIMESTAMP);
      }
      else if (value instanceof LocalDateTime) {
         preparedStatement.setTimestamp(index, DateTimeUtil.localDateTimeToSqlTimestamp((LocalDateTime)value), DateTimeUtil.getUTCCalender());
      }
      else if (value instanceof LocalDate) {
         preparedStatement.setTimestamp(index,
                                        DateTimeUtil.localDateTimeToSqlTimestamp(((LocalDate)value).atStartOfDay()),
                                        DateTimeUtil.getUTCCalender());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
