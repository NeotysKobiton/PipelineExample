package com.neotys.kobiton.sampleproject;


public class Utils {
	/**
	 * fetches cloud name
	 * @param cloudName
	 * @return
	 * @throws Exception
	 */
	public static String fetchCloudName(String cloudName) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties 
		String finalCloudName = cloudName.equalsIgnoreCase("<<cloud name>>") ? System.getProperty("cloudName") : cloudName;
		//throw exceptions if cloudName isnt passed:
		if(finalCloudName == null || finalCloudName.equalsIgnoreCase("<<cloud name>>"))
			throw new Exception("Please replace <<cloud name>> with your kobiton cloud name or pass it as maven properties: -DcloudName=<<cloud name>>");
		else
			return finalCloudName;
	}




	public static String fetchApplicationURL(String applicationURL) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties
		String finalApplicationURL = applicationURL.equalsIgnoreCase("<<applicationURL>>") ? System.getProperty("applicationURL") : applicationURL;
		//throw exceptions if cloudName isnt passed:
		if(finalApplicationURL == null || finalApplicationURL.equalsIgnoreCase("<<applicationURL>>"))
			throw new Exception("Please replace <<applicationURL>> with the url of your konakart applcication or pass it as maven properties: -DapplicationURL=<<applicationURL>>");
		else
			return finalApplicationURL;
	}
}

