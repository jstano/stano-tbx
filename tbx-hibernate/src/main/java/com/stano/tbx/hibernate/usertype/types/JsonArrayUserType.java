package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.JsonArrayUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class JsonArrayUserType extends AbstractDelegatedUserType {

   public JsonArrayUserType() {

      super(new JsonArrayUserTypeDelegate());
   }
}
