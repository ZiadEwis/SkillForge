package database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonDatabaseManager {

    private static JsonDatabaseManager instance; // الـ single instance
    private File file;
    private JSONParser parser;

    private JsonDatabaseManager(String filePath) {
        this.file = new File(filePath);
        this.parser = new JSONParser();
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    JSONObject root = new JSONObject();
                    root.put("data", new JSONArray());
                    writer.write(root.toJSONString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static JsonDatabaseManager getInstance() {
        if (instance == null) {
            instance = new JsonDatabaseManager("data/database.json");
        }
        return instance;
    }

    public JSONArray readAll() {
        try (FileReader reader = new FileReader(file)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("data");
            if (data == null) return new JSONArray();
            return data;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void addObject(JSONObject newObject) {
        JSONArray data = readAll();
        data.add(newObject);
        save(data);
    }

    public boolean removeById(String id) {
        JSONArray data = readAll();
        boolean removed = false;
        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            if (id.equals(obj.get("id"))) {
                data.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) save(data);
        return removed;
    }

    public boolean updateById(String id, JSONObject updatedObject) {
        JSONArray data = readAll();
        boolean updated = false;
        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            if (id.equals(obj.get("id"))) {
                data.set(i, updatedObject);
                updated = true;
                break;
            }
        }
        if (updated) save(data);
        return updated;
    }

    private void save(JSONArray data) {
        JSONObject root = new JSONObject();
        root.put("data", data);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(root.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int count() {
        return readAll().size();
    }
}
