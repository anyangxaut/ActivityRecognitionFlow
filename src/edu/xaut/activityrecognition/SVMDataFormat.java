package edu.xaut.activityrecognition;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.xaut.dao.SVMDataFormatDao;
import edu.xaut.daoImpl.SVMDataFormatImpl;

public class SVMDataFormat {

	/**
	 *  将原始特征数据格式转换为SVM支持的数据格式
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		startSVMData();
	}
	
	// 获取传感器的特征数据，并进行文件存储（SVM数据集格式：类别  特征1索引：特征1值  特征2索引：特征2值  特征3索引：特征3值  。。。）
	// 4.000000  1:1099510.000000 2:10.000000 3:4.000000 4:3.000000 5:1.000000 6:3.000000 7:3.000000 8:6.000000 9:5.000000 10:2.000000 ...
	public static void startSVMData(){

		// 创建ClassificationAlgorithmsDao类
		SVMDataFormatDao dao = new SVMDataFormatImpl();
		// 存储数据
		List<List<Double>> dataList = null;
		// 存储SVM格式数据
		// 训练数据
		List<List<String>> svmTrainList = new ArrayList<List<String>>();
		// 测试数据
		List<List<String>> svmTestList = new ArrayList<List<String>>();
		
		String sql = "select * from fusionresult where WeightType = 5;";
		dataList = dao.search(sql);
		
		for(int i = 0; i < dataList.size(); i++){
			List<Double> data = dataList.get(i);
			List<String> item = new ArrayList<String>();
			item.add(String.valueOf(data.get(data.size()-1)));
			for(int j = 0; j < data.size()-1; j++){
				item.add((j+1) + ":" + data.get(j));
			}
			if(i % 5 == 0){
				svmTestList.add(item);
			}else{
				svmTrainList.add(item);
			}
		}	

		// 保存到文件
		saveDataAsFile(svmTrainList, "SVMTrain");
		saveDataAsFile(svmTestList, "SVMTest");
	}

	/**
	 * SVM数据集格式：类别  特征1索引：特征1值  特征2索引：特征2值  特征3索引：特征3值  。。。
	 * @param dataList
	 * @return
	 */
	private static boolean saveDataAsFile(List<List<String>> dataList, String fileName){
		
	FileWriter writer;
		try {
			// 通过保存文件的路径及其文件名称初始化FileWriter对象
			writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\2015-7-14\\feature\\" + fileName + ".txt",true);
			// 将预处理后的原始数据逐条进行存储
			for(int i = 0; i < dataList.size();i++){
				writer.write(dataList.get(i).toString() + "\n");
				}
			writer.close();
			return true;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
