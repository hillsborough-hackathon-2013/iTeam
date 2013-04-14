/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */

package hackathon

import org.scalatest._
import org.scalatest.matchers._


/**
 * The '''ProjectSpec''' type defines the common ScalaTest behaviour for ''all''
 * unit tests defined in the system.
 *
 * @author svickers
 *
 */
trait ProjectSpec
	extends FlatSpec
		with MustMatchers
		with ShouldMatchers
{
	/// Class Types
	class AssignableToMatcher[T] (target : Class[T])
		extends Matcher[Any]
	{
		/// Instance Properties
		private lazy val targetTypeName = target.getName;
		
		
		override def apply (left : Any) : MatchResult =
		{
			val leftType = left.asInstanceOf[AnyRef].getClass;
			val leftTypeName = leftType.getName;
			val failureSuffix =
				leftTypeName + " is not assignable to " + targetTypeName;
			val invertedFailureSuffix =
				leftTypeName + " is assignable to " + targetTypeName;
			
			MatchResult (
				target.isAssignableFrom (leftType),
				"The type " + failureSuffix,
				"The type " + invertedFailureSuffix,
				"the type " + failureSuffix,
				"the type " + invertedFailureSuffix
				);
		}
	}
	
	
	protected def beAssignableTo[T] (target : Class[T]) =
		new AssignableToMatcher[T] (target);
}
