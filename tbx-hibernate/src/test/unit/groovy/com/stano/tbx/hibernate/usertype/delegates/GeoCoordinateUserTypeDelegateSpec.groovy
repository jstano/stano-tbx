package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.core.geolocation.GeoCoordinate
import org.hibernate.type.DoubleType
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class GeoCoordinateUserTypeDelegateSpec extends Specification {

   void testGeneralStuff() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()

      expect:
      geoCoordinateUserTypeDelegate.propertyNames.length == 2
      geoCoordinateUserTypeDelegate.propertyNames[0] == "latitude"
      geoCoordinateUserTypeDelegate.propertyNames[1] == "longitude"

      geoCoordinateUserTypeDelegate.propertyTypes.length == 2
      geoCoordinateUserTypeDelegate.propertyTypes[0] == DoubleType.INSTANCE
      geoCoordinateUserTypeDelegate.propertyTypes[1] == DoubleType.INSTANCE

      geoCoordinateUserTypeDelegate.returnedClass() == GeoCoordinate.class

      !geoCoordinateUserTypeDelegate.isMutable()
   }

   void testGetPropertyValue() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()

      expect:
      geoCoordinateUserTypeDelegate.getPropertyValue(GeoCoordinate.of(1.0, 2.0), 0) == 1.0
      geoCoordinateUserTypeDelegate.getPropertyValue(GeoCoordinate.of(1.0, 2.0), 1) == 2.0
      geoCoordinateUserTypeDelegate.getPropertyValue(GeoCoordinate.of(1.0, 2.0), 2) == null
   }

   void testSetPropertyValue() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()

      when:
      geoCoordinateUserTypeDelegate.setPropertyValue(GeoCoordinate.of(1.0, 2.0), 0, 3.0)

      then:
      thrown UnsupportedOperationException
   }

   void testDoGetNull() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"

      resultSet.getObject(latitudeColumnName) >> latitude
      resultSet.getObject(longitudeColumnName) >> longitude

      when:
      def result = geoCoordinateUserTypeDelegate.doGet(resultSet, [latitudeColumnName, longitudeColumnName] as String[])

      then:
      result == null

      where:
      latitude | longitude
      null     | null
      null     | 2.0
      1.0      | null
   }

   void testDoGet() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def geoCoordinate = GeoCoordinate.of(39.760000d, -106.340000d)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"

      resultSet.getObject(latitudeColumnName) >> 39.760000d
      resultSet.getObject(longitudeColumnName) >> -106.340000d

      when:
      def result = geoCoordinateUserTypeDelegate.doGet(resultSet, [latitudeColumnName, longitudeColumnName] as String[])

      then:
      result == geoCoordinate
   }

   void testDoSetNullValue() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DOUBLE)
      1 * preparedStatement.setNull(columnIndex + 1, Types.DOUBLE)
      0 * preparedStatement._
   }

   void testDoSetValidGeoCoordinate() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def geoCoordinate = GeoCoordinate.of(39.760000d, -106.340000d)

      when:
      geoCoordinateUserTypeDelegate.doSet(preparedStatement, geoCoordinate, columnIndex)

      then:
      1 * preparedStatement.setDouble(columnIndex, 39.760000d)
      1 * preparedStatement.setDouble(columnIndex + 1, -106.340000d)
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def geoCoordinateUserTypeDelegate = new GeoCoordinateUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
