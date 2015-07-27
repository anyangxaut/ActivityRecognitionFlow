package edu.xaut.dao;

import java.util.List;

public interface SVMDataFormatDao {
	// 查询数据库中的数据库,并以List的形式返回数据信息
	public List<List<Double>> search(String sql);
}
