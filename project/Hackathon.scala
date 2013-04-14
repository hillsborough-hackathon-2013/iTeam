import sbt._
import sbt.Keys._

import play.Project._

import com.typesafe.sbt.osgi.OsgiKeys
import com.typesafe.sbt.osgi.SbtOsgi.osgiSettings


/**
 * The '''Hackathon''' `object` defines the
 * [[http://www.scala-sbt.org/release/docs sbt]] build settings for the
 * iTeamSolutions Hack-a-thon project.
 *
 * Inspiration for the structure was drawn from the AkkaBuild.scala code
 * within akka/project.  Many thanks go to the people involved in that!
 *
 * @author	svickers
 *
 */
object Hackathon
	extends Build
{
	/// Class Types
	object OSGi
	{
		/// Instance Properties
		val defaultImports = Seq (
			"!sun.misc",
			"*"
			);

		val server = exports ("com.iteamsolutions.server.*") ++ imports ();


		def exports (packages : String *) = osgiSettings ++ Seq (
			OsgiKeys.exportPackage := packages
			);

		def imports (packages : String *) = Seq (
			OsgiKeys.importPackage := packages ++ defaultImports
			);
	}


	/// Instance Properties
	val appName = "hackathon-web"
	val appVersion = "1.0-SNAPSHOT";

	lazy val buildSettings = Seq (
		organization := "com.iteamsolutions",
		version := appVersion,
		scalaVersion := "2.10.1"
		);

	/// sbt settings applicable to any build
	lazy val defaultSettings = Defaults.defaultSettings ++ Seq (
		scalacOptions in Compile ++= Seq (
			"-encoding", "UTF-8",
			"-target:jvm-1.6",
			"-deprecation",
			"-feature",
			"-unchecked"
			)
		);

	override lazy val settings = super.settings ++ buildSettings;

	lazy val main = play.Project (
		appName,
		appVersion,
		dependencies = Seq (
			jdbc,
			anorm
			) ++ Dependencies.web
		);


		/// Constructor Body
}


object Dependencies
{
	object Scalaz
	{
		private val version = "7.0.0-M9";

		lazy val core = Seq (
			"org.scalaz" % "scalaz-core_2.10" % version,
			"org.scalaz" % "scalaz-typelevel_2.10" % version
			);

		lazy val effect = Seq (
			"org.scalaz" % "scalaz-effect_2.10" % version
			);

		lazy val iteratee = Seq (
			"org.scalaz" % "scalaz-iteratee_2.10" % version
			);

		lazy val xml = Seq (
			"org.scalaz" % "scalaz-xml_2.10" % version
			);

		lazy val all = core ++ effect ++ iteratee ++ xml;
	}


	object OSGi
	{
		private val version = "4.3.1";

		val core = Seq (
			"org.osgi" % "org.osgi.core" % version % "provided"
			);

		val compendium = Seq (
			"org.osgi" % "org.osgi.compendium" % version % "provided"
			);

		val all = compendium ++ core;
	}


	/// Compile Dependencies
	lazy val compile = Scalaz.core ++ OSGi.all ++ Seq (
		"com.h2database" % "h2" % "1.3.170",
		"org.scala-lang" % "scala-reflect" % "2.10.1",
		"org.squeryl" %% "squeryl" % "0.9.5-6",
		"postgresql" % "postgresql" % "9.1-901-1.jdbc4"
		);

	/// Test Dependencies
	lazy val test = Seq (
		"junit" % "junit" % "4.10" % "test",
		"org.hamcrest" % "hamcrest-core" % "1.3" % "test",
		"org.hamcrest" % "hamcrest-library" % "1.3" % "test",
		"org.jmock" % "jmock" % "2.5.1" % "test",
		"org.jmock" % "jmock-junit4" % "2.5.1" % "test" intransitive(),
		"org.jmock" % "jmock-legacy" % "2.5.1" % "test",
		"org.scalacheck" % "scalacheck_2.10" % "1.10.0" % "test",
		"org.scalamock" % "scalamock-core_2.10" % "3.0.1" % "test",
		"org.scalamock" % "scalamock-scalatest-support_2.10" % "3.0.1" % "test",
		"org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
		"org.slf4j" % "slf4j-jdk14" % "1.7.2" % "test"
		);

	lazy val hackathon = compile;
	lazy val server = compile ++ test;
	lazy val web = compile ++ test;
}

