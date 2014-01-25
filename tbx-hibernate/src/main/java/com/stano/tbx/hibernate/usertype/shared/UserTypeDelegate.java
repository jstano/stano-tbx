package com.stano.tbx.hibernate.usertype.shared;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserTypeDelegate {

   int[] sqlTypes();

   Class returnedClass();

   boolean isMutable();

   Object doGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException;

   void doSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException;
}
