//
//  Copyright (c) 2014-2016 Actor LLC. <https://actor.im>
//

import Foundation

import ActorSDK

@objc public class AppDelegate : ActorApplicationDelegate {
    
    override init() {
        super.init()
        
        ActorSDK.sharedActor().inviteUrlHost = "repairpal.ezing.cn"
        ActorSDK.sharedActor().inviteUrlScheme = "repairpal"
        
        ActorSDK.sharedActor().style.searchStatusBarStyle = .Default
        
        // Enabling experimental features
        ActorSDK.sharedActor().enableExperimentalFeatures = true
        
        ActorSDK.sharedActor().enableCalls = true
        
        ActorSDK.sharedActor().enableVideoCalls = true
        
        // Setting Development Push Id
        ActorSDK.sharedActor().apiPushId = 771177
        
        ActorSDK.sharedActor().authStrategy = .PhoneEmail
        
        ActorSDK.sharedActor().endpoints = [
            "tls://actor.ezing.cn"
        ]
        
        ActorSDK.sharedActor().trustedKeys = [
            "3febe18c96630ace1d3afdf3de444a474f8f1a4d9cd5c2753289ce99efc67f17"
        ]
        
        // Creating Actor
        ActorSDK.sharedActor().createActor()
        
    }
    
    public override func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject : AnyObject]?) -> Bool {
        super.application(application, didFinishLaunchingWithOptions: launchOptions)
        
        ActorSDK.sharedActor().presentMessengerInNewWindow()
        
        return true;
    }
    
    public override func actorRootControllers() -> [UIViewController]? {
        return [AAContactsViewController(), AARecentViewController(), AASettingsViewController()]
    }
    
    public override func actorRootInitialControllerIndex() -> Int? {
        return 0
    }
}