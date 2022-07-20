package uk.gov.hmrc.test.api.specs

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, stubFor}
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Suite}

trait WireMock extends BeforeAndAfterEach with BeforeAndAfterAll {

  this: Suite =>

  val wireMockServerConfig: Config = ConfigFactory.load()
  private val wireMockServerPort   = wireMockServerConfig.getInt("mock.server.port")
  lazy val wireMockServer          = new WireMockServer(wireMockConfig().port(wireMockServerPort))

  override def beforeAll: Unit = {
    super.beforeAll()
    wireMockServer.start()
    WireMock.configureFor("127.0.0.1", wireMockServerPort)
  }

  override def beforeEach(): Unit = {

    stubFor(
      post("/write/audit")
        .willReturn(
          aResponse()
            .withStatus(200)
        )
    )

    stubFor(
      post("/write/audit/merged")
        .willReturn(
          aResponse()
            .withStatus(200)
        )
    )
  }

  override def afterEach(): Unit =
    wireMockServer.resetAll()

  override def afterAll: Unit = {
    wireMockServer.stop()
    super.afterAll()
  }
}
