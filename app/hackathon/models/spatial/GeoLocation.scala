/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon.models.spatial

import java.net.URI

import scalaz._


/**
 * The '''GeoLocation''' type is a Value Object which objectifies a specific
 * point on the surface of the planet by its '''longitude''' and
 * '''latitude'''.
 *
 * @author svickers
 *
 */
final case class GeoLocation (
	val latitude : Latitude,
	val longitude : Longitude
	)
{
	/// Class Imports
	import GeoLocation._
	
	
	/**
	 * The distance method computes the
	 * [[http://en.wikipedia.org/wiki/Meter meters]] between this
	 * '''GeoLocation''' and the '''other''' one given.
	 * 
	 * All calculations are in meters.  The algorithm used here is known as
	 * the [[http://en.wikipedia.org/wiki/Haversine_formula Haversine formula]].
	 */
	def distance (other : GeoLocation) : Int =
	{
		import scala.math._
		
		val latDelta = (latitude - other.latitude).toRadians;
		val longDelta = (longitude - other.longitude).toRadians;
		val a = (
			sin (latDelta / 2) * sin (latDelta / 2)
			+
			(
				cos (latitude.toRadians)
				*
				cos (other.latitude.toRadians)
				*
				sin (longDelta / 2) * sin (longDelta / 2)
				)
			);
		val c = 2 * atan2 (sqrt (a), sqrt (1.0 - a));
		
		return ((meanRadius * c).toInt);
	}
	
	
	def toUri () : URI =
		new URI ("%s:%f,%f".format (scheme, latitude.value, longitude.value));
}


object GeoLocation
{
	/// Class Imports
	import syntax.validation._
	
	
	/// Class Types
	private object BadScheme
	{
		def unapply (uriText : String) : Boolean =
			!uriText.startsWith (scheme + ":");
	}
	
	
	/// Instance Properties
	val scheme = "geo";
	private val meanRadius = 6371000;
	private val LocationPoints =
		"""^%s:(-?\d+\.\d+),(-?\d+\.\d+)$""".format (scheme).r;
	
	
	def apply (uri : URI) : Validation[String, GeoLocation] =
		apply (uri.toString);
	
	
	def apply (candidate : String) : Validation[String, GeoLocation] =
		candidate.toLowerCase match {
			case LocationPoints (Latitude (lat), Longitude (long)) =>
				GeoLocation (lat, long).success;
				
			case BadScheme () =>
				"Unsupported URI: %s".format (candidate).fail;
				
			case _ =>
				"Invalid '%s': %s".format (scheme, candidate).fail;
			}
	
	
	/// Implicit Conversions
	/**
	 * For '''GeoLocation''' [[scalaz.MetricSpace]] `distance`, the unit and
	 * all calculations are in meters.
	 */
	implicit def metricSpace : MetricSpace[GeoLocation] =
		new MetricSpace[GeoLocation] {
			override def distance (x : GeoLocation, y : GeoLocation) : Int =
				x.distance (y);
			}
}
