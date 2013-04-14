/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package hackathon.repositories

import scalaz._

import hackathon.models._
import hackathon.models.errors.DomainError


/**
 * The '''Repository''' type defines behaviour common to ''all'' persistent
 * store repositories.
 *
 * @author svickers
 *
 */
trait Repository[T <: Entity, SpecType <: Specification[T]]
{
	def lookup (id : Identifier) : DomainError \/ T;
	
	
	def query (spec : SpecType) : DomainError \/ Seq[T];
	
	
	def save (instance : T)
		(implicit kg : KeyGenerator)
		: DomainError \/ T;
}
