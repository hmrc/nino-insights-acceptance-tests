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

package uk.gov.hmrc.test.api.specs

import org.scalatest.{BeforeAndAfterAll, Suite}
import uk.gov.hmrc.internalauth.models.{AuthToken, Token}
import uk.gov.hmrc.test.api.helpers.InternalAuthHelper

trait InternalAuthToken extends BeforeAndAfterAll {

  this: Suite =>

  val internalAuthHelper: InternalAuthHelper = new InternalAuthHelper()
  val ninoGatewayInternalAuthToken: Token    = Token("local-test-token")
  var internalAuthToken: Option[AuthToken]   = None

  override def beforeAll(): Unit = {
    super.beforeAll()
    internalAuthToken = Some(internalAuthHelper.postConfigureInternalAuthToken())
    internalAuthHelper.deleteToken(ninoGatewayInternalAuthToken.value)
    internalAuthHelper.postConfigureInternalAuthToken(ninoGatewayInternalAuthToken)
  }

  override def afterAll(): Unit = {
    super.afterAll()
    if (internalAuthToken.isDefined) {
      internalAuthHelper.deleteToken(internalAuthToken.get.token.value)
    }
  }
}
