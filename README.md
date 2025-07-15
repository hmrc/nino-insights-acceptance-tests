# Bank Account Insights Acceptance Tests

API test suite for `nino-insights` using ScalaTest and [play-ws](https://github.com/playframework/play-ws) client.

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

## Running ZAP specs - on a developer machine

You can use the `run-local-zap-container.sh` script to build a local ZAP container that will allow you to run ZAP tests locally.  
This will clone a copy of the dast-config-manager repository in this projects parent directory; it will require `make` to be available on your machine.  
https://github.com/hmrc/dast-config-manager/#running-zap-locally has more information about how the zap container is built.

```bash
./run-local-zap-container.sh --start
./run-zap-specs.sh
./run-local-zap-container.sh --stop
``` 

***Note:** Results of your ZAP run will not be placed in your target directory until you have run `./run-local-zap-container.sh --stop`*

***Note:** `./run-local-zap-container.sh` should **NOT** be used when running in a CI environment!*

## Scalafmt

This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined
within [.scalafmt.conf](.scalafmt.conf).

To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll scalafmtSbt
 ```

To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```

[Visit the official Scalafmt documentation to view a complete list of tasks which can be run.](https://scalameta.org/scalafmt/docs/installation.html#task-keys)
