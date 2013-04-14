/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 7, 2013
 */
package hackathon.models

import java.net.{
	URI,
	URLEncoder
	}


/**
 * The '''Identifier''' type captures the Domain Object Model
 * concept of an individual [[hackathon.models.Entity]] type's unique
 * identifier.
 *
 * @author svickers
 *
 */
final case class Identifier (
	val uri : URI
	)
{
	/**
	 * This version of construction allows an <strong>Identifier</strong> to
	 * be created from a {@link URI}'s stringified form.
	 */
	def this (value : String) = this (new URI (value));
	
	
	/**
	 * The / method allows <strong>Identifier</strong>s to create
	 * <em>sub</em>-<strong>Identifier</strong>s in an intuitive manner.
	 */
	def / (child : String) : Identifier = Identifier (uri, Option (child));
	
	
	/**
	 * This version of the / method allows for "named"
	 * <em>sub</em>-<strong>Identifier</strong>s to be made with the syntax of:
	 * <code>
	 * someId / ("foo" -> aKeyOfFoo)
	 * </code>
	 */
	def / (namedChild : (String, String)) : Identifier =
		Identifier (uri, List (namedChild._1, namedChild._2));
}


object Identifier
{
	/// Class Imports
	import scalaz.std.AllInstances._
	import scalaz.syntax.std.option._
	import scalaz.Equal
	import URLEncoder._
	
	
	/// Instance Properties
	private val changePath : (URI, String) => URI =
		(uri, path) => new URI (
			uri.getScheme,
			uri.getUserInfo,
			uri.getHost,
			uri.getPort,
			path,
			uri.getQuery,
			uri.getFragment
			);
		
	/**
	 * This apply method is defined to allow for functional-style creation
	 * of <strong>Identifier</strong>s from a {@link String} <b>value</b>.
	 */
	def apply (value : String) : Identifier = new Identifier (new URI (value));
	
	
	private def apply (uri : URI, children : Iterable[String]) : Identifier =
	{
		val elements = (
			Option (uri).map (pathOf).orZero.split ("/") ++
			children.map (child => URLEncoder.encode (child, "UTF-8"))
			) filterNot (_.isEmpty);
		
		return (
			new Identifier (changePath (uri, elements.mkString ("/", "/", "")))
			);
	}


	private def pathOf (uri : URI) = uri.normalize ().getPath.toLowerCase;
	
	
	/// Implicit Conversions
	implicit def IdentifierEqual : Equal[Identifier] = Equal.equalA;
}
