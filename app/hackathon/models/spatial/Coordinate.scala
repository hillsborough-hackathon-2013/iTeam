/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon.models.spatial

import scala.util.control.Exception._

import scalaz._


/**
 * The '''Coordinate''' type defines a scalar value used within a
 * [[hackathon.models.spatial.GeoLocation]], such as ''longitude'' and
 * ''latitude''.
 *
 * @author svickers
 *
 */
trait Coordinate[T <: Coordinate[T]]
{
	/// Instance Properties
	lazy val toRadians : Double = value.toRadians;
	val value : Double;
	
	
	def - (other : T) : Double = value - other.value;
	def + (other : T) : Double = value + other.value;
}


trait CoordinateExtracter
{
	/// Class Imports
	import syntax.std.boolean._
	
	
	/// Instance Properties
	protected def range : (Double, Double);
	
	
	protected def whenWithinDomain[R] (candidate : Double)
		(block : Double => R)
		: Option[R] =
		(range._1 <= candidate && range._2 >= candidate).option (
			block (candidate)
			);
	
	
	protected def whenWithinDomain[R] (candidate : String)
		(block : Double => R)
		: Option[R] =
		catching (classOf[NumberFormatException]).opt (candidate.toDouble).flatMap {
			whenWithinDomain (_) (block);
			}
}
