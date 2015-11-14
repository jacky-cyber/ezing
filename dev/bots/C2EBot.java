package im.actor;

import im.actor.botkit.RemoteBot;
import im.actor.bots.BotMessages;
import im.actor.bots.BotMessages.TextMessage;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Created by rikinzan on 15/11/9.
 */
public class C2EBot extends RemoteBot {
    public C2EBot(String token, String endpoint) {
        super(token, "ws://54.223.205.123:9090");
    }

    @Override
    public void onMessage(BotMessages.Message msg) {
        System.out.println(((TextMessage) msg.message()).text());
        String clientid="eIofOTxRG2e2OOyuGl5nnIlQ";
        if (msg.message()!=null && msg.message() instanceof TextMessage) {
            String text = ((TextMessage) msg.message()).text();
            String senderName = getUser(msg.sender().id()).name();
            try {

                String transResult = "";
                String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=eIofOTxRG2e2OOyuGl5nnIlQ&q="+text+"&from=auto&to=auto";
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);

                // Create a response handler
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpclient.execute(httpget, responseHandler);
                JSONObject jsonobj = new JSONObject(responseBody);
                System.out.println(jsonobj);
                if(jsonobj!=null){
                    JSONArray results = jsonobj.getJSONArray("trans_result");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        requestSendMessage(msg.sender(), nextRandomId(), new TextMessage(result.getString("dst")));
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRawUpdate(BotMessages.RawUpdate rawUpdate) {
        System.out.println("----------");
    }
}
