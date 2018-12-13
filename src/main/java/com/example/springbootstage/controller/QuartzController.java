package com.example.springbootstage.controller;


import com.example.springbootstage.entity.Result;
import com.example.springbootstage.entity.system.Quartz;
import com.example.springbootstage.service.system.QuartzService;
import com.example.springbootstage.utils.ResultUtil;
import lombok.extern.java.Log;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/quartz")
@Log
public class QuartzController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzService quartzService;

    @PostMapping("/add")
    public Result save(Quartz quartz) {
        log.info("新增任务");
        try {
            //如果是修改  展示旧的 任务
            if (quartz.getId() != null) {
                JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
                scheduler.deleteJob(key);
            }
            Class cls = Class.forName(quartz.getJobClassName());
            cls.newInstance();
            //构建job信息
            JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                    quartz.getJobGroup())
                    .withDescription(quartz.getDescription()).build();
            // 触发时间点
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                    .startNow().withSchedule(cronScheduleBuilder).build();
            //交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok();
    }

    @GetMapping("/list")
    public Result list() {
        log.info("任务列表");
        List<Quartz> list = quartzService.getAll();
        return ResultUtil.ok("获取任务列表", list);
    }

    @PostMapping("/trigger")
    public Result trigger(Quartz quartz, HttpServletResponse response) {
        log.info("触发任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok();
    }

    @PostMapping("/pause")
    public Result pause(Quartz quartz, HttpServletResponse response) {
        log.info("停止任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok();
    }

    @PostMapping("/resume")
    public Result resume(Quartz quartz, HttpServletResponse response) {
        log.info("恢复任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok();
    }

    @PostMapping("/remove")
    public Result remove(Quartz quartz, HttpServletResponse response) {
        log.info("移除任务");
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            System.out.println("removeJob:" + JobKey.jobKey(quartz.getJobName()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.ok();
    }
}
