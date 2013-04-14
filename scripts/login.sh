#!/usr/bin/env bash
# @(#) Login to the system
#
# (C)opyright 2013 iTeamSolutions, llc
#
# THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF iTeamSolutions, llc
# The copyright notice above does not evidence any actual or intended
# publication of such source code.
#

. $(dirname $0)/ldev || {
	echo "settings not found" >&2
	exit 1
	}

name="${1:-test}"
password="${2:-example}"
phone="${3:-813-555-1212}"


${CURL} \
	--data "name=$name" \
	--data "password=$password" \
	--data "phone=$phone" \
	$URL

