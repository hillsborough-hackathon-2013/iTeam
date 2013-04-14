/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package hackathon.repositories
package population

import java.util.UUID.randomUUID

import scala.util.control.Exception._

import scalaz._

import org.squeryl._

import hackathon.models._
import hackathon.models.errors._

import population.{
	Subscriber,
	SubscriberSpecification
	}
import security._


/**
 * The '''SquerylSubscriberRepository''' type uses Squeryl-based types to
 * fulfill the [[hackathon.repositories.population.SubscriberRepository]]
 * contract.
 *
 * @author svickers
 *
 */
class SquerylSubscriberRepository
	extends SubscriberRepository[SquerylSubscriber]
		with RepositoryLike[
			SquerylSubscriber,
			SubscriberSpecification[SquerylSubscriber]
			]
{
	/// Class Imports
	import syntax.std.boolean._
	import SquerylSchema._
	import SquerylTypeMode._
	
	
	override def lookup (id : Identifier) : DomainError \/ SquerylSubscriber =
	{
		val key = id.uri.getSchemeSpecificPart;
		
		subscribers.where (s => s.entityId === key).headOption match {
			case Some (result) =>
				\/- (result);
				
			case None =>
				-\/ (
					ObjectNotFoundError[SquerylSubscriber] (
						"Subscriber not found"
						)
					);
			}
	}
	
	
	def query (spec : SubscriberSpecification[SquerylSubscriber])
		: DomainError \/ Seq[SquerylSubscriber] =
		-\/ (ObjectNotFoundError[Subscriber] ("TBD"));
	
	
	override def save (instance : SquerylSubscriber)
		(implicit kg : KeyGenerator)
		: DomainError \/ SquerylSubscriber =
	{
		this.persist (subscribers) {
			instance.isPersisted.fold (
				instance.touch,
				instance.copy (
					id = kg (),
					createdOn = Some (Now ()),
					lastChanged = Some (Now ())
					)
				);
			}
	}
}


case class SquerylSubscriber (
	override val id : PersistentKey,
	val entityId : String,
	val account : String,
	val password : Array[Byte],
	val phone : String,
	val createdOn : Option[DateTime] = None,
	val lastChanged : Option[DateTime] = None
	)
	extends KeyedEntity[PersistentKey]
		with Subscriber
{
	/// Class Imports
	import Scalaz._
	
	
	/// Class Types
	type EntityType = SquerylSubscriber
	
	
	/// Instance Properties
	lazy val externalId : Identifier =
		Identifier ("subscriber:%s".format (entityId));
	
	override val lenses = new SubscriberLenses {
		override def credentials = lensFor[Credentials] (
			set = (s, creds) => s.copy (
				account = creds.account,
				password = creds.password.content
				),
			get = s => Credentials (s.account, MD5 (s.password))
			);
		
		override def id = lensFor[Identifier] (
			set = (s, i) => s.copy (entityId = i.uri.getSchemeSpecificPart),
			get = s => externalId
			);
		
		override def phone = lensFor[String] (
			set = (s, v) => s.copy (phone = v),
			get = _.phone
			);
		
		override def timestamps = lensFor[Option[ModificationTimes]] (
			set = (s, v) => s.copy (
				createdOn = v map (_.createdOn),
				lastChanged = v map (_.lastChanged)
				),
			get = s => (createdOn |@| lastChanged) { ModificationTimes.apply }
			);
		}
		
	override val self = this;
}


object SquerylSubscriber
{
	def apply (account : String, password : Array[Byte], phone : String)
		: SquerylSubscriber =
		SquerylSubscriber (0, randomUUID.toString, account, password, phone);
}
