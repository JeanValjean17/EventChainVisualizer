package visualization.tool;

import java.util.ArrayList;

public class Event_chain {
	private String name;
	private ArrayList<Event_chain_entry> event_chain_entries;
	
	public Event_chain() {
		event_chain_entries = new ArrayList<Event_chain_entry>();
		name = "not set";
	}
	
	public void set_name(String name) {
		this.name = name;
	}
	
	public String get_name() {
		return name;
	}
	
	public void add_entry(Event_chain_entry event_chain_entry) {
		for (Event_chain_entry old_event_chain_entry : event_chain_entries) {
			if (old_event_chain_entry.get_runnable_name().compareTo(event_chain_entry.get_runnable_name()) == 0) {
				return;
			}
		}
		event_chain_entries.add(event_chain_entry);
	}
	
	public ArrayList<Event_chain_entry> get_entries(){
		return event_chain_entries;
	}
	
}
