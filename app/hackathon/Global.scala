/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 6, 2013
 */

package hackathon

import org.squeryl.{
	Session,
	SessionFactory
	}
import org.squeryl.adapters.{
	H2Adapter,
	PostgreSqlAdapter
	}
import org.squeryl.internals.DatabaseAdapter
import play.api.{
	Application,
	GlobalSettings
	}
import play.api.db.DB

import configuration.DataBaseSettings


/**
 * The '''Global''' type defines Play application settings useful in
 * initializing components.
 *
 * @author svickers
 *
 */
object Global
	extends GlobalSettings
{
	/**
	 * When onStart is called by Play, we want to ensure that Squeryl is
	 * properly initialized so that data base interactions can be
	 * performed by that library.
	 */
	override def onStart (app : Application) : Unit =
	{
		import DataBaseSettings._
		
		SessionFactory.concreteFactory =
			DataBaseSettings (app).driverClassName match {
				case Some (h2DriverName) =>
					Some (() => createSession (new H2Adapter) (app));
					
				case (postgresDriverName) =>
					Some (() => createSession (new PostgreSqlAdapter) (app));
				}
	}
	
	
	private def createSession (adapter : DatabaseAdapter)
		(implicit app : Application)
		=
		Session.create (DB.getConnection (), adapter);
}
