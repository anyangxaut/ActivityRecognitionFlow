package edu.xaut.activityrecognition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import edu.xaut.dao.ClassificationAlgorithmsDao;
import edu.xaut.daoImpl.ClassificationAlgorithmsImpl;
import edu.xaut.entity.KNNNode;

/**
 * KNN算法主体类
 * 
 */
public class KNNAlgorithm {
	/**
	 * 设置优先级队列的比较函数，距离越大，优先级越高
	 */
	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode o1, KNNNode o2) {
			if (o1.getDistance() >= o2.getDistance()) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	/**
	 * 获取K个不同的随机数
	 * @param k 随机数的个数
	 * @param max 随机数最大的范围
	 * @return 生成的随机数数组
	 */
	public List<Integer> getRandKNum(int k, int max) {
		List<Integer> rand = new ArrayList<Integer>(k);
		for (int i = 0; i < k; i++) {
			int temp = (int) (Math.random() * max);
			if (!rand.contains(temp)) {
				rand.add(temp);
			} else {
				i--;
			}
		}
		return rand;
	}
	/**
	 * 计算测试元组与训练元组之前的距离
	 * @param d1 测试元组
	 * @param d2 训练元组
	 * @return 距离值
	 */
	public double calDistance(List<Double> d1, List<Double> d2) {
		double distance = 0.00;
		for (int i = 0; i < d1.size(); i++) {
			distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
		}
		return distance;
	}
	/**
	 * 执行KNN算法，获取测试元组的类别
	 * @param datas 训练数据集
	 * @param testData 测试元组
	 * @param k 设定的K值
	 * @return 测试元组的类别
	 */
	public String knn(List<List<Double>> datas, List<Double> testData, int k) {
		PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
		List<Integer> randNum = getRandKNum(k, datas.size());
		for (int i = 0; i < k; i++) {
			int index = randNum.get(i);
			List<Double> currData = datas.get(index);
			String c = currData.get(currData.size() - 1).toString();
			KNNNode node = new KNNNode(index, calDistance(testData, currData), c);
			pq.add(node);
		}
		for (int i = 0; i < datas.size(); i++) {
			List<Double> t = datas.get(i);
			double distance = calDistance(testData, t);
			KNNNode top = pq.peek();
			if (top.getDistance() > distance) {
				pq.remove();
				pq.add(new KNNNode(i, distance, t.get(t.size() - 1).toString()));
			}
		}
		
		return getMostClass(pq);
	}
	/**
	 * 获取所得到的k个最近邻元组的多数类
	 * @param pq 存储k个最近近邻元组的优先级队列
	 * @return 多数类的名称
	 */
	private String getMostClass(PriorityQueue<KNNNode> pq) {
		Map<String, Integer> classCount = new HashMap<String, Integer>();
		for (int i = 0; i < pq.size(); i++) {
			KNNNode node = pq.remove();
			i--;
			String c = node.getLabel();
			if (classCount.containsKey(c)) {
				classCount.put(c, classCount.get(c) + 1);
			} else {
				classCount.put(c, 1);
			}
		}
		int maxIndex = -1;
		int maxCount = 0;
		Object[] classes = classCount.keySet().toArray();
		for (int i = 0; i < classes.length; i++) {
			if (classCount.get(classes[i]) > maxCount) {
				maxIndex = i;
				maxCount = classCount.get(classes[i]);
			}
		}
		return classes[maxIndex].toString();
	}
	
	public void startKNN(){
		// 创建ClassificationAlgorithmsDao类
		ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();
		// 查询训练数据信息的sql语句
		String[] sqlFindTrain = new String[4];
		sqlFindTrain[0] = "select * from featureextraction where Id between " + 1 + " and " + 92 + ";";
		sqlFindTrain[1] = "select * from featureextraction where Id between " + 116 + " and " + 207 + ";";
		sqlFindTrain[2] = "select * from featureextraction where Id between " + 231 + " and " + 322 + ";";
		sqlFindTrain[3] = "select * from featureextraction where Id between " + 346 + " and " + 437 + ";";
		// 执行查询操作
		List<List<Double>> listTrain1 = dao.search(sqlFindTrain[0]);
		List<List<Double>> listTrain2 = dao.search(sqlFindTrain[1]);
		List<List<Double>> listTrain3 = dao.search(sqlFindTrain[2]);
		List<List<Double>> listTrain4 = dao.search(sqlFindTrain[3]);
		// 准备训练数据
		listTrain1.addAll(listTrain2);
		listTrain1.addAll(listTrain3);
		listTrain1.addAll(listTrain4);
		List<List<Double>> trainList = listTrain1;
		
		
		// 查询测试数据信息的sql语句
		String[] sqlFindTest = new String[4];
		sqlFindTest[0] = "select * from featureextraction where Id between " + 93 + " and " + 115 + ";";
		sqlFindTest[1] = "select * from featureextraction where Id between " + 208 + " and " + 230 + ";";
		sqlFindTest[2] = "select * from featureextraction where Id between " + 323 + " and " + 345 + ";";
		sqlFindTest[3] = "select * from featureextraction where Id between " + 438 + " and " + 460 + ";";
		// 执行查询操作
		List<List<Double>> listTest1 = dao.search(sqlFindTest[0]);
		List<List<Double>> listTest2 = dao.search(sqlFindTest[1]);
		List<List<Double>> listTest3 = dao.search(sqlFindTest[2]);
		List<List<Double>> listTest4 = dao.search(sqlFindTest[3]);
		// 准备测试数据
		listTest1.addAll(listTest2);
		listTest1.addAll(listTest3);
		listTest1.addAll(listTest4);
		List<List<Double>> testList = listTest1;
		
		// 识别
		for(int i = 0; i < testList.size(); i++){
			// 从测试数据列表中取出单个测试数据信息
			 List<Double> test = testList.get(i);  
			 
//             System.out.print("测试元组: ");  
//             for (int j = 0; j < test.size(); j++) {  
//                 System.out.print(test.get(j) + " ");  
//             }  
             System.out.print("类别为: ");  
             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));
		}
	}
}

