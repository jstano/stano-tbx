@TypeDefs({
             @TypeDef(name = "EncryptedString",
                      typeClass = EncryptedStringUserType.class),

             @TypeDef(name = "DoubleCsvList",
                      typeClass = DoubleCSVListUserType.class),

             @TypeDef(name = "IntegerCsvList",
                      typeClass = IntegerCSVListUserType.class),

             @TypeDef(name = "JsonArray",
                      defaultForType = JSONArray.class,
                      typeClass = JsonArrayUserType.class),

             @TypeDef(name = "JsonObject",
                      defaultForType = JSONObject.class,
                      typeClass = JsonObjectUserType.class),

             @TypeDef(name = "Locale",
                      defaultForType = Locale.class,
                      typeClass = LocaleUserType.class),

             @TypeDef(name = "LocalDate",
                      defaultForType = LocalDate.class,
                      typeClass = LocalDateUserType.class),

             @TypeDef(name = "LocalDateTime",
                      defaultForType = LocalDateTime.class,
                      typeClass = LocalDateTimeUserType.class),

             @TypeDef(name = "LocalTime",
                      defaultForType = LocalTime.class,
                      typeClass = LocalTimeUserType.class),

             @TypeDef(name = "NullableDouble",
                      typeClass = NullableDoubleUserType.class),

             @TypeDef(name = "NullableInt",
                      typeClass = NullableIntUserType.class),

             @TypeDef(name = "TeaEncryptedString",
                      typeClass = TeaEncryptedStringUserType.class),

             @TypeDef(name = "ZoneId",
                      defaultForType = ZoneId.class,
                      typeClass = ZoneIdUserType.class)
          }) package com.stano.tbx.hibernate.usertype;

import com.stano.tbx.hibernate.usertype.types.DoubleCSVListUserType;
import com.stano.tbx.hibernate.usertype.types.EncryptedStringUserType;
import com.stano.tbx.hibernate.usertype.types.IntegerCSVListUserType;
import com.stano.tbx.hibernate.usertype.types.JsonArrayUserType;
import com.stano.tbx.hibernate.usertype.types.JsonObjectUserType;
import com.stano.tbx.hibernate.usertype.types.LocalDateTimeUserType;
import com.stano.tbx.hibernate.usertype.types.LocalDateUserType;
import com.stano.tbx.hibernate.usertype.types.LocalTimeUserType;
import com.stano.tbx.hibernate.usertype.types.LocaleUserType;
import com.stano.tbx.hibernate.usertype.types.NullableDoubleUserType;
import com.stano.tbx.hibernate.usertype.types.NullableIntUserType;
import com.stano.tbx.hibernate.usertype.types.TeaEncryptedStringUserType;
import com.stano.tbx.hibernate.usertype.types.ZoneIdUserType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
