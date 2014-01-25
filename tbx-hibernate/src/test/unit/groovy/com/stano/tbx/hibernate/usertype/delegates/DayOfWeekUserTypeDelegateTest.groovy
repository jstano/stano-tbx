package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.DayOfWeek

public class DayOfWeekUserTypeDelegateTest
   extends Specification {

   void testSqlTypes() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()

      expect:
      dayOfWeekUserTypeDelegate.sqlTypes().size() == 1
      dayOfWeekUserTypeDelegate.sqlTypes()[0] == Types.INTEGER
   }

   void testReturnedClass() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()

      expect:
      dayOfWeekUserTypeDelegate.returnedClass() == DayOfWeek.class
   }

   void testIsMutable() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()

      expect:
      !dayOfWeekUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def dayOfWeek = DayOfWeek.SUNDAY
      def columnName = "columnName"

      resultSet.getObject(columnName) >> 1

      when:
      def result = dayOfWeekUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == dayOfWeek
   }

   void testDoGetNull() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getObject(columnName) >> null

      when:
      def result = dayOfWeekUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      dayOfWeekUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.INTEGER)
      0 * preparedStatement._
   }

   void testDoSetDayOfWeek() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def dayOfWeek = DayOfWeek.SUNDAY
      def usDayNumber = 1

      when:
      dayOfWeekUserTypeDelegate.doSet(preparedStatement, dayOfWeek, columnIndex)

      then:
      1 * preparedStatement.setInt(columnIndex, usDayNumber);
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def dayOfWeekUserTypeDelegate = new DayOfWeekUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      dayOfWeekUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
