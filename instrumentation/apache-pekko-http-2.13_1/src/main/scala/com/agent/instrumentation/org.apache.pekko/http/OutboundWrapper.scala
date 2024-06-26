/*
 *
 *  * Copyright 2024 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package com.agent.instrumentation.org.apache.pekko.http

import org.apache.pekko.http.scaladsl.model.HttpResponse
import org.apache.pekko.http.scaladsl.model.headers.RawHeader
import com.newrelic.api.agent.{HeaderType, OutboundHeaders}

class OutboundWrapper(var response: HttpResponse) extends OutboundHeaders {

  def getHeaderType: HeaderType = {
    HeaderType.HTTP
  }

  def setHeader(name: String, value: String): Unit = {
    response = response.addHeader(new RawHeader(name, value))
  }

  def getHeader(name: String): String = {
    response.headers.find(header => header.is(name.toLowerCase)).map(header => header.value).orNull
  }

}
