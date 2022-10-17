package com.mdagdelen

import cats.effect._
import munit.CatsEffectSuite

class Bcrypt4sSpec extends CatsEffectSuite {
  test("must fail when log rounds exceeds limit") {
    interceptMessage[java.lang.IllegalArgumentException]("log_rounds exceeds maximum (30)") {
      bcrypt4s.genSalt[IO](31).unsafeRunSync()
    }
  }

  test("must return true if hash and the given password matches") {
    val password = "password"
    val result = (for {
      salt <- bcrypt4s.genSalt[IO]()
      hash <- bcrypt4s.hashPw[IO](password, salt)
      result <- bcrypt4s.checkPw[IO](password, hash)
    } yield result).unsafeRunSync()

    assert(result)
  }

  test("must return false if hash and the given password doesn't match") {
    val password = "password"
    val invalidPassword = "password!"

    val result = (for {
      salt <- bcrypt4s.genSalt[IO]()
      hash <- bcrypt4s.hashPw[IO](password, salt)
      result <- bcrypt4s.checkPw[IO](invalidPassword, hash)
    } yield result).unsafeRunSync()

    assert(!result)
  }
}
