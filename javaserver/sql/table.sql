create table QUARTZ_CRON
(
  CRONID         NUMBER,
  CRON           VARCHAR2(100),
  QUARTZNAME     VARCHAR2(200),
  STATUS         NUMBER,
  SCHEDULERCLASS VARCHAR2(100),
  TS             TIMESTAMP(6) default sysdate
)