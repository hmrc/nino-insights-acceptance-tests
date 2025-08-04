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

package uk.gov.hmrc.test.api.service

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.client.HttpClientHelper
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.helpers.Endpoints
import uk.gov.hmrc.test.api.models.NinoInsightsRequest

class NinoInsightsCheckService extends HttpClientHelper {
  var ninoInsights: String             = TestConfiguration.url("nino-insights")
  def postInsightsCheck(
                         endpoint: String,
                         ninoDetails: NinoInsightsRequest,
                         host: String = ninoInsights
                       ): StandaloneWSRequest#Self#Response =
    post(
      s"$host/$endpoint",
      ninoDetails,
      "Content-Type" -> "application/json",
      "User-Agent" -> "allowed-test-hmrc-service"
    )

  def postInsightsInvalidCheck(
                                ninoDetails: NinoInsightsRequest,
                                host: String = ninoInsights
                              ): StandaloneWSRequest#Self#Response =
    post(
      s"$host/${Endpoints.CHECK_INSIGHTS}",
      ninoDetails,
      "Content-Type" -> "application/json"
    )
}
