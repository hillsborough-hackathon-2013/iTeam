/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models

import scalaz._


/**
 * The '''ModificationTimes''' type is a value object which reifies when an
 * [[hackathon.models.Entity]] was created and last changed.
 *
 * @author svickers
 *
 */
final case class ModificationTimes (
	val createdOn : DateTime,
	val lastChanged : DateTime
	)
{

}
