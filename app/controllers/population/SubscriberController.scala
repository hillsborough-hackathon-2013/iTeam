/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 10, 2013
 */
package controllers.population

import scalaz._

import play.api._
import play.api.mvc._


/**
 * The '''SubscriberController''' type is a Play! [[play.api.mvc.Controller]]
 * which defines the supported actions relating to
 * [[hackathon.models.population.Subscriber]]s.
 *
 * @author svickers
 *
 */
object SubscriberController
	extends Controller
{
	/// Class Imports
	import syntax.std.option._
	
	
	def index = Action {
		Ok (views.html.population.index ());
		}
	
	
	/**
	 * The login method takes a '''name''' and '''password''' in order to
	 * determine what [[hackathon.models.population.Subscriber]] it uniquely
	 * identifies, if any.
	 * 
	 *  - Content-Type: HTML, JSON, XML
	 *  - Resource Class: 
	 */
	def login (name : String, password : String) = Action {
		Ok;
		}
}
