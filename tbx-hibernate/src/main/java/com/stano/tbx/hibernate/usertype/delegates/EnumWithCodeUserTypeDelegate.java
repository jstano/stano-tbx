package com.stano.tbx.hibernate.usertype.delegates;

import org.hibernate.HibernateException;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class EnumWithCodeUserTypeDelegate<T extends Enum> extends AbstractImmutableUserTypeDelegate {

   private Class<T> enumClass;
   private Method fromCodeMethod;
   private Method getCodeMethod;

   public Class<T> getEnumClass() {

      return enumClass;
   }

   public void setEnumClass(Class<T> enumClass) {

      this.enumClass = enumClass;
   }

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class<T> returnedClass() {

      return enumClass;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String text = resultSet.getString(names[0]);

      if (text == null) {
         return null;
      }

      return stringToEnum(text);
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
      }
      else {
         preparedStatement.setString(index, enumToString((Enum)value));
      }
   }

   private String enumToString(Enum value) {

      if (getCodeMethod == null) {
         try {
            getCodeMethod = enumClass.getMethod("getCode");
         }
         catch (NoSuchMethodException ignored) {
         }
      }

      if (getCodeMethod != null) {
         try {
            return (String)getCodeMethod.invoke(value);
         }
         catch (Exception x) {
            throw new HibernateException(x);
         }
      }

      return value.name();
   }

   private Enum stringToEnum(String str) {

      if (fromCodeMethod == null) {
         try {
            fromCodeMethod = enumClass.getMethod("fromCode", String.class);
         }
         catch (NoSuchMethodException ignored) {
         }
      }

      if (fromCodeMethod != null) {
         try {
            return (Enum)fromCodeMethod.invoke(null, str);
         }
         catch (Exception x) {
            throw new HibernateException(x);
         }
      }

      return Enum.valueOf(enumClass, str);
   }
}
