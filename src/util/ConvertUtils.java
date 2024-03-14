package util;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
 
public class ConvertUtils<T> {
    private ConvertUtils() {}
    
    
    // Map -> VO
    public static <T> T convertToVo(Map<String, Object> map, Class<T> type) {
        try {
            if (Objects.isNull(type)) {
            	return null;
            }
            if (Objects.isNull(map) || map.isEmpty()) {
            	return null;
           }
            T instance = type.getConstructor().newInstance();
            Field[] fields = type.getDeclaredFields();
 
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                	String key = entry.getKey();
                	String field_name = field.getName().toUpperCase();
                    if (entry.getKey().equals(field.getName())) {
                    	setData(field, entry, field_name, map, instance);
                        break;
                    }
                    else if (key.equals(field_name)) {
                    	setData(field, entry, field_name, map, instance);
                        break;
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Object reflect(Object obj) {
    	Object value = obj;
    	if(obj.getClass().getTypeName().contains("Timestamp")) {
        	Timestamp td = (Timestamp)obj;
        	if(td != null) { 
        		Date date = new Date(td.getTime());
        		value = date;
        	}
    	}
    	return value;
    }
    
    
    private static <T> void setData(Field field, Map.Entry<String, Object> entry, String field_name, Map map, T instance) {
        field.setAccessible(true);
        
        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                ? getDefaultValue(field.getType())
                : map.get(field_name);
        if(field.getType().getName().contains("int")) {
        	BigDecimal bd = (BigDecimal)map.get(field_name);
        	if(bd == null) {
        		value = 0;
        	}else {
        		value = bd.intValue();
        	}
        }else if(field.getType().getName().contains("long")) {
          	BigDecimal bd = (BigDecimal)map.get(field_name);
        	if(bd == null) {
        		value = 0;
        	}else {
        		value = bd.longValue();
        	}
        }else if(field.getType().getName().contains("String")){
        	Date date = (Date)map.get(field_name);
        	if(date == null) {
        		value = null;
        	}else {
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        		value = sdf.format(date);
        	}
        }
//        else if(field.getType().getName().contains("Date")) 
//        {
//        	Timestamp td = (Timestamp)map.get(field_name);
//        	if(td == null) {
//        		value = null;
//        	}else {
//        		Date date = new Date(td.getTime());
//        		value = date;
//        	}
//        }
        
        try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
    
    public static <T> List<T> convertToList(List<Map<String, Object>> list, Class<T> type) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> convertList = new ArrayList<>(list.size());
        for (Map<String, Object> map : list) {
            convertList.add(ConvertUtils.convertToVo(map, type));
        }
        return convertList;
    }
 
    private static Object getDefaultValue(Class<?> type) {
        switch (type.getName()) {
            case "byte": case "short": case "int": return 0;
            case "long"    : return 0L;
            case "float"   : return 0.0f;
            case "double"  : return 0.0d;
            case "char"    : return '\u0000';
            case "boolean" : return false;
        }
        return null;
    }
}