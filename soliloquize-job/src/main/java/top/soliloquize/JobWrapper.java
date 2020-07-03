package top.soliloquize;


/**
 * Job包装
 *
 * @author wb
 * @date 2020/7/3
 */
public class JobWrapper {
    private String defaultGroupId = "group";
    private String defaultJobId = "top/soliloquize";
    private String defaultTriggerId = "trigger";
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务所需参数,json化
     */
    private String jsonParams;

    public JobWrapper() {
    }

    public JobWrapper(String defaultGroupId, String defaultJobId, String defaultTriggerId, String cronExpression, String jsonParams) {
        this.defaultGroupId = defaultGroupId;
        this.defaultJobId = defaultJobId;
        this.defaultTriggerId = defaultTriggerId;
        this.cronExpression = cronExpression;
        this.jsonParams = jsonParams;
    }

    public String getDefaultGroupId() {
        return defaultGroupId;
    }

    public JobWrapper setDefaultGroupId(String defaultGroupId) {
        this.defaultGroupId = defaultGroupId;
        return this;
    }

    public String getDefaultJobId() {
        return defaultJobId;
    }

    public JobWrapper setDefaultJobId(String defaultJobId) {
        this.defaultJobId = defaultJobId;
        return this;
    }

    public String getDefaultTriggerId() {
        return defaultTriggerId;
    }

    public JobWrapper setDefaultTriggerId(String defaultTriggerId) {
        this.defaultTriggerId = defaultTriggerId;
        return this;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public JobWrapper setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public JobWrapper setJsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
        return this;
    }
}
