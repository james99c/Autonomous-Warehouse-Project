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
import weka.classifiers.trees.RandomTree;

public class Classify {

	private ArrayList<String> classes = new ArrayList<>();
	private String predictionAccuracy = "it didn't work";
	private File userMessagesFile = new File("csv");
    private String userMessagesPath = userMessagesFile.getAbsolutePath();
    

	public void classfy() throws Exception {
		// load training dataset
		DataSource source = new DataSource(userMessagesPath + "/WriterCon.arff");
		Instances trainDataset = source.getDataSet();
		// set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		// create and build the classifier
		RandomTree rf = new RandomTree();
		rf.buildClassifier(trainDataset);

		// load new dataset
		DataSource source1 = new DataSource(userMessagesPath + "/WriterDataSetCon.arff");
		Instances testDataset = source1.getDataSet();
		// set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);

		for (int i = 0; i < testDataset.numInstances(); i++) {
		// get Instance object of current instance
			Instance newInst = testDataset.instance(i);
			// call classifyInstance, which returns a double value for the class
			double predNB = rf.classifyInstance(newInst);
			//System.out.println(predNB);
			// use this value to get string value of the predicted class
			String predString = testDataset.classAttribute().value((int) predNB);

			classes.add(predString);
		}
	}
	
	public String getAccuracy() {
		return predictionAccuracy;
	}
	
	public ArrayList<String> getPredictions(){
		return classes;
	}
}