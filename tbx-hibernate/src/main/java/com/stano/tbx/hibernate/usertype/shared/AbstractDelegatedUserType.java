package com.stano.tbx.hibernate.usertype.shared;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDelegatedUserType implements UserType {

   private final UserTypeDelegate userTypeDelegate;

   protected AbstractDelegatedUserType(UserTypeDelegate userTypeDelegate) {

      this.userTypeDelegate = userTypeDelegate;
   }

   public UserTypeDelegate getUserTypeDelegate() {

      return userTypeDelegate;
   }

   @Override
   public int[] sqlTypes() {

      return userTypeDelegate.sqlTypes();
   }

   @Override
   public Class returnedClass() {

      return userTypeDelegate.returnedClass();
   }

   @Override
   public boolean equals(Object x, Object y) throws HibernateException {

      return x == y || !(x == null || y == null) && x.equals(y);
   }

   @Override
   public int hashCode(Object o) throws HibernateException {

      return o.hashCode();
   }

   @Override
   public Object deepCopy(Object o) throws HibernateException {

      return o;
   }

   @Override
   public boolean isMutable() {

      return userTypeDelegate.isMutable();
   }

   @Override
   public Serializable disassemble(Object object) throws HibernateException {

      return (Serializable)object;
   }

   @Override
   public Object assemble(Serializable cached, Object object) throws HibernateException {

      return cached;
   }

   @Override
   public Object replace(Object original, Object target, Object owner) throws HibernateException {

      return original;
   }

   @Override
   public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

      return userTypeDelegate.doGet(resultSet, names);
   }

   @Override
   public void nullSafeSet(PreparedStatement preparedStatement,
                           Object value,
                           int index,
                           SessionImplementor session) throws HibernateException, SQLException {

      userTypeDelegate.doSet(preparedStatement, value, index);
   }
}
