package rp.jobDecider;

import java.io.*;
import java.util.*;

public class Writer {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private final Classify classifier;
	private static final String FILE_HEADER = "aa, ab, ac,ad, be, bf, bg, bh, ca, cb, ci, cj, dc, dd, de, df, eg, eh,"
			+ " ei, ej, fa, fb, fc, fd, ge, gf, gg, gh, hi, hj, reward, weight, reward/numbOfTasks, cancellation";
	private File userMessagesFile = new File("csv");
    private String userMessagesPath = userMessagesFile.getAbsolutePath();

	public Writer(Classify classifier) {
		this.classifier = classifier;
	}

	public void startWriting(ArrayList<Job> jobs, ArrayList<Job> jobsTraining) {

		String trainFileName = userMessagesPath + "/writer.csv";
		String testFileName = userMessagesPath + "/writerDataSet.csv";

		FileWriter trainFileWriter = null;
		FileWriter testFileWriter = null;

		try {
			trainFileWriter = new FileWriter(trainFileName);
			testFileWriter = new FileWriter(testFileName);

			// Write the CSV file header
			trainFileWriter.append(FILE_HEADER.toString());
			testFileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			trainFileWriter.append(NEW_LINE_SEPARATOR);
			testFileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			String[] items = { "aa", "ab", "ac", "ad", "be", "bf", "bg", "bh", "ca", "cb", "ci", "cj", "dc", "dd", "de",
					"df", "eg", "eh", "ei", "ej", "fa", "fb", "fc", "fd", "ge", "gf", "gg", "gh", "hi", "hj" };

			for (int i = 0; i < jobs.size(); i++) {
				// Maybe we need to add something like the reward / numbOfTasks or weight /
				// numbOfTasks or both

				// fileWriter.append(String.valueOf(job.getJobID()));
				// fileWriter.append(COMMA_DELIMITER);
				for (int i1 = 0; i1 <= 29; i1++) {
					for (int j = 0; j <= jobsTraining.get(i).getJobTasks().size() - 1; j++)
						if (jobsTraining.get(i).getJobTasks().get(j).taskID == items[i1])
							trainFileWriter.append(String.valueOf(jobsTraining.get(i).getJobTasks().get(j).quantity));
						else
							trainFileWriter.append(String.valueOf('0'));
					trainFileWriter.append(COMMA_DELIMITER);
				}
				// testFileWriter.append(String.valueOf(job.getNumberOfTasks()));
				// testFileWriter.append(COMMA_DELIMITER);

				trainFileWriter.append(String.valueOf(Math.floor(jobsTraining.get(i).getJobReward() / 20) * 20));
				trainFileWriter.append(COMMA_DELIMITER);
				// testFileWriter.append(String.valueOf(Math.floor(job.getJobReward()/20)*20));
				// testFileWriter.append(COMMA_DELIMITER);

				trainFileWriter.append(String.valueOf(Math.floor(jobsTraining.get(i).getJobWeight() / 5) * 5));
				trainFileWriter.append(COMMA_DELIMITER);
				// testFileWriter.append(String.valueOf(Math.floor(job.getJobWeight()/5)*5));
				// testFileWriter.append(COMMA_DELIMITER);
				trainFileWriter.append(String.valueOf(jobsTraining.get(i).getJobWeight() / jobs.get(i).getNumberOfTasks()));
				trainFileWriter.append(COMMA_DELIMITER);
				// fileWriter.append(String.valueOf(job.getRewardDivWeight()));
				// fileWriter.append(COMMA_DELIMITER);
				// testFileWriter.append("?");
				// testFileWriter.append(NEW_LINE_SEPARATOR);
				trainFileWriter.append(String.valueOf(jobsTraining.get(i).getJobCancel()));
				trainFileWriter.append(NEW_LINE_SEPARATOR);

			}

			try {
				trainFileWriter.flush();
				trainFileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

			for (int i = 0; i < jobs.size(); i++) {

				// fileWriter.append(String.valueOf(job.getJobID()));
				// fileWriter.append(COMMA_DELIMITER);
				for (int i1 = 0; i1 <= 29; i1++) {
					for (int j = 0; j <= jobs.get(i).getJobTasks().size() - 1; j++)
						if (jobs.get(i).getJobTasks().get(j).taskID == items[i1])
							testFileWriter.append(String.valueOf(jobs.get(i).getJobTasks().get(j).quantity));
						else
							testFileWriter.append(String.valueOf('0'));
					testFileWriter.append(COMMA_DELIMITER);
				}
				// testFileWriter.append(String.valueOf(job.getNumberOfTasks()));
				// testFileWriter.append(COMMA_DELIMITER);

				testFileWriter.append(String.valueOf(Math.floor(jobs.get(i).getJobReward() / 20) * 20));
				testFileWriter.append(COMMA_DELIMITER);
				// testFileWriter.append(String.valueOf(Math.floor(job.getJobReward()/20)*20));
				// testFileWriter.append(COMMA_DELIMITER);

				testFileWriter.append(String.valueOf(Math.floor(jobs.get(i).getJobWeight() / 5) * 5));
				testFileWriter.append(COMMA_DELIMITER);
				// testFileWriter.append(String.valueOf(Math.floor(job.getJobWeight()/5)*5));
				// testFileWriter.append(COMMA_DELIMITER);
				
				testFileWriter.append(String.valueOf(jobs.get(i).getJobWeight() / jobs.get(i).getNumberOfTasks()));
				testFileWriter.append(COMMA_DELIMITER);
				// fileWriter.append(String.valueOf(job.getRewardDivWeight()));
				// fileWriter.append(COMMA_DELIMITER);
				testFileWriter.append("?");
				// testFileWriter.append(NEW_LINE_SEPARATOR);
				// testFileWriter.append(String.valueOf(jobs.get(i).getJobCancel()));
				testFileWriter.append(NEW_LINE_SEPARATOR);

			}

			try {
				testFileWriter.flush();
				testFileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

			System.out.println("CSV files were created successfully !!!");

			Converter converter = new Converter();
			converter.convertTrainFile(trainFileName, userMessagesPath + "/WriterCon.arff");
			converter.convertTest(testFileName, userMessagesPath + "/WriterDataSetCon.arff");

			System.out.println("CSV files were converted successfully !!!");

			classifier.classfy();

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

		}
	}
}