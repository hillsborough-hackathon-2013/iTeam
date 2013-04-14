/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 13, 2013
 */
package hackathon.repositories

import scala.util.control.Exception._

import scalaz._

import org.squeryl.{
	KeyedEntity,
	Table
	}

import hackathon.models._
import hackathon.models.errors.{
	DomainError,
	PersistenceError
	}


/**
 * The '''RepositoryLike''' type is a Squeryl-based convenience `trait` used
 * for providing [[hackathon.repositories.Repository]] functionality.
 *
 * @author svickers
 *
 */
trait RepositoryLike[T <: Entity with KeyedEntity[_], SpecType <: Specification[T]]
	extends DisjunctionFunctions
{
	/// Class Imports
	import syntax.std.boolean._
	import SquerylTypeMode._
	
	
	def persist (table : Table[T])
		(block : => T)
		: DomainError \/ T =
	{
		val prepped = block;
		
		allCatch either insertOrUpdate (table, prepped) match {
			case Left (error) =>
				-\/ (PersistenceError (error.getMessage));
				
			case Right (_) =>
				\/- (prepped);
			}
	}
	
	
	private def insertOrUpdate ( table : Table[T], entity : T) : T =
	{
		entity.isPersisted.fold (
			table.update (entity),
			table.insert (entity)
			);
		
		entity;
	}
}
