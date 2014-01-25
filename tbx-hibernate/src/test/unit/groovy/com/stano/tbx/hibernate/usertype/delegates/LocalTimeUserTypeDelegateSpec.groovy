package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDateTime
import java.time.LocalTime

public class LocalTimeUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()

      expect:
      localTimeUserTypeDelegate.sqlTypes().size() == 1
      localTimeUserTypeDelegate.sqlTypes()[0] == Types.TIME
   }

   void testReturnedClass() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()

      expect:
      localTimeUserTypeDelegate.returnedClass() == LocalTime.class
   }

   void testIsMutable() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()

      expect:
      !localTimeUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def localTime = LocalTime.of(8, 30, 45)
      def columnName = "columnName"

      resultSet.getTime(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> DateTimeUtil.localTimeToSqlTime(localTime)

      when:
      def result = localTimeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == localTime
   }

   void testDoGetNull() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getDate(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> null

      when:
      def result = localTimeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localTimeUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.TIME)
      0 * preparedStatement._
   }

   void testDoSetLocalDate() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localTime = LocalTime.of(8, 30, 45)
      def sqlTime = DateTimeUtil.localTimeToSqlTime(localTime)

      when:
      localTimeUserTypeDelegate.doSet(preparedStatement, localTime, columnIndex)

      then:
      1 * preparedStatement.setTime(columnIndex, sqlTime, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetLocalDateTime() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localDateTime = LocalDateTime.of(2013, 1, 1, 8, 30, 45)
      def sqlTime = DateTimeUtil.localTimeToSqlTime(localDateTime.toLocalTime())

      when:
      localTimeUserTypeDelegate.doSet(preparedStatement, localDateTime, columnIndex)

      then:
      1 * preparedStatement.setTime(columnIndex, sqlTime, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def localTimeUserTypeDelegate = new LocalTimeUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localTimeUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
