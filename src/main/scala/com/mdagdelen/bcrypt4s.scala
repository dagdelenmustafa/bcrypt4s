/*
 * Copyright 2022 dagdelenmustafa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mdagdelen

import cats._
import cats.effect._
import cats.implicits._
import org.mindrot.jbcrypt.BCrypt

import java.security.SecureRandom

object bcrypt4s {
  private val GEN_SALT_DEFAULT_LOG2_ROUNDS = 10
  private def safeWrap[A](f: => A): Either[Throwable, A] =
    Either.catchNonFatal(f)

  private def safeWrapApplicativeError[F[_]: MonadCancelThrow, A](f: => A): F[A] =
    safeWrap(f) match {
      case Right(a) => ApplicativeError[F, Throwable].pure(a)
      case Left(e)  => ApplicativeError[F, Throwable].raiseError(e)
    }

  /**
   * Generate a salt.
   * @param logRounds The log2 of the number of rounds of hashing to apply. Must be less than 31.
   * @param random java.security.SecureRandom instance.
   * @tparam F Effect Type.
   * @return An encoded salt value.
   */
  def genSalt[F[_]: MonadCancelThrow](logRounds: Int = GEN_SALT_DEFAULT_LOG2_ROUNDS,
                                      random: SecureRandom = new SecureRandom
  ): F[String] =
    safeWrapApplicativeError(BCrypt.gensalt(logRounds, random))

  /**
   * Hash a password.
   * @param password The password to hash.
   * @param salt The salt to hash password with. Can be generated via this.genSalt().
   * @tparam F  Effect Type.
   * @return The hashed password.
   */
  def hashPw[F[_]: MonadCancelThrow](password: String, salt: String): F[String] =
    safeWrapApplicativeError(BCrypt.hashpw(password, salt))

  /**
   * Compare the given plain password with the given hash. 
   * @param plainText Plain password value to compare.
   * @param hash Already hashed password.
   * @tparam F Effect Type.
   * @return Comparison result.
   */
  def checkPw[F[_]: MonadCancelThrow](plainText: String, hash: String): F[Boolean] =
    safeWrapApplicativeError(BCrypt.checkpw(plainText, hash))

}
