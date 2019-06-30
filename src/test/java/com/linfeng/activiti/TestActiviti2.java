package com.linfeng.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiviti2{
	private  ClassPathXmlApplicationContext applicationContext;
	private  ProcessEngine  processEngine;
	@Before
	public void  testCreateDBDefineConfiguration() {
		 applicationContext=new ClassPathXmlApplicationContext("activiti-context.xml") {
			 @Override
			protected void onClose() {
				System.out.println(applicationContext.isPrototype("processEngine"));
			}
		 };
		 processEngine = applicationContext.getBean("processEngine", ProcessEngine.class);
	}
	
//	@Test
//	public  void  testCreateDBAutoConfiguration() {
//		DeploymentBuilder createDeployment = processEngine.getRepositoryService().createDeployment();
//		createDeployment.addClasspathResource("test1.bpmn");
//		createDeployment.addClasspathResource("test1.png");
//		createDeployment.deploy();
//		DeploymentQuery createDeploymentQuery = processEngine.getRepositoryService().createDeploymentQuery();
//		System.out.println(createDeploymentQuery.count());
//	}
	
//	@Test
//	public  void  test2() {
//		DeploymentQuery createDeploymentQuery = processEngine.getRepositoryService().createDeploymentQuery();
//		createDeploymentQuery.processDefinitionKey("qjlc");
//
//		System.out.println(createDeploymentQuery.count());
//		System.out.println(createDeploymentQuery.list());
//		Deployment deployment = createDeploymentQuery.list().get(0);
//		System.out.println(deployment.getId()+","+deployment.getName()+","+deployment.getCategory());
//		ProcessDefinitionQuery createProcessDefinitionQuery = processEngine.getRepositoryService().createProcessDefinitionQuery();
//		createProcessDefinitionQuery.processDefinitionKey("qjlc");
//		System.out.println(createProcessDefinitionQuery.count());
//		System.out.println(createProcessDefinitionQuery.list());
//		ProcessDefinition processDefinition = createProcessDefinitionQuery.list().get(0);
//		System.out.println(processDefinition.getId()+","+processDefinition.getDiagramResourceName());
//	}
//	@Test
//	public  void  test3() {
//		ProcessInstance startProcessInstanceById = processEngine.getRuntimeService().startProcessInstanceById("qjlc:2:104");
//		System.out.println(startProcessInstanceById.getId());
//		
//	}
//	
	@Test
	public  void  test4() {
		TaskQuery createTaskQuery = processEngine.getTaskService().createTaskQuery();
		createTaskQuery.taskAssignee("ÕÅÈý");
		List<Task> list = createTaskQuery.list();
		for(Task task:list) {
			System.out.println(task.getId()+","+task.getName());
		}
		
	}
	@Test
	public  void  test5() {
		processEngine.getTaskService().complete("402");
	}
	
	@After
	public void relase() {
//		applicationContext.close();
	}
	
}
