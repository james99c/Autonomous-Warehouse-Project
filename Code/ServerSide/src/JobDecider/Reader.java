package JobDecider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CancellationException;

import DataObjects.Job;

import com.sun.xml.internal.ws.api.Cancelable;

public class Reader {

	public static void main(String[] args) {

		String itemFile = "/Users/RyanBolding/Documents/workspace/Test/items.csv";
		String locationFile = "/Users/RyanBolding/Documents/workspace/Test/locations.csv";
		BufferedReader br = null;
		BufferedReader br1 = null;
		String line = "";
		String line2 = "";
		String cvsSplitBy = ",";
		HashMap<String, Item> items = new HashMap<String, Item>();
		ArrayList<Job> jobNumber = new ArrayList<Job>();

		HashMap<Integer, Job> jobs = new HashMap<Integer, Job>();
		String jobFile = "/Users/RyanBolding/Documents/workspace/Test/training_jobs.csv";
		String cancellationFile = "/Users/RyanBolding/Documents/workspace/Test/cancellations.csv";
		boolean cancel = false;
		try {

			br = new BufferedReader(new FileReader(itemFile));
			br1 = new BufferedReader(new FileReader(locationFile));
			while ((line = br.readLine()) != null
					&& (line2 = br1.readLine()) != null) {

				// use comma as separator
				String[] item = line.split(cvsSplitBy);
				String[] locationArray = line2.split(cvsSplitBy);

				items.put(
						item[0],
						new Item(Float.parseFloat(item[1]), Float
								.parseFloat(item[2]), Integer
								.parseInt(locationArray[0]), Integer
								.parseInt(locationArray[1])));

				// System.out.println(" [name= " + item[0] + " , reward=" +
				// item[1] + "weight= " + item[2] + "]");

			}

			br = new BufferedReader(new FileReader(jobFile));
			br1 = new BufferedReader(new FileReader(cancellationFile));
			while ((line = br.readLine()) != null
					&& (line2 = br1.readLine()) != null) {
				String[] job = line.split(cvsSplitBy);
				int jobID = Integer.parseInt(job[0]);
				String[] cancellation = line2.split(cvsSplitBy);
				ArrayList<JobObject> itemList = new ArrayList<JobObject>();
				for (int i = 1; i < job.length - 1; i += 2) {
					itemList.add(new JobObject(job[i], Integer.parseInt(job[i + 1])));
				}
				if(cancellation[1].equals("1")) cancel = true; 
				jobNumber.add(new Job(jobID, itemList,items,cancel));
				
			}
			System.out.println(jobNumber.get(3).getCancellation());
			System.out.println(jobNumber.get(3).getID());
			System.out.println(jobNumber.get(3).getJob().get(3).getID());
			System.out.println(jobNumber.get(3).getJob().get(3).getQuantity());
	
			System.out.println(jobNumber.get(11).getJobReward());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
