package com.miyou.quartz;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;

public class ScheduledFutureFactory {

    private static Map<Long, QuartzScheduler> map = new HashMap<>(0);

    /**
     * 获取定时任务实例
     *
     * @param cronId
     * @param runnable
     * @param cron
     * @param threadPoolTaskScheduler
     * @return
     */
    public static QuartzScheduler createQuartzScheduler(Long cronId, Runnable runnable, String cron,
                                                        ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        QuartzScheduler quartzScheduler = new QuartzScheduler(runnable, cron, threadPoolTaskScheduler);
        map.put(cronId, quartzScheduler);
        return quartzScheduler;
    }

    /**
     * 根据key获取定时任务实例
     *
     * @param cronId
     * @return
     */
    public static QuartzScheduler getQuartzScheduler(Long cronId) {
        return map.get(cronId);
    }
}
