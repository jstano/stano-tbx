package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class AbstractStringUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()

      expect:
      testAbstractStringUserTypeDelegate.sqlTypes().size() == 1
      testAbstractStringUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testReturnedClass() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()

      expect:
      testAbstractStringUserTypeDelegate.returnedClass() == String.class
   }

   void testIsMutable() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()

      expect:
      !testAbstractStringUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def text = "ABC"
      def columnName = "columnName"

      resultSet.getString(columnName) >> text

      when:
      def result = testAbstractStringUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == "123"
   }

   void testDoGetNull() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getTimestamp(columnName) >> null

      when:
      def result = testAbstractStringUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      testAbstractStringUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetString() {

      def testAbstractStringUserTypeDelegate = new TestAbstractStringUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def text = "123"

      when:
      testAbstractStringUserTypeDelegate.doSet(preparedStatement, text, columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, "ABC");
      0 * preparedStatement._
   }
}

class TestAbstractStringUserTypeDelegate extends AbstractStringUserTypeDelegate {

   @Override
   protected Object convertFromString(String text) {

      return "123"
   }

   @Override
   protected String convertToString(Object object) {

      return "ABC"
   }
}
