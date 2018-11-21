package unittests;

import java.util.ArrayList;

import visualization.tool.Runnable;

public class TaskTest {
	
	public String name;
	public float total_time;
	public float periodic_time;
	public ArrayList<RunnableTest> runnables;
	
	public TaskTest(String name, float total_time, float periodic_time, ArrayList<RunnableTest> runnables)
	{
		this.name = name;
		this.total_time = total_time;
		this.periodic_time = periodic_time;
		this.runnables = runnables;
	}

}
