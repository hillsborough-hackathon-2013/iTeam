/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package hackathon.repositories

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._ 

import population._


/**
 * The '''SquerylSchema''' type defines the Squeryl schema types for
 * application managed data base tables.  The DDL for these tables is
 * externalized and resides in Play!'s `conf/evolutions` area.
 *
 * @author svickers
 *
 */
object SquerylSchema
	extends Schema
{
	/// Table Declarations
	val subscribers = table[SquerylSubscriber] ("subscriber");
	
	
	/// Table Mapping Customizations
	on (subscribers) {
		table =>
			
		declare (
			table.id is (unique)
			);
		}
}
