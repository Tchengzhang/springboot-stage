package com.example.springbootstage.entity.system;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "quartz")
@Data
public class Quartz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;//任务名称
    private String jobGroup;//任务分组
    private String description;//任务描述
    private String jobClassName;//执行类
    private String cronExpression;//执行时间
    private String jobState;//任务状态
}
