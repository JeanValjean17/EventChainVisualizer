package visualization.tool;

import java.util.ArrayList;
import java.util.Comparator;

public class Scheduler {
	
	private float current_time;
	private ArrayList<Event> event_queue;
	
	public ArrayList<Event> get_event_queue() {
		return event_queue;
	}
	
	public Scheduler() {
		current_time = 0;
		event_queue = new ArrayList<Event>();
	}
	
	public void build_schedule(ArrayList<Task> tasks, float max_time) {
		
		current_time = 0;
		
		for (Task task : tasks) {
			
			float event_time = 0;
			while (event_time < max_time) {
				Event event = new Event(task, null, Event_type.TASK_QUEUED, event_time);
				event_queue.add(event);
				
				event_time += task.get_periodic_time();
			}
		}
		
		Event next_event = null;
		
		do {
			
			Task selected_task = get_selected_task(tasks);
			
			if (selected_task != null) {
				Event start_event = new Event(selected_task, null, Event_type.TASK_START, current_time);
				Event end_event = new Event(selected_task, null, Event_type.TASK_END, current_time + selected_task.get_total_time());
				
				event_queue.add(start_event);
				event_queue.add(end_event);
			}
			
			next_event = get_next_event();
			
			if (next_event != null) {
				current_time = next_event.get_time();
			}
			
		} while (next_event != null);
		
	}
	
	public void sort_event_queue() {
		
		class Sort_by_time_ascending implements Comparator<Event> 
		{ 
		    public int compare(Event a, Event b) 
		    { 
		        if (a.get_time() - b.get_time() == 0) return 0;
		        else if (a.get_time() - b.get_time() < 0) return -1;
		        else return 1;
		    } 
		}
		
		event_queue.sort(new Sort_by_time_ascending());
		
		/*for (int i = 0; i < event_queue.size(); i++) {
			for (int j = i; j < event_queue.size(); j++) {
				if (event_queue.get(j).get_time() < event_queue.get(i).get_time()) {
					Event temp = event_queue.set(i, event_queue.get(j));
					event_queue.set(j, temp);
				}
			}
		}*/
	}
	
	public void build_event_chains(ArrayList<Event_chain> event_chains, float max_time) {
		
		for (Event_chain event_chain : event_chains) {
			
			current_time = 0;
			
			boolean max_time_reached = false;
			
			while (max_time_reached == false) {
				
				for (Event_chain_entry event_chain_entry : event_chain.get_entries()) {
					String runnable_name = event_chain_entry.get_runnable_name();
					Event selected_event = get_selected_runnable_event(runnable_name);
					
					if (selected_event != null) {
						float runnable_absolute_time = selected_event.get_time() + get_runnable_offset_from_task_start(selected_event.get_task(), runnable_name, event_chain_entry.get_type());
						Event chain_element = new Event(selected_event.get_task(), event_chain, event_chain_entry.get_type(), runnable_absolute_time);
						event_queue.add(chain_element);
						current_time = runnable_absolute_time;
					}
					else {
						max_time_reached = true;
						break;
					}
				}
			}
		}
		
		
	}
	
	private Event get_next_event() {
		Event next_event = null;
		
		for (Event event : event_queue) {
			if (event.get_time() <= current_time) {
				continue;
			}
			
			if (next_event == null) {
				next_event = event;
			}
			else {
					if (event.get_time() < next_event.get_time()) {
						next_event = event;
					}
			}
		}
		
		return next_event;
	}
	
	private Task get_selected_task(ArrayList<Task> tasks) {
		Task selected_task = null;
		
		for (Event enqueue_event : event_queue) {
			if (enqueue_event.get_type() == Event_type.TASK_QUEUED) {
				if (enqueue_event.get_time() <= current_time) {
					if (is_task_started(enqueue_event) == false) {
						Task task = enqueue_event.get_task();
						if (selected_task == null) {
							selected_task = task;
						}
						else {
							if (task.get_periodic_time() < selected_task.get_periodic_time()) {
								selected_task = task;
							}
						}
					}
				}
			}
		}
		
		return selected_task;
	}
	
	private boolean is_task_started(Event enqueue_event) {
		
		for (Event event : event_queue) {
			if (event.get_task() == enqueue_event.get_task() && event.get_time() >= enqueue_event.get_time() && event.get_type() == Event_type.TASK_START) {
				return true;
			}
		}
		
		return false;
	}
	
	private Event get_selected_runnable_event(String runnable_name) {
		Event selected_event = null;
		
		for (Event event : event_queue) {
			
			if (event.get_time() > current_time) {
				if (event.get_type() == Event_type.TASK_START) {
					for (Runnable runnable : event.get_task().get_runnables()) {
						if (runnable.get_name().compareTo(runnable_name) == 0) {
							if (selected_event == null) {
								selected_event = event;
							}
							else {
								if (event.get_time() < selected_event.get_time()) {
									selected_event = event;
								}
							}
						}
					}
				}
			}
		}
		
		return selected_event;
	}
	
	private float get_runnable_offset_from_task_start(Task task, String runnable_name, Event_type event_type) {
		float time = 0;
		
		for (Runnable runnable : task.get_runnables()) {
			
			time += runnable.get_time();
			//System.out.println("runnable " + runnable.get_name() + " added " + task.get_name() + " runnable name " + runnable_name + " compare result " + String.valueOf(runnable.get_name().equalsIgnoreCase(runnable_name)));
			if (runnable.get_name().equalsIgnoreCase(runnable_name) == true) {
				if (event_type == Event_type.CHAIN_START) {
					time -= runnable.get_time();
					//System.out.println("runnable found " + task.get_name());
				}
				break;
			}
		}
		
		return time;
	}
	
	/*private Task get_task_by_name(String task_name, ArrayList<Task> tasks) {
		Task Returned_task = null;
		
		for (Task task : tasks) {
			if (task.get_name().compareTo(task_name) == 0) {
				Returned_task = task;
			}
		}
		
		return Returned_task;
	}*/
}
