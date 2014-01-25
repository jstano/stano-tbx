package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.TeaEncryptedStringUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedUserType;

public class TeaEncryptedStringUserType extends AbstractDelegatedUserType {

   public TeaEncryptedStringUserType() {

      super(new TeaEncryptedStringUserTypeDelegate());
   }
}
