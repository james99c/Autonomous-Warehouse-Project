package rp.JobDecider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
<<<<<<< HEAD:Code/ServerSide/src/rp/JobDecider/Reader.java
//import org.apache.log4j.Logger;

import DataObjects.Job;
=======
import org.apache.log4j.Logger;
import rp.DataObjects.Job;
>>>>>>> testing:Code/ServerSide/src/rp/JobDecider/Reader.java

public class Reader {

	//final static Logger logger = Logger.getLogger(Reader.class);

	public static void main(String[] args) {

		String itemFile = "/Users/ioanateju/Desktop/csv/items.csv";
		String locationFile = "/Users/ioanateju/Desktop/csv/locations.csv";
		String jobFile = "/Users/ioanateju/Desktop/csv/training_jobs.csv";
		String cancellationFile = "/Users/ioanateju/Desktop/csv/cancellations.csv";
		BufferedReader br = null;
		BufferedReader br1 = null;
		String line = "";
		String line2 = "";
		String cvsSplitBy = ",";
		HashMap<String, Item> items = new HashMap<String, Item>();
		ArrayList<Job> jobNumber = new ArrayList<Job>();
		boolean cancel = false;

		try {

			br = new BufferedReader(new FileReader(itemFile));
			br1 = new BufferedReader(new FileReader(locationFile));
			//logger.debug("Reading items and locations csv files");
			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				// use comma as separator
				String[] item = line.split(cvsSplitBy);
				String[] locationArray = line2.split(cvsSplitBy);

				items.put(item[0], new Item(Float.parseFloat(item[1]), Float.parseFloat(item[2]),
						Integer.parseInt(locationArray[0]), Integer.parseInt(locationArray[1])));

			}

			br.close();
			br1.close();
			br = new BufferedReader(new FileReader(jobFile));
			br1 = new BufferedReader(new FileReader(cancellationFile));
			//logger.debug("Reading training_jobs and cancellations csv files");
			
			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				String[] job = line.split(cvsSplitBy);
				int jobID = Integer.parseInt(job[0]);
				String[] cancellation = line2.split(cvsSplitBy);
				ArrayList<JobObject> itemList = new ArrayList<JobObject>();
				
				for (int i = 1; i < job.length - 1; i += 2) {
					itemList.add(new JobObject(job[i], Integer.parseInt(job[i + 1])));
				}
				if (cancellation[1].equals("1"))
					cancel = true;
				
				jobNumber.add(new Job(jobID, itemList, items, cancel));
			}
			
			br.close();
			br1.close();
			
//			int jobIndexToTest = 3;
//			logger.debug("Is job cancelled? " + jobNumber.get(jobIndexToTest).getCancellation());
//			logger.debug("Job ID: " + jobNumber.get(jobIndexToTest).getID());
//			logger.debug("Is file cancelled?" + jobNumber.get(jobIndexToTest).getCancellation());
//			logger.debug("One of the items that is in the job ID: " + jobNumber.get(jobIndexToTest).getJob().get(3).getID());
//			logger.debug("The quantity of that item in the job: " + jobNumber.get(jobIndexToTest).getJob().get(3).getQuantity());
//			logger.debug("The total job reward:  " + jobNumber.get(jobIndexToTest).getJobReward());
//			logger.debug("The total job weight:  " + jobNumber.get(jobIndexToTest).getJobWeight());


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//logger.error("One or more files are not found");
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
}
