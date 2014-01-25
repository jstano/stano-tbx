package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.JsonObjectUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class JsonObjectUserType extends AbstractDelegatedUserType {

   public JsonObjectUserType() {

      super(new JsonObjectUserTypeDelegate());
   }
}
