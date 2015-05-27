package edu.xaut.activityrecognition;
/**
 * LDA（线性判别分析）算法实现
 * 需要JAMAjar包的支持
 * http://www.psychometrica.de/lda.html
 * 
 */
import java.util.ArrayList;

import Jama.Matrix;

public class LDA {
	// 每个类别的均值数据
	private double[][] groupMean;
	// 逆协方差池
	private double[][] pooledInverseCovariance;
	// 类别标签
	private ArrayList<Integer> groupList = new ArrayList<Integer>();

	/**
	 * LDA算法主要实现代码：
	 * @param d data---存储数据信息，其数据个数必须与group分组个数一致(7)，即一一对应关系---x(i)
	 * @param g group---分组数据信息---y(i)
	 * @param p Set to true, if the probability estimation should be based on
	 *          the real group sizes (true), or if the share of each group
	 *          should be equal
	 */
	@SuppressWarnings("unchecked")
	public LDA(double[][] d, int[] g, boolean p) {
		
		// 检查数据和分组信息是否一致，即一一对应关系
		if (d.length != g.length)
			return;
		
		// 存储data数据信息{ { 2.95, 6.63 }, { 2.53, 7.79 }, { 3.57, 5.65 },  
		// { 3.16, 5.47 }, { 2.58, 4.46 }, { 2.16, 6.22 }, { 3.27, 3.52 } }
		double[][] data = new double[d.length][d[0].length];
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				data[i][j] = d[i][j];
			}
		}
		
		// 存储group数据信息{1,1,1,1,2,2,2}
		int[] group = new int[g.length];
		for (int j = 0; j < g.length; j++) {
			group[j] = g[j];
		}
		
		// 原始数据（不区分类别）均值
		double[] globalMean;
		// 协方差
		double[][][] covariance;

		// 通过groupList存储标签信息1,2
		for (int i = 0; i < group.length; i++) {
			if (!groupList.contains(group[i])) {
				groupList.add(group[i]);
			}
		}

		// 根据标签信息将数据划分为一个个子集，同一子集内的数据信息所对应的标签（类别）相同
		ArrayList<double[]>[] subset = new ArrayList[groupList.size()];
		for (int i = 0; i < subset.length; i++) {
			subset[i] = new ArrayList<double[]>();
			for (int j = 0; j < data.length; j++) {
				if (group[j] == groupList.get(i)) {
					subset[i].add(data[j]);
				}
			}
		}

		// 计算每一个子集（标签1,2）内的中心点，即均值mean
		groupMean = new double[subset.length][data[0].length];
		for (int i = 0; i < groupMean.length; i++) {
			for (int j = 0; j < groupMean[i].length; j++) {
				groupMean[i][j] = getGroupMean(j, subset[i]);
			}
		}

		// 计算原始数据（不区分类别）的均值
		globalMean = new double[data[0].length];
		for (int i = 0; i < data[0].length; i++) {
			globalMean[i] = getGlobalMean(i, data);
		}

		// 将每一个子集内的数据-globalMean，存储在原来的位置,为下一步计算协方差准备好数据
		for (int i = 0; i < subset.length; i++) {
			for (int j = 0; j < subset[i].size(); j++) {
				double[] v = subset[i].get(j);

				for (int k = 0; k < v.length; k++)
					v[k] = v[k] - globalMean[k];

				subset[i].set(j, v);
			}
		}

		// 计算协方差
		covariance = new double[subset.length][globalMean.length][globalMean.length];
		for (int i = 0; i < covariance.length; i++) {
			for (int j = 0; j < covariance[i].length; j++) {
				for (int k = 0; k < covariance[i][j].length; k++) {
					// 为每一个子集计算协方差，因为subset中存储的数据已经减去了globalMean，所以只需要相乘求和取其均值即可
					for (int l = 0; l < subset[i].size(); l++)
						covariance[i][j][k] += (subset[i].get(l)[j] * subset[i]
								.get(l)[k]);

					covariance[i][j][k] = covariance[i][j][k]
							/ subset[i].size();
				}
			}
		}

		// 逆协方差池
		pooledInverseCovariance = new double[globalMean.length][globalMean.length];
		for (int j = 0; j < pooledInverseCovariance.length; j++) {
			for (int k = 0; k < pooledInverseCovariance[j].length; k++) {
				for (int l = 0; l < subset.length; l++) {
					pooledInverseCovariance[j][k] += ((double) subset[l].size() / (double) data.length)
							* covariance[l][j][k];
				}
			}
		}

		pooledInverseCovariance = new Matrix(pooledInverseCovariance).inverse()
				.getArray();
	}

	private double getGroupMean(int column, ArrayList<double[]> data) {
		double[] d = new double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			d[i] = data.get(i)[column];
		}

		return getMean(d);
	}

	private double getGlobalMean(int column, double data[][]) {
		double[] d = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			d[i] = data[i][column];
		}

		return getMean(d);
	}

	/**
	 * 计算权值
	 * 
	 * @return the weights
	 */
	public double[][] getFisherWeights() {
		
		double[][] tmp = new double[groupList.size()][];
		
		for (int i = 0; i < groupList.size(); i++) {
			// tmp就是w的转置
			tmp[i] = matrixMultiplication(groupMean[i],
					pooledInverseCovariance);	
		}
		return tmp;
	}

	/**
	 * 两个矩阵相乘并将计算结果以double[]-array形式返回.
	 * 
	 * @param a
	 *            the first matrix
	 * @param b
	 *            the second matrix
	 * @return the resulting matrix
	 */
	public double[] matrixMultiplication(double[] A, double[][] B) {

		if (A.length != B.length) {
			throw new IllegalArgumentException("A:Rows: " + A.length
					+ " did not match B:Columns " + B.length + ".");
		}

		double[] C = new double[A.length];
		for (int i = 0; i < C.length; i++) {
			C[i] = 0.00000;
		}

		for (int i = 0; i < A.length; i++) { // aRow
			for (int j = 0; j < B[0].length; j++) { // bColumn
				C[i] += A[j] * B[i][j];
			}
		}

		return C;
	}

	/**
	 * 返回样本均值. 当数据为null或者length=0时返回NaN.
	 * 
	 * @param values
	 *            The values.
	 * @return The mean.
	 * @since 1.5
	 */
	public static double getMean(final double[] values) {
		if (values == null || values.length == 0)
			return Double.NaN;

		double mean = 0.0d;

		for (int index = 0; index < values.length; index++)
			mean += values[index];

		return mean / (double) values.length;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] group = { 1, 1, 1, 1, 2, 2, 2 };
		double[][] data = { { 2.95, 6.63 }, { 2.53, 7.79 }, { 3.57, 5.65 },
				{ 3.16, 5.47 }, { 2.58, 4.46 }, { 2.16, 6.22 }, { 3.27, 3.52 } };

		LDA test = new LDA(data, group, true);

		double[][] values = test.getFisherWeights();
		for(int i = 0; i < values.length; i++){
			System.out.println("Class " + (i+1) + ": ");	
			for(int j = 0; j < values[i].length; j++){
					System.out.println(values[i][j]);	
			}
		}
		
	}
}
