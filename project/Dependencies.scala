import sbt._

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"         %% "scalatest"               % "3.2.12" % Test,
    "com.vladsch.flexmark"   % "flexmark-all"            % "0.62.2" % Test,
    "com.typesafe"           % "config"                  % "1.4.2"  % Test,
    "com.typesafe.play"     %% "play-ahc-ws-standalone"  % "2.1.10" % Test,
    "org.slf4j"              % "slf4j-simple"            % "1.7.36" % Test,
    "com.typesafe.play"     %% "play-ws-standalone-json" % "2.1.10" % Test,
    "com.github.tomakehurst" % "wiremock"                % "2.27.2" % Test,
    "org.assertj"            % "assertj-core"            % "3.23.1" % Test,
    "uk.gov.hmrc"           %% "nino-insights"           % "0.+"    % Test,
    "org.tpolecat"          %% "doobie-core"             % "0.7.1"  % Test,
    "org.tpolecat"          %% "doobie-postgres"         % "0.7.1"  % Test,
    "org.tpolecat"          %% "doobie-scalatest"        % "0.7.1"  % Test,
    "uk.gov.hmrc.mongo"     %% "hmrc-mongo-test-play-28" % "0.73.0" % Test,
    "org.typelevel"         %% "cats-core"               % "2.3.1"  % Test,
    "uk.gov.hmrc"           %% "attribute-risk-lists"    % "0.+"    % Test
  )
}
