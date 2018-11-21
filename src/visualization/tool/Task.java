package visualization.tool;

import java.util.ArrayList;

public class Task {

	private String name;
	private float total_time;
	private float periodic_time;
	private ArrayList<Runnable> runnables;

	public Task() {
		name = "not_set";
		total_time = 0;
		runnables = new ArrayList<Runnable>();
	}

	public void set_name(String name) {
		this.name = name;
	}

	public String get_name() {
		return name;
	}

	public void calculate_total_time() {
		for (Runnable runnable : runnables) {
			total_time += runnable.get_time();
		}
	}

	/*
	 * public float get_total_time() { return total_time; }
	 * 
	 * public void set_total_time(float total_time) { this.total_time = total_time;
	 * }
	 */

	public float get_periodic_time() {
		return periodic_time;
	}

	public void set_periodic_time(float periodic_time) {
		this.periodic_time = periodic_time;
	}

	public void add_runnable(Runnable runnable) {
		runnables.add(runnable);
	}

	public ArrayList<Runnable> get_runnables() {
		return runnables;
	}
}
