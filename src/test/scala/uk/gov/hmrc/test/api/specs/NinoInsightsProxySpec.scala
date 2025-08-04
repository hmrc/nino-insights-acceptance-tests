/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.test.api.specs

import uk.gov.hmrc.test.api.models.NinoInsightsResponse.{NINO_NOT_ON_WATCH_LIST, NINO_ON_WATCH_LIST}
import uk.gov.hmrc.test.api.testdata.ApiErrors.NOT_AUTHORISED
import uk.gov.hmrc.test.api.testdata.NationalInsuranceNumbers.{NO_RISK_NINO, RISKY_NINO, RISKY_NINO_LOWER_CASE}
import uk.gov.hmrc.test.api.helpers.Endpoints

class NinoInsightsProxySpec extends BaseSpec {

  val endpoint: String          = Endpoints.CHECK_INSIGHTS
  val endpointWithRoute: String = Endpoints.CHECK_INSIGHTS_WITH_ROUTE

  Feature("Check the NINO insights API") {

    Scenario("Get risking information for a NINO that is not on the risk list") {
      Given("I want to see if we hold any risking information for a NINO")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPI(endpoint, NO_RISK_NINO)

      Then("I am given the relevant risking information")
      assert(actual.riskScore == 0)
      assert(actual.reason == NINO_NOT_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list") {
      Given("I want to see if we hold any risking information for a NINO")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPI(endpoint, RISKY_NINO)

      Then("I am given the relevant risking information")
      assert(actual.riskScore == 100)
      assert(actual.reason == NINO_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using nino-insights route") {
      Given("I want to see if we hold any risking information for a NINO using nino-insights route")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPI(endpointWithRoute, RISKY_NINO)

      Then("I am given the relevant risking information")
      assert(actual.riskScore == 100)
      assert(actual.reason == NINO_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using lower case") {
      Given("I want to see if we hold any risking information for a NINO")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPI(endpoint, RISKY_NINO_LOWER_CASE)

      Then("I am given the relevant risking information")
      assert(actual.riskScore == 100)
      assert(actual.reason == NINO_ON_WATCH_LIST)
    }

    Scenario("Try to get risking information for a NINO on the risk list without using a user agent") {
      Given("I want to see if we hold any risking information for a NINO")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseInvalidNinoCheckResponseFromAPI(RISKY_NINO)

      Then("My query is rejected")
      assert(actual.code == 403)
      assert(actual.description contains NOT_AUTHORISED)
    }
  }
}
