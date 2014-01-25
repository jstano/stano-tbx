package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateWithRadiusUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedCompositeUserType;

public class GeoCoordinateWithRadiusUserType extends AbstractDelegatedCompositeUserType {

   public GeoCoordinateWithRadiusUserType() {

      super(new GeoCoordinateWithRadiusUserTypeDelegate());
   }
}
