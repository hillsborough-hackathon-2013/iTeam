/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.spatial

import java.net.URI

import scalaz._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec


/**
 * The '''GeoLocationSpec''' type defines the unit tests used to certify the
 * [[hackathon.models.spatial.GeoLocation]] Domain Object Model type.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class GeoLocationSpec
	extends ProjectSpec
{
	/// Class Imports
	import syntax.metricSpace._
	
	
	/// Instance Properties
	val largoUri = new URI ("geo:27.916463,-82.788506");
	val tampaLongitude = Longitude (27.966508);
	val tampaLatitude = Latitude (-82.459946);
	
	
	"A GeoLocation" should "be able to create a URI" in
	{
		val loc = GeoLocation (tampaLatitude, tampaLongitude);
		
		/// As specified in http://tools.ietf.org/html/rfc5870 the "geo" URI
		/// must be of the form: latitude, longitude.
		loc.toUri should be === (
			new URI (
				"geo:%s,%s".format (tampaLatitude.value, tampaLongitude.value)
				)
			)
	}
	
	it should "detect URI's with an unsupported scheme" in
	{
		GeoLocation (new URI ("testScheme:0,0")) should be ('failure);
	}
	
	it should "detect invalid URI's" in
	{
		GeoLocation ("geo:bad,numbers") should be ('failure);
	}
	
	it should "support distance calcuations (in meters)" in
	{
		val expectedDistance = 32750;
		val tampa = GeoLocation (new URI ("geo:27.966508,-82.459946"));
		val largo = GeoLocation (largoUri);
		
		tampa should be ('success);
		largo should be ('success);
		
		tampa foreach {
			t =>
				
			largo foreach {
				l =>
					
				((t <===> l) - expectedDistance) should be <= (1);
				}
			}
	}
}
