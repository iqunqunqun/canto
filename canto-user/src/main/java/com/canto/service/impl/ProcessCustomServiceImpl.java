package com.canto.service.impl;


import com.canto.result.JsonResult;
import com.canto.service.IProcessCustomService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>activiti配置封装类</p>
 *
 * @author chenyf
 * @version 1.0
 * @className ProcessCustomServiceImpl
 * @date 2019/9/30 13:51
 */
@Slf4j
@Service("processCustomService")

public class ProcessCustomServiceImpl implements IProcessCustomService {
    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected ManagementService managementService;


    private Logger logger = LoggerFactory.getLogger(ProcessCustomServiceImpl.class);


    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery().list();
    }


    @Override
    public List<ProcessDefinition> getProcessDefinitions(String category) {
        logger.info("【获取当前部署了的流程】category={}", category);
        List<ProcessDefinition> processDefinitions;
        if (StringUtils.isBlank(category)) {
            processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        } else {
            processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionCategory(category).list();
        }
        return processDefinitions;
    }

    @Override
    public JsonResult deployProcess(String bpmnName, String processName) {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(processName);
        Deployment deployment;
        try {
            deployment = deploymentBuilder
                    .addClasspathResource("processes/"+ bpmnName +".bpmn")
//                    .addClasspathResource("processes/"+ bpmnName +".png")
                    .deploy();
        } catch (Exception e) {
            return JsonResult.errorException("cuowu");
        }
        return deployment != null? JsonResult.ok(deployment): JsonResult.errorException("cuowu");
    }


    @Override
    public ProcessInstance startProcess(String processKey, Map<String, Object> map) {
        ProcessInstance instance = null;
        try {
            instance = runtimeService
                    .startProcessInstanceByKey(processKey, map);
        } catch (Exception e) {
            log.error("instance={}", processKey);
            e.printStackTrace();
        }
        return instance;
    }


    @Override
    public Task getTaskByInstanceId(String instanceId) {
        return taskService.createTaskQuery().processInstanceId(instanceId).active().singleResult();
    }

    @Override
    public Boolean validateProcess(String instanceId) {
        logger.info("【验证工作流是不是已经停止】instanceId={}", instanceId);
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();
        return pi == null;
    }

    @Override
    public List<Task> listAllTasksByUser(String userId) {
        logger.info("【获取用户当前处于的任务集合】userId={}", userId);
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(userId).list();
        List<Task> listTwo = taskService.createTaskQuery().taskAssignee(userId).list();
        List<Task> listThree = taskService.createTaskQuery().taskCandidateUser(userId).list();
        //排除重复的
        for (Task task1 : listTwo) {
            if (!list.contains(task1)) {
                list.add(task1);
            }
        }
        for (Task task : listThree) {
            if (!list.contains(task)) {
                list.add(task);
            }
        }
        return list;
    }

    @Override
    public List<Task> listTasksByAssignee(Integer assignee) {
        List<Task> taskList = null;
        try {
            taskList = taskService.createTaskQuery()
                    //指定个人任务查询
                    .taskAssignee(String.valueOf(assignee))
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取当前用户列表出错，assignee={}", assignee);
        }
        return taskList;
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables, Boolean localScope) {
        logger.info("【完成任务】taskId={}", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // owner不为空说明可能存在委托任务
        if (StringUtils.isNotBlank(task.getOwner())) {
            DelegationState delegationState = task.getDelegationState();
            switch (delegationState) {
                case PENDING:
                    taskService.resolveTask(taskId);
                    taskService.complete(taskId, variables, localScope);
                    break;

                case RESOLVED:
                    log.info("委托任务已经完成,task={}", task);
                    break;

                default:
                    log.info("不是委托任务,task={}",task);
                    taskService.complete(taskId, variables, localScope);
                    break;
            }
        } else {
            taskService.complete(taskId, variables, localScope);
        }
    }

    @Override
    public void claimTask(String taskId, Integer userId) {
        taskService.claim(taskId, String.valueOf(userId));
    }

    @Override
    public List<HistoricTaskInstance> listHistoryTaskByAssignee(Integer assignee) {
        return processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(String.valueOf(assignee))
                .finished()
                .or()
                .unfinished()
                .list();
    }

    @Override
    public void transferByAssignee(String taskId, Integer assignee) {
        taskService.setAssignee(taskId, String.valueOf(assignee));
    }

    @Override
    public void delegateTaskByAssignee(String taskId, Integer assignee) {
        taskService.delegateTask(taskId, String.valueOf(assignee));
    }

    @Override
    public void deleteProcess(String instanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
        log.info("流程已删除，instanceId = {}", instanceId);
    }

    @Override
    public boolean addComment(Integer assignee, String taskId, String instanceId, Integer procProgress, String comment) {
        // 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
        Authentication.setAuthenticatedUserId(String.valueOf(assignee));
        // 删除原有的批注
        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee", assignee);
        map.put("instanceId", instanceId);
        taskService.addComment(taskId, instanceId, String.valueOf(procProgress), comment);
        return true;
    }

    @Override
    public HistoricProcessInstance getHistoricProcessInstance(String instanceId) {
        return null;
    }

}
