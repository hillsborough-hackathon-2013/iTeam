/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 10, 2013
 */
package hackathon.configuration

import play.api.{
	Application,
	Configuration
	}


/**
 * The '''DataBaseSettings''' type provides a single point of truth for
 * retrieving Play! configuration settings which relate to the underlying
 * DBMS.
 *
 * @author svickers
 *
 */
final case class DataBaseSettings (
	private val config : Configuration
	)
{
	/// Class Imports
	import DataBaseSettings._
	
	
	/// Instance Properties
	lazy val driverClassName : Option[String] =
		config.getString (driverKey) filter (supportedDrivers.contains);
}


object DataBaseSettings
{
	/// Instance Properties
	val h2DriverName = "org.h2.Driver";
	val postgresDriverName = "org.postgresql.Driver";
	val supportedDrivers = Set (h2DriverName, postgresDriverName);
	private val driverKey = "db.default.driver";
	
	
	def apply (app : Application) : DataBaseSettings =
		new DataBaseSettings (app.configuration);
}
