package rp.jobDecider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class Reader {

	final static Logger logger = Logger.getLogger(Reader.class);
	static HashMap<String, Item> items = new HashMap<>();
	static ArrayList<Job> jobsTraining = new ArrayList<>();
	static ArrayList<Job> jobs = new ArrayList<>();
	static ArrayList<Task> tasks = new ArrayList<>();

	public static void main(String[] args) {
		
		File userMessagesFile = new File("csv");
        String userMessagesPath = userMessagesFile.getAbsolutePath();

		String itemFile = userMessagesPath + "/items.csv";
		String locationFile = userMessagesPath + "/locations.csv";
		String jobFile = userMessagesPath + "/jobs.csv";
		String jobTrainingFile = userMessagesPath + "/training_jobs.csv";
		String cancellationFile = userMessagesPath + "/cancellations.csv";
		BufferedReader br = null;
		BufferedReader br1 = null;
		String line = "";
		String line2 = "";
		String cvsSplitBy = ",";
		boolean cancel = false;

		try {

			br = new BufferedReader(new FileReader(itemFile));
			br1 = new BufferedReader(new FileReader(locationFile));
			logger.debug("Reading items and locations csv files");

			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				String[] item = line.split(cvsSplitBy);
				String[] locationArray = line2.split(cvsSplitBy);

				items.put(item[0], new Item(item[0], Float.parseFloat(item[1]), Float.parseFloat(item[2]),
						Integer.parseInt(locationArray[0]), Integer.parseInt(locationArray[1])));

			}

			br.close();
			br1.close();

			br = new BufferedReader(new FileReader(jobTrainingFile));
			br1 = new BufferedReader(new FileReader(cancellationFile));

			logger.debug("Reading training_jobs and cancellations csv files");

			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				tasks = new ArrayList<Task>();
				String[] jobInfo = line.split(cvsSplitBy);
				int jobID = Integer.parseInt(jobInfo[0]);
				String[] cancellation = line2.split(cvsSplitBy);

				for (int i = 1; i < jobInfo.length; i += 2) {
					tasks.add(new Task(jobInfo[i], Integer.parseInt(jobInfo[i + 1])));
				}

				if (cancellation[1].equals("1"))
					cancel = true;
				else
					cancel = false;

				jobsTraining.add(new Job(jobID, tasks, items, cancel));
			}

			br.close();
			br1.close();
			
			br = new BufferedReader(new FileReader(jobFile));
			br1 = new BufferedReader(new FileReader(cancellationFile));
			
			logger.debug("Reading training_jobs and cancellations csv files");
			
			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				tasks = new ArrayList<Task>();
				String[] jobInfo = line.split(cvsSplitBy);
				int jobID = Integer.parseInt(jobInfo[0]);
				String[] cancellation = line2.split(cvsSplitBy);
				
				for (int i = 1; i < jobInfo.length; i += 2) {
					tasks.add(new Task(jobInfo[i], Integer.parseInt(jobInfo[i + 1])));
				}
				
				if (cancellation[1].equals("1"))
					cancel = true;
				else
					cancel = false;
				
				jobs.add(new Job(jobID, tasks, items, cancel));
			}
			
			br.close();
			br1.close();

			// Sorting the jobs reward/weight

			sort(jobs, 0, jobs.size()-1);
			logger.debug("Jobs sorted");
			
			
			logger.debug("Number of jobs before predictions: " + jobs.size());
			Classify classifier = new Classify();
			
			Writer writer = new Writer(classifier);
			writer.startWriting(jobs, jobsTraining);
			ArrayList<String> predictions = classifier.getPredictions();
			
			for(int i = 0; i < predictions.size(); i++) {
				if(predictions.get(i).equals("true")) {
								
					jobs.remove(i);
					predictions.remove(i);
				}
			}
			
			logger.debug("Number of jobs after predictions: " + jobs.size());

			br.close();
			br1.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 logger.error("One or more files are not found");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public HashMap<String, Item> getItems() {
		return items;
	}

	public ArrayList<Job> getJobs() {
		return jobs;
	}
	
	public static int partition(ArrayList<Job> jobs, int low, int high) {
		Float pivot = jobs.get(high).getRewardDivWeight();
		int i = low-1;
		for(int j = low; j < high; j++) {
			if(jobs.get(j).getRewardDivWeight() >= pivot) {
				i++;
				Job temp = jobs.get(i);
				jobs.set(i, jobs.get(j));
				jobs.set(j, temp);
			}
				
		}
		Job temp = jobs.get(i+1);
		jobs.set(i+1, jobs.get(high));
		jobs.set(high, temp);
			
		return i+1;
	}
	
	public static void sort(ArrayList<Job> jobs, int low, int high) {
		if(low < high) {
			int pi = partition(jobs, low, high);
			sort(jobs, low, pi-1);
			sort(jobs, pi+1, high);
		}
	}

}