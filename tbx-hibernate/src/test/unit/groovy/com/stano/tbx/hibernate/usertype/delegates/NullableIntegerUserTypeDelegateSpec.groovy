package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class NullableIntegerUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()

      expect:
      nullableIntUserTypeDelegate.sqlTypes().size() == 1
      nullableIntUserTypeDelegate.sqlTypes()[0] == Types.INTEGER
   }

   void testReturnedClass() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()

      expect:
      nullableIntUserTypeDelegate.returnedClass() == Integer.class
   }

   void testIsMutable() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()

      expect:
      !nullableIntUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def value = 99
      def columnName = "abc"

      resultSet.getInt(columnName) >> value

      when:
      def result = nullableIntUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == value
   }

   void testDoGetNull() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getInt(columnName) >> 99
      resultSet.wasNull() >> true

      when:
      def result = nullableIntUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == 0
   }

   void testDoSetNullValue() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableIntUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.INTEGER)
      0 * preparedStatement._
   }

   void testDoSetZeroValue() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableIntUserTypeDelegate.doSet(preparedStatement, 0 as Integer, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.INTEGER)
      0 * preparedStatement._
   }

   void testDoSetNonNullValue() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def value = 99 as Integer

      when:
      nullableIntUserTypeDelegate.doSet(preparedStatement, value, columnIndex)

      then:
      1 * preparedStatement.setInt(columnIndex, value);
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def nullableIntUserTypeDelegate = new NullableIntUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableIntUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
