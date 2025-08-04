# NINO Insights API Tests

API test suite for `nino-insights` using [api-test-runner](https://github.com/hmrc/api-test-runner) library.

# Running the tests

Prior to executing the tests ensure you have:

- Installed/configured [service manager](https://github.com/hmrc/service-manager).

## Start the local services

If you don't have mongodb installed locally you can run it in docker using the following command

    docker run -d --rm --name mongodb -p 27017-27019:27017-27019 mongo:4

If you don't have postgres installed locally you can run it in docker using the following command

    docker run -d --rm --name postgresql -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:10.14

Run the following commands to start services locally:

    sm2 --start NINO_INSIGHTS_PROXY NINO_INSIGHTS NINO_GATEWAY INTERNAL_AUTH --appendArgs '{
        "NINO_INSIGHTS": [
            "-J-Dmicroservice.nino-insights.database.dbName=postgres",
            "-J-Dmicroservice.nino-insights.database.use-canned-data=true",
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

## Running specs

Execute the `run_specs.sh` script:

`./run-specs.sh`

This script takes two parameters:

- `<ENV>` which is set to `local` by default
- `<DAST>` which is set to `false` by default, but is used to run Dynamic Application Security Testing (DAST) tests by setting this value to `true` in Jenkins builds

## Scalafmt

This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined
within [.scalafmt.conf](.scalafmt.conf).

To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) you can use this handy shell script:

 ```bash
 ./scalafmt.sh
 ```

[Visit the official Scalafmt documentation to view a complete list of tasks which can be run.](https://scalameta.org/scalafmt/docs/installation.html#task-keys)
