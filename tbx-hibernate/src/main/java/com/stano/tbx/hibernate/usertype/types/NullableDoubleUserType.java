package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.NullableDoubleUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class NullableDoubleUserType extends AbstractDelegatedUserType {

   public NullableDoubleUserType() {

      super(new NullableDoubleUserTypeDelegate());
   }
}
