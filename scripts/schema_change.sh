#!/usr/bin/env bash
# @(#) Create Play! schema change files
#
# (C)opyright 2013 iTeamSolutions, llc
#
# THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF iTeamSolutions, llc
# The copyright notice above does not evidence any actual or intended
# publication of such source code.
#

export PATH="$(cd $(dirname $0); pwd):$PATH"

test -d "${DEV_HOME:-/dev/null}" || {
	echo "DEV_HOME environment variable not set" >&2
	exit 1
}

cd $DEV_HOME || {
	echo "unable to cd into: $DEV_HOME" >&2
	exit 2
	}

test -d conf/evolutions/default || mkdir -p conf/evolutions/default
cd conf/evolutions/default

typeset -r NOW="$(date +'%Y%m%d%H%M%S')"
typeset -r DESC="$(echo $* | sed -e 's/  */_/g')"

cat - <<EOT >>${NOW}_${DESC}.sql
# Schema script generated on: $(date)

# --- !Ups

# --- !Downs

EOT

# Resequence the symbolic links for Play! to be able to pick things up
sequence_schema_links.sh

if [[ -z "${EDITOR:-}" ]]
then
	echo "SQL skeleton created: conf/evolutions/default/${NOW}_${DESC}.sql"
else
	$EDITOR ${NOW}_${DESC}.sql
fi

