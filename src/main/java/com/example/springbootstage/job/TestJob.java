package com.example.springbootstage.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import java.io.Serializable;

public class TestJob implements Job,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行测试");
    }
}
