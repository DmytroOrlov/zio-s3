import java.nio.file.Files

import BuildHelper._

inThisBuild(
  List(
    organization := "dev.zio",
    homepage := Some(url("https://zio.github.io/zio-s3/")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer("regis-leray", "Regis Leray", "regis.leray@gmail.com", url("https://github.com/regis-leray"))
    ),
    Test / fork := true,
    parallelExecution in Test := false,
    scmInfo := Some(
      ScmInfo(url("https://github.com/zio/zio-s3/"), "scm:git:git@github.com:zio/zio-s3.git")
    )
  )
)

ThisBuild / scalaVersion := "2.13.1"

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

lazy val `zio-s3` = project
  .in(file("."))
  .settings(
    inThisBuild(Seq(
      publishTo := Some("releases" at "https://nexus.com/nexus/content/repositories/releases"),
    )),
    libraryDependencies ++= Seq(
      "dev.zio"                %% "zio"                         % "1.0.0-RC17",
      "dev.zio"                %% "zio-streams"                 % "1.0.0-RC17",
      "dev.zio"                %% "zio-nio"                     % "0.4.0",
      "dev.zio"                %% "zio-interop-java"            % "1.1.0.0-RC6",
      "dev.zio"                %% "zio-interop-reactivestreams" % "1.0.3.5-RC2",
      "software.amazon.awssdk" % "s3"                           % "2.10.50",
      "dev.zio"                %% "zio-test"                    % "1.0.0-RC17" % Test,
      "dev.zio"                %% "zio-test-sbt"                % "1.0.0-RC17" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
