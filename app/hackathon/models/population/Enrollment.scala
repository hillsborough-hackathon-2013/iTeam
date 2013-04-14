/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models
package population


/**
 * The '''Enrollment''' type is a Domain Object Model type capturing the
 * concept of an attempt to enroll into the system as a
 * [[hackathon.models.population.Subscriber]].
 *
 * @author svickers
 *
 */
final case class Enrollment (
	val name : String,
	val password : String,
	val phone : String,
	val when : DateTime
	)
{

}
