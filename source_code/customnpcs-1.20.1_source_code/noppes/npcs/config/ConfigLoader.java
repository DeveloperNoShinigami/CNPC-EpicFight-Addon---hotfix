/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import noppes.npcs.config.ConfigProp;
import noppes.npcs.shared.common.util.LogWriter;

public class ConfigLoader {
    private boolean updateFile = false;
    private File dir;
    private String fileName;
    private Class<?> configClass;
    private LinkedList<Field> configFields;

    public ConfigLoader(Class<?> clss, File dir, String fileName) {
        Field[] fields;
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.dir = dir;
        this.configClass = clss;
        this.configFields = new LinkedList();
        this.fileName = fileName + ".cfg";
        for (Field field : fields = this.configClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigProp.class)) continue;
            this.configFields.add(field);
        }
    }

    public void loadConfig() {
        try {
            File configFile = new File(this.dir, this.fileName);
            HashMap<String, Field> types = new HashMap<String, Field>();
            for (Field field : this.configFields) {
                ConfigProp configProp = field.getAnnotation(ConfigProp.class);
                types.put(!configProp.name().isEmpty() ? configProp.name() : field.getName(), field);
            }
            if (configFile.exists()) {
                HashMap<String, Object> properties = this.parseConfig(configFile, types);
                for (String string : properties.keySet()) {
                    Field field = types.get(string);
                    Object obj = properties.get(string);
                    if (obj.equals(field.get(null))) continue;
                    field.set(null, obj);
                }
                for (String string : types.keySet()) {
                    if (properties.containsKey(string)) continue;
                    this.updateFile = true;
                }
            } else {
                this.updateFile = true;
            }
        }
        catch (Exception e) {
            this.updateFile = true;
            LogWriter.except(e);
        }
        if (this.updateFile) {
            this.updateConfig();
        }
        this.updateFile = false;
    }

    private HashMap<String, Object> parseConfig(File file, HashMap<String, Field> types) throws Exception {
        String strLine;
        HashMap<String, Object> config = new HashMap<String, Object>();
        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), "UTF8"));
        while ((strLine = reader.readLine()) != null) {
            if (strLine.startsWith("#") || strLine.length() == 0) continue;
            int index = strLine.indexOf("=");
            if (index <= 0 || index == strLine.length()) {
                this.updateFile = true;
                continue;
            }
            String name = strLine.substring(0, index);
            String prop = strLine.substring(index + 1);
            if (!types.containsKey(name)) {
                this.updateFile = true;
                continue;
            }
            Object obj = null;
            Class<Object> class2 = types.get(name).getType();
            if (class2.isAssignableFrom(String.class)) {
                obj = prop;
            } else if (class2.isAssignableFrom(Integer.TYPE)) {
                obj = Integer.parseInt(prop);
            } else if (class2.isAssignableFrom(Short.TYPE)) {
                obj = Short.parseShort(prop);
            } else if (class2.isAssignableFrom(Byte.TYPE)) {
                obj = Byte.parseByte(prop);
            } else if (class2.isAssignableFrom(Boolean.TYPE)) {
                obj = Boolean.parseBoolean(prop);
            } else if (class2.isAssignableFrom(Float.TYPE)) {
                obj = Float.valueOf(Float.parseFloat(prop));
            } else if (class2.isAssignableFrom(Double.TYPE)) {
                obj = Double.parseDouble(prop);
            }
            if (obj == null) continue;
            config.put(name, obj);
        }
        reader.close();
        return config;
    }

    public void updateConfig() {
        File file = new File(this.dir, this.fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Field field : this.configFields) {
                ConfigProp prop = field.getAnnotation(ConfigProp.class);
                if (prop.info().length() != 0) {
                    out.write("#" + prop.info() + System.getProperty("line.separator"));
                }
                String name = !prop.name().isEmpty() ? prop.name() : field.getName();
                try {
                    field.setAccessible(true);
                    out.write(name + "=" + field.get(null).toString() + System.getProperty("line.separator"));
                    out.write(System.getProperty("line.separator"));
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

