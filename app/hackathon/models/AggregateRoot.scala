/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon.models


/**
 * The '''AggregateRoot''' type reifies the Domain Driven Design
 * concept of an aggregate root in the model.
 *
 * @author svickers
 *
 */
trait AggregateRoot
{
	/// Instance Properties
	protected def root : Entity;
}
