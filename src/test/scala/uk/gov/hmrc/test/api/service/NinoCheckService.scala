package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest
import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest.implicits.ninoInsightsRequestWrites
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.helpers.Endpoints

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class NinoCheckService extends HttpClient {
  val host: String            = TestConfiguration.url("nino-insights")
  val checkAccountURL: String = s"$host/${Endpoints.CHECK_INSIGHTS}"

  def postInsightsCheck(ninoDetails: NinoInsightsRequest): StandaloneWSRequest#Self#Response =
    Await.result(
      post(
        checkAccountURL,
        ninoInsightsRequestWrites.writes(ninoDetails).toString(),
        ("Content-Type", "application/json")
      ),
      10.seconds
    )
}
