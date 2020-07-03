package top.soliloquize;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * 测试任务
 *
 * @author wb
 * @date 2020/7/3
 */
public class MyJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MyJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info(context.getJobDetail().getKey().getName() + "任务正在执行，执行时间: " + Calendar.getInstance().getTime().getSeconds());
    }
}
