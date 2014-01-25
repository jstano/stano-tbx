package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedCompositeUserType;

public class GeoCoordinateUserType extends AbstractDelegatedCompositeUserType {

   public GeoCoordinateUserType() {

      super(new GeoCoordinateUserTypeDelegate());
   }
}
