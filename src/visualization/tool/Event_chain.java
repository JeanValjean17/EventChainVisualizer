package visualization.tool;

import java.util.ArrayList;

public class Event_chain {
	private ArrayList<String> runnables_names;
	
	public Event_chain() {
		runnables_names = new ArrayList<String>();
	}
	
	public void add_runnable(String runnable_name) {
		runnables_names.add(runnable_name);
	}
	
	public ArrayList<String> get_runnables()
	{
		return runnables_names;
	}
}
