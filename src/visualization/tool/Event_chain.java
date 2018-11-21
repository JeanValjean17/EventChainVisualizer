package visualization.tool;

import java.util.ArrayList;

public class Event_chain {
	private String name;
	private ArrayList<String> runnables_names;
	
	public Event_chain() {
		runnables_names = new ArrayList<String>();
		name = "not set";
	}
	
	public void set_name(String name) {
		this.name = name;
	}
	
	public String get_name() {
		return name;
	}
	
	public void add_runnable(String runnable_name) {
		for (String string : runnables_names) {
			if (string.compareTo(runnable_name) == 0) {
				return;
			}
		}
		runnables_names.add(runnable_name);
	}
	
	public ArrayList<String> get_runnables_names(){
		return runnables_names;
	}
	
}
