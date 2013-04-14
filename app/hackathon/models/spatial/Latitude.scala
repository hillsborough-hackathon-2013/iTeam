/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.spatial


/**
 * The '''Latitude''' type * is a [[hackathon.models.spatial.Coordinate]]
 * defining the geo-spatial [[http://en.wikipedia.org/wiki/Latitude]] of a
 * point on Earth.
 *
 * @author svickers
 *
 */
final case class Latitude (
	override val value : Double
	)
	extends Coordinate[Latitude]
{

}

object Latitude
	extends CoordinateExtracter
{
	/// Instance properties
	override val range = (-90.0, 90.0);
	
	
	def unapply (that : Any) : Option[Latitude] =
		that match {
			case l : Latitude =>
				Some (l);
				
			case d : Double =>
				whenWithinDomain (d) {
					Latitude.apply;
					}
				
			case s : String =>
				whenWithinDomain(s) {
					Latitude.apply;
					}
				
			case _ =>
				None;
			}
}