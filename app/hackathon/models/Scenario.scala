/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 12, 2013
 */
package hackathon.models

import java.util.concurrent.Callable

import scalaz._


/**
 * The '''Scenario''' type embodies a UML Use-Case scenario in code.
 * What this means is that concrete '''Scenario'''s are models of
 * the behaviour defined in Use-Case requirements which, by
 * [[http://en.wikipedia.org/wiki/Use_case this definition]] can be
 * thought of as:
 * 
 * <blockquote>
 * a use case is a list of steps, typically defining interactions between a
 * role (known in UML as an "actor") and a system, to achieve a goal. The actor
 * can be a human or an external system.
 * </blockquote>
 * 
 * A key concept expressed in the aforementioned definition is ''a list of
 * steps''.	In Scalax, we capture this as being a '''Scenario'''
 * which is responsible for coordinating the activities of
 * [[com.tubros.scalax.usecase.Role]]s participating in the Use-Case.
 *
 * @author svickers
 *
 * @see Callable
 * @see Role
 */
trait Scenario[+A]
	extends (() => A)
{
	/// Self Type Constraints
	self =>


	/// Class Types
	type ResultType = A


	/**
	 * Similar to the `map<` method, flatMap requires a
	 * '''f'''unctor which consumes an ''A'' and produces a
	 * `Scenario[B]` instance, where ''B'' does not have to be
	 * related to ''A'' in any way.
	 */
	def flatMap[B] (f : A => Scenario[B]) : Scenario[B] = f (apply ());


	/**
	 * The foreach method is provided for conveniently using the result of a
	 * '''Scenario''' in an "effectual" way.
	 */
	def foreach[U] (f : A => U) : Unit = f (apply ());


	/**
	 * The map monadic method applies a given '''f'''unctor to the result of
	 * '''this''' '''Scenario''', thus allowing "chaining" of
	 * logic "within" a '''Scenario'''.
	 */
	def map[B] (f : A => B) : Scenario[B] = new Scenario[B] {
		override def apply = f (self ());
		}
}


object Scenario
{
	/// Class Types
	private class ScalazSupport
		extends Monad[Scenario]
			with Each[Scenario]
	{
		override def ap[A, B] (fa : => Scenario[A]) (f : => Scenario[A => B])
			: Scenario[B] = f map (_ (fa ()));
			
		override def bind[A, B] (s : Scenario[A]) (f : A => Scenario[B])
			: Scenario[B] = s flatMap (f);
		
		override def each[A] (s : Scenario[A]) (f : A => Unit) = s foreach (f);
		
		override def point[A] (a : => A) = Scenario (a);
		
		override def map[A, B] (fa : Scenario[A]) (f : A => B)
			: Scenario[B] = fa map (f);
	}
	
	
	/**
	 * The '''SupportsLifting''' type is used as a
	 * ''type class'' constraining the set of ''M'' generic types to
	 * be those which we specifically support when `lift`ing
	 * [[com.tubros.scalax.usecase.Scenario]]s.
	 */
	sealed trait SupportsLifting[M[_, _], A, B, C]
	{
		def lift : M[A, B] => Scenario[M[A, C]];
	}


	implicit class EitherSupportsLifting[A, B, C] (
		val scenario : Scenario[A <^> (B => C)]
		)
		extends SupportsLifting[<^>, A, B, C]
	{
		override def lift : (A <^> B) => Scenario[A <^> C] =
			in => Scenario[A <^> C] {
				in.right.flatMap {
					b =>
	
					scenario ().right.map ((f : B => C) => f (b));
					}
				}
	}


	implicit class ValidationSupportsLifting[A, B, C] (
		val scenario : Scenario[A <\/> (B => C)]
		)
		extends SupportsLifting[<\/>, A, B, C]
	{
		override def lift : (A <\/> B) => Scenario[A <\/> C] =
			in => Scenario[A <\/> C] {
				in.flatMap {
					good =>
	
					scenario ().map ((f : B => C) => f (good));
					}
				}
	}


	/**
	 * The apply method allows for creation of a '''Scenario'''
	 * from an arbitrary '''expr'''ession in the functional style.
	 */
	def apply[T] (expr : => T) : Scenario[T] = new Scenario[T] {
		override def apply = expr;
		}
	
	
	/// Implicit Conversions
	implicit def ScenarioCallable[A] (s : Scenario[A]) : Callable[A] =
		new Callable[A] {
			override def call : A = s ();
			}


	implicit def ScenarioEach : Each[Scenario] = new ScalazSupport;


	implicit def ScenarioMonad : Monad[Scenario] = new ScalazSupport;

	
	implicit def ScenarioMonoid[A : Monoid] : Monoid[Scenario[A]] =
		new Monoid[Scenario[A]] {
			override lazy val zero : Scenario[A] = Scenario {
				implicitly[Monoid[A]].zero;
				}
			
			override def append (first : Scenario[A], second : => Scenario[A])
				: Scenario[A] = Scenario {
					implicitly[Monoid[A]].append (first (), second ());
					}
		}
}

