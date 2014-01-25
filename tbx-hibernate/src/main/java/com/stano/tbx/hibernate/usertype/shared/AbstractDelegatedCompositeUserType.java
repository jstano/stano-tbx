package com.stano.tbx.hibernate.usertype.shared;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDelegatedCompositeUserType implements CompositeUserType {

   private final CompositeUserTypeDelegate compositeUserTypeDelegate;

   protected AbstractDelegatedCompositeUserType(CompositeUserTypeDelegate compositeUserTypeDelegate) {

      this.compositeUserTypeDelegate = compositeUserTypeDelegate;
   }

   public CompositeUserTypeDelegate getCompositeUserTypeDelegate() {

      return compositeUserTypeDelegate;
   }

   @Override
   public Class returnedClass() {

      return compositeUserTypeDelegate.returnedClass();
   }

   @Override
   public boolean equals(Object value1, Object value2) throws HibernateException {

      return value1 == value2 || !(value1 == null || value2 == null) && value1.equals(value2);
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

      return compositeUserTypeDelegate.isMutable();
   }

   @Override
   public Serializable disassemble(Object value, SessionImplementor sessionImplementor) throws HibernateException {

      return (Serializable)value;
   }

   @Override
   public Object assemble(Serializable cached, SessionImplementor sessionImplementor, Object owner) throws HibernateException {

      return cached;
   }

   @Override
   public Object replace(Object original, Object target, SessionImplementor sessionImplementor, Object owner) throws HibernateException {

      return original;
   }

   @Override
   public String[] getPropertyNames() {

      return compositeUserTypeDelegate.getPropertyNames();
   }

   @Override
   public Type[] getPropertyTypes() {

      return compositeUserTypeDelegate.getPropertyTypes();
   }

   @Override
   public Object getPropertyValue(Object component, int property) throws HibernateException {

      return compositeUserTypeDelegate.getPropertyValue(component, property);
   }

   @Override
   public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

      compositeUserTypeDelegate.setPropertyValue(component, property, value);
   }

   @Override
   public Object nullSafeGet(ResultSet resultSet,
                             String[] names,
                             SessionImplementor sessionImplementor,
                             Object paramObject) throws HibernateException, SQLException {

      return compositeUserTypeDelegate.doGet(resultSet, names);
   }

   @Override
   public void nullSafeSet(PreparedStatement preparedStatement,
                           Object value,
                           int column,
                           SessionImplementor sessionImplementor) throws HibernateException, SQLException {

      compositeUserTypeDelegate.doSet(preparedStatement, value, column);
   }
}
