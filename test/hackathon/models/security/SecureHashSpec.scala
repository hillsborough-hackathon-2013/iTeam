/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 8, 2013
 */
package hackathon.models.security

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import hackathon.ProjectSpec


/**
 * The '''SecureHashSpec''' type
 *
 * @author svickers
 *
 */
@RunWith (classOf[JUnitRunner])
class SecureHashSpec
	extends ProjectSpec
{
	"A SecureHash" should "not compare the same as a different one" in
	{
		val hello = MD5.createHashFrom ("hello");
		val world = MD5.createHashFrom ("world");
		
		hello must not be === (world);
	}
	
	it should "compare the same as the same type with the same content" in
	{
		val testPhrase = "The quick brown fox jumped over the fence";
		
		MD5.createHashFrom (testPhrase) must be === (
			MD5.createHashFrom (testPhrase)
			);
	}
}
