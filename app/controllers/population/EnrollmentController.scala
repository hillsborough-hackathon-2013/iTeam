/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package controllers.population

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.mvc._

import hackathon.models._

import population.Enrollment


/**
 * The '''EnrollmentController''' type defines the Play! controller used to
 * enroll a [[hackathon.models.Subscriber]] in the system.
 *
 * @author svickers
 *
 */
object EnrollmentController
	extends Controller
{
	/// Class Types
	object Fields
	{
		/// Instance Properties
		val name = "name";
		val password = "password";
		val phone = "phone";
	}
	
	
	/// Instance Properties
	val enrollmentForm = Form (
		mapping (
			Fields.name -> nonEmptyText (minLength = 1, maxLength = 128),
			Fields.password -> nonEmptyText,
			Fields.phone -> nonEmptyText (minLength = 10, maxLength = 14)
			) (Enrollment (_, _, _, Now ())) {
				_ match {
					case Enrollment (name, password, phone, _) =>
						Some (name, password, phone);
					}
				}
		);
	
	
	def enroll = Action {
		implicit request =>

		enrollmentForm.bindFromRequest.fold (
			hadErrors => BadRequest,
			bound => Redirect (routes.SubscriberController.index ())
			)
		}
}
