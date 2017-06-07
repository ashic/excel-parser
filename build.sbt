lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion       := "2.12.1",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
//    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  addCompilerPlugin("org.spire-math"  % "kind-projector" % "0.9.3" cross CrossVersion.binary),
  resolvers += Resolver.sonatypeRepo("releases")
)

lazy val excel = project.in(file("."))
  .settings(moduleName := "excel")
  .settings(baseSettings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "excel-core")
  .settings(baseSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "org.typelevel"  %% "cats"       % "0.9.0",
    "org.apache.poi"  % "poi-ooxml"  % "3.15",
    "org.scalatest"  %% "scalatest"  % "3.0.1"  % "test"
  ))


lazy val slides = project
  .settings(moduleName := "excel-slides")
  .settings(baseSettings: _*)
  .settings(tutSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "../docs"
  ).dependsOn(core)
