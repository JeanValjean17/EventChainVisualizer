package visualization.tool;

public class Event {
	private Task task;
	private Event_chain event_chain;
	private Event_type type;
	private float absolute_time;
	
	public Event(Task task, Event_chain event_chain, Event_type type, float absolute_time) {
		this.task = task;
		this.setEvent_chain(event_chain);
		this.type = type;
		this.absolute_time = absolute_time;
	}
	
	public float get_time() {
		return absolute_time;
	}
	
	public Event_type get_type() {
		return type;
	}
	
	public Task get_task() {
		return task;
	}

	public Event_chain getEvent_chain() {
		return event_chain;
	}

	public void setEvent_chain(Event_chain event_chain) {
		this.event_chain = event_chain;
	}
	
	
}
