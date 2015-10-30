package im.actor;

import java.util.Optional;

import im.actor.botkit.RemoteBot;
import im.actor.bots.BotMessages;
import im.actor.bots.BotMessages.Message;
import im.actor.bots.BotMessages.TextMessage;
import shardakka.ShardakkaExtension;
import shardakka.keyvalue.SimpleKeyValueJava;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Example Hello Bot: Entry point of development bots for Actor Platform
 */
public class HelloBot extends RemoteBot {

    /**
     * Local persistent key-value. Useful for storing bot's data.
     */
    private SimpleKeyValueJava<String> localKeyValue;

    public HelloBot(String token, String endpoint) {
        //super(token, endpoint);
        super(token, "ws://54.223.205.123:9090");
        // Creating KeyValue. Don't try to understand this, this is not necessary.
        // We will improve this in future versions
        // "msgs" is name of storage, asJava is required for better Java API.
        //localKeyValue = ShardakkaExtension.get(context().system()).simpleKeyValue("msgs").asJava();
    }


    @Override
    public void onMessage(BotMessages.Message msg) {
        //System.out.println(((TextMessage)msg.message()).text());
        if (msg.message()!=null && msg.message() instanceof TextMessage){
            String text = ((TextMessage)msg.message()).text();
            String senderName = getUser(msg.sender().id()).name();
            if (text.startsWith("hello")) {
                requestSendMessage(msg.sender(), nextRandomId(), new TextMessage("Hey," + senderName + "!"));
            }else{
                try {

                    String weatherInfo = "";
                    String url = "http://api.map.baidu.com/telematics/v3/weather?location=" + text + "&output=json&ak=33ed68853ad1645c4aa4abe87766b378";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpget = new HttpGet(url);

                    // Create a response handler
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    String responseBody = httpclient.execute(httpget, responseHandler);
                    JSONObject jsonobj = new JSONObject(responseBody);
                    JSONObject weather = jsonobj.getJSONArray("results").getJSONObject(0);
                    System.out.println(weather.getString("pm25"));

                    JSONObject weatherDay0 = weather.getJSONArray("weather_data").getJSONObject(0);
                    JSONObject weatherDay1 = weather.getJSONArray("weather_data").getJSONObject(1);
                    JSONObject weatherDay2 = weather.getJSONArray("weather_data").getJSONObject(2);
                    JSONObject weatherDay3 = weather.getJSONArray("weather_data").getJSONObject(3);

                    weatherInfo = weatherInfo + weatherDay0.getString("date") +"\n"+ weatherDay0.getString("weather") + "  " +
                            weatherDay0.getString("temperature") + "  " +
                            weatherDay0.getString("wind") + "\nPM2.5指数: " + weather.getString("pm25");
                    /*JSONArray jsonarrayIndex = weather.getJSONArray("index");
                    String indexInfo = "\n";
                    for (int i = 0; i < jsonarrayIndex.length(); i++) {
                        JSONObject index = jsonarrayIndex.getJSONObject(i);
                        indexInfo = indexInfo +"" +index.getString("title")+":"+ index.getString("zs")+"\n";
                    }

                    weatherInfo = weatherInfo + indexInfo;*/

                    weatherInfo = weatherInfo +"\n\n" +weatherDay1.getString("date") +"\n"+ weatherDay1.getString("weather") + "  " +
                            weatherDay1.getString("temperature") + "  " +
                            weatherDay1.getString("wind");
                    weatherInfo = weatherInfo +"\n\n" +weatherDay2.getString("date") +"\n"+ weatherDay2.getString("weather") + "  " +
                            weatherDay2.getString("temperature") + "  " +
                            weatherDay2.getString("wind");
                    weatherInfo = weatherInfo +"\n\n" +weatherDay3.getString("date") +"\n"+ weatherDay3.getString("weather") + "  " +
                            weatherDay3.getString("temperature") + "  " +
                            weatherDay3.getString("wind");
                    /*System.out.println(weather.getJSONArray("weather_data").getJSONObject(0).getString("date"));
                    System.out.println(weather.getJSONArray("weather_data").getJSONObject(0).getString("weather"));
                    System.out.println(weather.getJSONArray("weather_data").getJSONObject(0).getString("wind"));
                    System.out.println(weather.getJSONArray("weather_data").getJSONObject(0).getString("temperature"));*/

                    requestSendMessage(msg.sender(), nextRandomId(), new TextMessage(weatherInfo));
                }catch (Exception e) {
                    e.printStackTrace();
                    requestSendMessage(msg.sender(), nextRandomId(), new TextMessage("请正确输入城市名称"));
                }
                //requestSendMessage(tm.sender().asOutPeer(), nextRandomId(), "请正确输入城市名称");

                //requestSendMessage(msg.sender().asOutPeer(), nextRandomId(), new TextMessage("Please say hello"));
            }


        }
        String senderName = getUser(msg.sender().id()).name();
        /*if ((TextMessage)msg.message()) {

        }*/
    }

    @Override
    public void onReceive(Object msg) {
        System.out.println("----------");
    }
}