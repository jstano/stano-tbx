package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.IntegerCSVListUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class IntegerCSVListUserType extends AbstractDelegatedUserType {

   public IntegerCSVListUserType() {

      super(new IntegerCSVListUserTypeDelegate());
   }
}
