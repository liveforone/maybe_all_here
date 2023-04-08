## 데이터 베이스 전반 설계
* ddl은 개발을 할때만 사용하고, 프로젝트 종료시 none으로 두고 직접 생성 변경, 인덱스 쿼리를 날린다.
* 타 서비스를 대용량, 대규모 트래픽으로 조회한다면 Read Only DB를 사용한다.
* 모든 서비스별로 DB가 구축되며, 모두 MySql을 사용한다.
* 도커에서도 똑같이 모두 개별 DB를 구성한다.

## 데이터 베이스 생성 + 테이블 생성 및 변경 쿼리
* item-service는 테이블이 두개이다.(item, uploadFile)
```
CREATE DATABASE mah_user_db;
CREATE DATABASE mah_mileage_db;
CREATE DATABASE mah_shop_db;
CREATE DATABASE mah_item_db;
CREATE DATABASE mah_order_db;
CREATE DATABASE mah_review_db;

create table member (
    id bigint not null auto_increment,
    auth varchar(255),
    email varchar(255) not null UNIQUE,
    password varchar(100) not null,
    real_name varchar(255) not null,
    primary key (id)
)
CREATE INDEX email_idx ON member (email);

create table mileage (
    id bigint not null auto_increment,
    email varchar(255) not null UNIQUE,
    mileage_point bigint not null,
    primary key (id)
)
CREATE INDEX email_idx ON mileage (email);

create table shop (
    id bigint not null auto_increment,
    address varchar(255) not null,
    email varchar(255) not null UNIQUE,
    shop_name varchar(255) not null,
    tel varchar(255) not null,
    primary key (id)
)
CREATE INDEX email_idx ON shop (email);

create table item (
    id bigint not null auto_increment,
    bad bigint not null,
    content TEXT not null,
    good bigint not null,
    price bigint not null,
    remaining bigint not null,
    shop_id bigint not null,
    title varchar(255) not null,
    primary key (id)
)
CREATE INDEX shop_id_idx ON item (shop_id);
CREATE INDEX title_idx ON item (title);

create table upload_file (
    id bigint not null auto_increment,
    save_file_name varchar(255) not null,
    item_id bigint,
    FOREIGN KEY (item_id) references item (id),
    primary key (id)
)

create table orders (
    id bigint not null auto_increment,
    created_date date,
    discounted_price bigint not null,
    email varchar(255) not null,
    item_id bigint not null,
    item_title varchar(255),
    order_quantity bigint not null,
    order_state varchar(255),
    total_price bigint not null,
    primary key (id)
)
CREATE INDEX email_idx ON orders (email);

create table review (
    id bigint not null auto_increment,
    content TEXT not null,
    created_date date,
    email varchar(255) not null,
    item_id bigint not null,
    order_id bigint not null UNIQUE,
    recommend varchar(255) not null,
    primary key (id)
)
CREATE INDEX order_id_idx ON review (order_id);
CREATE INDEX item_id_idx ON review (item_id);
```

## 인덱스 설계
* 인덱스는 모든 테이블에 걸려있다.
* 인덱스는 적극 사용하되, 최대 4개가 넘지 않도록 한다.
* 인덱스는 DB에 직접 쿼리를 날린다.(jpa 도움 받지 않음)