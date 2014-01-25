package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.EncryptedStringUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class EncryptedStringUserType extends AbstractDelegatedUserType {

   public EncryptedStringUserType() {

      super(new EncryptedStringUserTypeDelegate());
   }
}
