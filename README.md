BarrageAndroid
==============
Android client for Barrage Application
Project structure:

MainApplication
-Applicaiton entry for android


Create Cassandra

CREATE KEYSPACE barrage 
with placement_strategy = 'org.apache.cassandra.locator.SimpleStrategy'
and strategy_options = {replication_factor:1};

SHOW KEYSPACES;
USE barrage;
CREATE COLUMN FAMILY user_feed_timeline 
WITH comparator = UTF8Type 
AND key_validation_class=UTF8Type 
AND default_validation_class = UTF8Type;
ASSUME user_feed_timeline KEYS AS ascii; 
ASSUME user_feed_timeline COMPARATOR AS ascii; 
ASSUME user_feed_timeline VALIDATOR AS ascii;