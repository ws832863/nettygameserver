dirpath=`pwd`

for jar in libs/*.jar; do
    CLASSPATH=$CLASSPATH:$dirpath/$jar
done

java com.ovgu.mmorpg.gametest.NoGameServerTest

