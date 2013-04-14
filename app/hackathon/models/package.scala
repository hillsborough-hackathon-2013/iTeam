/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon

import scalaz._


/**
 * =Overview=
 * 
 * The '''models''' `package` defines types related to the domain model used
 * in Evac Assist.  There are two main implementation techniques a developer
 * needs to be aware of when using the types defined here.
 * 
 * ==Value Objects==
 * 
 * A
 * [[http://stochastyk.blogspot.com/2008/05/value-objects-in-domain-driven-design.html Value Object]]
 * is, as the referenced article describes and originally defined in the
 * [[http://www.amazon.com/exec/obidos/ASIN/0321125215/domainlanguag-20 Evans book]],
 * a type which has no intrinsic identity beyond that which its properties
 * provide.
 * 
 * In the `hackathon.models` code base, these types are reified in the form
 * of `case class`es and are ''immutable'' once created.
 * 
 * ==Entity Types==
 * 
 * Abstractions which have an identity not strictly based on their properties
 * are embodied in ''entity'' types.  To insulate persistent storage concerns
 * from other components, entities are defined as `trait`s and implemented
 * in a manner most applicable/optimal for the storage technology involved.
 * 
 * Something to note when familiarizing oneself with entity types are a lack of
 * `public` ''property accessors''.  This is by design, as entity attributes
 * are an implementation detail and ''not'' to be depended upon by types
 * collaborating with them.
 * 
 * ==Aggregate Roots==
 * 
 * As with Entity Types above, Aggregate Root types have identity which
 * transcend their properties.  What differentiates the two type categories
 * is the fact that Aggregate Roots is defined in terms of a ''root entity''
 * as well as other entities and value objects, so long as any acceptable
 * modification to an Aggregate Root results in ''all'' of its constituent
 * invariants being completely consistent within a single transaction.
 * 
 * This requirement has implications on the use of Aggregate Root types and,
 * as such, it is expected that there may be Aggregate Roots which appear
 * very similar.  When these situations exist, they are only allowed to
 * remain when a documented reason justifies the context.
 * 
 * @author svickers
 *
 */
package object models
{
	/// Class Types
	type <^>[+L, +R] = Either[L, R]
	type <\/>[+L, +R] = Validation[L, R]
	
	type DateTime = java.util.Date
}
