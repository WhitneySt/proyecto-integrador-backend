#!/usr/bin/env bash
cd /home/ec2-user/server
sudo java -jar -Dserver.port=5000 \
    *.jar > /dev/null 2> /dev/null < /dev/null &