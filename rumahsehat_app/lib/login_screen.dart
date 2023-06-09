import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:rumahsehat_app/providers/auth.dart';
import 'package:rumahsehat_app/signup_screen.dart';

class LoginScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => StartState();
}

class StartState extends State<LoginScreen> {
  TextEditingController usernameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return initWidget();
  }

  initWidget() {
    return Scaffold(
        body: SingleChildScrollView(
            child: Column(
      children: [
        Container(
          height: 300,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.only(bottomLeft: Radius.circular(90)),
            color: Color.fromARGB(255, 119, 176, 233),
            gradient: LinearGradient(
              colors: const [
                (Color.fromARGB(255, 138, 198, 224)),
                Color.fromARGB(255, 75, 183, 210)
              ],
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
            ),
          ),
          child: Center(
              child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.only(top: 50),
                child: Image.asset("images/19836-removebg-preview.png",
                    height: 200, width: 250),
              ),
              Container(
                margin: const EdgeInsets.only(right: 20, top: 20),
                alignment: Alignment.topCenter,
                // ignore: prefer_const_constructors
                child: Text(
                  "Rumah Sehat",
                  style: const TextStyle(fontSize: 20, color: Colors.white),
                ),
              )
            ],
          )),
        ),
        Container(
          alignment: Alignment.center,
          margin: const EdgeInsets.only(left: 20, right: 20, top: 70),
          padding: const EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Colors.grey[200],
            boxShadow: const [
              BoxShadow(
                  offset: Offset(0, 10),
                  blurRadius: 50,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextFormField(
              controller: usernameController,
              cursorColor: const Color.fromARGB(255, 10, 3, 0),
              decoration: const InputDecoration(
                icon: Icon(
                  Icons.email,
                  color: Color.fromARGB(255, 15, 4, 0),
                ),
                hintText: "Enter Username",
                enabledBorder: InputBorder.none,
                focusedBorder: InputBorder.none,
              ),
              validator: (text) {
                if (text == null || text.isEmpty) {
                  return 'username tidak boleh kosong';
                } else if (text != usernameController.text.toString()) {
                  return 'username salah';
                }
                return null;
              }),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 20),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Color(0xffEEEEEE),
            boxShadow: const [
              BoxShadow(
                  offset: Offset(0, 20),
                  blurRadius: 100,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextFormField(
              controller: passwordController,
              cursorColor: Color.fromARGB(255, 20, 5, 0),
              obscureText: true,
              decoration: InputDecoration(
                focusColor: Color.fromARGB(255, 17, 5, 0),
                icon: Icon(
                  Icons.vpn_key,
                  color: Color.fromARGB(255, 10, 3, 0),
                ),
                hintText: "Enter Password",
                enabledBorder: InputBorder.none,
                focusedBorder: InputBorder.none,
              ),
              validator: (text) {
                if (text == null || text.isEmpty) {
                  return 'password tidak boleh kosong';
                } else if (text != passwordController.text.toString()) {
                  return 'password salah';
                }
                return null;
              }),
        ),
        Container(
          margin: EdgeInsets.symmetric(horizontal: 30, vertical: 20),
          alignment: Alignment.centerRight,
          child: GestureDetector(
            onTap: () {
              // Write Click Listener Code Here
            },
            child: Text("Forget Password?"),
          ),
        ),
        GestureDetector(
          onTap: () => Provider.of<Authentication>(context, listen: false)
              .login(usernameController.text.toString(),
                  passwordController.text.toString()),
          child: Container(
            alignment: Alignment.center,
            margin: EdgeInsets.only(left: 20, right: 20, top: 70),
            padding: EdgeInsets.only(left: 20, right: 20),
            height: 54,
            decoration: BoxDecoration(
              gradient: LinearGradient(colors: [
                (Color.fromARGB(255, 138, 198, 224)),
                Color.fromARGB(255, 75, 183, 210)
              ], begin: Alignment.centerLeft, end: Alignment.centerRight),
              borderRadius: BorderRadius.circular(50),
              color: Colors.grey[200],
              boxShadow: [
                BoxShadow(
                    offset: Offset(0, 10),
                    blurRadius: 50,
                    color: Color(0xffEEEEEE)),
              ],
            ),
            child: Text(
              "LOGIN",
              style: TextStyle(color: Colors.white),
            ),
          ),
        ),
        Container(
          margin: EdgeInsets.only(top: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Don't Have Any Account?  "),
              GestureDetector(
                child: Text(
                  "Register Now",
                  style: TextStyle(color: Color.fromARGB(255, 1, 39, 152)),
                ),
                onTap: () {
                  // Write Tap Code Here.
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => SignUpScreen(),
                      ));
                },
              )
            ],
          ),
        )
      ],
    )));
  }
}
