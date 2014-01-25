package com.stano.tbx.hibernate.usertype.delegates

import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.ZoneId

public class ZoneIdUserTypeDelegateSpec extends Specification {

   void testSqlTypes() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()

      expect:
      zoneIdUserTypeDelegate.sqlTypes().size() == 1
      zoneIdUserTypeDelegate.sqlTypes()[0] == Types.VARCHAR
   }

   void testReturnedClass() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()

      expect:
      zoneIdUserTypeDelegate.returnedClass() == ZoneId.class
   }

   void testIsMutable() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()

      expect:
      !zoneIdUserTypeDelegate.isMutable()
   }

   void testDoGet() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def zoneId = ZoneId.of("UTC")
      def columnName = "abc"

      resultSet.getString(columnName) >> "UTC"

      when:
      def result = zoneIdUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == zoneId
   }

   void testDoGetNull() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def columnName = "abc"

      resultSet.getString(columnName) >> null

      when:
      def result = zoneIdUserTypeDelegate.doGet(resultSet, columnName)

      then:
      result == null
   }

   void testDoSetNullValue() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      zoneIdUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.VARCHAR)
      0 * preparedStatement._
   }

   void testDoSetZoneId() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def zoneId = ZoneId.of("UTC")
      def zoneIdStr = "UTC"

      when:
      zoneIdUserTypeDelegate.doSet(preparedStatement, zoneId, columnIndex)

      then:
      1 * preparedStatement.setString(columnIndex, zoneIdStr);
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def zoneIdUserTypeDelegate = new ZoneIdUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      zoneIdUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
