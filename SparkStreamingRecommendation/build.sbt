name := "SparkStreamingRecommendation"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-optimize",
  "-unchecked",
  "-deprecation"
)

classpathTypes += "maven-plugin"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.4.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.4.0",
  "org.apache.spark" %% "spark-mllib" % "1.4.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.bytedeco" % "javacpp" % "0.11",
  "org.bytedeco" % "javacv" % "0.11",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11",
  "org.bytedeco.javacpp-presets" % "opencv" % "2.4.11-0.11" classifier "macosx-x86_64",
  "com.typesafe.play" % "play-json_2.11" % "2.4.2",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalaj" %% "scalaj-http" % "1.1.5",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0" classifier "models",
  "org.apache.commons" % "commons-lang3" % "3.0",
  "org.eclipse.jetty" % "jetty-client" % "8.1.14.v20131031",

  "org.elasticsearch" % "elasticsearch-hadoop-mr" % "2.0.0.RC1"

)

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "JavaCV maven repo" at "http://maven2.javacv.googlecode.com/git/",
  "JavaCPP maven repo" at "http://maven2.javacpp.googlecode.com/git/",
  Resolver.sonatypeRepo("public")
)