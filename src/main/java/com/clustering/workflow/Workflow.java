package main.java.com.clustering.workflow;

import java.util.HashMap;
import java.util.List;

import main.java.com.blockmanager.Block;
import main.java.com.controllers.items.AppMode;
import main.java.com.controllers.items.callback.FunctionSequenceManager;
import main.java.com.gui.items.ProgressBarPanel;

public class Workflow {
	public static final String START = "START";
	public static final String RESULT = "RESULT";

	public static List<Block> blocks;
	public static HashMap<Integer, Block> blockMap;
	public static ProgressBarPanel progressBarPanel;
	public static AppMode appMode = AppMode.LocalInputMode;

	public Workflow() {
		progressBarPanel = new ProgressBarPanel(0, 100);
	}

	public static void startWorkflow() {
		FunctionSequenceManager fl = new FunctionSequenceManager(START);
		fl.start();
	}

	public static void resultWorkflow() {
		FunctionSequenceManager fl = new FunctionSequenceManager(RESULT);
		fl.start();

	}

}