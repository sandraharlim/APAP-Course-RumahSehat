import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/login_screen.dart';

class SignUpScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => InitState();
}

class Pasien {
  late String email;
  late String nama;
  late String password;
  // String role = "Pasien";
  late String username;
  // int saldo = 0;
  late int umur;

  Pasien(
      {required this.email,
      required this.nama,
      required this.password,
      // required this.role,
      required this.username,
      // required this.saldo,
      required this.umur});

  factory Pasien.fromJson(Map<String, dynamic> json) {
    return Pasien(
        email: json['email'],
        nama: json['nama'],
        password: json['password'],
        username: json['username'],
        umur: json['umur']);
  }
}

class InitState extends State<SignUpScreen> {
  TextEditingController usernameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController namaController = TextEditingController();
  TextEditingController umurController = TextEditingController();

  _register() async {
    var data = {
      'username': usernameController.text,
      'password': passwordController.text,
      'email': emailController.text,
      'nama': namaController.text,
      'umur': umurController.text,
    };

    // final String urlPost = "https://apap-061.cs.ui.ac.id/api/pasien/sign-up"
    final String urlPost = "http://localhost:8080/api/pasien/sign-up";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode(data),
          headers: {
            "content-type": "application/json",
            "accept": "application/json"
          });
      if (response.statusCode == 200) {
        showDialog<String>(
            context: context,
            builder: (BuildContext context) => AlertDialog(
                  title: const Text("Registration Status"),
                  content: const Text('Success!!'),
                  actions: <Widget>[
                    TextButton(
                        onPressed: (() => Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => new LoginScreen()))),
                        child: const Text("Back to Login Page")),
                  ],
                ));
      }
    } catch (err) {
      print(err);
    }
  }

  @override
  Widget build(BuildContext context) => initWidget();

  Widget initWidget() {
    return Scaffold(
        body: SingleChildScrollView(
            child: Column(
      children: [
        Container(
          height: 250,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.only(bottomLeft: Radius.circular(90)),
            color: Color.fromARGB(255, 119, 176, 233),
            gradient: LinearGradient(
              colors: [
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
                    height: 150, width: 220),
              ),
              Container(
                margin: EdgeInsets.only(right: 20, top: 20),
                alignment: Alignment.center,
                child: Text(
                  "Rumah Sehat",
                  style: TextStyle(fontSize: 20, color: Colors.white),
                ),
              )
            ],
          )),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 70),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Colors.grey[200],
            boxShadow: [
              BoxShadow(
                  offset: Offset(0, 10),
                  blurRadius: 50,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextField(
            controller: namaController,
            cursorColor: Color.fromARGB(255, 15, 4, 1),
            decoration: InputDecoration(
              icon: Icon(
                Icons.people_alt_outlined,
                color: Color.fromARGB(255, 7, 2, 0),
              ),
              hintText: "Full Name",
              enabledBorder: InputBorder.none,
              focusedBorder: InputBorder.none,
            ),
          ),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 20),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Colors.grey[200],
            boxShadow: [
              BoxShadow(
                  offset: Offset(0, 10),
                  blurRadius: 50,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextField(
            controller: usernameController,
            cursorColor: Color.fromARGB(255, 9, 3, 0),
            decoration: InputDecoration(
              icon: Icon(
                Icons.person,
                color: Color.fromARGB(255, 8, 2, 0),
              ),
              hintText: "Username",
              enabledBorder: InputBorder.none,
              focusedBorder: InputBorder.none,
            ),
          ),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 20),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Color(0xffEEEEEE),
            boxShadow: [
              BoxShadow(
                  offset: Offset(0, 20),
                  blurRadius: 100,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextField(
            obscureText: true,
            controller: passwordController,
            cursorColor: Color.fromARGB(255, 12, 4, 0),
            decoration: InputDecoration(
              focusColor: Color.fromARGB(255, 6, 2, 0),
              icon: Icon(
                Icons.vpn_key,
                color: Color.fromARGB(255, 5, 1, 0),
              ),
              hintText: "Password",
              enabledBorder: InputBorder.none,
              focusedBorder: InputBorder.none,
            ),
          ),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 20),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Color(0xffEEEEEE),
            boxShadow: [
              BoxShadow(
                  offset: Offset(0, 20),
                  blurRadius: 100,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextField(
            controller: emailController,
            cursorColor: Color.fromARGB(255, 16, 5, 0),
            decoration: InputDecoration(
              focusColor: Color.fromARGB(255, 5, 2, 0),
              icon: Icon(
                Icons.email,
                color: Color.fromARGB(255, 6, 2, 0),
              ),
              hintText: "Email",
              enabledBorder: InputBorder.none,
              focusedBorder: InputBorder.none,
            ),
          ),
        ),
        Container(
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 20, right: 20, top: 20),
          padding: EdgeInsets.only(left: 20, right: 20),
          height: 54,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(50),
            color: Color(0xffEEEEEE),
            boxShadow: [
              BoxShadow(
                  offset: Offset(0, 20),
                  blurRadius: 100,
                  color: Color(0xffEEEEEE)),
            ],
          ),
          child: TextField(
            controller: umurController,
            cursorColor: Color.fromARGB(255, 16, 5, 0),
            keyboardType: TextInputType.number,
            inputFormatters: <TextInputFormatter>[
              FilteringTextInputFormatter.digitsOnly
            ],
            decoration: InputDecoration(
              focusColor: Color.fromARGB(255, 5, 2, 0),
              icon: Icon(
                Icons.cake,
                color: Color.fromARGB(255, 6, 2, 0),
              ),
              hintText: "Umur",
              enabledBorder: InputBorder.none,
              focusedBorder: InputBorder.none,
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            if (namaController.text == '' ||
                usernameController.text == '' ||
                passwordController.text == '' ||
                emailController.text == '' ||
                umurController.text == '') {
              ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("Mohon lengkapi data anda!")));
            } else {
              _register();
            }
          },
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
              "REGISTER",
              style: TextStyle(color: Colors.white),
            ),
          ),
        ),
        Container(
          margin: EdgeInsets.only(top: 10, bottom: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Have Already Member?  "),
              GestureDetector(
                child: Text(
                  "Login Now",
                  style: TextStyle(color: Color.fromARGB(255, 6, 69, 230)),
                ),
                onTap: () {
                  // Write Tap Code Here.
                  Navigator.pop(context);
                },
              )
            ],
          ),
        )
      ],
    )));
  }
}
