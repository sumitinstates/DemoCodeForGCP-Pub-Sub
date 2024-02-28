Go to kafka installation folder and then go to bin/windows and open kafka-run-class batch file in notepad++ and search JAVA_HOME and make sure below script is there.
rem Which java to use
IF ["%JAVA_HOME%"] EQU [""] (
	set JAVA=java
) ELSE (
	set JAVA="%JAVA_HOME%/java"
)


Then go to kafka/bin and open kafka-run-class batch file in notepad++ and search JAVA_HOME and make sure below script is there.
# Which java to use
if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

After than do following to start kafka server.

1. start zookeeper server on terminal windows laptop by running command:
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

2. start kafka server on terminal
   .\bin\windows\kafka-server-start.bat .\config\server.properties

3. run consumer on terminal where you can see message from topic where service will send message to
   .bin\windows\kafka-console-consumer.bat --topic test-topic --from-beginning --bootstrap-server localhost:9092


Then go to pubsub link. In my case it's : https://console.cloud.google.com/cloudpubsub/topic/list?authuser=1&project=pubsubproject-414417

And then publish message in the pub sub topic. And if you setup code properly on local then on kafka consumer terminal you should be able to see message that was posted from GCP pub sub.

   

