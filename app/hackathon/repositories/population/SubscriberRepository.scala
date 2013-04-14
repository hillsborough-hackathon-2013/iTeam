/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package hackathon.repositories
package population

import scalaz._

import hackathon.models.{
	Identifier,
	Specification
	}
import hackathon.models.population.{
	Subscriber,
	SubscriberSpecification
	}


/**
 * The '''SubscriberRepository''' type defines the contract for managing the
 * persistent representation of [[hackathon.models.population.Subscriber]]s.
 *
 * @author svickers
 *
 */
trait SubscriberRepository[T <: Subscriber]
	extends Repository[T, SubscriberSpecification[T]]
{
}
