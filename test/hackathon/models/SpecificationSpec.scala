/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec


/**
 * The '''SpecificationSpec''' type defines the unit tests which verify the
 * [[hackathon.models.Specification]] abstraction.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class SpecificationSpec
	extends ProjectSpec
{
	/// Class Types
	case class NameAndAge (val name : String, val age : Int)
	
	
	"A Specification" should "be able to be defined 'in-line" in
	{
		val all = List (
			NameAndAge ("bob", 21),
			NameAndAge ("sara", 19)
			);
		val over21 = Specification[NameAndAge] (_.age >= 21);
		
		all.filter (person => over21 (person)) should have size (1);
	}
}
