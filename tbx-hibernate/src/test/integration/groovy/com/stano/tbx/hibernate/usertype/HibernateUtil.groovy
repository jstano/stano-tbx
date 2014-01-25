package com.stano.tbx.hibernate.usertype

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

public class HibernateUtil {

   private static final SessionFactory sessionFactory

   static {
      sessionFactory = new Configuration().configure("com/unifocus/tbx/hibernate/usertype/hibernate.cfg.xml").buildSessionFactory()
   }

   public static Session getSession() {

      return sessionFactory.openSession();
   }
}
