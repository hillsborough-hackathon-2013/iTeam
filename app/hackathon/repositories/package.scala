/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon

import org.squeryl.PrimitiveTypeMode


/**
 * The '''repositories''' `package` defines the types involved in managing the
 * persistent storage of Domain Object Model types defined in
 * [[hackathon.models]].
 *
 * @author svickers
 *
 */
package object repositories
{
	/// Class Types
	type DateTime = hackathon.models.DateTime
	type PersistentKey = Int
	
	
	/**
	 * The '''SquerylTypeMode''' defines the Squeryl column mapping mode used
	 * by ''all'' repositories.
	 */
	object SquerylTypeMode
		extends PrimitiveTypeMode
}
