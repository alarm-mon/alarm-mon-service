PROJECT_ROOT="/home/ubuntu/app/deploy"
JAR_FILE="$PROJECT_ROOT/alarm-mon.jar"
ENV_FILE="$PROJECT_ROOT/.env"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# 환경 변수 파일 로드
if [ -f "$ENV_FILE" ]; then
  export $(cat $ENV_FILE | xargs)
else
  echo "$TIME_NOW > $ENV_FILE 파일이 없습니다." >> $DEPLOY_LOG
fi

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -DTEAMS_CROSSFIT_NOTI_URL=${TEAMS_CROSSFIT_NOTI_URL} -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG