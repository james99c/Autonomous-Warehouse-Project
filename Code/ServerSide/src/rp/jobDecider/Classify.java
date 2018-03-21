package rp.jobDecider;
/*
 *  How to use WEKA API in Java 
 *  Copyright (C) 2014 
 *  @author Dr Noureddin M. Sadawi (noureddin.sadawi@gmail.com)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it as you wish ... 
 *  I ask you only, as a professional courtesy, to cite my name, web page 
 *  and my YouTube Channel!
 *  
 */

//import required classes
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;

public class Classify {

	private ArrayList<String> classes = new ArrayList<>();
	private final String NEW_LINE_SEPARATOR = "\n";
	private String predictionAccuracy = "it didn't work";
	private final String FILE_HEADER = "Actual Class, NB Predicted";
	private File userMessagesFile = new File("csv");
    private String userMessagesPath = userMessagesFile.getAbsolutePath();
    

	public void classfy() throws Exception {
		// load training dataset
		DataSource source = new DataSource(userMessagesPath + "/WriterCon.arff");
		Instances trainDataset = source.getDataSet();
		// set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		// get number of classes
		int numClasses = trainDataset.numClasses();
		// print out class values in the training dataset
		// create and build the classifier
		RandomForest rf = new RandomForest();
		rf.buildClassifier(trainDataset);

		// load new dataset
		DataSource source1 = new DataSource(userMessagesPath + "/WriterDataSetCon.arff");
		Instances testDataset = source1.getDataSet();
		// set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);

		// loop through the new dataset and make predictions
		//System.out.println("===================");
		//System.out.println("What it supposed to be, NB Predicted");

		int correctPredicts = 0;
		for (int i = 0; i < testDataset.numInstances(); i++) {
			// get class double value for current instance
			double actualClass = trainDataset.instance(i).classValue();
			// System.out.println(actualClass);
			// get class string value using the class index using the class's int value
			String actual = testDataset.classAttribute().value((int) actualClass);
			// get Instance object of current instance
			Instance newInst = testDataset.instance(i);
			// call classifyInstance, which returns a double value for the class
			double predNB = rf.classifyInstance(newInst);
			//System.out.println(predNB);
			// use this value to get string value of the predicted class
			String predString = testDataset.classAttribute().value((int) predNB);
			//System.out.println(actual + ", " + predString);
		if (actual.equals(predString))
				correctPredicts++;
			classes.add(actual + ", " + predString);
		}
		
//		 Evaluation eval = new Evaluation(trainDataset);
//		 eval.crossValidateModel(rf, trainDataset, 10, new Random(1));
//		 System.out.println(eval.toSummaryString("\nResults\n======\n", true));
		predictionAccuracy = Integer.toString((int) Math.round((float) correctPredicts / classes.size() * 100));
		System.out.println("ARFF files were classified successfully with accuracy of: " + predictionAccuracy);


		String fileName = userMessagesPath + "/Predictions.csv";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			fileWriter.append("Prediction accuracy is: " + predictionAccuracy);
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			for (String clas : classes) {
				fileWriter.append(clas);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			//String[] array = predictionAccuracy.split(".");
			//System.out.println(array);
			String newFileName = userMessagesPath + "/Predictions" + predictionAccuracy + ".csv";
			
			// File (or directory) with old name
			File file = new File(fileName);

			// File (or directory) with new name
			File file2 = new File(newFileName);

//			if (file2.exists())
//			   throw new java.io.IOException("file exists");

			// Rename file (or directory)
			boolean success = file.renameTo(file2);

			if (!success) {
				System.out.println("Predictions file with the same accuracy already exists");
			}else
				System.out.println("Predictions file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
	
	public String getAccuracy() {
		return predictionAccuracy;
	}
	
	public ArrayList<String> getPredictions(){
		return classes;
	}
}