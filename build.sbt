organization := "com.blackfynn"

name := "utilities"

scalaVersion := "2.12.4"

version := sys.props.get("version").getOrElse("0.0.0-SNAPSHOT")

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-Xmax-classfile-name","100",
  "-feature",
  "-deprecation",
  "-Ypartial-unification"
)

publishTo := {
  val nexus = "https://nexus.pennsieve.cc/repository"

  if (isSnapshot.value) {
    Some("Nexus Realm" at s"$nexus/maven-snapshots")
  } else {
    Some("Nexus Realm" at s"$nexus/maven-releases")
  }
}

publishMavenStyle := true
publishArtifact in Test := true

scalafmtOnCompile := true

val catsVersion = "1.2.0"
val circeVersion = "0.11.1"
val scalatestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "com.typesafe" % "config" % "1.3.3",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-shapes" % circeVersion,
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.scalactic" %% "scalactic" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)

credentials += Credentials("Sonatype Nexus Repository Manager",
  "nexus.pennsieve.cc",
  sys.env.getOrElse("PENNSIEVE_NEXUS_USER", "pennsieve-ci"),
  sys.env.getOrElse("PENNSIEVE_NEXUS_PW", "")
)
