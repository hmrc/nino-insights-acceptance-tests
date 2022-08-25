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

package uk.gov.hmrc.test.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse.implicits.ninoInsightsResponseFormat
import uk.gov.hmrc.test.api.service.NinoCheckService

class NinoCheckHelper {
  val ninoCheckAPI: NinoCheckService = new NinoCheckService

  def getNinoCheckResponse(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val authServiceRequestResponse: StandaloneWSRequest#Self#Response =
      ninoCheckAPI.postInsightsCheck(ninoDetails)
    Json.parse(authServiceRequestResponse.body).as[NinoInsightsResponse]
  }
}
