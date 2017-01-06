name := "scala-thrift-finagle-example"

version := "2.0"

scalaVersion := "2.12.1"

val scroogeCore = "com.twitter" %% "scrooge-core" % "4.13.0"

resolvers ++= Seq(
  "twitter" at "http://maven.twttr.com",
  "sonatype" at "https://oss.sonatype.org/content/groups/public")

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.9.3",
  "com.twitter" %% "finagle-thrift" % "6.41.0",
  "org.scaldi" %% "scaldi" % "0.5.8",

  // Тестирование
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test")

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

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
