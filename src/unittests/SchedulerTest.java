package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eclipse.app4mc.amalthea.model.EventChain;
import org.junit.Before;
import org.junit.Test;

import visualization.tool.Scheduler;
import visualization.tool.Event;
import visualization.tool.Event_chain;
import visualization.tool.Model_reader;
import visualization.tool.Runnable;
import visualization.tool.Task;

public class SchedulerTest {

	ArrayList<Task> taskList;

	@Before
	public void execBefore() {

		taskList = new ArrayList<Task>();

		Task t0 = new Task();
		Task t1 = new Task();
		Task t2 = new Task();
		Task t3 = new Task();
		Task t4 = new Task();
		Task t5 = new Task();
		Task t6 = new Task();
		Task t7 = new Task();
		Task t8 = new Task();
		Task t9 = new Task();

		t0.set_name("Task_ESSP0");
		t1.set_name("Task_ESSP1");
		t2.set_name("Task_ESSP2");
		t3.set_name("Task_ESSP3");
		t4.set_name("Task_ESSP4");
		t5.set_name("Task_ESSP5");
		t2.set_name("Task_ESSP6");
		t3.set_name("Task_ESSP7");
		t4.set_name("Task_ESSP8");
		t5.set_name("Task_ESSP9");

		t0.set_periodic_time(5);
		t1.set_periodic_time(10);
		t2.set_periodic_time(10);
		t3.set_periodic_time(5);
		t4.set_periodic_time(5);
		t5.set_periodic_time(5);
		t6.set_periodic_time(10);
		t7.set_periodic_time(10);
		t8.set_periodic_time(10);
		t9.set_periodic_time(5);

		taskList.add(t0);
		taskList.add(t1);
		taskList.add(t2);
		taskList.add(t3);
		taskList.add(t4);
		taskList.add(t5);
		taskList.add(t6);
		taskList.add(t7);
		taskList.add(t8);
		taskList.add(t9);
	}

	@Test
	public void test() {
		Model_reader model = new Model_reader();

		model.read_model("model-input/model_democar_AMALTHEA_Democar_Corrected.amxmi");

		System.out.println("TASKS");
		for (Task t : model.tasks) {
			System.out.println("Name: ");
			System.out.println(t.get_name());
		}

		Scheduler sch = new Scheduler();

		ArrayList<Event_chain> evcs = model.event_chains;

		sch.build_schedule(model.tasks, 100);

		sch.sort_event_queue();

		sch.build_event_chains(evcs, 100);

		for (Event evn : sch.event_queue) {
			System.out.println(evn.get_task().get_name());
			System.out.println("Type");
			System.out.println(evn.get_type());
			System.out.println("Absolute Time");
			System.out.println(evn.get_time());
			System.out.println("");
			Event_chain event_chain = ((Event) evn).getEvent_chain();
			if (event_chain != null) {
				System.out.println("");
				System.out.println(event_chain.get_name());
				System.out.println("");

			}

		}
	}

}
