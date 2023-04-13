## Docker
* msa와 클라우드 환경에서는 컨테이너 가상화 기술이 거의 필수이다.
* 컨테이너 가상화 기술은 배포를 아주 편리하게 만들어 주었다.
* 이번 프로젝트에서 데이터베이스와 카프카를 포함하여 모든 서비스를 도커로 배포하였다.

## 주의점
1. wurstmeister/kafka와 wurstmeister/zookeeper의 이미지가 도커에 pull되어 있어야한다.
2. mysql도 도커에 이미지가 pull 되어있어야한다.
3. 네트워크가 만들어져야한다.
4. wurstmeister를 git clone하고 docker-compose-single-broker.yml 파일에 포트와 네트워크 정보가 수정되어있어야한다.
5. 카프카를 사용하는 모든 서비스의 카프카 서버 포트는 docker-compose-single-broker.yml에 지정한 kafka 포트 번호와 동일해야한다.
6. application.yml에는 eureka url에 localhost가 아닌 discovery-service:8761이 들어가 있어야한다.
7. datasource url은 도커 빌드전 반드시 도커 db의 url을 가지고 있어야한다.
8. db는 생성시에 utf8로 설정하여 생성해야한다.

## 도커 command 및 순서
### 0. 기본 순서
* 도커 빌드는 맨 마지막 순서이다.
* 이에 따라 로컬환경으로 개발후에는 ip주소, sql 서버 주소등 주소를 변경하는 작업을 거쳐야한다.
* 여기서 꼬이게 되면 복잡해지기 때문에 항상 로컬환경과 배포 환경을 생각하며 개발하고 배포하여야한다.
### 1. 네트워크
* 기존에 존재하는 네트워크 중 같은 ip대에 있을경우 해당 ip는 사용 불가하다.
```
docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 네트워크이름
```
### 2. 데이터 베이스
* mysql 이미지는 미리 pull되어있어야한다.
* db를 생성할때에는 반드시 utf8 설정을 걸어준다.
* 그렇지 않으면 한글이 깨져서 보이지 않는다...
* 3307까지 로컬에서 사용하고 있기 때문에 3308포트부터 사용한다.
```
docker run -d --name 이름 -e MYSQL_ROOT_PASSWORD=1111 -p 3308:3306 --network 네트워크명 mysql:latest

docker exec -it db이미지명 bash

mysql -u root -p

CREATE DATABASE DB명 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use DB이름;
```
### 3. Kafka and Zookeeper
* 프로젝트 안에 폴더를 미리 만들어 놓는다.
* 혹은 외부에 미리 만들어 놓는다.
* kafka와 zookeeper는 바로 도커를 사용하는 것이 아니라서 경로를 계속 따라가야한다.
* compose 파일은 네트워크 정보를 추가하고, 이미지를 추가하고, ip주소를 수정한다.
```
git clone https://github.com/wurstmeister/kafka-docker.git

cd kafka-docker

docker-compose -f docker-compose-single-broker.yml up -d
docker-compose -f docker-compose-single-broker.yml down
```
### 4. 마이크로 서비스
* Dockerfile을 만들어놓는다.
* application.yml에서는 eureka url과 도커 db로 datasource를 수정한다.
* 랜덤포트는 compose를 이용하진 않고 불가능하니 포트를 하나씩 늘려가며 실행한다.
* 로컬 db와 kafka로 제작한 후에 모든 테스트를 마치고 yml 파일을 수정하는 것이 일반적이나,
* 그대로 빌드하고 실행시 command에서 -e 옵션으로 yml을 수정해 배포할 수 있다.
* 다만 너무 번거로우니 yml을 수정하고 배포하는 방식으로 한다.
* 도커 이미지명은 반드시 소문자로 작성해야 빌드 가능하다.
```
./gradlew.bat clean build --exclude-task test
docker build --tag yc4852/이미지:1.0 .

docker run -d -p 8761:8761 --network 네트워크이름 --name discovery-service yc4852/discovery-service:1.0

docker run -d -p 8000:8000 --network 네트워크이름 --name gateway-service yc4852/gateway-service:1.0

docker run -d -p 8001:8001 --network 네트워크이름 --name user-service yc4852/user-service:1.0
```

## 도커 상세
### 도커의 포트
* 도커에서 -p 하고 aport:bport 라는 옵션을 주곤 한다.
* aport는 호스트의 port, 즉 나의 컴퓨터이고,
* bport는 컨테이너의 포트이다.
* 컨테이너도 나의 컴퓨터와 마찬가지로 같은 갯수의 포트를 가지고 있다.
* 간단히 말해 a는 외부포트이고, b는 컨테이너 내부포트이다.
* data source url같은 부분에서 어떤 포트 번호를 적어야할지 고민이 될 수 있다.
* 특히나 MSA 아키텍처처럼 각 서비스 별로 DB를 두는 경우에 말이다.
* MYSQL 을 사용하는 경우 포트는 3306을 써야만 한다.
* aport를 웹어플리케이션 배포하듯이 포트를 변경해가며 배포해주고
* bport를 3306으로 놓으면 문제 없다.
* 도커에 배포된 DB를 사용할때에는 같은 네트워크 내에 존재하는 컨테이너이름:3306으로 접속하기 때문에 위와 같이 배포해주면된다.
* 아래는 예시이다.
```
user db : 3306:3306
item db : 3307:3306
...
```
### cloud에서
* 클라우드에서 도커를 사용할땐 생성된 이미지를 docker hub에 push 해주어야한다.
* 클라우드에서는 docker hub에 push된 이미지를 pull해서 사용한다.