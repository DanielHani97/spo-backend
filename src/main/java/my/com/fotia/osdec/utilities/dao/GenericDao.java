package my.com.fotia.osdec.utilities.dao;

import java.util.List;

public interface GenericDao<T> {
	
	T findOne(final String id, Class<T> t);
	
	List<T> findAll();
	
	List<T> findAll(String column1, String value1);
	
	List<T> findAll(String column1, String value1, String column2, String value2);
	
	List<T> findAll(String column1, String value1, String column2, String value2, String column3, String value3);
	
	List<T> findAllOrderBy(String column1);
	
	List<T> findAllOrderBy(String column1, String value1, String orderby);
	
	List<T> findAllOrderByDesc(String column1, String value1, String orderby1, String orderby2);
	List<T> findAllOrderByDesc(String column1, String value1, String column2, String value2, String orderby1, String orderby2);
	List<T> findAllOrderByDesc(String column1, String value1, String column2, String value2, String column3, String value3,String orderby1, String orderby2);
	
	List<T> findAllOrderBy(String column1, String value1, String column2, String value2, String orderby);
	
	T create(T t);
	
	void delete(T t);
	
	void deleteById(String entityId, Class<T> t);
	
	T find(Object id, Class<T> t);
	
	T update(T t);	
	
	T getOneRow();
}