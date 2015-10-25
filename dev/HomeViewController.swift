//
//  HomeViewController.swift
//  ActorApp
//
//  Created by 李金山 on 15/10/17.
//  Copyright © 2015年 Actor LLC. All rights reserved.
//

import Foundation
import UIKit
import Haneke

public class HomeViewController: AAViewController,UITableViewDelegate ,UITableViewDataSource {
    
    var tableView :UITableView?
    var bots:NSArray? = []
    public override init() {
        super.init(nibName: nil, bundle: nil)
        
        view.backgroundColor = appStyle.vcBackyardColor
    }
    public required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting UITabBarItem
        
        tabBarItem = UITabBarItem(title: "TabDiscover", img: "TabIconChats", selImage: "TabIconChatsHighlighted")
        
        
        navigationItem.title = "易致"
        
        self.tableView = UITableView(frame:self.view!.frame)
        self.tableView!.delegate = self
        self.tableView!.dataSource = self
        self.tableView!.registerClass(UITableViewCell.self, forCellReuseIdentifier:"cell")
        self.tableView!.rowHeight = 66
        
        self.view?.addSubview(self.tableView!)
    }
    
    public override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        let cache = Cache<JSON>(name: "bots_latest")
        let URL = NSURL(string: "https://app.ezing.cn/bots/bots/")!
        var error:NSError?
        let isReachable = URL.checkResourceIsReachableAndReturnError(&error)
        if(isReachable){
            cache.removeAll()
        }
        cache.fetch(URL: URL).onSuccess { JSON in
            self.bots = JSON.dictionary?["bots"] as? NSArray;
            self.tableView?.reloadData()
        }
    }
    
    public func tableView(tableView:UITableView, numberOfRowsInSection section:Int) -> Int
    {
        return (self.bots?.count)!;
    }
    
    public func tableView(tableView:UITableView, cellForRowAtIndexPath indexPath:NSIndexPath) -> UITableViewCell
    {
        let cell = UITableViewCell(style: UITableViewCellStyle.Subtitle,reuseIdentifier: "cell")
        let bot = self.bots?[indexPath.row] as! NSDictionary;
    
        let name = bot["name"] as! NSString
        cell.textLabel!.text = name as String
        
        let desc = bot["desc"] as! NSString
        cell.detailTextLabel!.text = desc as String
        cell.detailTextLabel?.numberOfLines = 0
    
        cell.detailTextLabel!.textColor = UIColor.darkGrayColor()
        
        let labeltext = cell.textLabel!.text as NSString?
        let title = labeltext!.substringToIndex(1)
        cell.imageView?.image = Placeholders.avatarPlaceholder(jint(indexPath.row),size: 44, title:title, rounded: true)

        return cell
    }
    
    
}
