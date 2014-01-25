package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.LocaleUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class LocaleUserType extends AbstractDelegatedUserType {

   public LocaleUserType() {

      super(new LocaleUserTypeDelegate());
   }
}
