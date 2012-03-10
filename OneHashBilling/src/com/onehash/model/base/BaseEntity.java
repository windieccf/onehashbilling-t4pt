package com.onehash.model.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable, Cloneable {
	
	@Override
	public Object clone() {
	    try {
            return  super.clone();
        } catch (CloneNotSupportedException e) {            
            e.printStackTrace();
            return null;
        }
	}
	

	/*@Override
	public int hashCode(){}*/
	
	public void show() {
		System.out.println(this.toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
    public boolean equals(Object object) {    	
        if (object == null || object.getClass() != this.getClass()) return false; 
        
        Class klass = this.getClass();
        List<Field> fields = retrieveFields();
        for(Field field : fields){
        	field.setAccessible(true);
        	try {
				Object val01 = field.get(this);
				Object val02 = field.get(object);
				if( ( val02 == null && val01 != null ) || (val01 == null && val02 != null))
					return false;

				if(val02.equals(val01)) continue;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
        }
        return true;
        
    }
	
	@SuppressWarnings("rawtypes")
	private List<Field> retrieveFields(){
		List<Field> fields = new ArrayList<Field>();
		Class currentClass = this.getClass();
		do{
			fields.addAll( Arrays.asList(currentClass.getFields()));
			currentClass = currentClass.getSuperclass();
		}while(!currentClass.getName().endsWith("BaseEntity"));
		
		
		return fields;
	}
	
}
