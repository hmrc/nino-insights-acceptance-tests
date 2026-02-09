#!/usr/bin/env bash

sm2 --start NINO_INSIGHTS_PROXY NINO_INSIGHTS NINO_GATEWAY INTERNAL_AUTH --appendArgs \
  '{
    "NINO_INSIGHTS": [
        "-J-Ddb.ninoinsights.url=jdbc:postgresql://localhost:5432/",
        "-J-Ddb.ninoinsights.readOnlyUrl=jdbc:postgresql://localhost:5432/",
        "-J-Ddb.ninoinsights.use-canned-data=true",
        "-J-Dauditing.consumer.baseUri.port=6001",
        "-J-Dauditing.consumer.baseUri.host=localhost",
        "-J-Dauditing.enabled=true"
    ],
    "NINO_INSIGHTS_PROXY": [
        "-J-Dauditing.consumer.baseUri.port=6001",
        "-J-Dauditing.consumer.baseUri.host=localhost",
        "-J-Dauditing.enabled=false",
        "-J-Dmicroservice.services.access-control.enabled=true",
        "-J-Dmicroservice.services.access-control.allow-list.0=nino-gateway",
        "-J-Dmicroservice.services.access-control.allow-list.1=allowed-test-hmrc-service"
    ]
  }'
