package com.stano.tbx.hibernate.usertype.delegates;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractCSVListUserTypeDelegate<T> extends AbstractMutableUserTypeDelegate {

   protected static final String MAGIC_NULL = "_";

   private static final String LIST_SEPARATOR = ",";

   @Override
   public int[] sqlTypes() {

      return new int[] {Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {

      return List.class;
   }

   @Override
   public Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {

      String value = resultSet.getString(names[0]);

      if (value != null) {
         String[] parsedValues = value.split(LIST_SEPARATOR);

         List<T> list = new ArrayList<>();

         for (String text : parsedValues) {

            if (StringUtils.isEmpty(text) || text.equalsIgnoreCase(MAGIC_NULL)) {
               list.add(null);
            }
            else {
               list.add(parseValue(text));
            }
         }

         return list;
      }

      return new ArrayList<T>();
   }

   @Override
   public void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {

      if (value == null) {
         preparedStatement.setNull(index, Types.VARCHAR);
         return;
      }

      if (!(value instanceof List)) {
         throw new IllegalArgumentException("The object is not a list.");
      }

      @SuppressWarnings("unchecked")
      List<T> listOfValues = (List<T>)value;
      List<String> listOfStrings = listOfValues.stream().map(this::convertValueToString).collect(Collectors.toList());

      preparedStatement.setString(index, StringUtils.join(listOfStrings, ","));
   }

   protected abstract T parseValue(String text);

   protected String convertValueToString(T value) {

      if (value != null) {
         return value.toString();
      }

      return MAGIC_NULL;
   }

   protected String[] splitText(String text, String separator) {

      String[] parts = text.split(separator);

      if (parts.length == 1) {
         throw new IllegalArgumentException(String.format("The string '%s' is missing the separator", text)); //NON-NLS
      }

      parts[0] = parts[0].trim();
      parts[1] = parts[1].trim();

      if (parts[0].isEmpty()) {
         throw new IllegalArgumentException(String.format("The string '%s' is missing the left value", text)); //NON-NLS
      }

      if (parts[1].isEmpty()) {
         throw new IllegalArgumentException(String.format("The string '%s' is missing the right value", text)); //NON-NLS
      }

      return parts;
   }
}
