resolvers ++= Seq (
	"Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
	Classpaths.typesafeSnapshots
	)

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.4.0")

addSbtPlugin("play" % "sbt-plugin" % "2.1.1")

