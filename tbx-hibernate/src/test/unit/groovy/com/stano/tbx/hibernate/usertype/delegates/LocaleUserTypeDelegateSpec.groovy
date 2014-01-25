package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class LocaleUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()

      expect:
      localeUserTypeDelegate.sqlTypes().size() == 1
      localeUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testReturnedClass() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()

      expect:
      localeUserTypeDelegate.returnedClass() == Locale.class
   }

   void testIsMutable() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()

      expect:
      !localeUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def locale = Locale.US
      def columnName = "abc"

      resultSet.getString(columnName) >> "en_US"

      when:
      def result = localeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == locale
   }

   void testDoGetLanguageOnly() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def locale = Locale.ENGLISH
      def columnName = "abc"

      resultSet.getString(columnName) >> "en"

      when:
      def result = localeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == locale
   }

   void testDoGetNull() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> null

      when:
      def result = localeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoGetBlank() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> ""

      when:
      def result = localeUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localeUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetLocale() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def locale = Locale.US
      def localeStr = "en_US"

      when:
      localeUserTypeDelegate.doSet(preparedStatement, locale, columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, localeStr);
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def localeUserTypeDelegate = new LocaleUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      localeUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
