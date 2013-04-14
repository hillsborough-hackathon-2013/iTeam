/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.repositories
package population

import scalaz._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import play.api.test._
import play.api.test.Helpers._

import hackathon.models._

import population._
import security._


/**
 * The '''SquerylSubscriberRepositorySpec''' type verifies the
 * [[hackathon.repositories.population.SquerylSubscriberRepository]] for
 * expected behaviour.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class SquerylSubscriberRepositorySpec
	extends RepositorySpec
{
	/// Class Imports
	import SquerylSchema._
	
	/// Testing Collaborators
	lazy val repository = new SquerylSubscriberRepository;
	
	
	"The SubscriberRepository" should "be able to insert a Subscriber" in
	{
		val unsaved = createSubscriber () copy (id = testGenerator ()) touch;
		
		usingDataBase {
			val inserted = subscribers insert (unsaved);
			
			inserted.id should not be === (0);
			inserted.isPersisted should be === (true);
			}
	}
	
	it should "be able to save a Subscriber" in
	{
		val unsaved = createSubscriber ();
		
		usingDataBase {
			val saved = repository.save (unsaved) match {
				case \/- (ok) =>
					ok;
					
				case -\/ (error) =>
					fail (error.message);
				}
			
			saved.id should not be === (0);
			saved.isPersisted should be === (true);
			}
	}
	
	it should "be able to look up a Subscriber" in
	{
		val unsaved = createSubscriber ();
		
		usingDataBase {
			val saved = repository.save (unsaved) match {
				case \/- (ok) =>
					ok;
					
				case -\/ (error) =>
					fail (error.message);
				}
			val resolved = repository.lookup (saved.externalId);
			
			resolved should be ('right);
			}
		
	}
	
	
	def createSubscriber () =
		SquerylSubscriber (
			account = "test@example.com",
			password = MD5.createHashFrom ("hello, world!").content,
			phone = "813-555-1212"
			);
}
