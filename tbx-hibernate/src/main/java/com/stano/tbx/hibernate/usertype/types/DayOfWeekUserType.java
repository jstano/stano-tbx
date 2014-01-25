package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.DayOfWeekUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class DayOfWeekUserType extends AbstractDelegatedUserType {

   public DayOfWeekUserType() {

      super(new DayOfWeekUserTypeDelegate());
   }
}
