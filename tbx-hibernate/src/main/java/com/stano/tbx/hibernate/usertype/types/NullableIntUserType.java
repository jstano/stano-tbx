package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.NullableIntUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class NullableIntUserType extends AbstractDelegatedUserType {

   public NullableIntUserType() {

      super(new NullableIntUserTypeDelegate());
   }
}
