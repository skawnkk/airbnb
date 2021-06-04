//
//  NetworkManager.swift
//  Airbnb
//
//  Created by Lia on 2021/05/20.
//

import Foundation
import Combine

enum NetworkError: Error {
    case BadURL
    case DecodingError
}

enum EndPoint {
    static let scheme = "http"
    static let host = "54.180.21.249"
    static let path = ""
    
    static func url(path: String) -> URL? {
        var components = URLComponents()
        
        components.scheme = EndPoint.scheme
        components.host = EndPoint.host
        components.path = "\(path)"
        
        return components.url
    }
}


protocol NetworkManageable {
    func get<T: Decodable>(type: T.Type, url: URL) -> AnyPublisher<T, Error>
    func post<T: Encodable, R: Decodable>(url: URL, data: T, result: R.Type) -> AnyPublisher<R, Error>
}


class NetworkManager {
    
    private let session: URLSession
    
    init(session: URLSession = .shared) {
      self.session = session
    }
    
}


extension NetworkManager: NetworkManageable {
    
    func get<T: Decodable>(type: T.Type, url: URL) -> AnyPublisher<T, Error> {
        
        return self.session.dataTaskPublisher(for: url)
            .tryMap { element -> Data in
                guard let httpResponse = element.response as? HTTPURLResponse,
                    httpResponse.statusCode == 200 else {
                    throw NetworkError.BadURL
                    }
                return element.data
            }
            .decode(type: T.self, decoder: JSONDecoder())
            .mapError({ (error) -> Error in
                return error
            })
            .eraseToAnyPublisher()
    }
    
    func post<T: Encodable, R: Decodable>(url: URL, data: T, result: R.Type) -> AnyPublisher<R, Error> {

        return Just(data)
            .encode(encoder: JSONEncoder())
            .mapError { error -> Error in
                print(error) ///사용자에게 에러 표시하는 부분 미구현
                return error
            }
            .map { data -> URLRequest in
                var request = URLRequest(url: url)
                request.httpMethod = "post"
                request.httpBody = data
                request.addValue("application/json", forHTTPHeaderField: "Content-Type")
                request.setValue(String(data.count), forHTTPHeaderField: "Content-Length")
                
                return request
            }
            .flatMap { request in
                return self.session.dataTaskPublisher(for: request)
                    .tryMap { element -> R in
                        guard let httpResponse = element.response as? HTTPURLResponse,
                              httpResponse.statusCode == 200 else {
                            throw NetworkError.BadURL
                        }
                        let result = try JSONDecoder().decode(R.self, from: element.data)
                        return result
                    }
            }
            .eraseToAnyPublisher()
    }
    
}
