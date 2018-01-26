package utils

import org.specs2.mutable._
import SemVer._

class SemVerSpec extends Specification {

  // SemVer Docs: https://github.com/npm/node-semver

  "SemVerUtil.convertSemVerToMaven" should {
    "work with static versions" in {
      SemVer.convertSemVerToMaven("1.2.3")       must be equalTo Some("1.2.3")
      SemVer.convertSemVerToMaven("=1.2.3")      must be equalTo Some("1.2.3")
      SemVer.convertSemVerToMaven("1.2.3-alpha") must be equalTo Some("1.2.3-alpha")
      SemVer.convertSemVerToMaven("1.2.3-dev-r") must be equalTo Some("1.2.3-dev-r")
    }
    "work with basic ranges" in {
      SemVer.convertSemVerToMaven(">1")           must be equalTo Some("(1,)")
      SemVer.convertSemVerToMaven("> 1")          must be equalTo Some("(1,)")
      SemVer.convertSemVerToMaven(">1.2")         must be equalTo Some("(1.2,)")
      SemVer.convertSemVerToMaven(">1.2.3")       must be equalTo Some("(1.2.3,)")
      SemVer.convertSemVerToMaven(">1.2.3-alpha") must be equalTo Some("(1.2.3-alpha,)")
      SemVer.convertSemVerToMaven(">1.2.3-dev-r") must be equalTo Some("(1.2.3-dev-r,)")

      SemVer.convertSemVerToMaven(">=1")           must be equalTo Some("[1,)")
      SemVer.convertSemVerToMaven(">= 1")          must be equalTo Some("[1,)")
      SemVer.convertSemVerToMaven(">=1.2")         must be equalTo Some("[1.2,)")
      SemVer.convertSemVerToMaven(">=1.2.3")       must be equalTo Some("[1.2.3,)")
      SemVer.convertSemVerToMaven(">=1.2.3-alpha") must be equalTo Some("[1.2.3-alpha,)")
      SemVer.convertSemVerToMaven(">=1.2.3-dev-r") must be equalTo Some("[1.2.3-dev-r,)")
      SemVer.convertSemVerToMaven(">=1.9.x")       must be equalTo Some("[1.9,)")

      SemVer.convertSemVerToMaven("<1")           must be equalTo Some("(,1)")
      SemVer.convertSemVerToMaven("< 1")          must be equalTo Some("(,1)")
      SemVer.convertSemVerToMaven("<1.2")         must be equalTo Some("(,1.2)")
      SemVer.convertSemVerToMaven("<1.2.3")       must be equalTo Some("(,1.2.3)")
      SemVer.convertSemVerToMaven("<1.2.3-alpha") must be equalTo Some("(,1.2.3-alpha)")
      SemVer.convertSemVerToMaven("<1.2.3-dev-r") must be equalTo Some("(,1.2.3-dev-r)")

      SemVer.convertSemVerToMaven("<=1")           must be equalTo Some("(,1]")
      SemVer.convertSemVerToMaven("<= 1")          must be equalTo Some("(,1]")
      SemVer.convertSemVerToMaven("<=1.2")         must be equalTo Some("(,1.2]")
      SemVer.convertSemVerToMaven("<=1.2.3")       must be equalTo Some("(,1.2.3]")
      SemVer.convertSemVerToMaven("<=1.2.3-alpha") must be equalTo Some("(,1.2.3-alpha]")
      SemVer.convertSemVerToMaven("<=1.2.3-dev-r") must be equalTo Some("(,1.2.3-dev-r]")
    }
    "work with range sets" in {
      SemVer.convertSemVerToMaven(">=1.0.0 <1.4.0")      must be equalTo Some("[1.0.0,1.4.0)")
      SemVer.convertSemVerToMaven(">= 0.10.1 < 0.12.0")  must be equalTo Some("[0.10.1,0.12.0)")
      SemVer.convertSemVerToMaven(">=1.2.x <=1.4.x")     must be equalTo Some("[1.2,1.4]")
      SemVer.convertSemVerToMaven(">=1.2.16 1.4.x")      must be equalTo Some("[1.2.16,),[1.4,1.5)")
      SemVer.convertSemVerToMaven(">=1.2.16 1.4.0")      must be equalTo Some("[1.2.16,),1.4.0")
    }
    "work with hyphen ranges" in {
      SemVer.convertSemVerToMaven("1 - 2")           must be equalTo Some("[1,3)")
      SemVer.convertSemVerToMaven("1 - 2.4")         must be equalTo Some("[1,2.5)")
      SemVer.convertSemVerToMaven("1 - 2.3.4")       must be equalTo Some("[1,2.3.4]")
      SemVer.convertSemVerToMaven("1 - 2.3.4-alpha") must be equalTo Some("[1,2.3.4-alpha]")
      SemVer.convertSemVerToMaven("1 - 2.3.4-dev-r") must be equalTo Some("[1,2.3.4-dev-r]")

      SemVer.convertSemVerToMaven("1.2 - 2")           must be equalTo Some("[1.2,3)")
      SemVer.convertSemVerToMaven("1.2 - 1.4")         must be equalTo Some("[1.2,1.5)")
      SemVer.convertSemVerToMaven("1.2 - 1.2.3")       must be equalTo Some("[1.2,1.2.3]")
      SemVer.convertSemVerToMaven("1.2 - 1.2.3-alpha") must be equalTo Some("[1.2,1.2.3-alpha]")
      SemVer.convertSemVerToMaven("1.2 - 1.2.3-dev-r") must be equalTo Some("[1.2,1.2.3-dev-r]")

      SemVer.convertSemVerToMaven("1.2.3 - 2")           must be equalTo Some("[1.2.3,3)")
      SemVer.convertSemVerToMaven("1.2.3 - 1.4")         must be equalTo Some("[1.2.3,1.5)")
      SemVer.convertSemVerToMaven("1.2.3 - 1.3.4")       must be equalTo Some("[1.2.3,1.3.4]")
      SemVer.convertSemVerToMaven("1.2.3 - 1.3.4-alpha") must be equalTo Some("[1.2.3,1.3.4-alpha]")
      SemVer.convertSemVerToMaven("1.2.3 - 1.3.4-dev-r") must be equalTo Some("[1.2.3,1.3.4-dev-r]")

      SemVer.convertSemVerToMaven("1.2.3-alpha - 2")           must be equalTo Some("[1.2.3-alpha,3)")
      SemVer.convertSemVerToMaven("1.2.3-dev-r - 2")           must be equalTo Some("[1.2.3-dev-r,3)")
      SemVer.convertSemVerToMaven("1.2.3-alpha - 1.4")         must be equalTo Some("[1.2.3-alpha,1.5)")
      SemVer.convertSemVerToMaven("1.2.3-dev-r - 1.4")         must be equalTo Some("[1.2.3-dev-r,1.5)")
      SemVer.convertSemVerToMaven("1.2.3-alpha - 1.3.4")       must be equalTo Some("[1.2.3-alpha,1.3.4]")
      SemVer.convertSemVerToMaven("1.2.3-dev-r - 1.3.4")       must be equalTo Some("[1.2.3-dev-r,1.3.4]")
      SemVer.convertSemVerToMaven("1.2.3-alpha - 1.3.4-alpha") must be equalTo Some("[1.2.3-alpha,1.3.4-alpha]")
      SemVer.convertSemVerToMaven("1.2.3-dev-r - 1.3.4-dev-r") must be equalTo Some("[1.2.3-dev-r,1.3.4-dev-r]")
    }
    "work with X ranges" in {
      SemVer.convertSemVerToMaven("")      must be equalTo Some("[0,)")
      SemVer.convertSemVerToMaven("*")     must be equalTo Some("[0,)")
      SemVer.convertSemVerToMaven("1")     must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.x")   must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.x.x") must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.X")   must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.X.X") must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.*")   must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1.*.*") must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("1-alpha") must be equalTo Some("[1-alpha,2)")

      SemVer.convertSemVerToMaven("1.2")       must be equalTo Some("[1.2,1.3)")
      SemVer.convertSemVerToMaven("1.2.x")     must be equalTo Some("[1.2,1.3)")
      SemVer.convertSemVerToMaven("1.2.X")     must be equalTo Some("[1.2,1.3)")
      SemVer.convertSemVerToMaven("1.2.*")     must be equalTo Some("[1.2,1.3)")
      SemVer.convertSemVerToMaven("1.2-alpha") must be equalTo Some("[1.2-alpha,1.3)")
    }
    "work with tilde ranges" in {
      SemVer.convertSemVerToMaven("~1")           must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("~1.2")         must be equalTo Some("[1.2,1.3)")
      SemVer.convertSemVerToMaven("~1.2.3")       must be equalTo Some("[1.2.3,1.3)")
      SemVer.convertSemVerToMaven("~1.2.3-alpha") must be equalTo Some("[1.2.3-alpha,1.3)")
      SemVer.convertSemVerToMaven("~1.2.3-dev-r") must be equalTo Some("[1.2.3-dev-r,1.3)")
      SemVer.convertSemVerToMaven("~1.x")         must be equalTo Some("[1,2)")
      SemVer.convertSemVerToMaven("~ 0.1.11")     must be equalTo Some("[0.1.11,0.2)")
    }
    "work with caret ranges" in {
      SemVer.convertSemVerToMaven("^1.2.3")        must be equalTo Some("[1.2.3,2)")
      SemVer.convertSemVerToMaven("^1.2.3-beta.2") must be equalTo Some("[1.2.3-beta.2,2)")

      SemVer.convertSemVerToMaven("^0.2.3")        must be equalTo Some("[0.2.3,0.3)")
      SemVer.convertSemVerToMaven("^0.0.3")        must be equalTo Some("[0.0.3,0.0.4)")
      SemVer.convertSemVerToMaven("^0.0.3-beta")   must be equalTo Some("[0.0.3-beta,0.0.4)")

      SemVer.convertSemVerToMaven("^1.2.x") must be equalTo Some("[1.2.0,2)")
      SemVer.convertSemVerToMaven("^0.0.x") must be equalTo Some("[0.0.0,0.1)")
      SemVer.convertSemVerToMaven("^0.0")   must be equalTo Some("[0.0.0,0.1)")

      SemVer.convertSemVerToMaven("^1.x") must be equalTo Some("[1.0.0,2)")
      SemVer.convertSemVerToMaven("^0.x") must be equalTo Some("[0.0.0,1)")
    }
    "work with crazy stuff" in {
      SemVer.convertSemVerToMaven("latest") must be equalTo Some("[0,)")
      SemVer.convertSemVerToMaven("b4e74e38e43ac53af8acd62c78c9213be0194245") must be equalTo Some("b4e74e38e43ac53af8acd62c78c9213be0194245")
      SemVer.convertSemVerToMaven("2432d39a1693ccd728cbe7eb55810063737d3403") must be equalTo Some("2432d39a1693ccd728cbe7eb55810063737d3403")
    }
    "work with || syntax" in {
      SemVer.convertSemVerToMaven("^1.3.0 || >1.4.0-beta.0")  must be equalTo Some("[1.3.0,2),(1.4.0-beta.0,)")
      SemVer.convertSemVerToMaven("2 || 3 || 4")              must be equalTo Some("[2,3),[3,4),[4,5)")
    }
  }

  "Version" should {
    "parse" in {
      Version("") must beEqualTo(Version())
      Version("1") must beEqualTo(Version(Some(1)))
      Version("1.2") must beEqualTo(Version(Some(1), Some(2)))
      Version("1.2.3") must beEqualTo(Version(Some(1), Some(2), Some(3)))
      Version("1.2.3-alpha") must beEqualTo(Version(Some(1), Some(2), Some(3), Some("alpha")))
      Version("1.2.3-dev-r") must beEqualTo(Version(Some(1), Some(2), Some(3), Some("dev-r")))
      Version("asdf") must beEqualTo(Version(None, None, None, Some("asdf")))
      Version("1asdf") must beEqualTo(Version(None, None, None, Some("1asdf")))
      Version("1-asdf") must beEqualTo(Version(Some(1), None, None, Some("asdf")))
    }
  }

  "VersionOrdering" should {
    "work" in {
      VersionOrdering.compare(Version("1"), Version("1")) must beEqualTo(0)
      VersionOrdering.compare(Version("1"), Version("1.0")) must beEqualTo(0)
      VersionOrdering.compare(Version("1"), Version("1.0.0")) must beEqualTo(0)
      VersionOrdering.compare(Version("1.2"), Version("1.2")) must beEqualTo(0)
      VersionOrdering.compare(Version("1.2"), Version("1.2.0")) must beEqualTo(0)
      VersionOrdering.compare(Version("1.2.3"), Version("1.2.3")) must beEqualTo(0)
      VersionOrdering.compare(Version("1.2.3-alpha"), Version("1.2.3-alpha")) must beEqualTo(0)

      Version("3") must beGreaterThan (Version(""))
      Version("3") must beGreaterThan (Version("2"))
      Version("3") must beGreaterThan (Version("2.0"))
      Version("3") must beGreaterThan (Version("2.0.0"))
      Version("3.2") must beGreaterThan (Version("2"))
      Version("3.2") must beGreaterThan (Version("2.0"))
      Version("3.2") must beGreaterThan (Version("2.0.0"))
      Version("3.2.1") must beGreaterThan (Version("2"))
      Version("3.2.1") must beGreaterThan (Version("2.0"))
      Version("3.2.1") must beGreaterThan (Version("2.0.0"))
      Version("3.2.1-alpha") must beGreaterThan (Version("2"))
      Version("3.2.1-alpha") must beGreaterThan (Version("2.0"))
      Version("3.2.1-alpha") must beGreaterThan (Version("2.0.0"))

      Version("") must beLessThan (Version("3"))
      Version("2") must beLessThan (Version("3"))
      Version("2.0") must beLessThan (Version("3"))
      Version("2.0.0") must beLessThan (Version("3"))
      Version("2") must beLessThan (Version("3.2"))
      Version("2.0") must beLessThan (Version("3.2"))
      Version("2.0.0") must beLessThan (Version("3.2"))
      Version("2") must beLessThan (Version("3.2.1"))
      Version("2.0") must beLessThan (Version("3.2.1"))
      Version("2.0.0") must beLessThan (Version("3.2.1"))
      Version("2") must beLessThan (Version("3.2.1-alpha"))
      Version("2.0") must beLessThan (Version("3.2.1-alpha"))
      Version("2.0.0") must beLessThan (Version("3.2.1-alpha"))

      Version("1.1") must beGreaterThan (Version("1.0"))
      Version("1.1.0") must beGreaterThan (Version("1.0"))
      Version("1.1") must beGreaterThan (Version("1.0.0"))
      Version("1.1.1") must beGreaterThan (Version("1.1.0"))

      Version("1.1.1-alpha") must beLessThan (Version("1.1.1"))
      Version("1.1.1-alpha") must beLessThan (Version("1.1.1-beta"))
    }
  }


  "Comparator.includesVersion" should {
    "work with GT" in {
      val comparatorMajor = Comparator(Operator.GT, Version("0"))
      comparatorMajor.includesVersion(Version("0")) must beFalse
      comparatorMajor.includesVersion(Version("0.1")) must beTrue
      comparatorMajor.includesVersion(Version("1")) must beTrue

      val comparatorMinor = Comparator(Operator.GT, Version("0.0"))
      comparatorMinor.includesVersion(Version("0.0")) must beFalse
      comparatorMinor.includesVersion(Version("0.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.0")) must beTrue
      comparatorMinor.includesVersion(Version("1.1")) must beTrue

      val comparatorPatch = Comparator(Operator.GT, Version("0.0.0"))
      comparatorPatch.includesVersion(Version("0.0.0")) must beFalse
      comparatorPatch.includesVersion(Version("0.0.1")) must beTrue
      comparatorPatch.includesVersion(Version("0.1.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.0.0")) must beTrue
    }
    "work with GTE" in {
      val comparatorMajor = Comparator(Operator.GTE, Version("1"))
      comparatorMajor.includesVersion(Version("0")) must beFalse
      comparatorMajor.includesVersion(Version("1")) must beTrue
      comparatorMajor.includesVersion(Version("2")) must beTrue

      val comparatorMinor = Comparator(Operator.GTE, Version("1.1"))
      comparatorMinor.includesVersion(Version("0.0")) must beFalse
      comparatorMinor.includesVersion(Version("0.1")) must beFalse
      comparatorMinor.includesVersion(Version("1.0")) must beFalse
      comparatorMinor.includesVersion(Version("1.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.2")) must beTrue
      comparatorMinor.includesVersion(Version("2.0")) must beTrue

      val comparatorPatch = Comparator(Operator.GTE, Version("1.1.1"))
      comparatorPatch.includesVersion(Version("0.0.0")) must beFalse
      comparatorPatch.includesVersion(Version("0.0.1")) must beFalse
      comparatorPatch.includesVersion(Version("0.1.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.0.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.1.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.1.1")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.2")) must beTrue
      comparatorPatch.includesVersion(Version("1.2.1")) must beTrue
      comparatorPatch.includesVersion(Version("2.1.1")) must beTrue
    }
    "work with LT" in {
      val comparatorMajor = Comparator(Operator.LT, Version("1"))
      comparatorMajor.includesVersion(Version("0")) must beTrue
      comparatorMajor.includesVersion(Version("0.1")) must beTrue
      comparatorMajor.includesVersion(Version("1")) must beFalse

      val comparatorMinor = Comparator(Operator.LT, Version("1.1"))
      comparatorMinor.includesVersion(Version("0.0")) must beTrue
      comparatorMinor.includesVersion(Version("0.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.0")) must beTrue
      comparatorMinor.includesVersion(Version("1.1")) must beFalse

      val comparatorPatch = Comparator(Operator.LT, Version("1.1.1"))
      comparatorPatch.includesVersion(Version("0.0.0")) must beTrue
      comparatorPatch.includesVersion(Version("0.0.1")) must beTrue
      comparatorPatch.includesVersion(Version("0.1.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.0.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.1")) must beFalse
      comparatorPatch.includesVersion(Version("1.1.2")) must beFalse
      comparatorPatch.includesVersion(Version("1.2.1")) must beFalse
      comparatorPatch.includesVersion(Version("2.1.1")) must beFalse
    }
    "work with LTE" in {
      val comparatorMajor = Comparator(Operator.LTE, Version("1"))
      comparatorMajor.includesVersion(Version("0")) must beTrue
      comparatorMajor.includesVersion(Version("1")) must beTrue
      comparatorMajor.includesVersion(Version("2")) must beFalse

      val comparatorMinor = Comparator(Operator.LTE, Version("1.1"))
      comparatorMinor.includesVersion(Version("0.0")) must beTrue
      comparatorMinor.includesVersion(Version("0.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.0")) must beTrue
      comparatorMinor.includesVersion(Version("1.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.2")) must beFalse
      comparatorMinor.includesVersion(Version("2.0")) must beFalse

      val comparatorPatch = Comparator(Operator.LTE, Version("1.1.1"))
      comparatorPatch.includesVersion(Version("0.0.0")) must beTrue
      comparatorPatch.includesVersion(Version("0.0.1")) must beTrue
      comparatorPatch.includesVersion(Version("0.1.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.0.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.0")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.1")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.2")) must beFalse
      comparatorPatch.includesVersion(Version("1.2.1")) must beFalse
      comparatorPatch.includesVersion(Version("2.1.1")) must beFalse
    }
    "work with EQ" in {
      val comparatorMajor = Comparator(Operator.EQ, Version("0"))
      comparatorMajor.includesVersion(Version("0")) must beTrue
      comparatorMajor.includesVersion(Version("1")) must beFalse

      val comparatorMinor = Comparator(Operator.EQ, Version("0.1"))
      comparatorMinor.includesVersion(Version("0.0")) must beFalse
      comparatorMinor.includesVersion(Version("0.1")) must beTrue
      comparatorMinor.includesVersion(Version("1.0")) must beFalse

      val comparatorPatch = Comparator(Operator.EQ, Version("1.1.1"))
      comparatorPatch.includesVersion(Version("0.0.0")) must beFalse
      comparatorPatch.includesVersion(Version("0.0.1")) must beFalse
      comparatorPatch.includesVersion(Version("0.1.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.0.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.1.0")) must beFalse
      comparatorPatch.includesVersion(Version("1.1.1")) must beTrue
      comparatorPatch.includesVersion(Version("1.1.2")) must beFalse
      comparatorPatch.includesVersion(Version("1.2.1")) must beFalse
      comparatorPatch.includesVersion(Version("2.1.1")) must beFalse
    }
  }

  "VersionRange.includesVersion" should {
    "work" in {
      val range = VersionRange(Some(Comparator(Operator.GT, Version("0"))), Some(Comparator(Operator.LT, Version("1"))))
      range.includesVersion(Version("0")) must beFalse
      range.includesVersion(Version("0.1")) must beTrue
      range.includesVersion(Version("1.0")) must beFalse
    }
  }

  "latestInRange" should {
    "work" in {
      val latest = latestInRange(VersionRange(Some(Comparator(Operator.GT, Version("0.1"))), None), Set("0", "0.1", "0.2", "1.0"))
      latest must beSome(Version("1.0"))
    }
    "be empty if no version is in the range" in {
      val latest = latestInRange(VersionRange(Some(Comparator(Operator.GT, Version("1"))), None), Set("0", "0.1", "0.2", "1.0"))
      latest must beEmpty
    }
  }

}
