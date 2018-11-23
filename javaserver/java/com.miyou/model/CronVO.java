package com.miyou.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "quartz_cron")
public class CronVO implements Serializable {

    private static final long serialVersionUID = -3406421161273529348L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cronid;
    /**
     * cron
     */
    private String cron;
    /**
     * 定时任务名称
     */
    private String quartzname;
    /**
     * 状态("1":有效 "0":无效)
     */
    private Integer status;
    /**
     * 定时任务类
     */
    private String schedulerclass;
    /**
     * 时间戳
     */
    private Date ts;

    public CronVO() {
    }
}
