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

package uk.gov.hmrc.test.api.client

import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import play.api.libs.json.{Json, Writes}
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.apitestrunner.http.HttpClient

trait HttpClientHelper extends HttpClient {

  def get(url: String, headers: (String, String)*): StandaloneWSRequest#Self#Response =
    mkRequest(url)
      .withHttpHeaders(headers: _*)
      .get()
      .futureValue

  def post[T](url: String, body: T, headers: (String, String)*)(implicit
    writes: Writes[T]
  ): StandaloneWSRequest#Self#Response =
    mkRequest(url)
      .withHttpHeaders(headers: _*)
      .post(Json.toJson(body))
      .futureValue

  def delete(url: String, headers: (String, String)*): StandaloneWSRequest#Self#Response =
    mkRequest(url)
      .withHttpHeaders(headers: _*)
      .delete()
      .futureValue
}
