package com.linfeng.activiti;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.FileCopyUtils;


public class TestActiviti{
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
	
	/**
	 * 部署流程定义  两种方式  zip
	 */
	@Test
	public  void test1() {
		DeploymentBuilder createDeployment = processEngine.getRepositoryService().createDeployment();
		createDeployment.addZipInputStream(new  ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("test1.zip")));
		createDeployment.deploy();
		
	}
	
	//查询部署列表
	@Test
	public  void  test2() {
		DeploymentQuery createDeploymentQuery = processEngine.getRepositoryService().createDeploymentQuery();
		List<Deployment> list = createDeploymentQuery.list();
		for(Deployment deployment:list) {
			System.out.println(deployment.getId()+"，"+deployment.getName()+","+deployment.getCategory()+","+deployment.getDeploymentTime());
		}
		
	}
	
	@Test
	public  void  test3() {
		ProcessDefinitionQuery createProcessDefinitionQuery = processEngine.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> list = createProcessDefinitionQuery.list();
		for(ProcessDefinition  processDefinition:list) {
			System.out.println(processDefinition.getName());
		}
	}
	
	
	@Test
	public  void  test4() {
		processEngine.getRepositoryService().deleteDeployment("101",true);
	}
	
	@Test
	public  void   test5() {
		processEngine.getRuntimeService().startProcessInstanceByKey("qjlc");
	}
	
	@Test
	public  void  test6() throws FileNotFoundException, IOException {
		List<String> deploymentResourceNames = processEngine.getRepositoryService().getDeploymentResourceNames("1001");
		for(String name:deploymentResourceNames) {
			FileCopyUtils.copy(processEngine.getRepositoryService().getResourceAsStream("1001", name), new  FileOutputStream("E://"+name));
		}
	}
	
	@Test
	public  void  test7() throws FileNotFoundException, IOException {
		InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram("qjlc:3:604");
		FileCopyUtils.copy(processDiagram, new  FileOutputStream("E://+my.png"));
	}
	
	@Test
	public  void  test8() {
		ProcessInstanceQuery createProcessInstanceQuery = processEngine.getRuntimeService().createProcessInstanceQuery();
		createProcessInstanceQuery.processDefinitionKey("qjlc");
		createProcessInstanceQuery.orderByProcessInstanceId().desc();
		createProcessInstanceQuery.listPage(0, 3);
		List<ProcessInstance> list = createProcessInstanceQuery.list();
		for(ProcessInstance processInstance:list) {
			System.out.println(processInstance.getId()+","+processInstance.getActivityId());
		}
	}
	
	@Test
	public  void  test9() {
		processEngine.getRuntimeService().deleteProcessInstance("1301", "我愿意");
		
	}
	
	
	@Test
	public  void   test10() {
		TaskQuery createTaskQuery = processEngine.getTaskService().createTaskQuery();
		
		List<Task> list = createTaskQuery.list();
		for(Task task:list) {
			System.out.println(task.getAssignee()+","+task.getId());
		}
	}
	
	@After
	public void relase() {
//		applicationContext.close();
	}
	
}
