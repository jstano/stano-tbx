package com.stano.tbx.hibernate.usertype

import org.hibernate.annotations.Type
import org.json.JSONArray
import org.json.JSONObject

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import java.time.*

@Entity
public class TestEntity {

   @Id
   @GeneratedValue
   Long id

   LocalDate localDate
   LocalDateTime localDateTime
   LocalTime localTime
   Locale locale
   ZoneId zoneId
   DayOfWeek dayOfWeek

   @Type(type = "EncryptedString")
   String encryptedText

   @Type(type = "TeaEncryptedString")
   String teaEncryptedText

   JSONArray jsonArray
   JSONObject jsonObject

   @Type(type = "NullableDouble")
   @Column(nullable = true)
   double nullableDouble

   @Type(type = "NullableInt")
   @Column(nullable = true)
   int nullableInt

   @Type(type = "DoubleCsvList")
   List<Double> doubleList

   @Type(type = "IntegerCsvList")
   List<Integer> integerList

   boolean flag
}
