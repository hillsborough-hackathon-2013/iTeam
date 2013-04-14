/**
 * (C)opyright 2013 iTeamSolutions, llc.
 * All Rights Reserved
 *
 * Created on: Apr 11, 2013
 */
package controllers

import play.api.test._
import play.api.test.Helpers._

import hackathon.ProjectSpec


/**
 * The '''ControllerSpec''' type provides convenience behaviour for unit testing
 * [[play.api.mvc.Controller]] types.
 *
 * @author svickers
 *
 */
trait ControllerSpec
	extends ProjectSpec
{
	def runningAction (block : => Unit) : Unit =
		running (FakeApplication (additionalConfiguration = inMemoryDatabase ())) {
			block;
			}
}