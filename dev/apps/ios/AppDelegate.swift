//
//  Copyright (C) 2014-2015 Actor LLC. <https://actor.im>
//

import Foundation

import Fabric
import Crashlytics
import ActorSDK

@objc public class AppDelegate : ActorApplicationDelegate {
    
    override init() {
        super.init()
        
        // Even when Fabric/Crashlytics not configured
        // this method doesn't crash
        Fabric.with([Crashlytics.self()])
        
        ActorSDK.sharedActor().inviteUrlHost = "app.ezing.cn"
        ActorSDK.sharedActor().inviteUrlScheme = "ezing"
        
        ActorSDK.sharedActor().style.searchStatusBarStyle = .Default
        
        ActorSDK.sharedActor().endpoints = [
            "tls://actor.ezing.cn",
            "tls://actor.ezingsoft.com"
        ]
        
        // Creating Actor
        ActorSDK.sharedActor().createActor()
    }
    
    public override func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject : AnyObject]?) -> Bool {
        super.application(application, didFinishLaunchingWithOptions: launchOptions)
        
        ActorSDK.sharedActor().presentMessengerInNewWindow()
        
        return true;
    }
}