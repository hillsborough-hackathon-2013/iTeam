/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models


/**
 * The '''Specification''' type is a contract for identifying Domain Object
 * Model types which satisfy the requirements of concrete implementations.
 *
 * @author svickers
 *
 */
trait Specification[T]
	extends (T => Boolean)
{

}


object Specification
{
	def apply[T] (inline : T => Boolean) : Specification[T] =
		new Specification[T] {
			override def apply (candidate : T) : Boolean =
				inline (candidate);
			}
}
