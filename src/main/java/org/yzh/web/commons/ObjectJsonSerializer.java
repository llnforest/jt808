package org.yzh.web.commons;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.JavaType;

import java.util.List;
import java.util.Map;

public class ObjectJsonSerializer {

	private final static JsonMapper mapper = JsonMapper.buildNormalMapper();

	/**
	 * 将对象序列化为json
	 * 
	 * @return
	 */
	public static <T> String serialize(T t) {
		if (t == null) {
			return null;
		}

		try {
			return mapper.getMapper().writeValueAsString(t);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.warn("write to json string error:" + t, e);
			System.out.println("write to json string error:" + t + e);
			return null;
		}
	}

	/**
	 * 序列化一个list的对象
	 * 
	 * @param jsons
	 * @return
	 */

	public static <T> List<String> serializeAsList(List<T> jsons) {
		List<String> results = Lists.newArrayList();
		if (jsons == null || jsons.size() == 0) {
			return results;
		}

		for (T t : jsons) {
			results.add(serialize(t));
		}

		return results;
	}

	/**
	 * 将json反序列化为对象
	 * 
	 * @param json
	 * @return
	 */
	public static <T> T deSerialize(Class<T> clazz, String json) {
		if (StringUtils.isBlank(json)) {
			return null;
		}

		return mapper.fromJson(json, clazz);
	}

	public static <K, V> Map<K, V> deSerialize(String json, Class<K> key,
			Class<V> value) {
		if (StringUtils.isBlank(json)) {
			return null;
		}

		JavaType mapType = mapper
				.constructParametricType(Map.class, key, value);
		return mapper.fromJson(json, mapType);

	}

	public static <T> List<T> deSerialize(String json, Class<T> t) {
		JavaType listType = mapper.constructParametricType(List.class, t);
		return mapper.fromJson(json, listType);
	}

	/**
	 * 反序列化一个list的json
	 * 
	 * @param jsons
	 * @return
	 */
	public static <T> List<T> deSerialize(Class<T> clazz, List<String> jsons) {
		List<T> result = Lists.newArrayList();
		if (jsons == null || jsons.size() == 0) {
			return result;
		}

		for (String json : jsons) {
			T deSerialize = deSerialize(clazz, json);
			if (deSerialize == null) {
				continue;
			}

			result.add(deSerialize);
		}
		return result;
	}

	public static List<Map<String, Object>> deSerialize(List<String> jsons) {
		List<Map<String, Object>> result = Lists.newArrayList();
		if (jsons == null || jsons.size() == 0) {
			return result;
		}

		for (String json : jsons) {
			Map<String, Object> deSerialize = deSerialize(json, String.class,
					Object.class);
			if (deSerialize == null) {
				continue;
			}

			result.add(deSerialize);
		}
		return result;
	}

}
