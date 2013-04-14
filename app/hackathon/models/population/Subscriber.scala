/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models
package population

import scalaz._

import security._


/**
 * The '''Subscriber''' type defines the Domain Object Model concept of a
 * person who has enrolled in the Evac Assist system and is capable of
 * interacting with it.
 *
 * @author svickers
 *
 */
trait Subscriber
	extends Entity
{
	/// Class Imports
	import syntax.std.boolean._
	
	
	/// Class Types
	type EntityType <: Subscriber
	
	protected trait SubscriberLenses
		extends EntityLenses
	{
		/// Class Imports
		import Credentials._
		
		
		def credentials : EntityType @> Credentials;
		def phone : EntityType @> String;
		
		def account : EntityType @> String =
			credentials >=> CredentialsLenses.account;
		def password : EntityType @> SecureHash =
			credentials >=> CredentialsLenses.password;
	}


	/// Instance Properties
	override protected val lenses : SubscriberLenses;


	/// Constructor Body
	import lenses._


	def changeCredentials (replacement : Credentials) : Subscriber =
        credentials.get (self).matches (replacement).fold (
			self,
			credentials.set (self, replacement)
			);
	
	
	def resetPassword (hashed : SecureHash) : Subscriber =
		password.set (self, hashed);
}


trait SubscriberSpecification[T <: Subscriber]
	extends Specification[T]
{
	/// Instance Properties
	lazy val account : Option[String] = None;
	lazy val credentials : Option[Credentials] = None;
	lazy val phone : Option[String] = None;
	
	
	// TODO: use the provided properties to filter 'candidate'
	def apply (candidate : T) : Boolean =
		true
}
