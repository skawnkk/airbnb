//
//  SearchCoordinator.swift
//  airbnb
//
//  Created by 박혜원 on 2021/05/24.
//

import UIKit

class SearchCoodinator : NSObject, ChildCoordinator {
    
    var childCoordinators = [ChildCoordinator]()
    weak var parentCoordinator: MainCoordinator?
    var navigationController: UINavigationController
    
    override init(){
        let search = SearchViewController.instantiate()
        self.navigationController = UINavigationController(rootViewController: search)
        navigationController.tabBarItem = UITabBarItem(title: "검색", image: UIImage(named: "search"), tag: 0)
        super.init()
        search.coordinator = self
    }

    func start(){
        let travelListViewController = TravelListViewController.instantiate()
        travelListViewController.coordinator = self
        navigationController.navigationItem.backButtonTitle = "뒤로"
        navigationController.pushViewController(travelListViewController, animated: true)
    }
    
    func choosePlace(to place : String){
        let containerViewController = ContainerViewController.instantiate()
        containerViewController.coordinator = self
        containerViewController.placeName = place
        navigationController.navigationItem.backButtonTitle = "뒤로"
        navigationController.pushViewController(containerViewController, animated: true)
    }
    
    func searchDidFinish(){
        parentCoordinator?.childDidFinish(self)
    }
    
}
