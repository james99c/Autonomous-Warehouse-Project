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

import weka.core.Attribute;
//import required classes
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Converter {
  
  public void convertTrainFile(String srcName, String outputName) throws Exception {
    
    // load CSV
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File(srcName));
    ///home/tomas/Dropbox/Mokslai/RP/job files/writer.csv
    Instances data = loader.getDataSet();//get instances object
    

    // save ARFF
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);//set the dataset we want to convert
    //and save as ARFF
    saver.setFile(new File(outputName));
    ///home/tomas/Dropbox/Mokslai/RP/job files/WriterCon.arff
    saver.writeBatch();
  }
  
  public void convertTest(String srcName, String outputName) throws Exception {
	  
	  // load CSV
	  CSVLoader loader = new CSVLoader();
	  loader.setSource(new File(srcName));
	  Instances data = loader.getDataSet();//get instances object
	  
	  ArrayList<String> my_nominal_values = new ArrayList<>(); 
	  my_nominal_values.add("false"); 
	  my_nominal_values.add("true"); 
	  
	  Attribute classAtt = new Attribute("Class", my_nominal_values); 
	  data.deleteAttributeAt(data.numAttributes()-1);
	  data.insertAttributeAt(classAtt, data.numAttributes());
	  
	  // save ARFF
	  ArffSaver saver = new ArffSaver();
	  saver.setInstances(data);//set the dataset we want to convert
	  //and save as ARFF
	  saver.setFile(new File(outputName));
	  saver.writeBatch();
  }
} 
