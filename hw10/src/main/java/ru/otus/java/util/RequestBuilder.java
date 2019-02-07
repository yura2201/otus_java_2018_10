package ru.otus.java.util;

import java.util.List;
import java.util.Map;

public interface RequestBuilder<T> {

  /**
   * Checks value of the field with {@code @Id} annotation for NULL equality as a tag of necessity
   * to do check for entity existence in the DB.
   * 
   * @param object
   *          object to be saved. Is used to extract data for a builder through {@code Reflection}
   * @return {@code true} only when idenitity field value is not null
   */
  boolean doCheckRequest(T object);

  /**
   * If {@link RequestBuilderImpl#doCheckRequest(Object)} has returned {@code true} then this method
   * must be invoked to construct a request for checking an enitiy existence in the DB
   * 
   * @param object
   *          object to be saved. Is used to extract data for a builder through {@code Reflection}
   * @return Map.Entry&lt;String, Object&gt; where <i>key</i> - SQL request, <i>value</i> - value of
   *         the identity field
   */
  Map.Entry<String, Object> buildCheckEntityExistenceRequest(T object);

  /**
   * Builds an SQL request for an entity update
   * 
   * @param object
   *          object to be updated. Is used to extract data for a builder through {@code Reflection}
   * @return Map.Entry&lt;String, List&lt;Object&gt;&gt; where <i>key</i> - SQL request,
   *         <i>value</i> - List&lt;Object&gt; of parameters to be passed into the request
   */
  Map.Entry<String, List<Object>> buildUpdateRequest(T object);

  /**
   * Builds an SQL request for an entity insert
   * 
   * @param object
   *          object to be inserted. Is used to extract data for a builder through
   *          {@code Reflection}
   * @return Map.Entry&lt;String, List&lt;Object&gt;&gt; where <i>key</i> - SQL request,
   *         <i>value</i> - List&lt;Object&gt; of parameters to be passed into the request
   */
  Map.Entry<String, List<Object>> buildInsertRequest(T object);

  /**
   * Builds an SQL request for loading of a particular entity from the DB
   * 
   * @param clazz
   *          Class&lt;T&gt; determines a class type of the object that has to be loaded
   * @return Map.Entry&lt;String, List&lt;String&gt;&gt; where <i>key</i> - SQL request,
   *         <i>value</i> - List&lt;String&gt; of aliases of the fields in the request
   */
  Map.Entry<String, List<String>> buildLoadRequest(Class<T> clazz);

  /**
   * Builds an SQL request for loading of entities from the DB
   * 
   * @param clazz
   *          Class&lt;T&gt; determines a class type of objects that have to be loaded
   * @return Map.Entry&lt;String, List&lt;String&gt;&gt; where <i>key</i> - SQL request,
   *         <i>value</i> - List&lt;String&gt; of aliases of the fields in the request
   */
  Map.Entry<String, List<String>> buildLoadAllRequest(Class<T> clazz);
}
