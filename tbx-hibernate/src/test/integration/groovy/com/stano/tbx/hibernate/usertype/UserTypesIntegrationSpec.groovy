package com.stano.tbx.hibernate.usertype

import org.json.JSONArray
import org.json.JSONObject
import spock.lang.Specification

import java.time.*

public class UserTypesIntegrationSpec
   extends Specification {

   void testUserTypes() {

      def session = HibernateUtil.session

      def jsonArray = new JSONArray([1, 2, 3])
      def jsonObject = new JSONObject([name: "Bill Smith", age: 37])

      def testEntity = new TestEntity(localDate: LocalDate.of(2014, 1, 1),
                                      localDateTime: LocalDateTime.of(2014, 1, 1, 8, 30, 45),
                                      localTime: LocalTime.of(8, 30, 45),
                                      locale: Locale.US,
                                      zoneId: ZoneId.of("UTC"),
                                      dayOfWeek: DayOfWeek.WEDNESDAY,
                                      encryptedText: "ABC123",
                                      teaEncryptedText: "XYZ987",
                                      jsonArray: jsonArray,
                                      jsonObject: jsonObject,
                                      nullableDouble: 0.0,
                                      nullableInt: 0,
                                      doubleList: [1.5, 2.5, 3.5],
                                      integerList: [4, 5, 6],
                                      flag: true)

      session.save(testEntity)
      session.clear()

      when:
      TestEntity result = (TestEntity)session.get(TestEntity, testEntity.id)

      then:
      result.id == testEntity.id
      result.localDate == testEntity.localDate
      result.localDateTime == testEntity.localDateTime
      result.localTime == testEntity.localTime
      result.locale == Locale.US
      result.zoneId == ZoneId.of("UTC")
      result.dayOfWeek == DayOfWeek.WEDNESDAY
      result.encryptedText == "ABC123"
      result.teaEncryptedText == "XYZ987"

      result.jsonArray.length() == jsonArray.length()
      result.jsonArray.get(0) == jsonArray.get(0)
      result.jsonArray.get(1) == jsonArray.get(1)
      result.jsonArray.get(2) == jsonArray.get(2)

      result.jsonObject.get("name") == "Bill Smith"
      result.jsonObject.get("age") == 37

      result.nullableDouble == 0.0
      result.nullableInt == 0

      result.doubleList == [1.5, 2.5, 3.5]
      result.integerList == [4, 5, 6]
   }
}
