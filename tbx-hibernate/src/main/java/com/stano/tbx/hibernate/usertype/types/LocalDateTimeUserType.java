package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.LocalDateTimeUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class LocalDateTimeUserType extends AbstractDelegatedUserType {

   public LocalDateTimeUserType() {

      super(new LocalDateTimeUserTypeDelegate());
   }
}
