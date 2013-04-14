/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 14, 2013
 */
package controllers
package location

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import play.api.test._
import play.api.test.Helpers._

import hackathon.ProjectSpec


/**
 * The '''MapControllerSpec''' type defines the unit tests which certify the
 * [[controllers.location.MapController]] Play! controller for suitability in
 * producing cartographic maps.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class MapControllerSpec
	extends ControllerSpec
{
	/// Testing Collaborators
	val requestUri = "/location/map";
	
	
	"The renderMap Action" should "require longitude, latitude, and zoom" in
	{
		runningAction {
			val result = MapController.renderMap (FakeRequest ());
			
			status (result) should be === (BAD_REQUEST);
			}
	}
	
	it should "detect invalid required parameters" in
	{
		runningAction {
			val result = MapController.renderMap (
				FakeRequest (
					"GET",
					requestUri + "?latitude=bad&longitude=12345!&zoom=-1"
					)
				);
			
			status (result) should be === (BAD_REQUEST);
			}
	}
	
	it should "produce an HTML snippet when all parameters are valid" in
	{
		runningAction {
			val result = MapController.renderMap (
				FakeRequest (
					"GET",
					requestUri + "?latitude=-82.328796&longitude=27.97189&zoom=80"
					)
				);
			
			status (result) should be === (OK);
			}
	}
}
