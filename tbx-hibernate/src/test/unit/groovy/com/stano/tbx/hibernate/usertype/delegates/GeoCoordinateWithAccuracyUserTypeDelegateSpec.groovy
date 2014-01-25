package com.stano.tbx.hibernate.usertype.delegates

import com.stano.tbx.core.geolocation.GeoCoordinateWithAccuracy
import org.hibernate.type.DoubleType
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class GeoCoordinateWithAccuracyUserTypeDelegateSpec extends Specification {

   void testGeneralStuff() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()

      expect:
      geoCoordinateWithAccuracyUserTypeDelegate.propertyNames.length == 3
      geoCoordinateWithAccuracyUserTypeDelegate.propertyNames[0] == "latitude"
      geoCoordinateWithAccuracyUserTypeDelegate.propertyNames[1] == "longitude"
      geoCoordinateWithAccuracyUserTypeDelegate.propertyNames[2] == "accuracy"

      geoCoordinateWithAccuracyUserTypeDelegate.propertyTypes.length == 3
      geoCoordinateWithAccuracyUserTypeDelegate.propertyTypes[0] == DoubleType.INSTANCE
      geoCoordinateWithAccuracyUserTypeDelegate.propertyTypes[1] == DoubleType.INSTANCE
      geoCoordinateWithAccuracyUserTypeDelegate.propertyTypes[2] == DoubleType.INSTANCE

      geoCoordinateWithAccuracyUserTypeDelegate.returnedClass() == GeoCoordinateWithAccuracy.class

      !geoCoordinateWithAccuracyUserTypeDelegate.isMutable()
   }

   void testGetPropertyValue() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()

      expect:
      geoCoordinateWithAccuracyUserTypeDelegate.getPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 0) == 1.0
      geoCoordinateWithAccuracyUserTypeDelegate.getPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 1) == 2.0
      geoCoordinateWithAccuracyUserTypeDelegate.getPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 2) == 3.0
      geoCoordinateWithAccuracyUserTypeDelegate.getPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 3) == null
   }

   void testSetPropertyValue() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()

      when:
      geoCoordinateWithAccuracyUserTypeDelegate.setPropertyValue(GeoCoordinateWithAccuracy.of(1.0, 2.0, 3.0), 0, 1.5)

      then:
      thrown UnsupportedOperationException
   }

   void testDoGetNull() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"
      def accuracyColumnName = "accuracyColumnName"

      resultSet.getObject(latitudeColumnName) >> latitude
      resultSet.getObject(longitudeColumnName) >> longitude
      resultSet.getObject(accuracyColumnName) >> accuracy

      when:
      def result = geoCoordinateWithAccuracyUserTypeDelegate.doGet(
         resultSet,
         [latitudeColumnName, longitudeColumnName, accuracyColumnName] as String[])

      then:
      result == null

      where:
      latitude | longitude | accuracy
      null     | null      | null
      null     | 2.0       | 3.0
      1.0      | null      | 3.0
      1.0      | 2.0       | null
   }

   void testDoGet() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()
      def resultSet = Mock(ResultSet)
      def geoCoordinateWithAccuracy = GeoCoordinateWithAccuracy.of(39.760000d, -106.340000d, 100.0d)
      def latitudeColumnName = "latitudeColumnName"
      def longitudeColumnName = "longitudeColumnName"
      def accuracyColumnName = "accuracyColumnName"

      resultSet.getObject(latitudeColumnName) >> 39.760000d
      resultSet.getObject(longitudeColumnName) >> -106.340000d
      resultSet.getObject(accuracyColumnName) >> 100.0d

      when:
      def result = geoCoordinateWithAccuracyUserTypeDelegate.doGet(
         resultSet,
         [latitudeColumnName, longitudeColumnName, accuracyColumnName] as String[])

      then:
      result == geoCoordinateWithAccuracy
   }

   void testDoSetNullValue() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateWithAccuracyUserTypeDelegate.doSet(preparedStatement, null, columnIndex)

      then:
      1 * preparedStatement.setNull(columnIndex, Types.DOUBLE)
      1 * preparedStatement.setNull(columnIndex + 1, Types.DOUBLE)
      1 * preparedStatement.setNull(columnIndex + 2, Types.DOUBLE)
      0 * preparedStatement._
   }

   void testDoSetValidGeoCoordinate() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17
      def geoCoordinateWithAccuracy = GeoCoordinateWithAccuracy.of(39.760000d, -106.340000d, 100.0d)

      when:
      geoCoordinateWithAccuracyUserTypeDelegate.doSet(preparedStatement, geoCoordinateWithAccuracy, columnIndex)

      then:
      1 * preparedStatement.setDouble(columnIndex, 39.760000d)
      1 * preparedStatement.setDouble(columnIndex + 1, -106.340000d)
      1 * preparedStatement.setDouble(columnIndex + 2, 100.0d)
      0 * preparedStatement._
   }

   void testDoSetInvalidType() {

      def geoCoordinateWithAccuracyUserTypeDelegate = new GeoCoordinateWithAccuracyUserTypeDelegate()
      def preparedStatement = Mock(PreparedStatement)
      def columnIndex = 17

      when:
      geoCoordinateWithAccuracyUserTypeDelegate.doSet(preparedStatement, "ABC", columnIndex)

      then:
      thrown IllegalArgumentException
   }
}
