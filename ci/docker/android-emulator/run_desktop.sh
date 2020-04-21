# Start marathon as AUTOTESTER user
# bla
set -ex

echo Starting desktop.jar
cd /folder
DESKTOP_JAR_COMMAND="java -jar desktop.jar"
echo Running command: $DESKTOP_JAR_COMMAND
$DESKTOP_JAR_COMMAND
