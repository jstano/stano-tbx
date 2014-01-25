package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.LocalTimeUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class LocalTimeUserType extends AbstractDelegatedUserType {

   public LocalTimeUserType() {

      super(new LocalTimeUserTypeDelegate());
   }
}
