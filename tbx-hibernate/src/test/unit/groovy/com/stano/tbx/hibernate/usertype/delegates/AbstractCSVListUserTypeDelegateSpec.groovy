package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class AbstractCSVListUserTypeDelegateSpec extends Specification {

   def testSqlTypes() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      expect:
      abstractCSVListUserTypeDelegate.sqlTypes().size() == 1
      abstractCSVListUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   def testReturnedClass() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      expect:
      abstractCSVListUserTypeDelegate.returnedClass() == List.class
   }

   def testIsMutable() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      expect:
      abstractCSVListUserTypeDelegate.isMutable()
   }

   void testDoGetNull() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> null

      when:
      def result = abstractCSVListUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == []
   }

   void testDoGetValidData() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> "1,2,3,_, "

      when:
      def result = abstractCSVListUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == [1, 2, 3, null, null]
   }

   void testDoSetNullValue() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      abstractCSVListUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetList() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      abstractCSVListUserTypeDelegate.doSet(preparedStatement, [1, 2, 3, null], columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, "1,2,3,_");
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      abstractCSVListUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }

   void testSplitText() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      expect:
      abstractCSVListUserTypeDelegate.splitText("1:2", ":") == ["1", "2"]
   }

   void testSplitTextOne() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      when:
      abstractCSVListUserTypeDelegate.splitText("1", ":")

      then:
      thrown IllegalArgumentException
   }

   void testSplitTextLeftBlank() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      when:
      abstractCSVListUserTypeDelegate.splitText(":2", ":")

      then:
      thrown IllegalArgumentException
   }

   void testSplitTextRightBlank() {

      def abstractCSVListUserTypeDelegate = new TestableCSVListUserType()

      when:
      abstractCSVListUserTypeDelegate.splitText("2: ", ":")

      then:
      thrown IllegalArgumentException
   }
}

class TestableCSVListUserType extends AbstractCSVListUserTypeDelegate<Integer> {

   @Override
   protected Integer parseValue(String text) {

      if (text && text.trim().length()) {
         return Integer.valueOf(text)
      }

      return null
   }
}
