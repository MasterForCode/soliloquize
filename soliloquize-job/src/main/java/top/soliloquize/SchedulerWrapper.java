package top.soliloquize;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import top.soliloquize.json.Jsons;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wb
 * @date 2020/7/3
 */
public enum SchedulerWrapper {

    /**
     * 全局唯一实例
     */
    INSTANCE;

    private static Scheduler scheduler;

    private static Map<String, Status> jobMap = new ConcurrentHashMap<>();

    static {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.getListenerManager().addJobListener(new WrappedJobListener(jobMap));
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public SchedulerWrapper put(Class<? extends Job> clz, JobWrapper jobWrapper) {
        Objects.requireNonNull(clz);
        if (jobWrapper == null) {
            jobWrapper = new JobWrapper();
        }
        JobBuilder jobBuilder = JobBuilder.newJob(clz).withIdentity(jobWrapper.getDefaultJobId(), jobWrapper.getDefaultGroupId());
        JobDetail job;
        if (jobWrapper.getJsonParams() == null) {
            job = JobBuilder.newJob(clz).withIdentity(jobWrapper.getDefaultJobId(), jobWrapper.getDefaultGroupId()).build();
        } else {
            JobDataMap jobDataMap = new JobDataMap(Jsons.json2BeanEx(jobWrapper.getJsonParams(), HashMap.class));
            job = jobBuilder.setJobData(jobDataMap).build();
        }
        Trigger trigger;
        if (jobWrapper.getCronExpression() != null && jobWrapper.getCronExpression().length() > 0) {
            trigger = TriggerBuilder.newTrigger().withIdentity(jobWrapper.getDefaultTriggerId(), jobWrapper.getDefaultGroupId()).withSchedule(CronScheduleBuilder.cronSchedule(jobWrapper.getCronExpression())).startNow().build();
        } else {
            trigger = TriggerBuilder.newTrigger().withIdentity(jobWrapper.getDefaultTriggerId(), jobWrapper.getDefaultGroupId()).startNow().build();
        }
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SchedulerWrapper pauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            throw new RuntimeException();
        }
        return this;
    }

    public SchedulerWrapper resumeAll() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            throw new RuntimeException();
        }
        return this;
    }

    public SchedulerWrapper pause(JobWrapper jobWrapper) {
        Objects.requireNonNull(jobWrapper);
        try {
            scheduler.pauseJob(JobKey.jobKey(jobWrapper.getDefaultJobId(), jobWrapper.getDefaultGroupId()));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public SchedulerWrapper resume(JobWrapper jobWrapper) {
        Objects.requireNonNull(jobWrapper);
        try {
            scheduler.resumeJob(JobKey.jobKey(jobWrapper.getDefaultJobId(), jobWrapper.getDefaultGroupId()));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Map<String, Status> getJobMap() {
        return SchedulerWrapper.jobMap;
    }
}
