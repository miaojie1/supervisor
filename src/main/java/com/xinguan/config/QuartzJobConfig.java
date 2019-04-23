package com.xinguan.config;

import com.xinguan.job.BackUpDataJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

/**
 * @author zhangzhan
 */
@Configuration
public class QuartzJobConfig {

    /**
     * 系统数据定时备份Job
     *
     * @param backUpDataJob
     * @return
     */
    @Bean("backUpDataJobBean")
    public MethodInvokingJobDetailFactoryBean backUpDataJobBean(BackUpDataJob backUpDataJob) {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setName("System Data BackUp Job");
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.setTargetObject(backUpDataJob);
        jobDetailFactoryBean.setTargetMethod("execute");
        return jobDetailFactoryBean;
    }

    /**
     * 系统数据定时备份Job对应的Trigger
     *
     * @param jobDetailFactoryBean
     * @return
     */
    @Bean("backUpDataJobTrigger")
    public CronTriggerFactoryBean backUpDataJobTrigger(@Qualifier("backUpDataJobBean")
                                                               MethodInvokingJobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("0 * */1 * * ?");
        cronTriggerFactoryBean.setName("System Data BackUp Job Trigger");
        return cronTriggerFactoryBean;
    }

}
