#
# Makefile for watchdog system.
#
# $Id: Makefile,v 1.1 2000/11/16 23:52:25 iefbr14 Exp $
# $Source: /cvsroot/watchdog/doc/sadmin/Makefile,v $
#

DOC=sadmin

default: html

all: html ps

html:
	rm -f html/*.html
	cd doc && sdf -2html_topics ${DOC}.sdf 
	mv doc/*.html html

ps:
	cd doc  && sdf -2ps ${DOC}.sdf 
	mv ${DOC}.ps ../ps

clean:
	rm -f html/*.html Manual/*.ps
