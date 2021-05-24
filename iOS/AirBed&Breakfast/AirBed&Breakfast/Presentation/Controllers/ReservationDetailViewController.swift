//
//  ReservationDetailViewController.swift
//  AirBed&Breakfast
//
//  Created by 조중윤 on 2021/05/20.
//

import UIKit

protocol ReservationDetailViewControllerProtocol {
    func setContext(with description: String)
    func changeLocation()
    func changeDateRange(date: Date, isLowerDay: Bool)
    func changePriceRange()
    func changeNumberOfHeads()
}

class ReservationDetailViewController: UIViewController {
    
    enum Details {
        case location
        case date
        case price
        case numberOfHeads
    }
    
    @IBOutlet weak var reservationDetailTableView: UITableView!
    @IBOutlet weak var deleteCurrentDetailButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
    
    var lowerDate: Date?
    var upperDate: Date?
    var currentContext: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print("Reservation Detail view loaded")
        reservationDetailTableView.register(ReservationDetailTableViewCell.nib, forCellReuseIdentifier: ReservationDetailTableViewCell.reuseIdentifier)
        
        reservationDetailTableView.dataSource = self
        reservationDetailTableView.delegate = self
    }
    
    deinit {
        print("Reservation Detail View Has Been deinit")
    }
    
    @IBAction func deleteCurrentDetailButtonPressed(_ sender: UIButton) {
        switch currentContext {
        case String(describing: CalendarControlView.self):
            self.detailLabel(for: .date).text = ""
//            (parent as? SetUpViewController)?.calendarControlView?.clearCalendarView()
        default:
            return
        }
        
        self.deleteCurrentDetailButton.isEnabled = false
        self.nextButton.isEnabled = false
    }
    
    @IBAction func nextButtonPressed(_ sender: UIButton) {
        switch currentContext {
        case String(describing: CalendarControlView.self):
            (parent as? SetUpViewController)?.deinitializeCalendarControlView()
        default: break
        }
    }
    
    func detailLabel(for detail: Details) -> UILabel {
        var indexPath: IndexPath
        
        switch detail {
        case .location:
            indexPath = IndexPath(row: 0, section: 0)
        case .date:
            indexPath = IndexPath(row: 1, section: 0)
        case .price:
            indexPath = IndexPath(row: 2, section: 0)
        case .numberOfHeads:
            indexPath = IndexPath(row: 3, section: 0)
        }
        
        let cell = reservationDetailTableView.cellForRow(at: indexPath) as? ReservationDetailTableViewCell
        
        guard let safeCell = cell else { return UILabel()}
        return safeCell.detailContentsLabel
    }
    
}

extension ReservationDetailViewController: ReservationDetailViewControllerProtocol {
    func setContext(with description: String) {
        self.currentContext = description
    }
    
    func changeLocation() {
        print("Changed Location")
    }
    
    func changeDateRange(date: Date, isLowerDay: Bool) {
        let dateDetailLabel = detailLabel(for: .date)
        
        if isLowerDay {
            self.lowerDate = date
            self.upperDate = nil
            dateDetailLabel.text = "\(date.month)월 \(date.day)일"
            self.nextButton.isEnabled = false
        } else {
            self.upperDate = date
            dateDetailLabel.text = "\(dateDetailLabel.text!) ~ \(date.month)월 \(date.day)일"
            self.nextButton.isEnabled = true
        }
        
        self.deleteCurrentDetailButton.isEnabled = true
    }
    
    func changePriceRange() {
        //todo
    }
    
    func changeNumberOfHeads() {
        //todo
    }
    
}

extension ReservationDetailViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = reservationDetailTableView.dequeueReusableCell(withIdentifier: ReservationDetailTableViewCell.reuseIdentifier) as! ReservationDetailTableViewCell
        
        switch indexPath.row {
        case 0: cell.titleLabel.text = "위치"
        case 1: cell.titleLabel.text = "체크인/체크아웃"
        case 2: cell.titleLabel.text = "요금"
        case 3: cell.titleLabel.text = "인원"
        default: return cell
        }
        
        return cell
    }
}

extension ReservationDetailViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return self.reservationDetailTableView.frame.height/4
    }
}
