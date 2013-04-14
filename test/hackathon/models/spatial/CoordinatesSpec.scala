/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */

package hackathon.models.spatial

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec


/**
 * The '''CoordinatesSpec''' type defines the unit tests for the three
 * closely-related [[hackathon.models.spatial.Coordinate]] types.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class CoordinatesSpec
	extends ProjectSpec
{
	"Longitude" should "be a Coordinate type" in
	{
		Longitude (0.0) should beAssignableTo (classOf[Coordinate[Longitude]]);
	}
	
	it should "know its valid range of values" in
	{
		val (min, max) = Longitude.range;
		
		min must not be < (-180.0);
		max must not be > (180.0);
		min must be < (max);
	}
	
	"Latitude" should "be a Coordinate type" in
	{
		Latitude (0.0) should beAssignableTo (classOf[Coordinate[Latitude]]);
	}
	
	it should "know its valid range of values" in
	{
		val (min, max) = Latitude.range;
		
		min must not be < (-90.0);
		max must not be > (90.0);
		min must be < (max);
	}
}
