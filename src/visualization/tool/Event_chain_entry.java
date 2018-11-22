package visualization.tool;

public class Event_chain_entry {
	private String runnable_name;
	private Event_type type;

	public Event_chain_entry(String runnable_name, Event_type type) {
		this.runnable_name = runnable_name;
		this.type = type;
	}
	
	public String get_runnable_name() {
		return runnable_name;
	}

	public Event_type get_type() {
		return type;
	}
	
}
