package im.actor;

import akka.util.Timeout;
import im.actor.bots.BotMessages;
import im.actor.bots.BotMessages.Message;
import im.actor.bots.BotMessages.TextMessage;
import im.actor.botkit.RemoteBot;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import scala.concurrent.Future;
import shardakka.ShardakkaExtension;
import shardakka.keyvalue.SimpleKeyValueJava;

import java.util.Optional;

import static scala.compat.java8.JFunction.proc;



public class HelloBot extends RemoteBot {

    SimpleKeyValueJava<String> msgsKv;
    //Timeout timeout = new Timeout(1000);

    public HelloBot(String token, String endpoint) {
        super(token, "ws://54.223.205.123:9090");
        //msgsKv = ShardakkaExtension.get(context().system()).simpleKeyValue("msgs", context().system()).asJava();

    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        //System.out.println("----preStart------");

        //onStarted();
    }

    public void onStarted() {

    }
    /*@Override
    public void onTextMessage(BotMessages.TextMessage tm) {
        if (tm.getText().startsWith("hello")) {
            requestSendTextMessage(
                    tm.getSender().asOutPeer(),
                    nextRandomId(),
                    "Hi, " + getUser(tm.getSender().getId()).getName() + "!");
        } else if (tm.getText().startsWith("last")) {
            Future<Optional<String>> future = msgsKv
                    .get("last", timeout);

            future.foreach(proc(s -> {
                String msg = s
                        .map(text -> "Last message I received was: " + text)
                        .orElse("I'm alone :'(");
                requestSendTextMessage(tm.getSender().asOutPeer(), nextRandomId(), msg);
            }), context().dispatcher());
        } else {
            requestSendTextMessage(tm.getSender().asOutPeer(), nextRandomId(), "Please, say hello");
        }

        msgsKv.upsert("last", tm.getText(), timeout);
    }*/

    @Override
    public void onMessage(Message msg) {
        //System.out.println(((TextMessage)msg.message()).text());
        if (msg.message()!=null && msg.message() instanceof TextMessage){
            String text = ((TextMessage)msg.message()).text();
            String senderName = getUser(msg.sender().id()).name();
            if (text.startsWith("hello")) {
                requestSendMessage(msg.sender().asOutPeer(), nextRandomId(), new TextMessage("Hey," + senderName + "!"));
            }else{
                try {

                    String weatherInfo = text + ",";
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

                    requestSendMessage(msg.sender().asOutPeer(), nextRandomId(), new TextMessage(weatherInfo));
                }catch (Exception e) {
                    e.printStackTrace();
                    requestSendMessage(msg.sender().asOutPeer(), nextRandomId(), new TextMessage("请正确输入城市名称"));
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
        //System.out.println("----------");

    }




}