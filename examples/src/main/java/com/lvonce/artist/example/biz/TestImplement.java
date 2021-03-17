package com.lvonce.artist.example.biz;

import com.google.inject.Inject;
import com.lvonce.artist.annotation.Provider;
import com.lvonce.artist.annotation.TccTransaction;
import com.lvonce.artist.example.api.TestInterface;
import com.lvonce.artist.example.clg.Task1;
import com.lvonce.artist.example.clg.Task2;
import com.lvonce.artist.example.clg.Task3;
import com.lvonce.artist.example.dal.entity.Person;
import com.lvonce.artist.example.dal.entity.TaskInfo;
import com.lvonce.artist.example.dal.mapper.PersonMapper;
import com.lvonce.artist.example.dal.mapper.TaskInfoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Provider
public class TestImplement implements TestInterface {

    @Inject
    PersonMapper personMapper;

    @Inject
    TaskInfoMapper taskInfoMapper;

    @Inject
    Task1 task1;

    @Inject
    Task2 task2;

    @Inject
    Task3 task3;

    @Override
    @TccTransaction
    public String sayTest(String name) {
        task1.execute("test1");
        task2.execute("test1");
        task3.execute("test1");
        return "hello test";
    }

    @Override
    public String sayHello(String name) {

        Person person = new Person();
        person.setId(101);
        person.setName("hello world!");
        person.setAge(32);
        personMapper.insert(person);

        String taskUuid = UUID.randomUUID().toString();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskUuid(taskUuid);
        taskInfo.setParams("{}");
        taskInfo.setStatus(0);

        taskInfo.setExecutorUuid(UUID.randomUUID().toString());
        taskInfo.setPreemptCount(0);
        taskInfo.setExpireTime(LocalDateTime.now());

        taskInfo.setCreateTime(LocalDateTime.now());
        taskInfo.setUpdateTime(LocalDateTime.now());


        try {
            taskInfoMapper.createTask(taskInfo);
            TaskInfo taskInfoResult = taskInfoMapper.selectById(taskUuid);

            log.info("{}", taskInfoResult);
        }catch (Exception ex) {
            log.info("{}", ex);
        }

        Person result = personMapper.selectById(101);
//        throw new RuntimeException("error");
        return result.getName() + " " + name;
    }
}
