# About #

This is the kafka cluster node.

## Setup Guide ##

1. Change the conf/application.properties
- Define node id as you prefer
- Update broker IP and Port based on your localhost or server setup [your host IP]

2. Update the Zookeeper IP and Hosts based on your Zookeeper setup
- zookeeper.connect

3. Define a folder to keep kafka logs
-log.dirs = [log file path]

4. Start the kafka server
- ./bin/kafka-server-start.sh ./config/server.properties
