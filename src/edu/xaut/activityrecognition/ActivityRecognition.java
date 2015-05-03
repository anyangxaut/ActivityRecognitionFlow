package edu.xaut.activityrecognition;

public class ActivityRecognition {
	/**
	 * 行为识别main文件
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 数据预处理
		DataPreprocess dataPreprocess = new DataPreprocess("C:\\Users\\Administrator\\Desktop\\train\\");
		dataPreprocess.startPreprocess();
		
		// 窗口划分，特征提取
		FeatureExtraction featureExtraction = new FeatureExtraction(64, 0.5, 178072);
		featureExtraction.startFeatureExtraction();
		
		// 分类算法
		
	}

}
