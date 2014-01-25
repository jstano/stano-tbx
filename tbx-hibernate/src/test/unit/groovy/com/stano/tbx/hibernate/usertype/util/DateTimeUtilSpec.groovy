package com.stano.tbx.hibernate.usertype.util

import spock.lang.Specification

import java.sql.Time
import java.sql.Timestamp
import java.time.*

public class DateTimeUtilSpec extends Specification {

   void testJavaDateToLocalDate() {

      def localDate = DateTimeUtil.javaDateToLocalDate(javaDate)

      expect:
      localDate.year == year
      localDate.month == month
      localDate.dayOfMonth == dayOfMonth

      where:
      javaDate                      | year | month       | dayOfMonth
      date(2013, 3, 17, 0, 0, 0)    | 2013 | Month.MARCH | 17
      date(2013, 3, 17, 8, 0, 0)    | 2013 | Month.MARCH | 17
      date(2013, 3, 17, 16, 47, 0)  | 2013 | Month.MARCH | 17
      date(2013, 3, 17, 18, 30, 45) | 2013 | Month.MARCH | 17
   }

   void testJavaDateToLocalDateTime() {

      def localDateTime = DateTimeUtil.javaDateToLocalDateTime(javaDate)

      expect:
      localDateTime.year == year
      localDateTime.month == month
      localDateTime.dayOfMonth == dayOfMonth
      localDateTime.hour == hour
      localDateTime.minute == minute
      localDateTime.second == second

      where:
      javaDate                      | year | month       | dayOfMonth | hour | minute | second
      date(2013, 3, 17, 0, 0, 0)    | 2013 | Month.MARCH | 17         | 0    | 0      | 0
      date(2013, 3, 17, 8, 0, 0)    | 2013 | Month.MARCH | 17         | 8    | 0      | 0
      date(2013, 3, 17, 16, 47, 0)  | 2013 | Month.MARCH | 17         | 16   | 47     | 0
      date(2013, 3, 17, 18, 30, 45) | 2013 | Month.MARCH | 17         | 18   | 30     | 45
   }

   void testJavaDateToLocalTime() {

      def localTime = DateTimeUtil.javaDateToLocalTime(javaDate)

      expect:
      localTime.hour == hour
      localTime.minute == minute
      localTime.second == second

      where:
      javaDate                      | hour | minute | second
      date(2013, 3, 17, 0, 0, 0)    | 0    | 0      | 0
      date(2013, 3, 17, 8, 0, 0)    | 8    | 0      | 0
      date(2013, 3, 17, 16, 47, 0)  | 16   | 47     | 0
      date(2013, 3, 17, 18, 30, 45) | 18   | 30     | 45
   }

   void testSqlDateToLocalDate() {

      def localDate = DateTimeUtil.sqlDateToLocalDate(sqlDate)

      expect:
      localDate.year == year
      localDate.month == month
      localDate.dayOfMonth == dayOfMonth

      where:
      sqlDate               | year | month           | dayOfMonth
      sqlDate(2013, 1, 17)  | 2013 | Month.JANUARY   | 17
      sqlDate(2013, 2, 17)  | 2013 | Month.FEBRUARY  | 17
      sqlDate(2013, 3, 17)  | 2013 | Month.MARCH     | 17
      sqlDate(2013, 4, 17)  | 2013 | Month.APRIL     | 17
      sqlDate(2013, 5, 17)  | 2013 | Month.MAY       | 17
      sqlDate(2013, 6, 17)  | 2013 | Month.JUNE      | 17
      sqlDate(2013, 7, 17)  | 2013 | Month.JULY      | 17
      sqlDate(2013, 8, 17)  | 2013 | Month.AUGUST    | 17
      sqlDate(2013, 9, 17)  | 2013 | Month.SEPTEMBER | 17
      sqlDate(2013, 10, 17) | 2013 | Month.OCTOBER   | 17
      sqlDate(2013, 11, 17) | 2013 | Month.NOVEMBER  | 17
      sqlDate(2013, 12, 17) | 2013 | Month.DECEMBER  | 17
   }

   void testSqlTimeToLocalTime() {

      def localTime = DateTimeUtil.sqlTimeToLocalTime(sqlTime)

      expect:
      localTime.hour == hour
      localTime.minute == minute
      localTime.second == second

      where:
      sqlTime             | hour | minute | second
      sqlTime(0, 0, 0)    | 0    | 0      | 0
      sqlTime(8, 0, 0)    | 8    | 0      | 0
      sqlTime(16, 47, 0)  | 16   | 47     | 0
      sqlTime(18, 30, 45) | 18   | 30     | 45
   }

