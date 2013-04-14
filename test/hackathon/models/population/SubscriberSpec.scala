/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 9, 2013
 */
package hackathon.models
package population

import scalaz._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec

import security._


/**
 * The '''SubscriberSpec''' type defines the unit tests which verify the
 * [[hackathon.models.population.Subscriber]] Domain Object Model abstraction.
 * Note that due to the [[hackathon.models.population.Subscriber]] being an
 * [[hackathon.models.Entity]], it is defined as a `trait`.  Therefore, the
 * types here may not reflect a concrete implementation found in other
 * packages.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class SubscriberSpec
	extends ProjectSpec
{
	/// Class Types
	case class SampleSubscriber (
		val id : Identifier,
		val credentials : Credentials,
		val phone : String,
		val timestamps : Option[ModificationTimes] = None
		)
		extends Subscriber
	{
		/// Class Types
		type EntityType = SampleSubscriber
		
		
		/// Instance Properties
		override val lenses = new SubscriberLenses {
			override def credentials = lensFor[Credentials] (
				set = (s, v) => s.copy (credentials = v),
				get = _.credentials
				);
			
			override def id = lensFor[Identifier] (
				set = (s, v) => s.copy (id = v),
				get = _.id
				);
			
			override def phone = lensFor[String] (
				set = (s, v) => s.copy (phone = v),
				get = _.phone
				);
			
			override def timestamps = lensFor[Option[ModificationTimes]] (
				set = (s, v) => s.copy (timestamps = v),
				get = _.timestamps
				);
			}
		
		override val self = this;
	}
	
	
	"A Subscriber" should "be able to change their Credentials" in
	{
		val subscriber = createSubscriber ();
		val updated = subscriber.changeCredentials (
			Credentials (
				"test@example.com",
				MD5.createHashFrom ("different")
				)
			);
		
		/*
		updated.id should be === (subscriber.id);
		updated.credentials should not be === (subscriber.credentials);
		*/
	}
	
	it should "be able to reset its password" in
	{
		val subscriber = createSubscriber ();
		val updated = subscriber.resetPassword (MD5.createHashFrom ("test"));
		
		/*
		updated.credentials should not be === (subscriber.credentials);
		*/
	}
	
	
	def createSubscriber () =
		SampleSubscriber (
			id = Identifier ("subscriber:test-key"),
			credentials = Credentials (
				"test@example.com",
				MD5.createHashFrom ("hello, world!")
				),
			phone = "813-555-1212"
			);
}
