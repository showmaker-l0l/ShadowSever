package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import db.DBUtil;
import db.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.HandleJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandleMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "select * from " + DBUtil.TABLE_MESSAGE;
        JsonArray jsonArray = new JsonArray();
        try {
            ResultSet result = DBUtil.query(sql);
            while (result.next()){
                Message message = new Message();
                message.setMessageID(result.getInt("messageID"));
                message.setUserID(result.getInt("userID"));
                message.setContent(result.getString("content"));
                message.setTime(result.getLong("time"));
                message.setDate(result.getString("date"));
                JsonObject json = HandleJson.messageToJson(message);
                jsonArray.add(json);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(jsonArray);
        resp.getWriter().write(String.valueOf(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader read = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = read.readLine()) != null) {
            sb.append(line);
        }

        String reqe = sb.toString();
        System.out.println(reqe);

        Gson gson = new Gson();
        Message message = gson.fromJson(reqe, Message.class);

        String sql = "insert into " + DBUtil.TABLE_MESSAGE + "(userID,content,time,date) values('" + 001 + "','" + message.getContent() + "','"
                + message.getTime() + "','" + message.getDate() +"')";
        System.out.println(sql);
        try {
            DBUtil.update(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
