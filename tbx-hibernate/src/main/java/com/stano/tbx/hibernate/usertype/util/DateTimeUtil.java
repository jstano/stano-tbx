package com.stano.tbx.hibernate.usertype.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class DateTimeUtil {

   public static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC"); //NON-NLS

   private static final Calendar UTC_CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
   private static final int[] US_DAY_NUMBER_MAP = {2, 3, 4, 5, 6, 7, 1};

   public static LocalDate javaDateToLocalDate(Date date) {

      Instant instant = Instant.ofEpochMilli(date.getTime());

      return LocalDateTime.ofInstant(instant, UTC_ZONE_ID).toLocalDate();
   }

   public static LocalDateTime javaDateToLocalDateTime(Date date) {

      Instant instant = Instant.ofEpochMilli(date.getTime());

      return LocalDateTime.ofInstant(instant, UTC_ZONE_ID);
   }

   public static LocalTime javaDateToLocalTime(Date date) {

      Instant instant = Instant.ofEpochMilli(date.getTime());

      return LocalDateTime.ofInstant(instant, UTC_ZONE_ID).toLocalTime();
   }

   public static LocalDate sqlDateToLocalDate(java.sql.Date date) {

      return javaDateToLocalDate(date);
   }

   public static LocalTime sqlTimeToLocalTime(Time time) {

      return javaDateToLocalTime(time);
   }

   public static LocalDateTime sqlTimestampToLocalDateTime(Timestamp timestamp) {

      return javaDateToLocalDateTime(timestamp);
   }

   public static Date localDateToJavaDate(LocalDate localDate) {

      return new Date(localDate.atStartOfDay(UTC_ZONE_ID).toInstant().toEpochMilli());
   }

   public static java.sql.Date localDateToSqlDate(LocalDate localDate) {

      return new java.sql.Date(localDate.atStartOfDay(UTC_ZONE_ID).toInstant().toEpochMilli());
   }

   public static Time localTimeToSqlTime(LocalTime localTime) {

      return new Time(localTime.atDate(LocalDate.ofEpochDay(0)).toInstant(ZoneOffset.UTC).toEpochMilli());
   }

   public static Timestamp localDateTimeToSqlTimestamp(LocalDateTime localDateTime) {

      return new Timestamp(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
   }

   public static DayOfWeek usDayNumberToDayOfWeek(int usDayNumber) {

      switch (usDayNumber) {
         case 1:
            return DayOfWeek.SUNDAY;

         case 2:
            return DayOfWeek.MONDAY;

         case 3:
            return DayOfWeek.TUESDAY;

         case 4:
            return DayOfWeek.WEDNESDAY;

         case 5:
            return DayOfWeek.THURSDAY;

         case 6:
            return DayOfWeek.FRIDAY;

         case 7:
            return DayOfWeek.SATURDAY;
      }

      throw new IllegalArgumentException(String.format("Invalid day number: %d", usDayNumber));
   }

   public static int dayOfWeekToUsDayNumber(DayOfWeek dayOfWeek) {

      int isoDayNumber = dayOfWeek.getValue();

      return US_DAY_NUMBER_MAP[isoDayNumber - 1];
   }

   public static Calendar getUTCCalender() {

      return UTC_CALENDAR;
   }

   private DateTimeUtil() {

   }
}
