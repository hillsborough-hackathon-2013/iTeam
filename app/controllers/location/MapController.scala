/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 14, 2013
 */
package controllers.location

import scala.concurrent._

import scalaz._

import play.api._
import play.api.libs.ws._
import play.api.mvc._

import hackathon.models._

import spatial._


/**
 * The '''MapController''' type processes normalized
 * [[hackathon.models.spatial.GeoLocation]] cartographic requests.
 *
 * @author svickers
 *
 */
object MapController
	extends Controller
{
	/// Class Imports
	import play.api.libs.concurrent.Execution.Implicits._
	import Scalaz._
	
	
	/// Class Types
	object Fields
	{
		/// Instance Properties
		val latitude = "latitude";
		val longitude = "longitude";
		val zoom = "zoom";
	}
	
	
	/// Instance Properties
	private lazy val mapServerLogin = Play.current.configuration.getString (
		"map.server.login"
		);
	private lazy val mapServerUri = Play.current.configuration.getString (
		"map.server.uri"
		);
	
	
	def renderMap = Action {
		implicit request =>
			
		Async {
			queryFields (request) match {
				case Some ((Latitude (lat), Longitude (long), zoom))  =>
					WS.url (mapServerUri.get).get ().map {
						response =>
							
						Ok (response.body).as ("text/html");
						}
					
				case _ =>
					Future (BadRequest);
				}
			}
		}
	
	
	private def queryFields[T] (req : Request[T])
		: Option[(String, String, String)] =
		(
		req.getQueryString (Fields.latitude) |@|
		req.getQueryString (Fields.longitude) |@|
		req.getQueryString (Fields.zoom)
		) { (_, _, _) }
}
