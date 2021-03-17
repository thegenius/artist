package com.lvonce.artist.example.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvonce.artist.annotation.SqlDataSource;
import com.lvonce.artist.example.dal.entity.TaskInfo;
import org.apache.ibatis.annotations.Insert;

//          "task_uuid VARCHAR(128) NOT NULL, \n" +
//                  "params TEXT NOT NULL, \n" +
//                  "status TINYINT NOT NULL,\n"+
//
//                  "executor_uuid VARCHAR(128) NOT NULL, \n"+
//                  "expire_time DATETIME NOT NULL, \n"+
//                  "create_time DATETIME NOT NULL, \n"+
//                  "update_time DATETIME NOT NULL, \n"+
//                  "preempt_count INT NOT NULL DEFAULT 0, \n"+

@SqlDataSource(name="h2-mem")
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    @Insert("insert into task_info" +
            "(task_uuid, params, status, executor_uuid, expire_time, create_time, update_time, preempt_count) values" +
            "(#{taskId}, #{params}, #{status}, #{executorUuid}, #{expireTime}, #{createTime}, #{updateTime}, #{preemptCount})")
    String createTask(TaskInfo taskInfo);

}
