#!/usr/bin/env bash
# @(#) Create Play! schema evolutions links
#
# (C)opyright 2013 iTeamSolutions, llc
#
# THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF iTeamSolutions, llc
# The copyright notice above does not evidence any actual or intended
# publication of such source code.
#
# $HeadURL

test -d "${DEV_HOME:-/dev/null}" || {
	echo "DEV_HOME environment variable not set" >&2
	exit 1
}

cd $DEV_HOME || {
	echo "unable to cd into: $DEV_HOME" >&2
	exit 2
	}

test -d conf/evolutions/default || exit 0
cd conf/evolutions/default || exit 1

# Clean out old sym links
find . -type l -name '[0-9]*.sql' -delete

num=1

for source in [0-9]*_*.sql
do
	ln -s $source ${num}.sql
	num=$((num + 1))
done

