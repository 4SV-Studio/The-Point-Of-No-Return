package org.studio4sv.tponr.client;

import java.util.HashMap;
import java.util.Map;

public class ClientAttributesData {
    private static final Map<String, Integer> attributes = new HashMap<>();

    public static void set(Map<String, Integer> newAttributes) {
        ClientAttributesData.attributes.putAll(newAttributes);
    }

    public static Map<String, Integer> get() {
        return attributes;
    }

    public static Integer getValue(String key) {
        return attributes.get(key);
    }
}
