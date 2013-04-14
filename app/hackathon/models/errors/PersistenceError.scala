/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 13, 2013
 */
package hackathon.models.errors

/**
 * The '''PersistenceError''' type is produced when there is a problem
 * interacting with the persistent store.
 *
 * @author svickers
 *
 */
final case class PersistenceError (
	override val message : String
	)
	extends DomainError
