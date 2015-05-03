package edu.xaut.activityrecognition;

import java.sql.ResultSet;
import java.sql.SQLException;

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
			// 查询特定窗口大小数据信息的sql语句
			String sql = "select * from preprocessingdata where Id between " + minWindow + " and " + maxWindow + ";";
			// 执行查询操作
			ResultSet rs = dao.search(sql);
			// 计算均值
			double[] meanValue = means(rs);
			// 计算方差
			double[] varianceValue = variance(rs, meanValue);
			// 计算相关系数
			double[] correlationValue = correlation(rs, meanValue, varianceValue);
			// 计算能量
			double[] energyValue = energy(rs);
		}
	}
	
	// 计算均值
	public double[] means(ResultSet rs){
		// 各轴加速度数据之和
		double sum_RKN_accX = 0;
		double sum_RKN_accY = 0;
		double sum_RKN_accZ = 0;
		double sum_HIP_accX = 0;
		double sum_HIP_accY = 0;
		double sum_HIP_accZ = 0;
		double sum_LUA_accX = 0;
		double sum_LUA_accY = 0;
		double sum_LUA_accZ = 0;
		// 各轴加速度数据均值
		double[] means = new double[9];
		
		// 循环读取查询到的数据记录
		try {
			while (rs != null && rs.next() == true){
				// 计算各轴加速度数据之和
				sum_RKN_accX = sum_RKN_accX + Double.parseDouble(rs.getString(3));
				sum_RKN_accY = sum_RKN_accY + Double.parseDouble(rs.getString(4));
				sum_RKN_accZ = sum_RKN_accZ + Double.parseDouble(rs.getString(5));
				sum_HIP_accX = sum_HIP_accX + Double.parseDouble(rs.getString(6));
				sum_HIP_accY = sum_HIP_accY + Double.parseDouble(rs.getString(7));
				sum_HIP_accZ = sum_HIP_accZ + Double.parseDouble(rs.getString(8));
				sum_LUA_accX = sum_LUA_accX + Double.parseDouble(rs.getString(9));
				sum_LUA_accY = sum_LUA_accY + Double.parseDouble(rs.getString(10));
				sum_LUA_accZ = sum_LUA_accZ + Double.parseDouble(rs.getString(11));
			}
			// 计算各轴加速度数据均值
			means[0] = sum_RKN_accX / windowSize;
			means[1] = sum_RKN_accY / windowSize;
			means[2] = sum_RKN_accZ / windowSize;
			means[3] = sum_HIP_accX / windowSize;
			means[4] = sum_HIP_accY / windowSize;
			means[5] = sum_HIP_accZ / windowSize;
			means[6] = sum_LUA_accX / windowSize;
			means[7] = sum_LUA_accY / windowSize;
			means[8] = sum_LUA_accZ / windowSize;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return means;
	}
	
	// 计算方差
	public double[] variance(ResultSet rs, double[] meanValue){
		
		double[] variance = new double[9];
		
		return variance;
		
	}
}
