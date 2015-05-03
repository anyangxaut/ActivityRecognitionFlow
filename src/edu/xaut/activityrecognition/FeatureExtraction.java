package edu.xaut.activityrecognition;

import java.sql.ResultSet;

import edu.xaut.dao.FeatureExtractionDao;
import edu.xaut.daoImpl.FeatureExtractionImpl;

/**
 * // 窗口划分，特征提取(窗口大小64,重叠率50%，均值，方差，相关系数，能量)
 * @author Administrator
 *
 */
public class FeatureExtraction {
	// 窗口大小
	private final int windowSize;
	// 重叠率
	private final double overlap;
	// 数据总量
	private final int sumData;
	
	// 通过构造方法初始化类属性字段
	public FeatureExtraction(int windowSize, double overlap, int sumData) {
		// TODO Auto-generated constructor stub
		 this.windowSize = windowSize;
		 this.overlap = overlap;
		 this.sumData = sumData;
	}

	public void startFeatureExtraction(){
		// 创建FeatureExtractionDao类
		FeatureExtractionDao dao = new FeatureExtractionImpl();
		// 计算重叠窗口大小
		final int overlapSzie = (int)(windowSize * overlap);
		// 以窗口大小windowSize，重叠率overlap进行窗口切分，并提取其特征值
		for(int i = 0; i < sumData; i = i + overlapSzie){
			// 当前窗口下线
			int minWindow = i + 1;
			// 当前窗口上线
			int maxWindow = i + windowSize;
			//
			int sum = 0;
			// 查询特定窗口大小数据信息的sql语句
			String sql = "select * from preprocessingdata where Id between " + minWindow + " and " + maxWindow + ";";
			// 执行查询操作
			ResultSet rs = dao.search(sql);
			// 计算均值
			double meanValue = means(rs);
			// 计算方差
			double varianceValue = variance(rs, meanValue);
			// 计算相关系数
			double correlationValue = correlation(rs, meanValue, varianceValue);
			// 计算能量
			double energyValue = energy(rs);
		}
	}
	
	public double means(ResultSet rs){
		
		double result = 0.0;
		
		return result;
	}
}
