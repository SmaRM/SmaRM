package org.smarthome.activityrecognition.jni;

public class ActivityRecognizer {
	
	static {
		System.loadLibrary("AR_SVM");
	}
	
	// JNI: native methods
	private native int create(String baseDirectory);
	private native void clean(int lp);
	private native String readConfiguration(int lp, String configfile);
	private native String setConfiguration(int lp, String config);
	private native String trainSVM(int lp);
	private native String trainSVM(int lp, String trainingData);
	private native String testSVM(int lp, String testData);
	
	private static int objectId = -1;
	
	public ActivityRecognizer(String baseDirectory) {
		objectId = create(baseDirectory);
	}

	public String readConfiguration(String configfile) {
		return readConfiguration(objectId, configfile);
	}
	
	public String setConfiguration(String config) {
		return setConfiguration(objectId, config);
	}
	
	public String trainSVM() {
		return trainSVM(objectId);
	}
	
	public String trainSVM(String trainingData) {
		return trainSVM(objectId, trainingData);
	}
	
	public String testSVM(String testData) {
		return testSVM(objectId, testData);
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		clean(objectId);
		super.finalize();
	} 
}
