//
//  RocketView.swift
//  iosApp
//
//  Created by imac on 31/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RocketView: View {
    @State var viewModel: RocketView.ViewModel

      var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("Rocket Launches")
//            .navigationBarItems(trailing:
//                Button("Reload") {
//                self.viewModel.loadLaunches(forceReload: true)
//            })
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarHidden(true)
      }

      private func listView() -> AnyView {
          switch viewModel.launches {
          case .loading:
              return AnyView(Text("Loading...").multilineTextAlignment(.center))
          case .result(let launches):
              return AnyView(List(launches) { launch in
                  RocketLaunchRow(rocketLaunch: launch)
              })
          case .error(let description):
              return AnyView(Text(description).multilineTextAlignment(.center))
          }
      }
}

extension RocketLaunch: Identifiable { }

extension RocketView {

    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }

    class ViewModel: ObservableObject {
        let sdk: SpaceXSDK
        @Published var launches = LoadableLaunches.loading

        init(sdk: SpaceXSDK) {
            self.sdk = sdk
            self.loadLaunches(forceReload: true)
        }

        func loadLaunches(forceReload: Bool) {
            self.launches = .loading
            sdk.getLaunches(completionHandler: { launches, error in
                if let launches = launches {
                    self.launches = .result(launches)
                } else {
                    self.launches = .error(error?.localizedDescription ?? "error")
                }
            })
        }
    }
}
