package com.taskmanager.service;

import com.taskmanager.service.TaskServiceImpl.PriorityDefinitionStrategy;

public class PriorityDefinitionStrategyFactory {
	
	private static final PriorityQueue_Utility defaultPriorityQueue = new PriorityQueue_Utility();
	private static final PriorityQueue_CategoryPercentage categoryPercentagePriorityQueue = new PriorityQueue_CategoryPercentage();
	private static final PriorityQueue_CategoryGrade categoryGradePriorityQueue = new PriorityQueue_CategoryGrade();
	
	 //A factory can create a new instance of a class for each request, but since our calculation strategies are stateless,
    //we can hang on to a single instance of each strategy and return that whenever someone asks for it.
    public static PriorityDefinitionStrategy getPriorityDefinitionStrategy(String definition) {
        switch (definition) {
            case "Default": return defaultPriorityQueue;
            case "CategoryPercent": return categoryPercentagePriorityQueue;
            case "CategoryGrade": return categoryGradePriorityQueue;
            default: return defaultPriorityQueue;
        }
    }

}
