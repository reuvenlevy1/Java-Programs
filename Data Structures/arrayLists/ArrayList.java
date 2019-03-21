package reuven.java.datastructures;

public class ArrayList {
	private int curr_size = 0;
	private Object[] array;
	
	ArrayList(){
		array = new Object[10];
	}
	
	public void add(Object value) {
		_add(value);
	}
	
	private void _add(Object val) {
		if (array[array.length - 1] != null) {
			resize_Array();
		}
		array[curr_size++] = val;
	}
	
	private void resize_Array() {
		Object[] tmp = new Object[array.length + 5];
		
		for (int i = 0; i < array.length; i++) {
			tmp[i] = array[i];
		}
		array = tmp.clone();
		
	/*	
	   or
	   array = Arrays.copyOf(array, array.length*2);
	*/
	}
	
	public Object get(int index) {
		return _get(index);
	}
	
	private Object _get(int index){
        if (index < curr_size) {
            return array[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();			//throw this error instead of returning null value
        }
    }
	
	public Object remove(int index) {
		return _remove(index);
	}
	
	private Object _remove(int index) {
		if (index < curr_size) {
            Object value = array[index];
            array[index] = null;
            curr_size--;
            return value;
        } else {
            throw new ArrayIndexOutOfBoundsException();			//throw this error instead of returning null value
        }
	}
	
	public int size() {
		return curr_size;
	}
}
