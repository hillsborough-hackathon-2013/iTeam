/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models


/**
 * The '''Now''' type centralizes the creation of object time stamps so that
 * this knowledge does have to be spread about the system.
 *
 * @author svickers
 *
 */
object Now
{
	def apply () : DateTime = new DateTime ();
}
