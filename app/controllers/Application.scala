/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 10, 2013
 */
package controllers

import play.api._
import play.api.mvc._


/**
 * The '''Application''' [[play.api.mvc.Controller]] handles the landing page
 * for the system.
 */
object Application
	extends Controller
{
	/**
	 * Default action invoked for the '''Application'''.
	 * 
	 *  - Content-Type: N/A
	 *  - Resource Class: controllers.AppResource
	 */
	def index = Action {
		Ok (views.html.index ("Your new application is ready."));
		}
}
