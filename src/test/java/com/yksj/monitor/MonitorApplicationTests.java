package com.yksj.monitor;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.EOFException;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
class MonitorApplicationTests {

    @Test
    void contextLoads() throws JSONException {
//		test();
//		test2();
//        test3();
        test4();
        test5();
    }

    public void test5() {
        String[] array = {"mysql", "redis", "rabbitmq", "mongo", "elasticsearch", "node_grpc", "ttl", "nlp", "client", "asr"};
        String value = "rabbitmq";
        int index = Arrays.asList(array).indexOf(value);

//        int index = Arrays.binarySearch(array, value);
//        int[] array2 = {1,2,5,3,9,7};
//        int value = 9;
//        int index = Arrays.binarySearch(array2, value);

        System.out.println("元素" + value + "的索引为:" + index);


        String jsonString1 = "{\"BlockIO\": \" 35.7MB / 64.4MB \"}";
        String jsonString2 = "{\"restartCount\":0}";

//        JsonObject object= new JsonObject();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString1, JsonObject.class);
        jsonObject.addProperty("restartCount", "0");

        System.out.println(jsonObject);
    }

    public void test4() throws JSONException {

        String jsonString1 = "{\"BlockIO\": \" 35.7MB / 64.4MB \"}";
        String jsonString2 = "{\"restartCount\":0}";

        JSONObject json1 = new JSONObject(jsonString1);
        JSONObject json2 = new JSONObject(jsonString2);
// 合并两个 JSON 对象
        json1.put("restartCount", json2.get("restartCount"));
//		json1.put("country", json2.get("country"));
        // 将合并后的 JSON 对象转换为字符串
        String mergedJsonString = json1.toString();
        System.out.println(mergedJsonString);
    }

    public void test3() throws JSONException {
        String jsonString1 = "{\"name\": \"Alice\", \"age\": 25}";
        String jsonString2 = "{\"name2\": \"Bob\", \"age2\": 30}";

        JSONObject result = new JSONObject();
        JSONArray resultArr = new JSONArray();

        // 将第一个JSON字符串解析为JSON对象，并将其合并到最终结果中
//		JSONArray json1 = new JSONArray(jsonString1);
        char[] json1 = jsonString1.toCharArray();
        char[] json2 = jsonString2.toCharArray();
//		result.putOpt(jsonString1,result);
//		result.putOpt(jsonString2,result);

        // 将第二个JSON字符串解析为JSON对象，并将其合并到最终结果中
//		JSONArray json2 = new JSONArray(jsonString2);
//		result.putAll(json2);

        resultArr.put(json1);
        resultArr.put(json2);

        System.out.println("数组" + resultArr);
        // 将最终结果转换为字符串形式
        String mergedJsonString = result.toString();

        System.out.println(mergedJsonString);
    }


    public static void test() {
        // 获取当前日期和时间
        LocalDateTime datetime = LocalDateTime.now();
        System.out.println("当前日期和时间: " + datetime);

        // 创建指定日期和时间的DateTime对象
        LocalDateTime specificDatetime = LocalDateTime.of(2021, 3, 15, 10, 30, 0);
        System.out.println("指定日期和时间: " + specificDatetime);
    }

    public static void test2() {
        Entity entity = new Entity();
        System.out.println("Create Time: " + entity.getCreateTime());
        System.out.println("Update Time: " + entity.getUpdateTime());

        entity.update();
        System.out.println("Update Time after update: " + entity.getUpdateTime());
    }


}
