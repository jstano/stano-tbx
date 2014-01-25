package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.hibernate.usertype.util.DateTimeUtil
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime

public class LocalDateUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()

      expect:
      localDateUserTypeDelegate.sqlTypes().size() == 1
      localDateUserTypeDelegate.sqlTypes()[0] == Types.DATE
   }

   void testReturnedClass() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()

      expect:
      localDateUserTypeDelegate.returnedClass() == LocalDate.class
   }

   void testIsMutable() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()

      expect:
      !localDateUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def localDate = LocalDate.of(2013, 1, 1)
      def columnName = "columnName"

      resultSet.getDate(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> DateTimeUtil.localDateToSqlDate(localDate)

      when:
      def result = localDateUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == localDate
   }

   void testDoGetNull() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getDate(columnName, {Calendar it -> it.timeZone.ID == "UTC"}) >> null

      when:
      def result = localDateUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localDateUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DATE)
      0 * preparedStatement._
   }

   void testDoSetLocalDate() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localDate = LocalDate.of(2013, 1, 1)
      def sqlDate = DateTimeUtil.localDateToSqlDate(localDate)

      when:
      localDateUserTypeDelegate.doSet(preparedStatement, localDate, columnIndex)

      then:
      1 * preparedStatement.setDate(columnIndex, sqlDate, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetLocalDateTime() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def localDateTime = LocalDateTime.of(2013, 1, 1, 8, 30, 45)
      def sqlDate = DateTimeUtil.localDateToSqlDate(localDateTime.toLocalDate())

      when:
      localDateUserTypeDelegate.doSet(preparedStatement, localDateTime, columnIndex)

      then:
      1 * preparedStatement.setDate(columnIndex, sqlDate, {Calendar it -> it.timeZone.ID == "UTC"})
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def localDateUserTypeDelegate = new LocalDateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localDateUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
