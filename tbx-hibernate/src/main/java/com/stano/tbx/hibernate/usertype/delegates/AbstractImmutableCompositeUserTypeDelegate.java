package com.stano.tbx.hibernate.usertype.delegates;

import com.stano.tbx.hibernate.usertype.shared.CompositeUserTypeDelegate;

public abstract class AbstractImmutableCompositeUserTypeDelegate implements CompositeUserTypeDelegate {

   @Override
   public boolean isMutable() {

      return false;
   }
}
