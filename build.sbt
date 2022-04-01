organization := "com.pennsieve"

name := "utilities"

lazy val scala212 = "2.12.11"
lazy val scala213 = "2.13.8"
lazy val supportedScalaVersions = List(scala212, scala213)

scalaVersion := scala212

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-feature",
  "-deprecation"
)

scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 12)) =>
    Seq("-Xmax-classfile-name", "100", "-Ypartial-unification")
  case _ => Nil
})

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
Test / publishArtifact := true

credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "nexus.pennsieve.cc",
  sys.env("PENNSIEVE_NEXUS_USER"),
  sys.env("PENNSIEVE_NEXUS_PW")
)

lazy val circeVersion = SettingKey[String]("circeVersion")
lazy val scalatestVersion = SettingKey[String]("scalatestVersion")
lazy val catsVersion = SettingKey[String]("catsVersion")

catsVersion := (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 12)) => "1.5.0"
  case _ => "2.6.1"
})

circeVersion := (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 12)) => "0.11.1"
  case _ => "0.14.1"
})

scalatestVersion := (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 12)) => "3.0.5"
  case _ => "3.2.11"
})

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "com.typesafe" % "config" % "1.3.3",
  "io.circe" %% "circe-core" % circeVersion.value,
  "io.circe" %% "circe-shapes" % circeVersion.value,
  "org.typelevel" %% "cats-core" % catsVersion.value,
  "org.scalactic" %% "scalactic" % scalatestVersion.value,
  "org.scalatest" %% "scalatest" % scalatestVersion.value % "test"
)

crossScalaVersions := supportedScalaVersions
