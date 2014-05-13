package com.banditUI.articleViewer.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class ClickProbCalc {

	private static Dictionaries dictionaries = Dictionaries.getInstance();
	
	
	
	public static double calcClickProb(Article article, HashMap<String,Double> FeatureVector) {
		double score = calcFeatureVectorScore(article, FeatureVector);
		double click_chance = 1.0 - 1.0/(Math.exp(2*score));
		return click_chance;
	}
    
    private static double calcFeatureVectorScore( Article article, HashMap<String,Double> FeatureVector ) {
    	double score = 0;
    	
    	// Read the article, and get the appropriate dictionary.
    	HashMap<String, Double> dictionary = dictionaries.getDictionary(article.getTopic());
    	if (dictionary == null) { 
    		System.out.println("Unable to extract topic from article");
    		return -1;
    	}
    	
    	// Now read through the article and count up the number of times each word
    	// in the dictionary appears.
    	HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
    	WordCounter.CountWords(article.getStory(), wordCounts);
    	for (String word : wordCounts.keySet()) {
    		if (dictionary.containsKey(word))
    			score += (wordCounts.get(word)*dictionary.get(word)*FeatureVector.get(word));
    	}
    	
    	return score;
    }
    
    
	
    

}