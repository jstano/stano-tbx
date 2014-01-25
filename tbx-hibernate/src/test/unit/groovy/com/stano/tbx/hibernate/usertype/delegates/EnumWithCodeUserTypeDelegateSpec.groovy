package com.stano.tbx.hibernate.usertype.delegates

import org.hibernate.HibernateException
import spock.lang.Specification

import java.lang.reflect.InvocationTargetException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class EnumWithCodeUserTypeDelegateSpec
   extends Specification {

   void testSqlTypes() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate()

      expect:
      enumWithCodeUserTypeDelegate.sqlTypes().size() == 1
      enumWithCodeUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testEnumClassAndReturnedClass() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate()
      enumWithCodeUserTypeDelegate.setEnumClass(TestEnumWithCodeMethods)

      expect:
      enumWithCodeUserTypeDelegate.enumClass == TestEnumWithCodeMethods
      enumWithCodeUserTypeDelegate.returnedClass() == TestEnumWithCodeMethods
   }

   void testIsMutable() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate()

      expect:
      !enumWithCodeUserTypeDelegate.isMutable()
   }

   void testDoGetNull() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethods)
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getString(columnName) >> null

      when:
      def result = enumWithCodeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoGetEnumWithCodeMethods() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethods)
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getString(columnName) >> "A"

      when:
      def result = enumWithCodeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == TestEnumWithCodeMethods.A
   }

   void testDoGetEnumWithNoCodeMethods() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithNoCodeMethods)
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getString(columnName) >> "A"

      when:
      def result = enumWithCodeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == TestEnumWithNoCodeMethods.A
   }

   void testDoGetEnumWithCodeMethodsThrowException() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethodsThrowException)
      def resultSet = Mock(ResultSet)
      def columnName = "columnName"

      resultSet.getString(columnName) >> "A"

      when:
      enumWithCodeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      thrown HibernateException
   }

   void testDoSetNullValue() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethods)
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      enumWithCodeUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetValueWithCodeMethods() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethods)
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      enumWithCodeUserTypeDelegate.doSet(preparedStatement, TestEnumWithCodeMethods.B, columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, "B")
      0 * preparedStatement._
   }

   void testDoSetValueNoCodeMethods() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithNoCodeMethods)
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      enumWithCodeUserTypeDelegate.doSet(preparedStatement, TestEnumWithNoCodeMethods.B, columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, "B")
      0 * preparedStatement._
   }

   void testDoSetValueWithCodeMethodsThrowException() {

      def enumWithCodeUserTypeDelegate = new EnumWithCodeUserTypeDelegate(enumClass: TestEnumWithCodeMethodsThrowException)
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      enumWithCodeUserTypeDelegate.doSet(preparedStatement, TestEnumWithCodeMethodsThrowException.B, columnIndex)

      then:
      thrown HibernateException
   }
}

enum TestEnumWithCodeMethods {

   A, B, C;

   String getCode() {

      return name()
   }

   static TestEnumWithCodeMethods fromCode(String code) {

      return valueOf(code)
   }
}

enum TestEnumWithNoCodeMethods {

   A, B, C;
}

enum TestEnumWithCodeMethodsThrowException {

   A, B, C;

   String getCode() {

      throw new InvocationTargetException("Something bad happened")
   }

   static TestEnumWithCodeMethods fromCode(String code) {

      throw new InvocationTargetException("Something bad happened")
   }
}
