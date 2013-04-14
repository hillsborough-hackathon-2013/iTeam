/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.repositories

import java.util.concurrent.atomic.AtomicInteger

import play.api.test._
import play.api.test.Helpers._

import hackathon.ProjectSpec


/**
 * The '''RepositorySpec''' type defines unit test functionality common to
 * ''all'' repositories.
 *
 * @author svickers
 *
 */
trait RepositorySpec
	extends ProjectSpec
{
	/// Class Imports
	import SquerylTypeMode._
	
	
	/// Instance Properties
	implicit val testGenerator = new KeyGenerator {
		private val globalKey = new AtomicInteger;
		
		override def apply () : PersistentKey = globalKey.addAndGet (1);
		}
	
	
	def usingDataBase (block : => Unit) : Unit =
		running (FakeApplication (additionalConfiguration = inMemoryDatabase ())) {
			inTransaction {
				block;
				}
			}
}
