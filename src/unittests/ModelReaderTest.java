package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import visualization.tool.Event_chain;
import visualization.tool.Model_reader;
import visualization.tool.Runnable;
import visualization.tool.Task;

public class ModelReaderTest {
	
	public String[] taskNamesList;
	
	@Before
	public void execBefore() {
		taskNamesList = new String[2];
		taskNamesList[0] = "Task_ESSP1";
		taskNamesList[0] = "Task_ESSP3";
	}

	@Test
	public void test() {
		
		Model_reader model = new Model_reader();
		
		model.read_model("model-input/model_democar_AMALTHEA_Democar.amxmi");
		//model.read_model("model-input/ChallengeModel_withCommImplementationTypev082.amxmi");
		
		int i = 0;
		
		System.out.println("TASKS");
		for (Task t : model.tasks)
		{
			System.out.println("Name: ");
			System.out.println(t.get_name());
			System.out.print("Periodic Time: ");
			System.out.println(t.get_periodic_time());
			ArrayList<Runnable> runnables = t.get_runnables();
			System.out.println("Runnables: ");
			for (Runnable r : runnables)
			{
				System.out.println(r.get_name());
				System.out.print("Runnable Time: ");
				System.out.println(r.get_time());
			}
			//assertEquals(t.get_name(), taskNamesList[i]);
			//i++;
		}
		
		System.out.println("Event Chains Runnables");
		
		ArrayList<Event_chain> evcs = model.event_chains;
		
		for (Event_chain evc : evcs)
		{
			ArrayList<String> runnables = evc.get_runnables();
			
			for (String r : runnables)
			{
				System.out.println(r);
			}
		}
		
	}

}
