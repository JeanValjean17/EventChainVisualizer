package visualization.tool;

import java.io.File;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.EventChain;
import org.eclipse.app4mc.amalthea.model.EventChainItem;
import org.eclipse.app4mc.amalthea.model.ExecutionNeed;
import org.eclipse.app4mc.amalthea.model.FrequencyDomain;
import org.eclipse.app4mc.amalthea.model.HwDomain;
import org.eclipse.app4mc.amalthea.model.HwModule;
import org.eclipse.app4mc.amalthea.model.PeriodicStimulus;
import org.eclipse.app4mc.amalthea.model.ProcessingUnit;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.Time;
import org.eclipse.app4mc.amalthea.model.Event;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableEvent;
import org.eclipse.app4mc.amalthea.model.RunnableItem;
import org.eclipse.app4mc.amalthea.model.Stimulus;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.util.HardwareUtil;
import org.eclipse.app4mc.amalthea.model.util.RuntimeUtil;
import org.eclipse.app4mc.amalthea.model.util.RuntimeUtil.TimeType;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;
import org.eclipse.emf.common.util.EList;

import com.sun.javafx.event.EventUtil;

public class Model_reader {

	public ArrayList<visualization.tool.Task> tasks;
	public ArrayList<Event_chain> event_chains;
	
	public void read_model(String file_name) {
		
		File model_file = new File(file_name);
		Amalthea model = AmaltheaLoader.loadFromFile(model_file);
		
		ProcessingUnit processing_unit = HardwareUtil.getModulesFromHWModel(ProcessingUnit.class, model).get(0);
		
		/*HwModule hardware_module = HardwareUtil.getModulesFromHWModel(HwModule.class, model).get(0);
		float frequancy = HardwareUtil.getFrequencyOfModuleInHz(hardware_module);*/
		
		tasks = new ArrayList<visualization.tool.Task>();
		EList<Task> model_tasks = model.getSwModel().getTasks();
		
		for (Task model_task : model_tasks) {
			visualization.tool.Task task = new visualization.tool.Task();
			
			String task_name = model_task.getName();
			task.set_name(task_name);
			
			for (Runnable model_runnable : SoftwareUtil.getRunnableList(model_task, null)) {
				visualization.tool.Runnable runnable = new visualization.tool.Runnable();
				
				String runnable_name = model_runnable.getName();
				runnable.set_name(runnable_name);
				
				Time t = RuntimeUtil.getExecutionTimeForRunnable(model_runnable, TimeType.ACET, processing_unit,model.getHwModel().getFeatureCategories().get(0).getFeatures(), null);
				if(t == null) continue;
				float runnable_time = t.getValue().floatValue();				
				runnable.set_time(runnable_time / 1.0E9f);
				
				task.add_runnable(runnable);
			}
			
			task.calculate_total_time();
			
			float periodic_time = 0;
			periodic_time = RuntimeUtil.getPeriodsOfProcess(model, model_task, TimeType.ACET, null).get(0).getValue().floatValue();
			/*Stimulus stimulus = model_task.getStimuli().get(0);
			if (stimulus instanceof PeriodicStimulus) {
				periodic_time = ((PeriodicStimulus) stimulus).getRecurrence().getValue().floatValue();
				if (((PeriodicStimulus) stimulus).getRecurrence().getUnit().getLiteral() == "us")
				{
					periodic_time /= 1000;
				}
			}
			else {
				continue;	// ignore non-periodic tasks
			}*/
			task.set_periodic_time(periodic_time);
			
			tasks.add(task);
		}
		
		event_chains = new ArrayList<Event_chain>();
		EList<EventChain> model_event_chains = model.getConstraintsModel().getEventChains();
		for (EventChain model_event_chain : model_event_chains) {
			Event_chain event_chain = new Event_chain();
			
			event_chain.set_name(model_event_chain.getName());
			
			//for (EventChainItem event_chain_item : model_event_chain.getSegments()) {
				//Event stimulus_event = event_chain_item.getEventChain().getStimulus();
			Event stimulus_event = model_event_chain.getStimulus();
			
			if (stimulus_event instanceof RunnableEvent) {
				event_chain.add_runnable(((RunnableEvent) stimulus_event).getEntity().getName());
			}
			else {
				break;
			}
			
			//Event response_event = event_chain_item.getEventChain().getResponse();
			Event response_event = model_event_chain.getResponse();
			
			if (response_event instanceof RunnableEvent) {
				event_chain.add_runnable(((RunnableEvent) response_event).getEntity().getName());
			}
			else {
				break;
			}
			//}
			
			
			event_chains.add(event_chain);
		}
	}
}

