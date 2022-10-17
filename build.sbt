ThisBuild / scalaVersion := "2.13.5"

lazy val root = (project in file(".")).settings(
  name := "bcrypt4s",
  organization := "io.github.dagdelenmustafa",
  organizationName := "dagdelenmustafa",
  homepage := Some(url("https://github.com/dagdelenmustafa/bcrypt4s")),
  scmInfo := Some(
    ScmInfo(url("https://github.com/dagdelenmustafa/bcrypt4s"), "git@github.com:dagdelenmustafa/bcrypt4s.git")
  ),
  usePgpKeyHex("23F8E7FCED8D8E94497F1B0930EB464AFFA4A4FD"),
  developers := List(
    Developer("dagdelenmustafa",
              "Mustafa Dağdelen",
              "mustafadagdelen@protonmail.com",
              url("https://github.com/dagdelenmustafa")
    )
  ),
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),
  crossPaths := false,
  pomIncludeRepository := { _ => false },
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.3.12",
    "org.mindrot" % "jbcrypt" % "0.4",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
  )
)
