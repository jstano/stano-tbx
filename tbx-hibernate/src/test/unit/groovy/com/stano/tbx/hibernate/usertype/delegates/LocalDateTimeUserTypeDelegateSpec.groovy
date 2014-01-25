package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime

public class LocalDateTimeUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()

      expect:
      localDateTimeUserTypeDelegate.sqlTypes().size() == 1
      localDateTimeUserTypeDelegate.sqlTypes()[0] == Types.TIMESTAMP
   }

   void testReturnedClass() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()

      expect:
      localDateTimeUserTypeDelegate.returnedClass() == LocalDateTime.class
   }

   void testIsMutable() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()

      expect:
      !localDateTimeUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def localDateTime = LocalDateTime.of(2013, 1, 1, 8, 30, 45)
      def columnName = "columnName"

      resultSet.getTimestamp(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> DateTimeUtil.localDateTimeToSqlTimestamp(localDateTime)

      when:
      def result = localDateTimeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == localDateTime
   }

   void testDoGetNull() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getTimestamp(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> null

      when:
      def result = localDateTimeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localDateTimeUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.TIMESTAMP)
      0 * preparedStatement._
   }

   void testDoSetLocalDateTime() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localDateTime = LocalDateTime.of(2013, 1, 1, 8, 30, 45)
      def sqlTimestamp = DateTimeUtil.localDateTimeToSqlTimestamp(localDateTime)

      when:
      localDateTimeUserTypeDelegate.doSet(preparedStatement, localDateTime, columnIndex)

      then:
      1 * preparedStatement.setTimestamp(columnIndex, sqlTimestamp, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetLocalDate() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localDate = LocalDate.of(2013, 1, 1)
      def sqlTimestamp = DateTimeUtil.localDateTimeToSqlTimestamp(localDate.atStartOfDay())

      when:
      localDateTimeUserTypeDelegate.doSet(preparedStatement, localDate, columnIndex)

      then:
      1 * preparedStatement.setTimestamp(columnIndex, sqlTimestamp, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def localDateTimeUserTypeDelegate = new LocalDateTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localDateTimeUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
