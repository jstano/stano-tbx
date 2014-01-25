package com.stano.tbx.hibernate.usertype.types;

import com.stano.tbx.hibernate.usertype.delegates.GeoCoordinateWithAccuracyUserTypeDelegate;
import com.stano.tbx.hibernate.usertype.shared.AbstractDelegatedCompositeUserType;

public class GeoCoordinateWithAccuracyUserType extends AbstractDelegatedCompositeUserType {

   public GeoCoordinateWithAccuracyUserType() {

      super(new GeoCoordinateWithAccuracyUserTypeDelegate());
   }
}
