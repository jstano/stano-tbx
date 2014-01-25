package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil;
import org.hibernate.HibernateException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateUserTypeDelegate extends AbstractImmutableUserTypeDelegate {

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.DATE};
   }

   @Override
   public Class returnedClass() {

      return LocalDate.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      Date sqlDate = resultSet.getDate(names[0], DateTimeUtil.getUTCCalender());

      if (sqlDate == null || resultSet.wasNull()) {
         return null;
      }

      return DateTimeUtil.sqlDateToLocalDate(sqlDate);
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.DATE);
      }
      else if (value instanceof LocalDate) {
         preparedStatement.setDate(index, DateTimeUtil.localDateToSqlDate((LocalDate)value), DateTimeUtil.getUTCCalender());
      }
      else if (value instanceof LocalDateTime) {
         preparedStatement.setDate(index, DateTimeUtil.localDateToSqlDate(((LocalDateTime)value).toLocalDate()), DateTimeUtil.getUTCCalender());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
