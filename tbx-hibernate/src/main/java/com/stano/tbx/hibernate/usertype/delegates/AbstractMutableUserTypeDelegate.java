package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.shared.UserTypeDelegate;

public abstract class AbstractMutableUserTypeDelegate implements UserTypeDelegate {

   @Override
   public boolean isMutable() {

      return true;
   }
}
