package com.stano.tbx.hibernate.usertype.delegates

import org.json.JSONArray
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class JsonArrayUserTypeDelegateSpec
   extends Specification {

   void testSqlTypes() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()

      expect:
      jsonArrayUserTypeDelegate.sqlTypes().size() == 1
      jsonArrayUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testReturnedClass() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()

      expect:
      jsonArrayUserTypeDelegate.returnedClass() == JSONArray.class
   }

   void testIsMutable() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()

      expect:
      jsonArrayUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> "[1,2]"

      when:
      JSONArray result = (JSONArray)jsonArrayUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result.length() == 2
      result.get(0) == 1
      result.get(1) == 2
   }

   void testDoGetNull() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> null

      when:
      def result = jsonArrayUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonArrayUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetValue() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonArrayUserTypeDelegate.doSet(preparedStatement, new JSONArray([1, 2]), columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, "[1,2]");
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def jsonArrayUserTypeDelegate = new JsonArrayUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonArrayUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
