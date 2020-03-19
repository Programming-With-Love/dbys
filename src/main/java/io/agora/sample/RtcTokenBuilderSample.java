package io.agora.sample;

import io.agora.media.RtcTokenBuilder;
import io.agora.media.RtcTokenBuilder.Role;

public class RtcTokenBuilderSample {
    public static String appId = "02e8df44f24e4da5b2e17ef1d8b755bd";
    public static String appCertificate = "";
    static String channelName = "7d72365eb983485397e3e3f9d460bdda";
    static String userAccount = "2082341273";
    static int uid = 2082341273;
    static int expirationTimeInSeconds = 3600; 

    public static void main(String[] args) throws Exception {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
        String result = token.buildTokenWithUserAccount(appId, appCertificate,  
        		 channelName, userAccount, Role.Role_Publisher, timestamp);
        System.out.println(result);
        
        result = token.buildTokenWithUid(appId, appCertificate,  
       		 channelName, uid, Role.Role_Publisher, timestamp);
        System.out.println(result);
    }

}
