/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package controllers.population

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import play.api.test._
import play.api.test.Helpers._

import controllers.ControllerSpec


/**
 * The '''EnrollmentControllerSpec''' type defines unit tests for the
 * [[controllers.population.EnrollmentController]] Play! controller.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class EnrollmentControllerSpec
	extends ControllerSpec
{
	"The enroll action" should "produce a bad request with no input" in
	{
		runningAction {
			val result = EnrollmentController.enroll (FakeRequest ());
			
			status (result) should be === (BAD_REQUEST);
		}
	}
	
	it should "enroll a Subscriber when given enough context" in
	{
		runningAction {
			val result = EnrollmentController.enroll (
				FakeRequest ().withFormUrlEncodedBody (
					"name" -> "Bob",
					"password" -> "guess!",
					"phone" -> "813-555-1212"
					)
				);
			
			status (result) should be === (SEE_OTHER);
			}
	}
}
