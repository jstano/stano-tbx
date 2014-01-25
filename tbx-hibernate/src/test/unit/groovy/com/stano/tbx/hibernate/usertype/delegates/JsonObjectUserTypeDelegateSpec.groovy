package com.stano.tbx.hibernate.usertype.delegates

import org.json.JSONObject
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class JsonObjectUserTypeDelegateSpec
   extends Specification {

   void testSqlTypes() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()

      expect:
      jsonObjectUserTypeDelegate.sqlTypes().size() == 1
      jsonObjectUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testReturnedClass() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()

      expect:
      jsonObjectUserTypeDelegate.returnedClass() == JSONObject.class
   }

   void testIsMutable() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()

      expect:
      jsonObjectUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> "{id:100, name:'test'}"

      when:
      JSONObject result = (JSONObject)jsonObjectUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result.length() == 2
      result.get("id") == 100
      result.get("name") == "test"
   }

   void testDoGetNull() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> null

      when:
      def result = jsonObjectUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonObjectUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetValue() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonObjectUserTypeDelegate.doSet(preparedStatement, new JSONObject([id: 100, name: 'test']), columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, '{"name":"test","id":100}');
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def jsonObjectUserTypeDelegate = new JsonObjectUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      jsonObjectUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
