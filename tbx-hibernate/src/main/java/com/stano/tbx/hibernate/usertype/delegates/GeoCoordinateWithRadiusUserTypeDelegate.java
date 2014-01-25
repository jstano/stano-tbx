package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.core.geolocation.GeoCoordinateWithRadius;
import org.hibernate.HibernateException;
import org.hibernate.type.DoubleType;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class GeoCoordinateWithRadiusUserTypeDelegate extends AbstractImmutableCompositeUserTypeDelegate {

   private static final int LATITUDE_PROPERTY = 0;
   private static final int LONGITUDE_PROPERTY = 1;
   private static final int RADIUS_PROPERTY = 2;

   @Override
   public Class returnedClass() {

      return GeoCoordinateWithRadius.class;
   }

   @Override
   public String[] getPropertyNames() {

      return new String[] {"latitude", "longitude", "radius"}; //NON-NLS
   }

   @Override
   public Type[] getPropertyTypes() {

      return new Type[] {DoubleType.INSTANCE, DoubleType.INSTANCE, DoubleType.INSTANCE};
   }

   @Override
   public Object getPropertyValue(Object component, int property) throws HibernateException {

      GeoCoordinateWithRadius geoCoordinateWithRadius = (GeoCoordinateWithRadius)component;

      if (property == LATITUDE_PROPERTY) {
         return geoCoordinateWithRadius.getGeoCoordinate().getLatitude();
      }

      if (property == LONGITUDE_PROPERTY) {
         return geoCoordinateWithRadius.getGeoCoordinate().getLongitude();
      }

      if (property == RADIUS_PROPERTY) {
         return geoCoordinateWithRadius.getRadius();
      }

      return null;
   }

   @Override
   public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

      throw new UnsupportedOperationException("setPropertyValue is not supported for GeoCoordinateWithRadius types");
   }

   @Override
   public Object doGet(ResultSet resultSet,
                       String[] names) throws HibernateException, SQLException {

      Object latitude = resultSet.getObject(names[LATITUDE_PROPERTY]);
      Object longitude = resultSet.getObject(names[LONGITUDE_PROPERTY]);
      Object radius = resultSet.getObject(names[RADIUS_PROPERTY]);

      if (latitude != null && longitude != null && radius != null) {
         return GeoCoordinateWithRadius.of((Double)latitude, (Double)longitude, (Double)radius);
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
      else if (value instanceof GeoCoordinateWithRadius) {
         GeoCoordinateWithRadius geoCoordinateWithRadius = (GeoCoordinateWithRadius)value;

         preparedStatement.setDouble(column, geoCoordinateWithRadius.getGeoCoordinate().getLatitude());
         preparedStatement.setDouble(column + 1, geoCoordinateWithRadius.getGeoCoordinate().getLongitude());
         preparedStatement.setDouble(column + 2, geoCoordinateWithRadius.getRadius());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
