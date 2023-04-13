# Maybe All Here
> 오픈 마켓 서비스 플랫폼

----
# 0. 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 진행](#2-프로젝트-진행)
3. [요구사항](#3-요구사항)
4. [설계](#4-설계)
5. [마이크로 서비스별 문서](#5-마이크로-서비스별-상세)
6. [고민점](#6-고민점)
7. [서비스/데이터 간 통신](#7-서비스데이터-간-통신)
8. [리팩토링](#8-리팩토링)
9. [스타일 가이드](#9-스타일-가이드코드-컨벤션)

----
# 1. 프로젝트 소개
### 소개
* 오픈 마켓 서비스 플랫폼 입니다.
* Maybe All Here 이란 프로젝트 이름은 "이 세상 모든 물건은 다 여기서 구할 수 있다" 라는 의미를 가집니다.
* 장애처리와 성능향상을 위한 분산환경 조성을 위해 마이크로 서비스 아키텍처를 적용하였습니다.
### 기술 스택
* Framework : Spring Boot 3.0.X & Spring Cloud
* Lang : Java17
* Data : Spring Data Jpa & Query Dsl & MySql
* Security : Spring Security & Jwt
* Service Communication : Apache Kafka(Async), Open Feign Client(Sync)
* Container : Docker & Docker-compose
* Test : Junit5
* Util : Apache Commons Lang3, LomBok

----
# 2. 프로젝트 진행
* [소개와 들어가며](https://github.com/liveforone/maybe_all_here/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C%EC%99%80-%EB%93%A4%EC%96%B4%EA%B0%80%EB%A9%B0)
* [진행 시 주의점](https://github.com/liveforone/maybe_all_here/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A7%84%ED%96%89%EC%8B%9C-%EC%A3%BC%EC%9D%98%EC%A0%90)
* [프로젝트 회고](https://github.com/liveforone/maybe_all_here/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0)

----
# 3. 요구사항
* [코드 품질 요구사항](https://github.com/liveforone/maybe_all_here/wiki/%EC%BD%94%EB%93%9C-%ED%92%88%EC%A7%88-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD)
* [자료구조 요구사항](https://github.com/liveforone/maybe_all_here/wiki/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD)

----
# 4. 설계
### 아키텍처
* [아키텍처 설계](https://github.com/liveforone/maybe_all_here/wiki/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%84%A4%EA%B3%84)
### 수익 모델
* [수익 모델](https://github.com/liveforone/maybe_all_here/wiki/%EC%88%98%EC%9D%B5-%EB%AA%A8%EB%8D%B8)
### DB 설계
* [DB 설계 및 원칙](https://github.com/liveforone/maybe_all_here/wiki/DB-%EC%84%A4%EA%B3%84)

----
# 5. 마이크로 서비스별 상세
* [회원 서비스](https://github.com/liveforone/maybe_all_here/wiki/%ED%9A%8C%EC%9B%90-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [마일리지 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EB%A7%88%EC%9D%BC%EB%A6%AC%EC%A7%80-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [상점 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EC%83%81%EC%A0%90-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [상품 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EC%83%81%ED%92%88-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [리뷰 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EB%A6%AC%EB%B7%B0-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [주문 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EC%A3%BC%EB%AC%B8-%EC%84%9C%EB%B9%84%EC%8A%A4)

----
# 6. 고민점
### 비즈니스 문제해결
* [비즈니스 문제 해결](https://github.com/liveforone/maybe_all_here/wiki/%EB%B9%84%EC%A6%88%EB%8B%88%EC%8A%A4-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)
### 장애 시나리오
* [장애 시나리오](https://github.com/liveforone/maybe_all_here/wiki/%EC%9E%A5%EC%95%A0-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4)
### 프로젝트 시 고민점
* [페이징 성능을 높일 순 없을까?](https://github.com/liveforone/maybe_all_here/wiki/%ED%8E%98%EC%9D%B4%EC%A7%95-%EC%84%B1%EB%8A%A5%EC%9D%84-%EB%86%92%EC%9D%BC-%EC%88%9C-%EC%97%86%EC%9D%84%EA%B9%8C%3F)
* [데이터 변경 요청시 항상 모든 예외상황을 처리해야할까?](https://github.com/liveforone/maybe_all_here/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B3%80%EA%B2%BD-%EC%9A%94%EC%B2%AD%EC%8B%9C-%ED%95%AD%EC%83%81-%EB%AA%A8%EB%93%A0-%EC%98%88%EC%99%B8%EC%83%81%ED%99%A9%EC%9D%84-%EC%B2%98%EB%A6%AC%ED%95%B4%EC%95%BC%ED%95%A0%EA%B9%8C%3F)
* [JWT를 이용해 시큐리티 없이 회원 정보 파싱하기](https://github.com/liveforone/maybe_all_here/wiki/JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%97%86%EC%9D%B4-%ED%9A%8C%EC%9B%90-%EC%A0%95%EB%B3%B4-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0)

----
# 7. 서비스/데이터 간 통신
* [데이터 통신 전략](https://github.com/liveforone/maybe_all_here/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%86%B5%EC%8B%A0-%EC%A0%84%EB%9E%B5)
* [Kafka 활용 전략](https://github.com/liveforone/maybe_all_here/wiki/%EC%B9%B4%ED%94%84%EC%B9%B4-%ED%99%9C%EC%9A%A9-%EC%A0%84%EB%9E%B5)
* [Feign Client 활용 전략](https://github.com/liveforone/maybe_all_here/wiki/Feign-Client-%ED%99%9C%EC%9A%A9-%EC%A0%84%EB%9E%B5)
* [Kafka Command](https://github.com/liveforone/maybe_all_here/wiki/Kafka-Command)
* [Kafka 주요 에러 및 주의 사항](https://github.com/liveforone/maybe_all_here/wiki/Kafka-%EC%A3%BC%EC%9A%94-%EC%97%90%EB%9F%AC-%EB%B0%8F-%EC%A3%BC%EC%9D%98-%EC%82%AC%ED%95%AD)

----
# 8. 리팩토링
* [리팩토링 v1.0](https://github.com/liveforone/maybe_all_here/wiki/Refactoring-v1.0)

----
# 9. 스타일 가이드(코드 컨벤션)
* 아래는 필자가 직접 제작한 스타일 가이드이며, 본 프로젝트는 아래 스타일 가이드를 모두 지켜 제작되었다.
* [스타일 가이드 전문](https://github.com/liveforone/study/tree/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D)