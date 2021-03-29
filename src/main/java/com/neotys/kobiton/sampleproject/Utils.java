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

	public static String fetchDeviceName(String deviceName) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties
		String finalDeviceName = deviceName.equalsIgnoreCase("<<deviceName>>") ? System.getProperty("deviceName") : deviceName;
		//throw exceptions if cloudName isnt passed:
		if(finalDeviceName == null || finalDeviceName.equalsIgnoreCase("<<deviceName>>"))
			throw new Exception("Please replace <<deviceName>> with the name of the kobiton device or pass it as maven properties: -DdeviceName=<<deviceName>>");
		else
			return finalDeviceName;
	}

	public static String fetchDeviceType(String deviceType) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties
		String finalDeviceType = deviceType.equalsIgnoreCase("<<deviceType>>") ? System.getProperty("deviceType") : deviceType;
		//throw exceptions if deviceType isn't passed:
		if(finalDeviceType == null || finalDeviceType.equalsIgnoreCase("<<deviceType>>"))
			throw new Exception("Please replace <<deviceType>> with the type of the kobiton device or pass it as maven properties: -DdeviceType=<<deviceType>>");
		else
			return finalDeviceType;
	}
}

