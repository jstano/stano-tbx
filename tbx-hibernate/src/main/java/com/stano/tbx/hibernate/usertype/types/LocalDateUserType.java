package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.LocalDateUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class LocalDateUserType extends AbstractDelegatedUserType {

   public LocalDateUserType() {

      super(new LocalDateUserTypeDelegate());
   }
}
