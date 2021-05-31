import SwiftUI
import shared
import AlertToast

let lightGreyColor = Color(red: 239.0/255.0, green: 243.0/255.0, blue: 244.0/255.0, opacity: 1.0)

struct ContentView: View {
    let greet = Greeting().greeting()
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var status: String = ""
    @State private var showToast = false
    
    @State private var selection: Int?
    
    @ObservedObject var viewModel: ContentView.ViewModel
    let sdk = SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory())
    
    var body: some View {
        NavigationView {
            VStack{
                Header()
                
                TextField("Enter your username", text:$username)
                    .padding()
                    .background(lightGreyColor)
                    .cornerRadius(5.0)
                    .padding(.bottom, 20)
                
                SecureField("Enter your password", text:$password)
                    .padding()
                    .background(lightGreyColor)
                    .cornerRadius(5.0)
                    .padding(.bottom, 20)

                NavigationLink(
                    destination: RocketView(viewModel: .init(sdk: sdk)),
                    tag: 1,
                    selection: self.$selection
                ) {
                    Text("")
                }
                Button(action:{
                    print(self.$username)
                    print(self.$password)
                    let loginCheck = viewModel.login(username: self.username, password: self.password)
                    status = loginCheck
                    if (status != "Login Failed") {
                        self.selection = 1
                    }
                    showToast.toggle()
                    
                }) {
                    ButtonLogin()
                }
            }
            .padding()
            .toast(isPresenting: $showToast){
                AlertToast(displayMode: .hud, type: .regular, title: status)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView(viewModel: .init(loginRepository: LoginRepository(dataSource: LoginDataSource())))
        }
    }
}

struct Header: View {
    var body: some View {
        Text("Login")
            .font(.largeTitle)
            .fontWeight(.semibold)
            .padding(.bottom, 20)
    }
}

struct ButtonLogin: View {
    var body: some View {
        return Text("LOGIN")
            .font(.headline)
            .foregroundColor(.white)
            .padding()
            .frame(width: 220, height: 60)
            .background(Color.blue)
            .cornerRadius(15.0)
    }
}

extension ContentView {
    class ViewModel: ObservableObject {
        
        let loginRepository: LoginRepository
        private var status: String = ""
        
        init(loginRepository: LoginRepository) {
            self.loginRepository = loginRepository
        }
        
        func login(username: String, password: String) -> String {
            
            if let result = loginRepository.login(username: username, password: password) as? ResultSuccess {
                status = "Hello \(result.data.name)"
            } else {
                status = "Login Failed"
            }
            return status
        }
    }
}
