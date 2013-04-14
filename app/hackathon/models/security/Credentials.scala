/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.security

import scalaz._


/**
 * The '''Credentials''' type objectifies an actor's ability to provide
 * authentication information suitable for determining access to some or all
 * parts of the system.
 *
 * @author svickers
 *
 */
final case class Credentials (
	val account : String,
	val password : SecureHash
	)
{
	def matches (provided : Credentials) : Boolean =
		account.trim ().equalsIgnoreCase (provided.account.trim ()) &&
		password.canEqual (provided.password) &&
		password.equals (provided.password);
}


object Credentials
{
	object CredentialsLenses
	{
		/// Class Imports
		import Lens._
		
		
		/// Instance Properties
		val account = lensu[Credentials, String] (
			set = (c, n) => c.copy (account = n),
			get = _.account
			);
			
		val password = lensu[Credentials, SecureHash] (
			set = (c, pw) => c.copy (password = pw),
			get = _.password
			);
	}
}