package top.soliloquize;

import org.quartz.SchedulerException;
import top.soliloquize.json.Jsons;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wb
 * @date 2020/1/7
 */
public class Example {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("test", "hi");
        SchedulerWrapper.INSTANCE.put(MyJob.class, new JobWrapper().setJsonParams(Jsons.bean2JsonEx(map)).setDefaultJobId("job1").setDefaultTriggerId("trigger1").setCronExpression("0/2 * * * * ? "))
        ;
        Thread.sleep(1000);
        SchedulerWrapper.INSTANCE.put(MyJob.class, new JobWrapper().setDefaultJobId("job2").setDefaultTriggerId("trigger2").setCronExpression("0/5 * * * * ? "));
//        schedulerWrapper.pause(JobWrapper.builder().build());
        Thread.sleep(10000);
        System.out.println("====");
//        schedulerWrapper.resume(JobWrapper.builder().build());
    }
}
