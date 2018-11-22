package unittests;

import visualization.tool.Event_type;

public class EventTest
{
	public String taskName;
	public Event_type type;
	public float absolute_time;
	
	public EventTest(String taskName, Event_type type, float time)
	{
		this.taskName = taskName;
		this.type = type;
		this.absolute_time = time;
	}
}