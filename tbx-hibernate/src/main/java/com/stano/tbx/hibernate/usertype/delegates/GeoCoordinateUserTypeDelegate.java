package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.core.geolocation.GeoCoordinate;
import org.hibernate.HibernateException;
import org.hibernate.type.DoubleType;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class GeoCoordinateUserTypeDelegate extends AbstractImmutableCompositeUserTypeDelegate {

   private static final int LATITUDE_PROPERTY = 0;
   private static final int LONGITUDE_PROPERTY = 1;

   @Override
   public Class returnedClass() {

      return GeoCoordinate.class;
   }

   @Override
   public String[] getPropertyNames() {

      return new String[] {"latitude", "longitude"}; //NON-NLS
   }

   @Override
   public Type[] getPropertyTypes() {

      return new Type[] {DoubleType.INSTANCE, DoubleType.INSTANCE};
   }

   @Override
   public Object getPropertyValue(Object component, int property) throws HibernateException {

      GeoCoordinate geoCoordinate = (GeoCoordinate)component;

      if (property == LATITUDE_PROPERTY) {
         return geoCoordinate.getLatitude();
      }

      if (property == LONGITUDE_PROPERTY) {
         return geoCoordinate.getLongitude();
      }

      return null;
   }

   @Override
   public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

      throw new UnsupportedOperationException("setPropertyValue is not supported for GeoCoordinate types");
   }

   @Override
   public Object doGet(ResultSet resultSet,
                       String[] names) throws HibernateException, SQLException {

      Object latitude = resultSet.getObject(names[0]);
      Object longitude = resultSet.getObject(names[1]);

      if (latitude != null && longitude != null) {
         return GeoCoordinate.of((Double)latitude, (Double)longitude);
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
      }
      else if (value instanceof GeoCoordinate) {
         GeoCoordinate geoCoordinate = (GeoCoordinate)value;

         preparedStatement.setDouble(column, geoCoordinate.getLatitude());
         preparedStatement.setDouble(column + 1, geoCoordinate.getLongitude());
      }
      else {
         throw new IllegalArgumentException(String.format("The type (%s) of the value is not supported.", value.getClass())); //NON-NLS
      }
   }
}
