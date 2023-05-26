/*
 * Copyright 2023 HM Revenue & Customs
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

import org.assertj.core.api.Assertions.assertThat
import uk.gov.hmrc.ninoinsights.model.response.response_codes.{NINO_NOT_ON_WATCH_LIST, NINO_ON_WATCH_LIST}
import uk.gov.hmrc.test.api.testdata.ApiErrors.NOT_AUTHORISED
import uk.gov.hmrc.test.api.testdata.NationalInsuranceNumbers.{NO_RISK_NINO, RISKY_NINO, RISKY_NINO_LOWER_CASE}

class NinoInsightsGatewaySpec extends BaseSpec with WireMockTrait with InternalAuthToken {

  val ninoGatewayUserAgent = "nino-gateway"

  Feature("Check the NINO insights API") {

    Scenario("Get risking information for a NINO that is not on the risk list") {
      Given("I want to see if we hold any risking information for a NINO")
      And("No OriginatorId is passed as a header for the API call")

      When("I use the NINO check insights API to see what information we hol")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromGatewayByUserAgent(NO_RISK_NINO)

      Then("I am given the relevant risking information")
      assertThat(actual.riskScore).isEqualTo(0)
      assertThat(actual.reason).isEqualTo(NINO_NOT_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using a single User-Agent header") {
      Given("I want to see if we hold any risking information for a NINO using a single User-Agent header")
      And("No OriginatorId is passed as a header for the API call")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromGatewayByUserAgent(RISKY_NINO)

      Then("I am given the relevant risking information")
      assertThat(actual.riskScore).isEqualTo(100)
      assertThat(actual.reason).isEqualTo(NINO_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using multiple User-Agent headers") {
      Given("I want to see if we hold any risking information for a NINO")
      And("No OriginatorId is passed as a header for the API call")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromGatewayByUserAgents(RISKY_NINO)

      Then("I am given the relevant risking information")
      assertThat(actual.riskScore).isEqualTo(100)
      assertThat(actual.reason).isEqualTo(NINO_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using a single OriginatorId header") {
      Given("I want to see if we hold any risking information for a NINO using the OriginatorId header")
      And("No User-Agents are passed in the header for the API call")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromGatewayByOriginatorId(RISKY_NINO)

      Then("I am given the relevant risking information")
      assertThat(actual.riskScore).isEqualTo(100)
      assertThat(actual.reason).isEqualTo(NINO_ON_WATCH_LIST)
    }

    Scenario("Get risking information for a NINO on the risk list using lower case") {
      Given("I want to see if we hold any risking information for a NINO")
      And("No OriginatorId is passed as a header for the API call")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromGatewayByUserAgent(RISKY_NINO_LOWER_CASE)

      Then("I am given the relevant risking information")
      assertThat(actual.riskScore).isEqualTo(100)
      assertThat(actual.reason).isEqualTo(NINO_ON_WATCH_LIST)
    }

    Scenario("Try to get risking information for a NINO on the risk list without using a user agent") {
      Given("I want to see if we hold any risking information for a NINO")

      When("I use the NINO check insights API to see what information we hold")
      val actual = ninoCheckHelper.parseInvalidNinoCheckResponseFromAPI(RISKY_NINO)

      Then("My query is rejected")
      assertThat(actual.code).isEqualTo(403)
      assertThat(actual.description).contains(NOT_AUTHORISED)
    }
  }
}
