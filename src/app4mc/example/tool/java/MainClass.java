package app4mc.example.tool.java;

import java.io.File;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;

public class MainClass {

	public void LoadEventChain() {
		// example: relative path
		final File inputFile = new File("model-input/ChallengeModel_withCommImplementationTypev082.amxmi");
		final File outputFile = new File("model-output/LoadModifySave/democar_1.amxmi");

		// ***** Load *****

		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return;
		}
		
		model.getConstraintsModel().getEventChains().get(0).getSegments().get(0).getEventChain().getStimulus().getName();
		
	}

}
