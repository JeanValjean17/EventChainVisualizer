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

	public ArrayList<String> taskNamesList;

	private ArrayList<TaskTest> tasks;

	private ArrayList<ArrayList<RunnableTest>> tasksRunnables;

	@Before
	public void execBefore() {
		
		tasks = new ArrayList<TaskTest>();
		
		tasksRunnables = new ArrayList<ArrayList<RunnableTest>>();
		
		ArrayList<RunnableTest> rnT0 = new ArrayList<RunnableTest>();
		
		rnT0.add(new RunnableTest("EcuVehicleSpeedSensor", (float) 0.10101));
		rnT0.add(new RunnableTest("VehicleSpeedSensorTranslation", (float) 0.10101));
		rnT0.add(new RunnableTest("VehicleSpeedSensorVoter", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT1 = new ArrayList<RunnableTest>();
		rnT1.add(new RunnableTest("CylNumObserver", (float) 0.10101));
		rnT1.add(new RunnableTest("DecelerationSensorDiagnosis", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT2 = new ArrayList<RunnableTest>();
		rnT2.add(new RunnableTest("WheelSpeedSensorDiagnosis", (float) 0.10101));
		rnT2.add(new RunnableTest("BrakePedalSensorDiagnosis", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT3 = new ArrayList<RunnableTest>();
		rnT3.add(new RunnableTest("MassAirFlowSensor", (float) 0.10101));
		rnT3.add(new RunnableTest("BaseFuelMass", (float) 0.10101));
		rnT3.add(new RunnableTest("TransientFuelMass", (float) 0.10101));
		rnT3.add(new RunnableTest("TotalFuelMass", (float) 0.10101));
		rnT3.add(new RunnableTest("InjectionTimeActuation", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT4 = new ArrayList<RunnableTest>();
		rnT4.add(new RunnableTest("APedSensor", (float) 0.10101));
		rnT4.add(new RunnableTest("APedVoter", (float) 0.10101));
		rnT4.add(new RunnableTest("ThrottleController", (float) 0.10101));
		rnT4.add(new RunnableTest("ThrottleActuator", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT5 = new ArrayList<RunnableTest>();
		rnT5.add(new RunnableTest("ThrottleSensor", (float) 0.10101));
		rnT5.add(new RunnableTest("EcuBrakePedalSensor", (float) 0.20202));
		rnT5.add(new RunnableTest("BrakePedalSensorTranslation", (float) 0.10101));
		rnT5.add(new RunnableTest("BrakePedalSensorVoter", (float) 0.10101));
		rnT5.add(new RunnableTest("CheckPlausability", (float) 0.10101));
		rnT5.add(new RunnableTest("BrakeSafetyMonitor", (float) 0.10101));
		rnT5.add(new RunnableTest("BrakeForceCalculation", (float) 0.10101));
		rnT5.add(new RunnableTest("BrakeForceArbiter", (float) 0.10101));
		rnT5.add(new RunnableTest("StopLightActuator", (float) 0.10101));
		rnT5.add(new RunnableTest("EcuStopLightActuator", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT6 = new ArrayList<RunnableTest>();
		rnT6.add(new RunnableTest("VehicleStateMonitor", (float) 0.10101));
		rnT6.add(new RunnableTest("EcuDecelerationSensor", (float) 0.20202));
		rnT6.add(new RunnableTest("DecelerationSensorTranslation", (float) 0.10101));
		rnT6.add(new RunnableTest("DecelerationSensorVoter", (float) 0.10101));
		rnT6.add(new RunnableTest("ABSCalculation", (float) 0.10101));
		rnT6.add(new RunnableTest("BrakeForceActuation", (float) 0.10101));
		rnT6.add(new RunnableTest("CaliperPositionCalculation", (float) 0.10101));
		rnT6.add(new RunnableTest("BrakeActuator", (float) 0.10101));
		rnT6.add(new RunnableTest("EcuBrakeActuator", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT7 = new ArrayList<RunnableTest>();
		rnT7.add(new RunnableTest("VehicleSpeedSensorDiagnosis", (float) 0.101010));
		rnT7.add(new RunnableTest("BrakeActuatorMonitor", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT8 = new ArrayList<RunnableTest>();
		rnT8.add(new RunnableTest("DiagnosisArbiter", (float) 0.10101));
		rnT8.add(new RunnableTest("IgnitionTiming", (float) 0.10101));
		rnT8.add(new RunnableTest("IgnitionTimeActuation", (float) 0.10101));
		
		ArrayList<RunnableTest> rnT9 = new ArrayList<RunnableTest>();
		rnT9.add(new RunnableTest("EcuWheelSpeedSensor", (float) 0.10101));
		rnT9.add(new RunnableTest("WheelSpeedSensorTranslation", (float) 0.10101));
		rnT9.add(new RunnableTest("WheelSpeedSensorVoter", (float) 0.10101));

		tasks.add(new TaskTest("Task_ESSP0", 0, 5, rnT0));
		tasks.add(new TaskTest("Task_ESSP1", 0, 10, rnT1));
		tasks.add(new TaskTest("Task_ESSP2", 0, 10, rnT2));
		tasks.add(new TaskTest("Task_ESSP3", 0, 5, rnT3));
		tasks.add(new TaskTest("Task_ESSP4", 0, 5, rnT4));
		tasks.add(new TaskTest("Task_ESSP5", 0, 5, rnT5));
		tasks.add(new TaskTest("Task_ESSP6", 0, 10, rnT6));
		tasks.add(new TaskTest("Task_ESSP7", 0, 10, rnT7));
		tasks.add(new TaskTest("Task_ESSP8", 0, 10, rnT8));
		tasks.add(new TaskTest("Task_ESSP9", 0, 5, rnT9));
		
	}

	@Test
	public void test() {

		Model_reader model = new Model_reader();
		
		model.read_model("model-input/model_democar_AMALTHEA_Democar.amxmi");
		// model.read_model("model-input/ChallengeModel_withCommImplementationTypev082.amxmi");

		int i = 0;

		System.out.println("TASKS");
		for (Task t : model.tasks) {
			/*System.out.println("Name: ");
			System.out.println(t.get_name());
			System.out.print("Periodic Time: ");
			System.out.println(t.get_periodic_time());*/
			ArrayList<Runnable> runnables = t.get_runnables();
			//System.out.println("Runnables: ");
			
			int j = 0;
			
			assertEquals(t.get_name(), tasks.get(i).name);
			assertEquals(t.get_periodic_time(), tasks.get(i).periodic_time, 2);
			
			for (Runnable r : runnables) {
				/*System.out.println(r.get_name());
				System.out.print("Runnable Time: ");
				System.out.println(r.get_time());*/
				
				assertEquals(r.get_time(), tasks.get(i).runnables.get(j).time, 4);
				assertEquals(r.get_name(), tasks.get(i).runnables.get(j).name);
				j++;
			}
			i++;
		}

		System.out.println("Event Chains Runnables");

		ArrayList<Event_chain> evcs = model.event_chains;
		
		

		/*for (Event_chain evc : evcs) {
			//ArrayList<String> runnables = evc.get_runnables();

			for (String r : runnables) {
				System.out.println(r);
			}
		}*/

	}
}
