/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models.errors


/**
 * The '''DomainError''' type defines the category of objects which represent
 * errors in a Domain Object Model collaboration.
 *
 * @author svickers
 *
 */
trait DomainError
{
	/// Instance Properties
	def message : String;
}
