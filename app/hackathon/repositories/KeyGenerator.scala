/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 13, 2013
 */
package hackathon.repositories


/**
 * The '''KeyGenerator''' type defines how the persistence layer creates
 * unique [[hackathon.repositories.PersistentKey]]s.
 *
 * @author svickers
 *
 */
trait KeyGenerator
	extends (() => PersistentKey)
{
	
}
