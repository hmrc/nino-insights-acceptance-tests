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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest.implicits.ninoInsightsRequestWrites
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.helpers.Endpoints
import uk.gov.hmrc.test.api.models.AuthToken

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class NinoInsightsCheckService extends HttpClient {
  var ninoInsights: String             = TestConfiguration.url("nino-insights")
  def postInsightsCheck(
    internalAuthToken: Option[AuthToken],
    ninoDetails: NinoInsightsRequest,
    host: String = ninoInsights
  ): StandaloneWSRequest#Self#Response =
    Await.result(
      if (internalAuthToken.isDefined) {
        post(
          s"$host/${Endpoints.CHECK_INSIGHTS}",
          ninoInsightsRequestWrites.writes(ninoDetails).toString(),
          ("Content-Type", "application/json"),
          ("Authorization", internalAuthToken.get.token.value)
        )
      } else {
        post(
          s"$host/${Endpoints.CHECK_INSIGHTS}",
          ninoInsightsRequestWrites.writes(ninoDetails).toString(),
          ("Content-Type", "application/json")
        )
      },
      10.seconds
    )
}
