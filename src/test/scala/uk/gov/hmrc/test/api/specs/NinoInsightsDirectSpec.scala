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

import com.github.tomakehurst.wiremock.client.WireMock.{matchingJsonPath, postRequestedFor, urlEqualTo, verify}
import uk.gov.hmrc.test.api.models.NinoInsightsResponse.{NINO_NOT_ON_WATCH_LIST, NINO_ON_WATCH_LIST}
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.testdata.NationalInsuranceNumbers.{NO_RISK_NINO, RISKY_NINO}

import scala.concurrent.duration.DurationInt

class NinoInsightsDirectSpec extends BaseSpec with WireMockTrait {

  Feature("Check the NINO insights API directly") {

    Scenario("Get risk information for a NINO on the risk list using NINO insights API directly") {
      Given("I want to see if we hold any risk information for a NINO")

      When("I use the NINO check insights API directly to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPIDirectly(RISKY_NINO)

      Then("I am given the relevant risk information")
      assert(actual.riskScore == 100)
      assert(actual.reason == NINO_ON_WATCH_LIST)

      verify(
        delayedFunction(1.seconds)(
          postRequestedFor(urlEqualTo("/write/audit"))
            .withRequestBody(
              matchingJsonPath(
                "$[?(" +
                  s"@.auditSource == '${TestConfiguration.expectedServiceName}'" +
                  "&& @.auditType == 'NinoInsightsLookup'" +
                  s"&& @.detail.nino == '${RISKY_NINO.nino}'" +
                  s"&& @.detail.correlationId == '${actual.correlationId}'" +
                  s"&& @.detail.riskScore == '${actual.riskScore}'" +
                  s"&& @.detail.reason == '${actual.reason}'" +
                  s"&& @.detail.userAgent == '${TestConfiguration.userAgent}'" +
                  ")]"
              )
            )
        )
      )
    }

    Scenario("Get risk information for a NINO that is not on the risk list using NINO insights API directly") {
      Given("I want to see if we hold any risk information for a NINO")

      When("I use the NINO check insights API directly to see what information we hold")
      val actual = ninoCheckHelper.parseValidNinoCheckResponseFromAPIDirectly(NO_RISK_NINO)

      Then("I am given the relevant risk information")
      assert(actual.riskScore == 0)
      assert(actual.reason == NINO_NOT_ON_WATCH_LIST)

      verify(
        delayedFunction(1.seconds)(
          postRequestedFor(urlEqualTo("/write/audit"))
            .withRequestBody(
              matchingJsonPath(
                "$[?(" +
                  s"@.auditSource == '${TestConfiguration.expectedServiceName}'" +
                  "&& @.auditType == 'NinoInsightsLookup'" +
                  s"&& @.detail.nino == '${NO_RISK_NINO.nino}'" +
                  s"&& @.detail.correlationId == '${actual.correlationId}'" +
                  s"&& @.detail.riskScore == '${actual.riskScore}'" +
                  s"&& @.detail.reason == '${actual.reason}'" +
                  s"&& @.detail.userAgent == '${TestConfiguration.userAgent}'" +
                  ")]"
              )
            )
        )
      )
    }
  }
}
