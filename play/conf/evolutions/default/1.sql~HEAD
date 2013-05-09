# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table business_rating (
  business_id               varchar(255) not null,
  name                      varchar(255),
  avg_rating                float,
  num_reviews               integer,
  latitude                  float,
  longitude                 float,
  timestamp                 datetime,
  constraint pk_business_rating primary key (business_id))
;

create table tweets (
  tweets_id                 integer auto_increment not null,
  business_id               varchar(255),
  num_tweets                integer,
  follower_count            integer,
  timestamp                 datetime,
  constraint pk_tweets primary key (tweets_id))
;

create table yelp_review (
  review_id                 integer auto_increment not null,
  business_id               varchar(255),
  rating                    float,
  excerpt                   varchar(255),
  user_name                 varchar(255),
  user_id                   varchar(255),
  time_created              datetime,
  constraint pk_yelp_review primary key (review_id))
;

alter table tweets add constraint fk_tweets_businessId_1 foreign key (business_id) references business_rating (business_id) on delete restrict on update restrict;
create index ix_tweets_businessId_1 on tweets (business_id);
alter table yelp_review add constraint fk_yelp_review_businessId_2 foreign key (business_id) references business_rating (business_id) on delete restrict on update restrict;
create index ix_yelp_review_businessId_2 on yelp_review (business_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table business_rating;

drop table tweets;

drop table yelp_review;

SET FOREIGN_KEY_CHECKS=1;

