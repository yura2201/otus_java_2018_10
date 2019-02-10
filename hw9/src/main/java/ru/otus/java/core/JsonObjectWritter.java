package ru.otus.java.core;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import ru.otus.java.util.DataTypeHelper;
import ru.otus.java.util.ReflectionHelper;

public class JsonObjectWritter {

  private static final Set<Class<?>> SIMPLE_TYPES;
  static {
    Set<Class<?>> set = new HashSet<>();
    set.add(Boolean.class);
    set.add(String.class);
    set.add(Character.class);
    set.add(Date.class);
    set.add(Number.class);
    set.add(Integer.class);
    set.add(Byte.class);
    set.add(Short.class);
    set.add(Integer.class);
    set.add(Long.class);
    set.add(Float.class);
    set.add(Double.class);
    set.add(BigDecimal.class);
    SIMPLE_TYPES = Collections.unmodifiableSet(set);
  }

  private static boolean isSimpleType(Object object) {
    return SIMPLE_TYPES.contains(object.getClass());
  }

  private static void addArrayValue(Object object, JsonArrayBuilder arrayBuilder) {
    if (Objects.isNull(object)) {
      arrayBuilder.addNull();
      return;
    }
    if (isSimpleType(object)) {
      Class<?> clazz = object.getClass();
      if (String.class.isAssignableFrom(clazz)) {
        arrayBuilder.add((String) object);
      } else if (Integer.class == clazz || int.class == clazz) {
        arrayBuilder.add((Integer) object);
      } else if (Long.class == clazz || long.class == clazz) {
        arrayBuilder.add((Long) object);
      } else if (Double.class == clazz || double.class == clazz) {
        arrayBuilder.add((Double) object);
      } else if (Float.class == clazz || float.class == clazz) {
        arrayBuilder.add((Float) object);
      } else if (Short.class == clazz || short.class == clazz) {
        arrayBuilder.add((Short) object);
      } else if (Character.class == clazz || char.class == clazz) {
        arrayBuilder.add((Character) object);
      } else if (Byte.class == clazz || byte.class == clazz) {
        arrayBuilder.add((Byte) object);
      } else if (Boolean.class == clazz || boolean.class == clazz) {
        arrayBuilder.add((Boolean) object);
      } else if (Date.class.isAssignableFrom(object.getClass())) {
        arrayBuilder.add(DataTypeHelper.dateToString((Date) object));
      } else if (BigDecimal.class.isAssignableFrom(object.getClass())) {
        arrayBuilder.add((BigDecimal) object);
      }
    } else if (object.getClass().isArray()) {
      JsonArrayBuilder builder = Json.createArrayBuilder();
      int len = Array.getLength(object);
      for (int i = 0; i < len; i++) {
        addArrayValue(Array.get(object, i), builder);
      }
      arrayBuilder.add(builder);
    } else if (object instanceof Collection<?>) {
      JsonArrayBuilder builder = Json.createArrayBuilder();
      Iterator<?> itr = ((Collection<?>) object).iterator();
      while (itr.hasNext()) {
        addArrayValue(itr.next(), builder);
      }
      arrayBuilder.add(builder);
    } else if (object instanceof JsonArrayBuilder) {
      arrayBuilder.add((JsonArrayBuilder) object);
    } else {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      addObject(object, objectBuilder);
      arrayBuilder.add(objectBuilder);
    }
  }

  private static void addObject(Object object, JsonObjectBuilder objectBuilder) {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      addObjectValue(object, field, objectBuilder);
    }
  }

  private static void addObjectValue(Object object, Field field, JsonObjectBuilder objectBuilder) {
    Object fieldValue = ReflectionHelper.getFieldValue(object, field.getName());
    if (Objects.isNull(fieldValue)) {
      objectBuilder.addNull(field.getName());
      return;
    }
    if (isSimpleType(fieldValue)) {
      Class<?> clazz = fieldValue.getClass();
      if (String.class.isAssignableFrom(clazz)) {
        objectBuilder.add(field.getName(), (String) fieldValue);
      } else if (Integer.class == clazz || int.class == clazz) {
        objectBuilder.add(field.getName(), (Integer) fieldValue);
      } else if (Long.class == clazz || long.class == clazz) {
        objectBuilder.add(field.getName(), (Long) fieldValue);
      } else if (Double.class == clazz || double.class == clazz) {
        objectBuilder.add(field.getName(), (Double) fieldValue);
      } else if (Float.class == clazz || float.class == clazz) {
        objectBuilder.add(field.getName(), (Float) fieldValue);
      } else if (Short.class == clazz || short.class == clazz) {
        objectBuilder.add(field.getName(), (Short) fieldValue);
      } else if (Character.class == clazz || char.class == clazz) {
        objectBuilder.add(field.getName(), (Character) fieldValue);
      } else if (Byte.class == clazz || byte.class == clazz) {
        objectBuilder.add(field.getName(), (Byte) fieldValue);
      } else if (Boolean.class == clazz || boolean.class == clazz) {
        objectBuilder.add(field.getName(), (Boolean) fieldValue);
      } else if (Date.class.isAssignableFrom(clazz)) {
        objectBuilder.add(field.getName(), DataTypeHelper.dateToString((Date) fieldValue));
      } else if (BigDecimal.class.isAssignableFrom(clazz)) {
        objectBuilder.add(field.getName(), (BigDecimal) fieldValue);
      }
    } else {
      if (field.getType().isArray()) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        Object[] array = (Object[]) fieldValue;
        for (int i = 0; i < array.length; i++) {
          addArrayValue(array[i], builder);
        }
        objectBuilder.add(field.getName(), builder);
      } else if (Collection.class.isAssignableFrom(field.getType())) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        Collection<?> collection = (Collection<?>) fieldValue;
        Iterator<?> itr = collection.iterator();
        while (itr.hasNext()) {
          addArrayValue(itr.next(), builder);
        }
        objectBuilder.add(field.getName(), builder);
      } else {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        addObject(fieldValue, builder);
        objectBuilder.add(field.getName(), builder);
      }
    }
  }

  private static String writeToString(JsonObject jsonst) {
    StringWriter stWriter = new StringWriter();
    try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
      jsonWriter.writeObject(jsonst);
    }

    return stWriter.toString();
  }

  public static String toJson(Object object) {
    if (Objects.isNull(object)) {
      return null;
    }
    JsonObjectBuilder builder = Json.createObjectBuilder();
    addObject(object, builder);
    JsonObject structire = builder.build();
    return writeToString(structire);
  }
}
