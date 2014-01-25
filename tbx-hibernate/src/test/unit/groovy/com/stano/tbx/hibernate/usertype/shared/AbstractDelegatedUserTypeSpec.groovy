package com.stano.tbx.hibernate.usertype.shared

import org.hibernate.engine.spi.SessionImplementor
import spock.lang.Specification

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

public class AbstractDelegatedUserTypeSpec extends Specification {

   void testGeneralStuff() {

      def userTypeDelegate = Mock(UserTypeDelegate)
      def testableUserType = new TestableUserType(userTypeDelegate)

      def sqlTypes = [Types.VARCHAR] as int[]
      userTypeDelegate.sqlTypes() >> sqlTypes
      userTypeDelegate.returnedClass() >> String

      expect:
      testableUserType.userTypeDelegate == userTypeDelegate
      testableUserType.sqlTypes() == sqlTypes
      testableUserType.returnedClass() == String
   }

   void testEquals() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))

      def valueABC1 = "abc"
      def valueABC2 = "abc"
      def valueXYZ = "xyz"

      expect:
      testableUserType.equals(valueABC1, valueABC1)
      testableUserType.equals(valueABC1, valueABC2)

      !testableUserType.equals(valueABC1, valueXYZ)
      !testableUserType.equals(valueABC1, null)
      !testableUserType.equals(null, valueXYZ)
   }

   void testHashCode() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))

      expect:
      testableUserType.hashCode("abc") == "abc".hashCode()
   }

   void testDeepCopy() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))

      def object = "abc"

      expect:
      testableUserType.deepCopy(object) is object
   }

   void testIsMutable() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))

      expect:
      !testableUserType.isMutable()
   }

   void testDisassemble() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))
      def object = "abc"

      expect:
      testableUserType.disassemble(object) is object
   }

   void testAssemble() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))
      def cached = "abc"
      def object = "abc"

      expect:
      testableUserType.assemble(cached, object) is cached
   }

   void testReplace() {

      def testableUserType = new TestableUserType(Mock(UserTypeDelegate))
      def original = "abc"
      def target = "target"
      def owner = "owner"

      expect:
      testableUserType.replace(original, target, owner) is original
   }

   void testNullSafeGet() {

      def userTypeDelegate = Mock(UserTypeDelegate)
      def resultSet = Mock(ResultSet)
      def session = Mock(SessionImplementor)
      def names = ["columnOne"] as String[]
      def owner = "123"
      def resultValue = "ABC"
      def testableUserType = new TestableUserType(userTypeDelegate)

      userTypeDelegate.doGet(resultSet, names) >> resultValue

      when:
      def result = testableUserType.nullSafeGet(resultSet, names, session, owner)

      then:
      result == resultValue
   }

   void testNullSafeSet() {

      def userTypeDelegate = Mock(UserTypeDelegate)
      def preparedStatement = Mock(PreparedStatement)
      def session = Mock(SessionImplementor)
      def value = "ABC"
      def index = 17
      def testableUserType = new TestableUserType(userTypeDelegate)

      userTypeDelegate.doSet(preparedStatement, value, index)

      when:
      testableUserType.nullSafeSet(preparedStatement, value, index, session)

      then:
      1 * userTypeDelegate.doSet(preparedStatement, value, index)
      0 * userTypeDelegate._
   }
}

class TestableUserType extends AbstractDelegatedUserType {

   public TestableUserType(UserTypeDelegate userTypeDelegate) {
      super(userTypeDelegate)
   }
}
