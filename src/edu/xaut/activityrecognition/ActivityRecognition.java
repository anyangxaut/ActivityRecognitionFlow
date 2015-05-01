package edu.xaut.activityrecognition;

public class ActivityRecognition {

	/**
	 * 行为识别main文件
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 创建DataPreprocess对象，并调用其startPreprocess进行数据预处理
		DataPreprocess dataPreprocess = new DataPreprocess("C:\\Users\\Administrator\\Desktop\\train\\");
		dataPreprocess.startPreprocess();
	}

}
