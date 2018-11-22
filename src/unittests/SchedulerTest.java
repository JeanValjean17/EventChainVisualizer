package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.app4mc.amalthea.model.EventChain;
import org.junit.Before;
import org.junit.Test;

import visualization.tool.Scheduler;
import visualization.tool.Event;
import visualization.tool.Event_chain;
import visualization.tool.Event_type;
import visualization.tool.Model_reader;
import visualization.tool.Runnable;
import visualization.tool.Task;

public class SchedulerTest {

	ArrayList<EventTest> eventTests;

	@Before
	public void execBefore() {
		eventTests = new ArrayList<EventTest>();
		eventTests.add(new EventTest("Task_ESSP0", Event_type.TASK_START, 5));
		eventTests.add(new EventTest("Task_ESSP8", Event_type.TASK_END, 4.5f));
		eventTests.add(new EventTest("Task_ESSP5", Event_type.TASK_QUEUED, 15));
		eventTests.add(new EventTest("Task_ESSP1", Event_type.TASK_START, 12.6f));
		eventTests.add(new EventTest("Task_ESSP9", Event_type.TASK_END, 17.6f));
		eventTests.add(new EventTest("Task_ESSP6", Event_type.TASK_QUEUED, 0));
		eventTests.add(new EventTest("Task_ESSP2", Event_type.TASK_END, 3.0f));
		eventTests.add(new EventTest("Task_ESSP7", Event_type.TASK_START, 14.0f));
		eventTests.add(new EventTest("Task_ESSP3", Event_type.TASK_QUEUED, 20.0f));
		eventTests.add(new EventTest("Task_ESSP4", Event_type.CHAIN_START, 1.0f));
		eventTests.add(new EventTest("Task_ESSP5", Event_type.CHAIN_END, 1.3f));
	}

	@Test
	public void test() {
		Model_reader model = new Model_reader();

		model.read_model("model-input/model_democar_AMALTHEA_Democar_Corrected.amxmi");

		Scheduler sch = new Scheduler();

		ArrayList<Event_chain> evcs = model.get_event_chains();

		sch.build_schedule(model.get_tasks(), 100);

		sch.sort_event_queue();

		sch.build_event_chains(evcs, 100);

		int count = 0;
		for (EventTest evnT : eventTests) {
			for (Event event : sch.get_event_queue()) {
				float diff = Math.abs(event.get_time() - evnT.absolute_time);
				if (evnT.taskName.equalsIgnoreCase(event.get_task().get_name()) && evnT.type == event.get_type()
						&& diff < 0.1) {
					System.out.println(event.get_task().get_name() + " " + event.get_type() + " " + event.get_time());
					count++;
				}
			}
		}
		
		assertEquals(eventTests.size(), count);

		System.out.println(count);
		
		for (Event evn : sch.get_event_queue()) {
			System.out.println(evn.get_task().get_name());
			System.out.println("Type");
			System.out.println(evn.get_type());
			System.out.println("Absolute Time");
			System.out.println(evn.get_time());
			System.out.println("");
			Event_chain event_chain = ((Event) evn).get_event_chain();

			if (event_chain != null) {
				System.out.println("");
				System.out.println(event_chain.get_name());
				System.out.println("");

			}
		}
	}

}
