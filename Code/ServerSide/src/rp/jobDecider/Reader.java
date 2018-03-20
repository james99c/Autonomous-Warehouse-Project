package rp.jobDecider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;

//import org.apache.log4j.Logger;

public class Reader {

	// final static Logger logger = Logger.getLogger(Reader.class);
	HashMap<String, Item> items = new HashMap<>();
	ArrayList<Job> jobs = new ArrayList<>();
	ArrayList<Task> tasks = new ArrayList<>();

	public void startReading() {

		String itemFile = "/home/tomas/Dropbox/Mokslai/RP/job files/items.csv";
		String locationFile = "/home/tomas/Dropbox/Mokslai/RP/job files/locations.csv";
		String jobFile = "/home/tomas/Dropbox/Mokslai/RP/job files/training_jobs.csv";
		String cancellationFile = "/home/tomas/Dropbox/Mokslai/RP/job files/cancellations.csv";
		BufferedReader br = null;
		BufferedReader br1 = null;
		String line = "";
		String line2 = "";
		String cvsSplitBy = ",";
		boolean cancel = false;

		try {

			br = new BufferedReader(new FileReader(itemFile));
			br1 = new BufferedReader(new FileReader(locationFile));
			// logger.debug("Reading items and locations csv files");

			while ((line = br.readLine()) != null && (line2 = br1.readLine()) != null) {
				String[] item = line.split(cvsSplitBy);
				String[] locationArray = line2.split(cvsSplitBy);

				items.put(item[0], new Item(item[0], Float.parseFloat(item[1]), Float.parseFloat(item[2]),
						Integer.parseInt(locationArray[0]), Integer.parseInt(locationArray[1])));

			}

			br.close();
			br1.close();

			br = new BufferedReader(new FileReader(jobFile));
			br1 = new BufferedReader(new FileReader(cancellationFile));

			// logger.debug("Reading training_jobs and cancellations csv files");

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

			// System.out.println(jobs.get(0).getJobID() + " "
			// +jobs.get(0).getJobTasks().size() + jobs.get(0).getJobCancel() +
			// jobs.get(0).getItems().size() );
			// System.out.println(jobs.get(1).getJobReward());
			// System.out.println(jobs.get(1).getJobWeight());
			// System.out.println(jobs.get(0).getNumberOfTasks());
			// System.out.println("sdgh");
			// System.out.println("adfd");

			// Sorting the jobs reward/weight
			for (int i = 0; i <= jobs.size() - 2; i++)
				for (int j = i + 1; j <= jobs.size() - 1; j++)
					if (jobs.get(i).getRewardDivWeight() < jobs.get(j).getRewardDivWeight()) {
						Job temp = jobs.get(i);
						jobs.set(i, jobs.get(j));
						jobs.set(j, temp);
					}
			// System.out.println("sdgaergdg");
			// System.out.println(jobs.get(1));
			// System.out.println(jobs.get(1).getRewardDivWeight());
			// for(int i = 0; i <= jobs.size() -1; i++)
			// System.out.println(jobs.get(i).getRewardDivWeight());
			//
			// //System.out.println(jobs.size());
			// System.out.println(jobs.get(0).getJobID());
			// System.out.println(jobs.get(0).getNumberOfTasks());
			// System.out.println("sdg");


			br.close();
			br1.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// logger.error("One or more files are not found");
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

}
