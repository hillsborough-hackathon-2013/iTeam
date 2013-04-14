/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models.errors

import scala.reflect.ClassTag


/**
 * The '''ObjectNotFoundError''' type is encountered when a Domain type
 * ''should have'' existed, but didn't for whatever reason.
 *
 * @author svickers
 *
 */
final case class ObjectNotFoundError[T : ClassTag] (
	override val message : String
	)
	extends DomainError
{

}
