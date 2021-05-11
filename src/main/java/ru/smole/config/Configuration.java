package ru.smole.config;

import com.google.common.collect.Sets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Configuration {

    private static final char SEPARATOR = '.';

    private final Map<String, Object> self;
    private final File file;

    public Configuration(File file) {
        this(file, null);
    }

    Configuration(File file, Map<String, Object> self) {
        this.self = self == null ? new LinkedHashMap<>() : self;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    protected Map<String, Object> getSelf() {
        return self;
    }

    private Configuration getSectionFor(String path) {
        int index = path.indexOf('.');

        if (index == -1)
            return this;

        String root = path.substring(0, index);
        Object section = (Object) self.get(root);

        if (section == null) {
            section = new LinkedHashMap<>();
            self.put(root, section);
        }

        if (section instanceof Configuration)
            return (Configuration) section;

        return new Configuration(file, (Map) section);
    }

    private String getChild(String path) {
        int index = path.indexOf('.');
        return (index == -1) ? path : path.substring(index + 1);
    }

    public <T> T getOrDefault(String path, T def) {
        Object val;
        Configuration section = getSectionFor(path);

        if (section == this) {
            val = self.get(path);
        } else {
            val = section.get(getChild(path));
        }

        return val != null ? (T) val : def;
    }

    public Object get(String path) {
        return getOrDefault(path, null);
    }

    public void set(String path, Object value) {
        Configuration section = getSectionFor(path);

        if (section == this) {
            if (value == null) {
                self.remove(path);
            } else {
                self.put(path, value);
            }
        } else {
            section.set(getChild(path), value);
        }
    }

    public Configuration getSection(String path) {
        return getSectionFor(path);
    }

    public Collection<String> getKeys() {
        return Sets.newLinkedHashSet(self.keySet());
    }

    public byte getByte(String path) {
        return getByte(path, (byte) 0);
    }

    public byte getByte(String path, byte def) {
        return getOrDefault(path, def);
    }

    public List<Byte> getByteList(String path) {
        List<?> list = getList(path);
        List<Byte> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Byte.valueOf(((Number)object).byteValue()));
        }
        return result;
    }

    public short getShort(String path) {
        return getShort(path, (short) 0);
    }

    public short getShort(String path, short def) {
        return getOrDefault(path, def);
    }

    public List<Short> getShortList(String path) {
        List<?> list = getList(path);
        List<Short> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Short.valueOf(((Number)object).shortValue()));
        }
        return result;
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public int getInt(String path, int def) {
        return getOrDefault(path, def);
    }

    public List<Integer> getIntList(String path) {
        List<?> list = getList(path);
        List<Integer> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Integer.valueOf(((Number)object).intValue()));
        }
        return result;
    }

    public long getLong(String path) {
        return getLong(path, 0l);
    }

    public long getLong(String path, long def) {
        return getOrDefault(path, def);
    }

    public List<Long> getLongList(String path) {
        List<?> list = getList(path);
        List<Long> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Long.valueOf(((Number)object).longValue()));
        }
        return result;
    }

    public float getFloat(String path) {
        return getFloat(path, 0f);
    }

    public float getFloat(String path, float def) {
       return getOrDefault(path, def);
    }

    public List<Float> getFloatList(String path) {
        List<?> list = getList(path);
        List<Float> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Float.valueOf(((Number)object).floatValue()));
        }
        return result;
    }

    public double getDouble(String path) {
        return getDouble(path, 0d);
    }

    public double getDouble(String path, double def) {
        return getOrDefault(path, def);
    }

    public List<Double> getDoubleList(String path) {
        List<?> list = getList(path);
        List<Double> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number)
                result.add(Double.valueOf(((Number)object).doubleValue()));
        }
        return result;
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean def) {
        return getOrDefault(path, def);
    }

    public List<Boolean> getBooleanList(String path) {
        List<?> list = getList(path);
        List<Boolean> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Boolean)
                result.add((Boolean)object);
        }
        return result;
    }

    public char getChar(String path) {
        return getChar(path, ' ');
    }

    public char getChar(String path, char def) {
        return getOrDefault(path, def);
    }

    public List<Character> getCharList(String path) {
        List<?> list = getList(path);
        List<Character> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Character)
                result.add((Character)object);
        }
        return result;
    }

    public String getString(String path) {
        return getString(path, "");
    }

    public String getString(String path, String def) {
        return getOrDefault(path, def);
    }

    public List<String> getStringList(String path) {
        List<?> list = getList(path);
        List<String> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof String)
                result.add((String)object);
        }
        return result;
    }

    public List<?> getList(String path) {
        return getList(path, Collections.EMPTY_LIST);
    }

    public List<?> getList(String path, List<?> def) {
        Object val = getOrDefault(path, def);
        return (val instanceof List) ? (List) val : def;
    }

}