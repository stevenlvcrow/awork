package com.miyou.controller;

import com.miyou.model.BusinessResponse;
import com.miyou.model.CronVO;
import com.miyou.quartz.QuartzScheduler;
import com.miyou.quartz.ScheduledFutureFactory;
import com.miyou.service.IcronServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    @Autowired
    private IcronServiceImpl cronService;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 开启定时任务
     *
     * @param cronId
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseBody
    public BusinessResponse start(@RequestParam(name = "cronId", defaultValue = "") Long cronId) {
        // 1.参数校验
        Optional<CronVO> cronVO = cronService.findById(cronId);
        if (!cronVO.isPresent()) {
            return new BusinessResponse("cronId无效");
        }
        String cron = cronVO.get().getCron();
        String schedulerClass = cronVO.get().getSchedulerclass();
        // 2.开启任务
        try {
            Runnable runnable = (Runnable) Class.forName(schedulerClass).newInstance();
            QuartzScheduler quartzScheduler = ScheduledFutureFactory.getQuartzScheduler(cronId);
            if (quartzScheduler == null) {
                quartzScheduler = ScheduledFutureFactory.createQuartzScheduler(cronId, runnable, cron,
                        threadPoolTaskScheduler);
            }
            quartzScheduler.setCron(cron);
            cronVO.get().setStatus(1);
            cronService.save(cronVO.get());
            logger.info("开启定时任务成功");
            return new BusinessResponse("开启定时任务成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new BusinessResponse("开启定时任务失败");
        }
    }

    /**
     * 关闭定时任务
     *
     * @param cronId
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.GET)
    @ResponseBody
    public BusinessResponse close(@RequestParam(name = "cronId", defaultValue = "") Long cronId) {
        // 1.参数校验
        Optional<CronVO> cronVO = cronService.findById(cronId);
        if (!cronVO.isPresent()) {
            return new BusinessResponse("cronId无效");
        }
        String cron = cronVO.get().getCron();
        String schedulerClass = cronVO.get().getSchedulerclass();
        // 2.关闭任务
        try {
            Runnable runnable = (Runnable) Class.forName(schedulerClass).newInstance();
            QuartzScheduler quartzScheduler = ScheduledFutureFactory.getQuartzScheduler(cronId);
            if (quartzScheduler == null) {
                quartzScheduler = ScheduledFutureFactory.createQuartzScheduler(cronId, runnable, cron,
                        threadPoolTaskScheduler);
            }
            quartzScheduler.stop();
            cronVO.get().setStatus(0);
            cronService.save(cronVO.get());
            logger.info("关闭定时任务成功");
            return new BusinessResponse("关闭定时任务成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new BusinessResponse("关闭定时任务失败");
        }
    }

    /***
     * 更新定时任务
     *
     * @param cronVO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BusinessResponse update(@RequestBody CronVO cronVO) {
        // 1.参数校验
        Long cronId = cronVO.getCronid();
        String cron = cronVO.getCron();
        Integer status = cronVO.getStatus();
        String schedulerClass = cronVO.getSchedulerclass();
        if (StringUtils.isBlank(cron) || StringUtils.isBlank(schedulerClass)) {
            return new BusinessResponse("时间表达式和定时任务类不可为空");
        }
        try {
            // 2.更新实体，定时任务开启状态则重新设置表达式
            cronService.save(cronVO);
            if (status == 1) {
                Runnable runnable = (Runnable) Class.forName(schedulerClass).newInstance();
                QuartzScheduler quartzScheduler = ScheduledFutureFactory.getQuartzScheduler(cronId);
                if (quartzScheduler == null) {
                    quartzScheduler = ScheduledFutureFactory.createQuartzScheduler(cronId, runnable, cron,
                            threadPoolTaskScheduler);
                }
                quartzScheduler.setCron(cron);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new BusinessResponse("更新定时任务失败");
        }
        logger.info("更新定时任务成功");
        return new BusinessResponse("更新定时任务成功");
    }

    /***
     * 根据主键获取定时任务相关信息
     *
     * @param cronId
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public CronVO findById(@RequestParam(name = "cronId", defaultValue = "") Long cronId) {
        if (cronId == null) {
            return null;
        }
        Optional<CronVO> cronVO = cronService.findById(cronId);
        return cronVO.get();
    }

    /***
     * 获取所有定时任务信息
     *
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<CronVO> findAll() {
        return cronService.findAll();
    }
}
