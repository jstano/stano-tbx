package com.stano.tbx.hibernate.usertype.shared;

import org.hibernate.HibernateException;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CompositeUserTypeDelegate {

   Class returnedClass();

   boolean isMutable();

   String[] getPropertyNames();

   Type[] getPropertyTypes();

   Object getPropertyValue(Object component, int property) throws HibernateException;

   void setPropertyValue(Object component, int property, Object value) throws HibernateException;

   Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException;

   void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException;
}
