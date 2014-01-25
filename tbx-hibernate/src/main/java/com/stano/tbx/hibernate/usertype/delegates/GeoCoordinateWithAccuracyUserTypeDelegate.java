package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.core.geolocation.GeoCoordinateWithAccuracy;
import org.hibernate.HibernateException;
import org.hibernate.type.DoubleType;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class GeoCoordinateWithAccuracyUserTypeDelegate extends AbstractImmutableCompositeUserTypeDelegate {

   private static final int LATITUDE_PROPERTY = 0;
   private static final int LONGITUDE_PROPERTY = 1;
   private static final int ACCURACY_PROPERTY = 2;

   @Override
   public Class returnedClass() {

      return GeoCoordinateWithAccuracy.class;
   }

   @Override
   public String[] getPropertyNames() {

      return new String[] {"latitude", "longitude", "accuracy"}; //NON-NLS
   }

   @Override
   public Type[] getPropertyTypes() {

      return new Type[] {DoubleType.INSTANCE, DoubleType.INSTANCE, DoubleType.INSTANCE};
   }

   @Override
   public Object getPropertyValue(Object component, int property) throws HibernateException {

      GeoCoordinateWithAccuracy geoCoordinateWithAccuracy = (GeoCoordinateWithAccuracy)component;

      if (property == LATITUDE_PROPERTY) {
         return geoCoordinateWithAccuracy.getGeoCoordinate().getLatitude();
      }

      if (property == LONGITUDE_PROPERTY) {
         return geoCoordinateWithAccuracy.getGeoCoordinate().getLongitude();
      }

      if (property == ACCURACY_PROPERTY) {
         return geoCoordinateWithAccuracy.getAccuracy();
      }

      return null;
   }

   @Override
   public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

      throw new UnsupportedOperationException("setPropertyValue is not supported for GeoCoordinateWithAccuracy types");
   }

   @Override
   public Object doGet(ResultSet resultSet,
                       String[] names) throws HibernateException, SQLException {

      Object latitude = resultSet.getObject(names[LATITUDE_PROPERTY]);
      Object longitude = resultSet.getObject(names[LONGITUDE_PROPERTY]);
      Object accuracy = resultSet.getObject(names[ACCURACY_PROPERTY]);

      if (latitude != null && longitude != null && accuracy != null) {
         return GeoCoordinateWithAccuracy.of((Double)latitude, (Double)longitude, (Double)accuracy);
      }

      return null;
   }

   @Override
   public void doSet(PreparedStatement preparedStatement,
                     Object value,
                     int column) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(column, Types.DOUBLE);
         preparedStatement.setNull(column + 1, Types.DOUBLE);
         preparedStatement.setNull(column + 2, Types.DOUBLE);
      }
      else if (value instanceof GeoCoordinateWithAccuracy) {
         GeoCoordinateWithAccuracy geoCoordinateWithAccuracy = (GeoCoordinateWithAccuracy)value;

         preparedStatement.setDouble(column, geoCoordinateWithAccuracy.getGeoCoordinate().getLatitude());
         preparedStatement.setDouble(column + 1, geoCoordinateWithAccuracy.getGeoCoordinate().getLongitude());
         preparedStatement.setDouble(column + 2, geoCoordinateWithAccuracy.getAccuracy());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
