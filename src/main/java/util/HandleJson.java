package util;

import com.google.gson.JsonObject;
import db.Message;

public class HandleJson {

    public static JsonObject messageToJson(Message message) {
        JsonObject json = new JsonObject();
        json.addProperty("content", message.getContent());
        json.addProperty("userID", message.getUserID());
        json.addProperty("time", message.getTime());
        json.addProperty("date", message.getDate());
        return json;
    }
}
