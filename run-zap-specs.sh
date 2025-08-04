#!/usr/bin/env bash
ENV=$1

sbt -Denvironment=${ENV:=local} "testOnly uk.gov.hmrc.test.api.db.* testOnly uk.gov.hmrc.test.api.specs.*"
