/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.test.api.db

import doobie.implicits._
import doobie.util.fragment.Fragment.{const => csql}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DatabaseSchemaSpec extends AnyWordSpec with PostgresDB with Matchers {
  "PG Test" when {
    "connect" should {
      "be able to select current date time" in {
        val now = sql"SELECT NOW()".query[String].unique.transact(tx).unsafeRunSync()
        now should fullyMatch regex """\p{Digit}{4}-\p{Digit}{2}-\p{Digit}{2} \p{Digit}{2}:\p{Digit}{2}:\p{Digit}{2}\..*"""
      }
    }
  }

  "NINO Risk List Repository" when {
    "configured" should {
      "retrieve the correct number of NINOs from watch list" in {
        val ninoCount = csql(s"""select count(*) from  $schemaName.nino_reject""")
          .query[Int]
          .unique
          .transact(tx)
          .unsafeRunSync()
        ninoCount shouldBe 1000
      }

      "retrieve records correctly from database" in {
        val isAccountOnWatchList = csql(
          s"""select true from $schemaName.nino_reject WHERE nino = 'TP519322D'"""
        )
          .query[Boolean]
          .unique
          .transact(tx)
          .unsafeRunSync()
        isAccountOnWatchList shouldBe true
      }

      "reports records not found correctly" in {
        val isAccountOnWatchList = csql(
          s"""select true from $schemaName.nino_reject WHERE nino = 'ZZ519322D'"""
        )
          .query[Boolean]
          .option
          .transact(tx)
          .unsafeRunSync()
        isAccountOnWatchList shouldBe None
      }
    }
  }
}
