package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.core.geolocation.GeoCoordinateWithAccuracy
import com.stano.tbx.core.geolocation.GeoCoordinateWithRadius
import org.hibernate.type.DoubleType
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class GeoCoordinateWithRadiusUserTypeDelegateSpec extends Specification {

   void testGeneralStuff() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()

      expect:
      geoCoordinateWithRadiusUserTypeDelegate.propertyNames.length == 3
      geoCoordinateWithRadiusUserTypeDelegate.propertyNames[0] == "latitude"
      geoCoordinateWithRadiusUserTypeDelegate.propertyNames[1] == "longitude"
      geoCoordinateWithRadiusUserTypeDelegate.propertyNames[2] == "radius"

      geoCoordinateWithRadiusUserTypeDelegate.propertyTypes.length == 3
      geoCoordinateWithRadiusUserTypeDelegate.propertyTypes[0] == DoubleType.INSTANCE
      geoCoordinateWithRadiusUserTypeDelegate.propertyTypes[1] == DoubleType.INSTANCE
      geoCoordinateWithRadiusUserTypeDelegate.propertyTypes[2] == DoubleType.INSTANCE

      geoCoordinateWithRadiusUserTypeDelegate.returnedClass() == GeoCoordinateWithRadius

      !geoCoordinateWithRadiusUserTypeDelegate.isMutable()
   }

   void testGetPropertyValue() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()

      expect:
      geoCoordinateWithRadiusUserTypeDelegate.getPropertyValue(GeoCoordinateWithRadius.of(1.0, 2.0, 3.0), 0) == 1.0
      geoCoordinateWithRadiusUserTypeDelegate.getPropertyValue(GeoCoordinateWithRadius.of(1.0, 2.0, 3.0), 1) == 2.0
      geoCoordinateWithRadiusUserTypeDelegate.getPropertyValue(GeoCoordinateWithRadius.of(1.0, 2.0, 3.0), 2) == 3.0
      geoCoordinateWithRadiusUserTypeDelegate.getPropertyValue(GeoCoordinateWithRadius.of(1.0, 2.0, 3.0), 3) == null
   }

   void testSetPropertyValue() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()

      when:
      geoCoordinateWithRadiusUserTypeDelegate.setPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 0, 1.5)

      then:
      thrown UnsupportedOperationException
   }

   void testDoGetNull() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"
      def radiusColumnName = "radiusColumnName"

      resultSet.getObject(latitudeColumnName) >> latitude
      resultSet.getObject(longitudeColumnName) >> longitude
      resultSet.getObject(radiusColumnName) >> radius

      when:
      def result = geoCoordinateWithRadiusUserTypeDelegate.doGet(resultSet, [latitudeColumnName, longitudeColumnName, radiusColumnName] as String[])

      then:
      result == null

      where:
      latitude | longitude | radius
      null     | null      | null
      null     | 2.0       | 3.0
      1.0      | null      | 3.0
      1.0      | 2.0       | null
   }

   void testDoGet() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def geoCoordinateWithRadius = GeoCoordinateWithRadius.of(39.760000d, -106.340000d, 100.0d)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"
      def radiusColumnName = "radiusColumnName"

      resultSet.getObject(latitudeColumnName) >> 39.760000d
      resultSet.getObject(longitudeColumnName) >> -106.340000d
      resultSet.getObject(radiusColumnName) >> 100.0d

      when:
      def result = geoCoordinateWithRadiusUserTypeDelegate.doGet(resultSet, [latitudeColumnName, longitudeColumnName, radiusColumnName] as String[])

      then:
      result == geoCoordinateWithRadius
   }

   void testDoSetNullValue() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateWithRadiusUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DOUBLE)
      1 * preparedStatement.setNull(columnIndex + 1, Types.DOUBLE)
      1 * preparedStatement.setNull(columnIndex + 2, Types.DOUBLE)
      0 * preparedStatement._
   }

   void testDoSetValidGeoCoordinate() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def geoCoordinateWithRadius = GeoCoordinateWithRadius.of(39.760000d, -106.340000d, 100.0d)

      when:
      geoCoordinateWithRadiusUserTypeDelegate.doSet(preparedStatement, geoCoordinateWithRadius, columnIndex)

      then:
      1 * preparedStatement.setDouble(columnIndex, 39.760000d)
      1 * preparedStatement.setDouble(columnIndex + 1, -106.340000d)
      1 * preparedStatement.setDouble(columnIndex + 2, 100.0d)
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def geoCoordinateWithRadiusUserTypeDelegate = new GeoCoordinateWithRadiusUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateWithRadiusUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
