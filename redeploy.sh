#!/bin/bash
echo "🔄 재배포 시작..."

# 1. Git 업데이트
git checkout main
git pull origin main

# 2. 빌드
./gradlew clean build

# 3. 기존 프로세스 종료
ssh -i ~/Downloads/princess-backend-key.pem ec2-user@15.165.5.232 "sudo fuser -k 8080/tcp"

# 4. 새 파일 업로드
scp -i ~/Downloads/princess-backend-key.pem build/libs/princess-backend-0.0.1-SNAPSHOT.jar ec2-user@15.165.5.232:~/

# 5. 새 애플리케이션 실행
ssh -i ~/Downloads/princess-backend-key.pem ec2-user@15.165.5.232 "source /etc/environment && nohup java -jar princess-backend-0.0.1-SNAPSHOT.jar > app.log 2>&1 < /dev/null & disown"

echo "✅ 재배포 완료! http://15.165.5.232:8080/swagger-ui.html"
