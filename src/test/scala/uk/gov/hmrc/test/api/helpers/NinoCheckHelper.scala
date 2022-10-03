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
import uk.gov.hmrc.internalauth.models.AuthToken
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse.implicits.ninoInsightsResponseFormat
import uk.gov.hmrc.test.api.models.BadRequest
import uk.gov.hmrc.test.api.service.{NinoGatewayCheckService, NinoInsightsCheckService}

class NinoCheckHelper {
  val ninoInsightsCheckAPI: NinoInsightsCheckService = new NinoInsightsCheckService
  val ninoGatewayCheckAPI: NinoGatewayCheckService   = new NinoGatewayCheckService

  def callNinoCheckResponseFromAPI(
    internalAuthToken: Option[AuthToken],
    ninoDetails: NinoInsightsRequest
  ): StandaloneWSRequest#Self#Response =
    ninoInsightsCheckAPI.postInsightsCheck(internalAuthToken, ninoDetails)

  def parseValidNinoCheckResponseFromAPI(
    internalAuthToken: Option[AuthToken],
    ninoDetails: NinoInsightsRequest
  ): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response =
      callNinoCheckResponseFromAPI(internalAuthToken, ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def parseInvalidNinoCheckResponseFromAPI(
    internalAuthToken: Option[AuthToken],
    ninoDetails: NinoInsightsRequest
  ): BadRequest = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response =
      callNinoCheckResponseFromAPI(internalAuthToken, ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[BadRequest]
  }

  def callNinoCheckResponseFromAPI(ninoDetails: NinoInsightsRequest): StandaloneWSRequest#Self#Response =
    ninoGatewayCheckAPI.postGatewayCheck(ninoDetails)

  def parseValidNinoCheckResponseFromGateway(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response = callNinoCheckResponseFromAPI(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }
}
