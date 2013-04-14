/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.spatial


/**
 * The '''Longitude''' type is a [[hackathon.models.spatial.Coordinate]]
 * defining the geo-spatial [[http://en.wikipedia.org/wiki/Longitude]] of a
 * point on Earth.
 *
 * @author svickers
 *
 */
final case class Longitude (
	override val value : Double
	)
	extends Coordinate[Longitude]
{

}


object Longitude
	extends CoordinateExtracter
{
	/// Instance properties
	override val range = (-180.0, 180.0);
	
	
	def unapply (that : Any) : Option[Longitude] =
		that match {
			case l : Longitude =>
				Some (l);
				
			case d : Double =>
				whenWithinDomain (d) {
					Longitude.apply;
					}
				
			case s : String =>
				whenWithinDomain(s) {
					Longitude.apply;
					}
				
			case _ =>
				None;
			}
}
