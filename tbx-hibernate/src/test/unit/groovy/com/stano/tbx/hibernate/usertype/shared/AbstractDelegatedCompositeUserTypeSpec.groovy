package com.stano.tbx.hibernate.usertype.shared

import org.hibernate.engine.spi.SessionImplementor
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet

public class AbstractDelegatedCompositeUserTypeSpec
   extends Specification {

   void testGeneralStuff() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)
      def propertyNames = []
      def propertyTypes = []

      userTypeDelegate.returnedClass() >> String
      userTypeDelegate.getPropertyNames() >> propertyNames
      userTypeDelegate.getPropertyTypes() >> propertyTypes

      expect:
      testableUserType.compositeUserTypeDelegate == userTypeDelegate
      testableUserType.returnedClass() == String
      testableUserType.getPropertyNames() == propertyNames
      testableUserType.getPropertyTypes() == propertyTypes
   }

   void testIsMutable() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)

      userTypeDelegate.isMutable() >> mutable

      expect:
      testableUserType.isMutable() == mutableResult

      where:
      mutable | mutableResult
      false   | false
      true    | true
   }

   void testGetPropertyValue() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)
      def component = "ABC"
      def property = 0
      def result = "result"

      userTypeDelegate.getPropertyValue(component, property) >> result

      expect:
      testableUserType.getPropertyValue("ABC", 0) == result
   }

   void testSetPropertyValue() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)
      def component = "ABC"
      def property = 0
      def value = "value"

      when:
      testableUserType.setPropertyValue(component, property, value)

      then:
      1 * userTypeDelegate.setPropertyValue(component, property, value)
   }

   void testEquals() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))

      def valueABC1 = "abc"
      def valueABC2 = "abc"
      def valueXYZ = "xyz"

      expect:
      testableCompositeUserType.equals(valueABC1, valueABC1)
      testableCompositeUserType.equals(valueABC1, valueABC2)

      !testableCompositeUserType.equals(valueABC1, valueXYZ)
      !testableCompositeUserType.equals(valueABC1, null)
      !testableCompositeUserType.equals(null, valueXYZ)
   }

   void testHashCode() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))

      expect:
      testableCompositeUserType.hashCode("abc") == "abc".hashCode()
   }

   void testDeepCopy() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))

      def object = "abc"

      expect:
      testableCompositeUserType.deepCopy(object) is object
   }

   void testDisassemble() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))
      def object = "abc"

      expect:
      testableCompositeUserType.disassemble(object, Mock(SessionImplementor)) is object
   }

   void testAssemble() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))
      def cached = "abc"
      def owner = "owner"

      expect:
      testableCompositeUserType.assemble(cached, Mock(SessionImplementor), owner) is cached
   }

   void testReplace() {

      def testableCompositeUserType = new TestableCompositeUserType(Mock(CompositeUserTypeDelegate))
      def original = "abc"
      def target = "target"
      def owner = "owner"

      expect:
      testableCompositeUserType.replace(original, target, Mock(SessionImplementor), owner) is original
   }

   void testNullSafeGet() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def resultSet = Mock(ResultSet)
      def sessionImplementor = Mock(SessionImplementor)
      def names = ["columnOne"] as String[]
      def owner = "123"
      def resultValue = "ABC"
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)

      userTypeDelegate.doGet(resultSet, names) >> resultValue

      when:
      def result = testableUserType.nullSafeGet(resultSet, names, sessionImplementor, owner)

      then:
      result == resultValue
   }

   void testNullSafeSet() {

      def userTypeDelegate = Mock(CompositeUserTypeDelegate)
      def preparedStatement = Mock(PreparedStatement)
      def sessionImplementor = Mock(SessionImplementor)
      def value = "ABC"
      def index = 17
      def testableUserType = new TestableCompositeUserType(userTypeDelegate)

      userTypeDelegate.doSet(preparedStatement, value, index)

      when:
      testableUserType.nullSafeSet(preparedStatement, value, index, sessionImplementor)

      then:
      1 * userTypeDelegate.doSet(preparedStatement, value, index)
      0 * userTypeDelegate._
   }
}

class TestableCompositeUserType extends AbstractDelegatedCompositeUserType {

   protected TestableCompositeUserType(CompositeUserTypeDelegate compositeUserTypeDelegate) {
      super(compositeUserTypeDelegate)
   }
}
