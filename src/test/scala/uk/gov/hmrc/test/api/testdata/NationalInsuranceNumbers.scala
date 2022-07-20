package uk.gov.hmrc.test.api.testdata

import uk.gov.hmrc.ninoinsights.model.request.NinoInsightsRequest

object NationalInsuranceNumbers {
  val NO_RISK_NINO: NinoInsightsRequest = NinoInsightsRequest("AB123456A")
  val RISKY_NINO: NinoInsightsRequest   = NinoInsightsRequest("TP519322D")
}
