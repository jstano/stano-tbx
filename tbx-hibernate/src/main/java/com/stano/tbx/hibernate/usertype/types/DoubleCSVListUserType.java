package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.DoubleCSVListUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class DoubleCSVListUserType extends AbstractDelegatedUserType {

   public DoubleCSVListUserType() {

      super(new DoubleCSVListUserTypeDelegate());
   }
}
