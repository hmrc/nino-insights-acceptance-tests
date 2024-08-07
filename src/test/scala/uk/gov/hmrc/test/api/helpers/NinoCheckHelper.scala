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

package uk.gov.hmrc.test.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse
import uk.gov.hmrc.ninoinsights.model.response.NinoInsightsResponse.implicits.ninoInsightsResponseFormat
import uk.gov.hmrc.test.api.models.BadRequest
import uk.gov.hmrc.test.api.service.{NinoGatewayCheckService, NinoInsightsCheckDirectService, NinoInsightsCheckService}

class NinoCheckHelper {
  val ninoInsightsCheckAPI: NinoInsightsCheckService = new NinoInsightsCheckService
  val ninoGatewayCheckAPI: NinoGatewayCheckService   = new NinoGatewayCheckService
  val ninoInsightsCheckAPIDirectly: NinoInsightsCheckDirectService = new NinoInsightsCheckDirectService

  def callNinoCheckResponseFromAPIDirectly(
    ninoDetails: NinoInsightsRequest
  ): StandaloneWSRequest#Self#Response =
    ninoInsightsCheckAPIDirectly.postInsightsCheckDirectly(ninoDetails)

  def callNinoCheckResponseFromAPI(
    endpoint: String,
    ninoDetails: NinoInsightsRequest
  ): StandaloneWSRequest#Self#Response =
    ninoInsightsCheckAPI.postInsightsCheck(endpoint, ninoDetails)

  def callInvalidNinoCheckResponseFromAPI(
    ninoDetails: NinoInsightsRequest
  ): StandaloneWSRequest#Self#Response =
    ninoInsightsCheckAPI.postInsightsInvalidCheck(ninoDetails)

  def parseValidNinoCheckResponseFromAPIDirectly(
    ninoDetails: NinoInsightsRequest
  ): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response =
      callNinoCheckResponseFromAPIDirectly(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def parseValidNinoCheckResponseFromAPI(
    endpoint: String,
    ninoDetails: NinoInsightsRequest
  ): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response =
      callNinoCheckResponseFromAPI(endpoint, ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def parseInvalidNinoCheckResponseFromAPI(
    ninoDetails: NinoInsightsRequest
  ): BadRequest = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response =
      callInvalidNinoCheckResponseFromAPI(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[BadRequest]
  }

  def parseValidNinoCheckResponseFromGatewayByUserAgent(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response = ninoGatewayCheckAPI.postGatewayCheckByUserAgentHeader(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def parseValidNinoCheckResponseFromGatewayByOriginatorId(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response = ninoGatewayCheckAPI.postGatewayCheckByOriginatorIdHeader(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def parseValidNinoCheckResponseFromGatewayByUserAgents(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response = ninoGatewayCheckAPI.postGatewayCheckByMultipleUserAgentHeaders(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }

  def postGatewayCheckByMultipleUserAgentValuesInOneHeader(ninoDetails: NinoInsightsRequest): NinoInsightsResponse = {
    val ninoCheckRequestResponse: StandaloneWSRequest#Self#Response = ninoGatewayCheckAPI.postGatewayCheckByMultipleUserAgentValuesInOneHeader(ninoDetails)
    Json.parse(ninoCheckRequestResponse.body).as[NinoInsightsResponse]
  }
}
