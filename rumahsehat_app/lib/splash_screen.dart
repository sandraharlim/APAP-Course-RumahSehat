import 'dart:async';

import 'package:flutter/material.dart';

import 'login_screen.dart';

void main() {
  runApp(SplashScreen());
}

class SplashScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => StartState();
}

class StartState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    startTime();
  }

  startTime() async {
    var duration = Duration(seconds: 4);
    return Timer(duration, route);
  }

  route() {
    Navigator.pushReplacement(
        context, MaterialPageRoute(builder: (context) => LoginScreen()));
  }

  @override
  Widget build(BuildContext context) {
    return initWidget(context);
  }

  Widget initWidget(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
                color: Color.fromARGB(255, 138, 198, 224),
                gradient: LinearGradient(colors: const [
                  (Color.fromARGB(255, 138, 198, 224)),
                  Color.fromARGB(255, 75, 183, 210)
                ], begin: Alignment.topCenter, end: Alignment.bottomCenter)),
          ),
          Center(
            child: Container(
              child: Image.asset(
                "images/19836-removebg-preview.png",
                height: 200,
                width: 250,
              ),
            ),
          )
        ],
      ),
    );
  }
}
