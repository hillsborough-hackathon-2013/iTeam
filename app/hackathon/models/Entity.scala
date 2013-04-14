/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon.models

import scalaz._


/**
 * The '''Entity''' type reifies the Domain Driven Design concept of
 * a type which has identity defined in terms ''other than'' its attributes.
 *
 * @author svickers
 *
 */
trait Entity
{
	/// Class Types
	type EntityType <: Entity
	
	protected trait EntityLenses
	{
		/// Class Imports
		import ModificationTimes._
		
		
		/**
		 * The lensFor method is a convenience method used to define
		 * [[scalaz.Lens]]es for [[hackathon.models.Entity]] types.
		 * 
		 * Sample use:
		 * {{{
		 * val field = lensFor[String] (
		 * 		(foo, v) => foo.copy (field = v),
		 *   	_.field
		 *    );
		 * }}}
		 */
		def lensFor[A] (
			set : (EntityType, A) => EntityType,
			get : EntityType => A
			)
			: EntityType @> A =
			Lens.lensu[EntityType, A] (set, get);
		
		
        def id : EntityType @> Identifier;
		def timestamps : EntityType @> Option[ModificationTimes];
	}


	/// Instance Properties
	protected val lenses : EntityLenses;
	protected val self : EntityType;
	
	
	/// Constructor Body
	import lenses._
	
	
	def touch () : EntityType = timestamps.mod (
		_ match {
			case Some (times) =>
				Some (times copy (lastChanged = Now ()));
				
			case None =>
				Some (ModificationTimes (Now (), Now ()));
			},
		self
		);
}

