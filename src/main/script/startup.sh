#!/bin/sh

basedir=`dirname $0`
basedir=`cd $basedir/..; pwd;`

libdir=$basedir/lib
configdir=$basedir/conf

classpath=$classpath:$libdir/*
#load config file
classpath=$classpath:$configdir

main=com.salix.demo.MainServer

sysargs="-Dsystem.root.path=$basedir"
opts="-Xms128m"
if [ "$1" = "jpda" ] ; then
    DEBUG_PORT=8000
    opts="$opts -Xdebug -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n"
    echo "debug port $DEBUG_PORT"
fi

logdir=$basedir/logs
if [ ! -x "$logdir" ]; then
    mkdir "$logdir"
fi

exec java $opts -cp $classpath $sysargs $main >>$logdir/console.log 2>&1 &
