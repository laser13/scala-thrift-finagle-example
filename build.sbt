name := "scala-thrift-finagle-example"

version := "1.0"

scalaVersion := "2.11.7"

val scroogeCore = "com.twitter" %% "scrooge-core" % "4.0.0"

resolvers ++= Seq(
  "twitter" at "http://maven.twttr.com",
  "sonatype" at "https://oss.sonatype.org/content/groups/public")

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "com.twitter" %% "finagle-thrift" % "6.28.0",
  "org.scaldi" %% "scaldi" % "0.5.6",

  // Тестирование
  "org.scalatest" %% "scalatest" % "3.0.0-M7" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.4" % "test")

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-Xlint"
  ,"-Yclosure-elim"
  ,"-Yinline"
  ,"-Xverify"
  ,"-feature"
  ,"-language:postfixOps"
)

javacOptions in Compile ++= Seq(
  "-source", "1.8", "-target", "1.8",
  "-Xlint:unchecked", "-Xlint:deprecation")

//scroogeThriftSourceFolder in Compile <<= baseDirectory {
//  base => base / "src/main/thrift"
//}

scroogeThriftOutputFolder in Default <<= baseDirectory {
  base => base / "src/main/scala"
}

//scroogeThriftOutputFolder in Test <<= baseDirectory {
//  base => base / "src/main/scala"
//}
