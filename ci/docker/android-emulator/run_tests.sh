# Start marathon as AUTOTESTER user
# bla
set -ex
sleep 60
echo Starting Marathon
echo $(pwd)
echo $(ls)
cd /folder
echo $(ls)
MARATHON_COMMAND="marathon/bin/marathon -m MARATHONFILE"
echo Running command: $MARATHON_COMMAND
$MARATHON_COMMAND
