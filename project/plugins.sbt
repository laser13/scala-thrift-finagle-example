logLevel := sbt.Level.Warn

resolvers ++= Seq(
  "twitter" at "http://maven.twttr.com",
  "sonatype" at "https://oss.sonatype.org/content/groups/public")

addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "4.13.0")