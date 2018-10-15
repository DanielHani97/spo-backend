package my.com.fotia.osdec.utilities.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Class<T> type;
	
	protected final Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	protected final Session getNewSession(){
		return sessionFactory.openSession();
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public GenericDaoImpl() {
		
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}
	
	@Override
	public T findOne(String id, Class<T> clazz) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    T t =  (T) session.get(clazz, (Serializable) id );
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null || (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    List<T> result = session.createQuery( "from " + type.getName() ).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String column1, String value1) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"'";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String column1, String value1, String column2, String value2) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"' and " + column2 + "='" + value2 + "'";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String column1, String value1, String column2, String value2, String column3, String value3) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"' and " + column2 + "='" + value2 + "' and "+column3+"='"+value3+"'";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderBy(String column1) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " ORDER BY " + column1 +" ASC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderBy(String column1, String value1, String orderby) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"'" + " ORDER BY " + orderby +" ASC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderByDesc(String column1, String value1, String orderby1, String orderby2) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"'" + " ORDER BY " + orderby1 +" DESC, " + orderby2 + " DESC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderByDesc(String column1, String value1, String column2, String value2, String orderby1, String orderby2) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"' AND " + column2 + "='" + value2 +"' " + " ORDER BY " + orderby1 +" DESC, " + orderby2 + " DESC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderByDesc(String column1, String value1, String column2, String value2, String column3, String value3,String orderby1, String orderby2) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"' AND " + column2 + "='" + value2 +"' AND " + column3 + "='" + value3 +"' " + " ORDER BY " + orderby1 +" DESC, " + orderby2 + " DESC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllOrderBy(String column1, String value1, String column2, String value2, String orderby) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    String query = " where " + column1 + "='" + value1 +"' and " + column2 + "='" + value2 + "'" + " ORDER BY " + orderby +" ASC";
		List<T> result = session.createQuery( "from " + type.getName() + query).list();
		
	    try{
			session.getTransaction().commit();
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
	    
	    return result;
	}
	
	@Override
	public T create (T t) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    try{
	    	session.persist(t);
			session.getTransaction().commit();
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
	    
	    return t;
	}
	
	@Override
	public void delete(T t) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    try{
	    	session.delete(t);
			session.getTransaction().commit();
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
	    
	}
	
	@Override
	public void deleteById(String entityId, Class<T> t) {
		
		T entity = findOne(entityId, t);
		delete(entity);
	}
	
	@Override
	public T find(Object id, Class<T> clazz) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    T t =  (T) session.get(clazz, (Serializable) id );
	    
	    try{
			session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return t;
	}
	
	@Override
	public T update (T t) {
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
	    try{
	    	session.merge(t);
			session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return t;
	}
	
	@Override
	public T getOneRow(){
		
		Transaction txD;
		Session session = getNewSession();
	    
	    if (session.getTransaction() != null && (session.getTransaction().getStatus() == TransactionStatus.ACTIVE)) {
	        txD = session.getTransaction();
	    } else {
	        txD = session.beginTransaction();
	    }
	    
		@SuppressWarnings("unchecked")
		T t = (T) session.createQuery("from " + type.getName());
	    try{
	    	session.getTransaction().commit();
			
		}catch(Exception e){
			txD.rollback();
		    throw e;
		}finally{
			txD = null;
	        session.close();
		}
		
		return t;
	}

}
