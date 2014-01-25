package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.ZoneIdUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class ZoneIdUserType extends AbstractDelegatedUserType {

   public ZoneIdUserType() {

      super(new ZoneIdUserTypeDelegate());
   }
}
