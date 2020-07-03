package top.soliloquize;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.Map;

/**
 * Job监听
 *
 * @author wb
 * @date 2020/7/3
 */
public class WrappedJobListener implements JobListener {
    public static final String LISTENER_NAME = "simpleJobListener";

    private Map<String, Status> jobMap;

    public WrappedJobListener(Map<String, Status> jobMap) {
        this.jobMap = jobMap;
    }

    @Override
    public String getName() {
        return WrappedJobListener.LISTENER_NAME;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        jobMap.put(context.getJobDetail().getKey().getGroup() + "-" + context.getJobDetail().getKey().getName(), Status.AVAILABLE);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        jobMap.put(context.getJobDetail().getKey().getGroup() + "-" + context.getJobDetail().getKey().getName(), Status.RUNNING);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        jobMap.put(context.getJobDetail().getKey().getGroup() + "-" + context.getJobDetail().getKey().getName(), Status.FINISHED);
    }
}