   void testSqlTimestampToLocalDateTime() {

      def localDateTime = DateTimeUtil.sqlTimestampToLocalDateTime(sqlTimestamp)

      expect:
      localDateTime.year == year
      localDateTime.month == month
      localDateTime.dayOfMonth == dayOfMonth
      localDateTime.hour == hour
      localDateTime.minute == minute
      localDateTime.second == second

      where:
      sqlTimestamp                          | year | month       | dayOfMonth | hour | minute | second
      sqlTimestamp(2013, 3, 17, 0, 0, 0)    | 2013 | Month.MARCH | 17         | 0    | 0      | 0
      sqlTimestamp(2013, 3, 17, 8, 0, 0)    | 2013 | Month.MARCH | 17         | 8    | 0      | 0
      sqlTimestamp(2013, 3, 17, 16, 47, 0)  | 2013 | Month.MARCH | 17         | 16   | 47     | 0
      sqlTimestamp(2013, 3, 17, 18, 30, 45) | 2013 | Month.MARCH | 17         | 18   | 30     | 45
   }

   void testLocalDateToJavaDate() {

      def javaDate = DateTimeUtil.localDateToJavaDate(localDate)

      expect:
      javaDate.getTime() == millis

      where:
      localDate                            | millis
      LocalDate.of(1970, Month.JANUARY, 1) | 0
      LocalDate.of(2013, Month.MARCH, 17)  | 1_363_478_400_000
   }

   void testLocalDateToSqlDate() {

      def sqlDate = DateTimeUtil.localDateToSqlDate(localDate)

      expect:
      sqlDate.getTime() == millis

      where:
      localDate                            | millis
      LocalDate.of(1970, Month.JANUARY, 1) | 0
      LocalDate.of(2013, Month.MARCH, 17)  | 1_363_478_400_000
      LocalDate.of(2014, Month.JANUARY, 1) | 1_388_534_400_000
   }

   void testLocalTimeToSqlTime() {

      def sqlTime = DateTimeUtil.localTimeToSqlTime(localTime)

      expect:
      sqlTime.getTime() == millis

      where:
      localTime                | millis
      LocalTime.of(0, 0, 0)    | 0
      LocalTime.of(8, 0, 0)    | 28_800_000
      LocalTime.of(16, 47, 0)  | 60_420_000
      LocalTime.of(18, 30, 45) | 66_645_000
   }

   void testLocalDateTimeToSqlTimestamp() {

      def sqlTimestamp = DateTimeUtil.localDateTimeToSqlTimestamp(localDateTime)

      expect:
      sqlTimestamp.getTime() == millis

      where:
      localDateTime                                       | millis
      LocalDateTime.of(2013, Month.MARCH, 17, 0, 0, 0)    | 1_363_478_400_000
      LocalDateTime.of(2013, Month.MARCH, 17, 8, 0, 0)    | 1_363_507_200_000
      LocalDateTime.of(2013, Month.MARCH, 17, 16, 47, 0)  | 1_363_538_820_000
      LocalDateTime.of(2013, Month.MARCH, 17, 18, 30, 45) | 1_363_545_045_000
   }

   void testUsDayNumberToDayOfWeek() {

      expect:
      DateTimeUtil.usDayNumberToDayOfWeek(1) == DayOfWeek.SUNDAY
      DateTimeUtil.usDayNumberToDayOfWeek(2) == DayOfWeek.MONDAY
      DateTimeUtil.usDayNumberToDayOfWeek(3) == DayOfWeek.TUESDAY
      DateTimeUtil.usDayNumberToDayOfWeek(4) == DayOfWeek.WEDNESDAY
      DateTimeUtil.usDayNumberToDayOfWeek(5) == DayOfWeek.THURSDAY
      DateTimeUtil.usDayNumberToDayOfWeek(6) == DayOfWeek.FRIDAY
      DateTimeUtil.usDayNumberToDayOfWeek(7) == DayOfWeek.SATURDAY
   }

   void testUsDayNumberToDayOfWeekInvalidNumber() {

      when:
      DateTimeUtil.usDayNumberToDayOfWeek(8)

      then:
      thrown IllegalArgumentException
   }

   void testDayOfWeekToUsDayNumber() {

      expect:
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.SUNDAY) == 1
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.MONDAY) == 2
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.TUESDAY) == 3
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.WEDNESDAY) == 4
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.THURSDAY) == 5
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.FRIDAY) == 6
      DateTimeUtil.dayOfWeekToUsDayNumber(DayOfWeek.SATURDAY) == 7
   }

   void testGetUTCCalender() {

      expect:
      DateTimeUtil.getUTCCalender().getTimeZone() == TimeZone.getTimeZone("UTC")
   }

   void testPrivateConstructorSoCoverageIs100Percent() {

      expect:
      new DateTimeUtil() != null
   }

   private static Date date(int year, int month, int day, int hour, int minute, int second) {

      def calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
      calendar.set(year, month - 1, day, hour, minute, second)

      return calendar.getTime()
   }

   private static java.sql.Date sqlDate(int year, int month, int day) {

      return new java.sql.Date(date(year, month, day, 0, 0, 0).getTime())
   }

   private static Time sqlTime(int hour, int minute, int second) {

      return new Time(date(1970, 0, 1, hour, minute, second).getTime())
   }

   private static Timestamp sqlTimestamp(int year, int month, int day, int hour, int minute, int second) {

      return new Timestamp(date(year, month, day, hour, minute, second).getTime())
   }
}
