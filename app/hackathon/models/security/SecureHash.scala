/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.security

import java.security._


/**
 * The '''SecureHash''' type defines the contract for all hashing algorithms
 * used in authentication mechanisms for external actors.
 *
 * @author svickers
 *
 */
sealed trait SecureHash
	extends Equals
{
	/// Instance Properties
	def content : Array[Byte];
}


object SecureHash
{
	/// Instance Properties
	val empty = MD5.createHashFrom ("");
}


final case class MD5 (
	override val content : Array[Byte]
	)
	extends SecureHash
{
	override def equals (obj : Any) : Boolean =
		canEqual (obj) && java.util.Arrays.equals (
			content,
			obj.asInstanceOf[MD5].content
			);
}

	
object MD5
{
	def createHashFrom (source : String) : MD5 =
	{
		val md5Digester = MessageDigest.getInstance ("MD5");
		
		return (new MD5 (md5Digester.digest (source.getBytes("UTF-8"))));
	}
}