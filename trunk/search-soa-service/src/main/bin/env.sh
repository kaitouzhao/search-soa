CLASS_NAME="com.womai.pms.soa.launcher.Provider"
CONF_NAME="conf/applicationContext-dubbo.xml"

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`sed '/dubbo:application/!d;s/.*="//;s/".*//' $CONF_NAME | tr -d '\r'`
fi
if [ -z "$SERVER_NAME" ]; then
	SERVER_NAME=`hostname`
fi
if [ -z "SERVER_PORT" ]; then
    SERVER_PORT=`sed '/dubbo:protocol.*port/!d;s/.*port="//;s/".*//' $CONF_NAME | tr -d '\r'`
fi

#LOGS_DIR=""
LOGS_DIR="/opt/logs/soa/${SERVER_NAME}/"


if [ ! -n "$LOGS_DIR" ]; then
	LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
	mkdir -p $LOGS_DIR
fi

echo $SERVER_NAME $SERVER_PORT $CLASS_NAME  $CONF_NAME  $LOGS_DIR