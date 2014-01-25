package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class NullableDoubleUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()

      expect:
      nullableDoubleUserTypeDelegate.sqlTypes().size() == 1
      nullableDoubleUserTypeDelegate.sqlTypes()[0] == Types.DOUBLE
   }

   void testReturnedClass() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()

      expect:
      nullableDoubleUserTypeDelegate.returnedClass() == Double.class
   }

   void testIsMutable() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()

      expect:
      !nullableDoubleUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def value = 100.37
      def columnName = "abc"

      resultSet.getDouble(columnName) >> value

      when:
      def result = nullableDoubleUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == value
   }

   void testDoGetNull() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getDouble(columnName) >> 0.0
      resultSet.wasNull() >> true

      when:
      def result = nullableDoubleUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == 0.0
   }

   void testDoSetNullValue() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableDoubleUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DOUBLE)
      0 * preparedStatement._
   }

   void testDoSetZeroValue() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableDoubleUserTypeDelegate.doSet(preparedStatement, 0.0 as Double, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DOUBLE)
      0 * preparedStatement._
   }

   void testDoSetNonNullValue() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def value = 100.37 as Double

      when:
      nullableDoubleUserTypeDelegate.doSet(preparedStatement, value, columnIndex)

      then:
      1 * preparedStatement.setDouble(columnIndex, value);
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def nullableDoubleUserTypeDelegate = new NullableDoubleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      nullableDoubleUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
