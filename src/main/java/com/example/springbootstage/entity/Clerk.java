package com.example.springbootstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Clerk {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String phone;

    private String gender;

    private String status;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "store_id")
    private Store store;


    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateUser;
}
