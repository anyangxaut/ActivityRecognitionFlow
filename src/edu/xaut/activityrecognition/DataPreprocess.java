package edu.xaut.activityrecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.xaut.entity.DataEntity;

/**
 * 对数据进行预处理操作(去处数据集中缺失值数目大于3的数据项)
 * @author Administrator
 *
 */
public class DataPreprocess {

	private File dataFile = null;
	private String filePath = "";
	private BufferedReader bufferedReader = null;
	private List<DataEntity> dataList = new ArrayList<DataEntity>();
	private int counter = 0;
	
	/**
	 * 初始化数据集所在路径
	 * @param fileName
	 */
	public DataPreprocess(String filePath) {
		
		this.filePath = filePath;
	}
	
	// 数据集中S1-ADL1，S1-ADL2，S1-ADL3，S1-Drill，S2-Drill用作训练数据
	public void startPreprocess(){
		
		System.out.println("********************数据预处理开始**********************");
		preprocessing("S1-ADL1.dat");
//		preprocessing("S1-ADL2.dat");
//		preprocessing("S1-ADL3.dat");
//		preprocessing("S1-Drill.dat");
//		preprocessing("S2-Drill.dat");
		System.out.println("********************数据预处理结束**********************");
	}

	private void preprocessing(String fileName){
		// 根据文件目录及其名称创建File对象
		dataFile = new File(filePath + fileName);
		
		// 判断文件是否存在，且其属性是否为file
		 if(dataFile.exists() && dataFile.isFile()){ 
			 
			 try {
				bufferedReader = new BufferedReader(new FileReader(dataFile));
				String dataLine = "";
				String[] wholeData = null;
				try {
						// 按行读取数据集中的数据条目
					while((dataLine =  bufferedReader.readLine()) != null){
						// 创建新的List对象
						// 这里需要注意，必须重新创建一个新的new ArrayList<String>()赋值给useDate，否则当利用useDate
						// 创建DataEntity对象，并添加至dataList.add(dataEntity);中时，会由于引用了同一个useDate对象而使得dataList
						// 中的值都变成相同的（因为都引用了同一个对象地址），因此为了避免这种现象，需要新建一个useDate引用
						List<String> useData = new ArrayList<String>();
						// 将数据集中的数据条目按照空格分隔开来，并存储在数组中(0~249)
						wholeData = dataLine.split(" ");
						// 使用ArrayList存储wholeData中第0~9，243列数据
						for(int i = 0; i < 10; i++){
							useData.add(wholeData[i]);
							
						}
						useData.add(wholeData[243]);
						// 利用useData创建DataEntity对象，该对象存储即将要使用到的数据信息，我们将这些数据视为原始数据item
						DataEntity dataEntity = new DataEntity(useData);
						// DataEntity对象的集合，为原始数据集合
						dataList.add(dataEntity);
						// 计数器
						counter++;
					
					}
						// 输出信息
						System.out.print(fileName + "原始数据集数据共" + counter + "项！");
						// 数据预处理之删除含有缺失数据或是Locomotion标签为0的数据item
						deleteItem();
						// 将预处理后的原始数据进行文件存储
//						saveDataAsFile();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    } else{
		    	// 要打开的文件不存在或者该文件属性不是一个文件
		    	System.out.println(dataFile.getName() + "is not exist or not a file!");
		    }
	}
	
	// 数据预处理之删除含有缺失数据或是Locomotion标签为0的数据item
	private void deleteItem(){
		
		DataEntity dataEntity = null;
		List<String> dataItem = null;
		counter = 0;
		// 按顺序取出原始数据item--DataEntity
		for(int i = 0;i < dataList.size();i++){
			
			dataEntity = (DataEntity)dataList.get(i);
			dataItem = dataEntity.getDataInfo();

			// 按顺序比较DataEntity中是否存在缺失值或是Locomotion标签为0，如果存在，则从原始数据中删除该DataEntity，并break跳出循环
			for(int j = 0; j < dataItem.size();j++){
				
				if(dataItem.get(j).equals("NaN")){
					// 删除DataEntity
					dataList.remove(i);
					// 因为刚刚删除了一个item，因此i需要减1，使得下次循环可以访问当前item的下一条item
					i--;
					// 计数器
					counter++;
					break;
				}else if(j == (dataItem.size() - 1) && dataItem.get(j).equals("0")){
					// 删除DataEntity
					dataList.remove(i);
					// 因为刚刚删除了一个item，因此i需要减1，使得下次循环可以访问当前item的下一条item
					i--;
					// 计数器
					counter++;
					break;
				}
			}
		}
		System.out.println("预处理删除数据共" + counter + "项！");
	}
	
	// 将预处理后的原始数据进行文件存储
//	private boolean saveDataAsFile(){
//		
//		FileWriter writer;
//		try {
//			// 通过保存文件的路径及其文件名称初始化FileWriter对象
//			writer = new FileWriter(filePath + "DataPreprocess.txt",true);
//			// 将预处理后的原始数据逐条进行存储
//			for(int i = 0; i < dataList.size();i++){
//				writer.write(dataList.get(i).getDataInfo().toString() + "\n");
////				System.out.println(dataList.get(i).getDataInfo().get(10));
//				}
//			writer.close();
//			return true;
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	// 将预处理后的原始数据进行数据库存储
	private boolean saveDataAsDatabase(){
		
		return false;
	}
}
