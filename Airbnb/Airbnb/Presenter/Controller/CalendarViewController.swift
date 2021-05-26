import UIKit
import FSCalendar

class CalendarViewController: UIViewController {
    
    @IBOutlet var infoTableVIew: UITableView!
    @IBOutlet var backButton: UIButton!
    @IBOutlet var calendarView: FSCalendar!
    
    @IBOutlet var nextButton: UIButton!
    @IBOutlet var skipDeleteButton: UIButton!
    private var locationInfo:String?
    private var dateStroage:[String] = []
    private let viewModel = CalendarViewModel()
    
    private lazy var dateFormatter: DateFormatter = {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        return dateFormatter
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupMainView()
        bind()
    }
    
    func setupLocation(_ info:String) {
        viewModel.add(location: info)
    }
    
    private func bind() {
        viewModel.accommodationData()
            .drive(infoTableVIew.rx.items(cellIdentifier: CalendarCell.identifier, cellType: CalendarCell.self)) { _, data, cell in
                cell.configure(data)
            }.disposed(by: rx.disposeBag)
        
        infoTableVIew.rx.setDelegate(self).disposed(by: rx.disposeBag)
    }
}

private extension CalendarViewController {
    
    private func setupMainView() {
        setupCalendarView()
        setupButton()
    }
    
    private func setupCalendarView() {
        calendarView.allowsMultipleSelection = true
        calendarView.swipeToChooseGesture.isEnabled = true
        calendarView.appearance.headerDateFormat = "YYYY년 M월"
        calendarView.appearance.headerMinimumDissolvedAlpha = 0.0
        calendarView.appearance.headerTitleColor = UIColor.black
        calendarView.appearance.selectionColor = UIColor.systemGray3
        calendarView.appearance.todayColor = UIColor.black
        calendarView.appearance.weekdayTextColor = UIColor.systemGray
        calendarView.scrollDirection = .vertical
        calendarView.delegate = self
        calendarView.dataSource = self
    }
    
    private func setupButton() {
        backButton.rx.tap
            .subscribe(onNext: { [weak self] _ in
                self?.dismiss(animated: true, completion: nil)
            }).disposed(by: rx.disposeBag)
    }
}

extension CalendarViewController: FSCalendarDelegate, FSCalendarDataSource, FSCalendarDelegateAppearance {
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        dateStroage.append(dateFormatter.string(from: date))
        print(dateStroage)
    }
    
    func calendar(_ calendar: FSCalendar, didDeselect date: Date, at monthPosition: FSCalendarMonthPosition) {
        dateStroage.indices.forEach {
            if dateStroage[$0] == dateFormatter.string(from: date) {
                dateStroage.remove(at: $0)
            }
        }
        print(dateStroage)
    }
    
    func calendar(_ calendar: FSCalendar, shouldSelect date: Date, at monthPosition: FSCalendarMonthPosition) -> Bool {
        if date.compare(Date()) == .orderedAscending {
            return false
        } else {
            return true
        }
    }
    
    func calendar(_ calendar: FSCalendar, appearance: FSCalendarAppearance, titleDefaultColorFor date: Date) -> UIColor? {
        if date.compare(Date()) == .orderedAscending {
            return .systemGray3
        } else {
            return .black
        }
    }
}

extension CalendarViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableView.frame.height/4
    }
}
