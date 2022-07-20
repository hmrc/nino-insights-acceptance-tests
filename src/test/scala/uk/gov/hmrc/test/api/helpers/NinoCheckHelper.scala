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
