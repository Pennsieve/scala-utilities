organization := "com.pennsieve"

name := "utilities"

scalaVersion := "2.12.11"

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

scalafmtOnCompile := true

version := sys.props.get("version").getOrElse("SNAPSHOT")

publishMavenStyle := true
publishArtifact in Test := true

credentials += Credentials("Sonatype Nexus Repository Manager",
  "nexus.pennsieve.cc",
  sys.env("PENNSIEVE_NEXUS_USER"),
  sys.env("PENNSIEVE_NEXUS_PW")
)

val catsVersion = "1.5.0"
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
