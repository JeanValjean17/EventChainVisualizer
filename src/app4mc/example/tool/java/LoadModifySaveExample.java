/**
 ********************************************************************************
 * Copyright (c) 2018 Robert Bosch GmbH.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */

package app4mc.example.tool.java;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.ProcessingUnit;
import org.eclipse.app4mc.amalthea.model.Tag;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaWriter;
import org.eclipse.app4mc.amalthea.model.util.DeploymentUtil;
import org.eclipse.app4mc.amalthea.model.util.HardwareUtil;
import org.eclipse.app4mc.amalthea.model.util.ModelUtil;
import org.eclipse.app4mc.amalthea.model.util.RuntimeUtil;
import org.eclipse.app4mc.amalthea.model.util.RuntimeUtil.TimeType;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.emf.common.util.EList;

public class LoadModifySaveExample {

	public static void main(String[] args) {

		// example: absolute path
		// final File inputFile = new File("d:/temp/democar.amxmi");
		// final File outputFile = new File("d:/temp/democar_1.amxmi");

		// example: relative path
		final File inputFile = new File("model-input/democar.amxmi");
		final File outputFile = new File("model-output/LoadModifySave/democar_1.amxmi");

		// ***** Load *****

		
		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return;
		}
		
		int runnablesCount = model.getSwModel().getRunnables().size();
		
		System.out.println("Runnable count: " + runnablesCount);
				
		
		List<ProcessingUnit> processingUnits = HardwareUtil.getModulesFromHWModel(ProcessingUnit.class, model);
		
		EList<Task> tasks = model.getSwModel().getTasks();
		
		for (ProcessingUnit pu : processingUnits)
		{
			System.out.println("Processing Unit " + pu.getName() + " -- " + HardwareUtil.getFrequencyOfModuleInHz(pu) + " [Hz]");
		}
		
		for (Task task : tasks)
		{
			Set<ProcessingUnit> core = DeploymentUtil.getAssignedCoreForProcess(task, model);
		
		}
		
		EList<Runnable> runnables = model.getSwModel().getRunnables();
		
		for (Runnable runnable : runnables)
		{
			System.out.println("Labels for runnables " + runnable.getName() + ": ");
							
			Set<Label> readLabels = SoftwareUtil.getReadLabelSet(runnable, null);
			Set<Label> writeLabels = SoftwareUtil.getWriteLabelSet(runnable, null);
//			
//			for (ProcessingUnit pu : processingUnits)
//			{
//				Long time = RuntimeUtil.getExecutionNeedValueCountForRunnable(runnable, TimeType.WCET, procUnitDef, hwFeature, modes)
//			}
			
			for (Label label : readLabels)
			{
				System.out.println("*R " + label.getName() + ", " + label.getSize());
			}
			
			for (Label label : writeLabels)
			{
				System.out.println("*W " + label.getName() + ", " + label.getSize());
			}								
		}

		// ***** Modify *****

		final AmaltheaFactory fac = AmaltheaFactory.eINSTANCE;

		Tag tag = fac.createTag();
		tag.setName("The new tag!");
		ModelUtil.getOrCreateCommonElements(model).getTags().add(tag);

		// ***** Save *****

		AmaltheaWriter.writeToFile(model, outputFile);

		System.out.println("done");
	}

}

