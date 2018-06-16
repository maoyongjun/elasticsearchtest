package org.foxconn.elasticsearchtest;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
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
			if(task.taskStatus==Status.OPEN){
				taskService.scheduleAtFixedRate(task.runnable, 0, 10, TimeUnit.SECONDS);
			}
		}
	}
	
	public void addRunnables(){
		addSendPcsRunnable();
		addSendCodeRunnable();
		addChangeStatusRunnable();
	}
	
	public void addSendPcsRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("send pcs task Begin");
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
				logger.info("send Code task Begin");
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
					if(!task.taskName.equals("changeStatus")){
						task.taskStatus=Status.CLOSE;
					}
					logger.info("change task Stuats Begin:"+task.taskName);
				}
				
				
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
