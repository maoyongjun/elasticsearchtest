package org.foxconn.elasticsearchtest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ScheduleRunner {
	enum Status {
		OPEN,
		CLOSE
	}
	
	Logger logger = Logger.getLogger(ScheduleRunner.class);
	ScheduledExecutorService taskService = Executors.newScheduledThreadPool(10);
	Map<String,MyTask> oneDayRunnables = new HashMap<String,MyTask>();
	public void run() {
		addRunnables();
		
		for(MyTask task : oneDayRunnables.values()){
			if(task.taskName.equals("changeStatus")){
				taskService.scheduleAtFixedRate(task.runnable, 1, 10, TimeUnit.SECONDS);
			}else{
				taskService.scheduleAtFixedRate(task.runnable, 0, 10, TimeUnit.SECONDS);
			}
		}
	}
	
	/**
	 * 从数据库获取排程任务和状态，以及执行的间隔时间
	 * 
	 */
	public void addRunnables(){
		//从数据库获取所有的任务列表和状态
		
		//每一条记录建立一个MyTask，指定一个runnable.
		
		addSendPcsRunnable();
		addSendCodeRunnable();
		addChangeStatusRunnable();
	}
	
	public void addSendPcsRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				MyTask task = oneDayRunnables.get("pcs1");
				if(task.taskStatus==Status.OPEN){
					logger.info(task.taskName+":running....");
				}
			}
		};
		MyTask task = new MyTask();
		task.runnable = runnable;
		task.taskName="pcs1";
		task.taskStatus=Status.OPEN;
		oneDayRunnables.put(task.taskName, task);
	}
	
	public void addSendCodeRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				MyTask task = oneDayRunnables.get("pcs2");
				if(task.taskStatus==Status.OPEN){
					logger.info(task.taskName+":running....");
				}
			}
		};
		MyTask task = new MyTask();
		task.runnable = runnable;
		task.taskName="pcs2";
		task.taskStatus=Status.OPEN;
		oneDayRunnables.put(task.taskName, task);
	}
	
	public void addChangeStatusRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for(MyTask task :oneDayRunnables.values()){
					if(task.taskName.equals("changeStatus")){
						continue;
					}
					Random random = new Random();
					int num = random.nextInt();
					if(num%2==0){
						task.taskStatus=Status.CLOSE;
						logger.info(">>>change task Stuats close:"+task.taskName);
					}else{
						task.taskStatus=Status.OPEN;
						logger.info(">>>change task Stuats open:"+task.taskName);
					}
				}
				logger.info("***************************\n");
			}
		};
		MyTask task = new MyTask();
		task.runnable = runnable;
		task.taskName="changeStatus";
		task.taskStatus=Status.OPEN;
		oneDayRunnables.put(task.taskName, task);
	}
	private class MyTask{
		private String taskName ;
		private Status taskStatus;
		private Runnable runnable;
		
	}
}
