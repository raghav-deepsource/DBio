package main.java.net.preibisch.distribution.algorithm.clustering.scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import main.java.net.preibisch.distribution.algorithm.controllers.logmanager.MyLogger;

public class BatchScriptFile {

	public static final String TASK_JOB_NAME = "task_";
	public static final String PREPROCESS_JOB_NAME = "preprocess_";
	public static final String LOG_JOB_NAME = "provider_";
	
	public static void generate(File file,String clusterPath,  int totalInputFiles) throws FileNotFoundException {
		generate(file, clusterPath, totalInputFiles, true);
	}
	
	public static void generate(File file,String clusterPath,  int totalInputFiles, boolean parallel) throws FileNotFoundException {
		MyLogger.log.info("Start Generate batch file.. ");

		System.out.println("Input total files:" + totalInputFiles);
		try(PrintWriter out = new PrintWriter(file)){
			out.println("#!/bin/bash");
			out.println("cd " + clusterPath);
			String prepName = "prep";
			out.println("qsub -N \""+prepName+"\" -t " + 1 + " ./"+TaskType.file(TaskType.PREPARE));
			for (int i = 1; i <= totalInputFiles; i++) {
//				String exTaskName = taskName;
				String taskName = "task_" + i ;
				String hold = "";
//				if(!parallel)
//					hold = " -hold_jid "+exTaskName;
				hold = " -hold_jid \""+prepName+"\"";
				out.println("qsub -N \""+taskName+"\""+hold+" -t " + i + " ./"+TaskType.file(TaskType.PROCESS));
			}
			out.flush();
			out.close();
		}
	}

//	public static void GenerateBatchForClusterFile(AbstractCallBack callback, int taskPos) {
//		String id = Job.getId();
//		String path = Login.getServer().getPath();
//		boolean error = false;
//		String tmpDir = Job.getTmpDir();
//		File file = new File(tmpDir, BATCH_CLUSTER_NAME);
//		try (PrintWriter out = new PrintWriter(file)) {
//			out.println("#!/bin/bash");
//			out.println("cd " + path);
//			out.println("qsub -N \"task_1\" ./task.sh");
//			out.println("qsub -N \"prov_1\" -hold_jid task_1 -v uuid=" + id + " ./logProvider.sh");
//
//			out.flush();
//			out.close();
//
//		} catch (FileNotFoundException e) {
//			callback.onError(e.toString());
//			e.printStackTrace();
//			error = true;
//		}
//		if (!error) {
//			callback.onSuccess(taskPos);
//		}
//	}
//
//	public static void GenerateBatchForClusterFile(AbstractCallBack callback, int totalBlocks, int taskPos) {
//		MyLogger.log.info("Start Generate batch for Cluster file.. ");
//		String path = Login.getServer().getPath();
//		String tmpDir = Job.getTmpDir();
//		File file = new File(tmpDir, BATCH_CLUSTER_NAME);
//		try (PrintWriter out = new PrintWriter(file)) {
//			out.println("#!/bin/bash");
//			File f = new File(path, Job.getId());
//			out.print("mkdir " + f.getAbsolutePath());
//			out.println("cd " + f.getAbsolutePath());
//			for (int i = 1; i <= totalBlocks; i++) {
//				out.println(getPreProcessLine(i));
//				out.println(getTaskLine(i));
//				out.println(getLogProviderLine(i));
//			}
//			out.flush();
//			out.close();
//			callback.onSuccess(taskPos);
//			System.out.println("Called next from " + taskPos);
//		} catch (FileNotFoundException e) {
//			callback.onError(e.toString());
//			e.printStackTrace();
//		}
//		MyLogger.log.info("Finish Generate batch for Cluster file");
//	}
//
//	private static String getLogProviderLine(int i) {
//		String id = Job.getId();
//		return "qsub -N \"" + LOG_JOB_NAME + i + " -hold_jid " + TASK_JOB_NAME + i + " -v uuid=" + id + " ./"
//				+ ShellGenerator.LOG_PROVIDER_SHELL_NAME;
//	}
//
//	private static String getPreProcessLine(int i) {
//		return "qsub -N \"" + PREPROCESS_JOB_NAME + i + "\" -t " + i + " ./" + ShellGenerator.PREPROCESS_SHELL_NAME;
//	}
//
//	private static String getTaskLine(int i) {
//		return "qsub -N \"" + TASK_JOB_NAME + i + "\" -t " + i + " -hold_jid " + PREPROCESS_JOB_NAME + i + " ./"
//				+ ShellGenerator.TASK_SHELL_NAME;
//	}
//
//	public static void main(String[] args) {
//		BatchGenerator.GenerateBatchForLocalFiles(10, 94, new Callback(), 0);
//	}
}
