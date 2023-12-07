package ua.nure.dbtable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DBTableInMemory<T> implements DBTable<T> {
	private ConcurrentHashMap<Integer, T> items = new ConcurrentHashMap<>();
	private int index;
	
	private final Logger log = LoggerFactory.getLogger(DBTableInMemory.class);

	@Override
	public int insert(T item) {
		if (item == null) {
			log.error("item is null");
			throw new IllegalArgumentException("T can not be a null");
		}
		items.put(++index, item);
		log.debug("T added --> {}", item);
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int[] insert(T... items) {
		int[] ids = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			ids[i] = insert(items[i]);
		}
		return ids;
	}

	@Override
	public T delete(int id){
		T t = items.remove(id);
		log.debug("Removed : {}",t);
		return t;
	}

	@Override
	public void delete(T item, Filter<T> filter) {
		for (Entry<Integer, T> entry : items.entrySet()) {
			if (filter.accept(entry.getValue(), item)) {
				log.debug("Accepted to remove : {}",entry.getValue());
				boolean res = items.remove(entry.getKey(), entry.getValue());
				log.debug("IsRemoved : {}",res);
			}
		}
	}

	@Override
	public boolean update(int id, T item) {
		if (item == null) {
			throw new NullPointerException("Item is null");
		}
		T o = items.get(id);
		if (o == null) {
			return false;
		}
		items.put(id, item);
		return true;
	}

	@Override
	public <K> Collection<T> filter(K pattern, Filter<T> filter) {
		ArrayList<T> found = new ArrayList<>();
		for (T item : items.values()) {
			if (filter.accept(item, pattern)) {
				log.debug("Found : {}",item);
				found.add(item);
			}
		}
		return found;
	}

	@Override
	public Collection<T> selectAll() {
		log.debug("selectAll() values : {}",items.values());
		return items.values();
	}

	@Override
	public T get(int id){
		T b = items.get(id);
		if (b == null) {
			log.debug("Not found : {}",id);
		}
		log.debug("Found : {}",id);
		return b;
	}

	@Override
	public void clear() {
		items = new ConcurrentHashMap<>();
		index = 0;
	}

	@Override
	public int size() {
		return items.size();
	}

}
