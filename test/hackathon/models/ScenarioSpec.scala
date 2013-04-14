/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models

import scala.language._

import scalaz._

import org.joda.time.LocalDate
import org.junit._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec


/**
 * The '''ScenarioSpec''' type serves as an exemplar for the
 * capabilities provided by the [[hackathon.models.Scenario]] type.
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class ScenarioSpec
	extends ProjectSpec
{
	/// Class Types
	case class Person (name : String, birthday : LocalDate)
	
	case class Song (title : String)
	
	trait PickSong
	{
		def songFor (person : Person) : Option[Song];
	}
	
	trait PickSongByBirthday
		extends PickSong
	{
		override def songFor (person : Person) =
			person.birthday.getMonthOfYear match {
				case 7 =>
					Some (Song ("Born on the 4th of July!"));
					
				case 12 =>
					Some (Song ("Christmasy song"));
			
				case _ =>
					None;
				}
	}
	
	abstract class ComplexScenario (
		val person : Person
		)
		extends Scenario[String <\/> Song]
			with PickSong
	{
		/// Class Imports
		import Scalaz._
		
		
		override def apply () : ResultType =
		{
			songFor (person).fold ("Nothing applicable".fail[Song]) (_.success);
		}
	}
	
	
	"A Scenario" should "be able to be defined with Either types" in
	{
		val scenario = Scenario[String <^> Int] {
			Right (42);
			}
		
		scenario () should be === (Right (42));
	}
	
	it should "handle Either types which are 'Left'" in
	{
		val lefty = Scenario[String <^> Int] {
			Left ("Just an example");
			}
		
		lefty () should be ('left);
	}
	
	it should "also support Validation types" in
	{
		import scalaz._, Scalaz._
		
		val scenario = Scenario[Int <\/> String] {
			"hello!".success;
			}
		
		scenario () should be ('success);
	}
	
	it should "support monadic operations" in
	{
		import scalaz._, Scalaz._
		
		val scenario = Scenario (1) >>= (n => Scenario (n.toString));
		
		scenario () should be ("1");
	}
	
	it should "be able to 'lift' Validation-based results when successful" in
	{
		import scalaz._, Scalaz._
		
		val doubleAgain = (2 * (_ : Int)).success[String];
		val processor = Scenario (doubleAgain);
        val scenario = Scenario[String <\/> Int] (1.success) >>=
			Scenario ((2 * (_ : Int)).success[String]).lift >>=
			Scenario (doubleAgain).lift;
        
        scenario () should be === (Success (4));
	}
	
	it should "be able to 'lift' Validation-based results that fail" in
	{
		import scalaz._, Scalaz._
		
		val message = "Bad!";
        val scenario = Scenario[String <\/> Int] (message.fail) >>=
			Scenario ((2 * (_ : Int)).success[String]).lift;
        
        scenario () should be === (Failure (message));
	}
	
	it should "support 'lift'ing Either-based Scenarios" in
	{
		import scalaz._, Scalaz._
		
		val f : Int <^> (Double => Double) = Right ((d : Double) => 2 * d);
		val scenario = Scenario[Int <^> Double] (Right (42.0)) >>=
			Scenario (f).lift;
		
		scenario () should be === (Right (2.0 * 42.0));
	}
	
	it should "support applying functors only to successes" in
	{
		import scalaz._, Scalaz._
		
		// To apply a pure functor to a Scenario which is a Validation (or
		// an Either), use the Scalaz Bifunctor concept.
		val scenario = Scenario[String <\/> Int] (99.success[String]);
		val result = scenario () :-> ((_ : Int).toDouble + 100);
		
		result should be === (Success (199.0));
	}
	
	it should "allow for complex Role-based interactions" in
	{
		val scenario = new ComplexScenario (
			Person ("Bob July", new LocalDate ("2012-07-04"))
			) with PickSongByBirthday;
		
		val result = scenario ();
		
		result must not be === (null);
		result should be ('success);
		result foreach {
			song =>
				
			song must not be === (null);
			song.title should not be ('empty);
			}
	}
	
	it should "gracefully handle failed invocations" in
	{
		val scenario = new ComplexScenario (
			Person ("No Song For Me", new LocalDate ("2012-09-01"))
			) with PickSongByBirthday;
		val result = scenario ();
		
		result must not be === (null);
		result should be === (Failure ("Nothing applicable"));
	}
}

