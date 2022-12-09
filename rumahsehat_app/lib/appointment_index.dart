import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/appointment_model.dart';
import '../models/appointment_pasien_card.dart';

import 'navbar.dart';

class AppointmentViewAll extends StatefulWidget {
  AppointmentViewAll({Key? key}) : super(key: key);

  @override
  _AppointmentViewAllState createState() => _AppointmentViewAllState();
}

class _AppointmentViewAllState extends State<AppointmentViewAll> {
  List<Appointment> listAppointment = [];
  String token_prefix = "Bearer ";
  String token = "";
  // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXNpZW4yIiwiZXhwIjoxNjcxMjQ0MTc3fQ.4vuSC4zLB67MMvnNSN-s36ELL2iVRO5aUl3DNRlXKWrNWCErEjFRJZQO1zzXSSBYuoAXtgsCx0XhpIjvYLbLRA";

  Future<void> loginPasien() async {
    // const urlPost = "https://apap-061.cs.ui.ac.id/authenticate";
    const urlPost = "http://10.0.2.2:8080/authenticate";
    String username = "pasien1";
    String password = "Qwerty123";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({"username": username, "password": password}),
          headers: {
            "content-type": "application/json",
            "accept": "application/json"
          });
      Map<String, dynamic> extractedData = jsonDecode(response.body);
      String? jwtToken = extractedData["jwttoken"];

      if (jwtToken != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("berhasil login dengan username " + username)),
        );
        setState(() {
          token = jwtToken;
        });
        getData();
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Username atau password salah")),
        );
      }
    } catch (p) {
      print(p);
    }
  }

  Future<void> loginOther() async {
    const urlPost = "http://10.0.2.2:8080/authenticate";
    String username = "apoteker1";
    String password = "Qwerty123";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({"username": username, "password": password}),
          headers: {
            "content-type": "application/json",
            "accept": "application/json"
          });
      Map<String, dynamic> extractedData = jsonDecode(response.body);
      String? jwtToken = extractedData["jwttoken"];

      if (jwtToken != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("berhasil login dengan username " + username)),
        );
        setState(() {
          token = jwtToken;
          listAppointment = [];
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Username atau password salah")),
        );
      }
    } catch (p) {
      print(p);
    }
  }

  Future<void> getData() async {
    // String url = "https://apap-061.cs.ui.ac.id/api/appointment/viewall";
    String url = "http://10.0.2.2:8080/api/appointment/viewall";

    try {
      final response = await http.get(Uri.parse(url), headers: {
        "Authorization": (token_prefix + token)
        // "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXNpZW4yIiwiZXhwIjoxNjcxMjQ4MDYzfQ.98CbaphAN-6a9bskzMe6oiLBTG3wTuCXg3vA8vfcI6r0n7vle3UT4M5iu7VkGvyl0DfDcNUwrTtecijSYvM-Fg"
      });

      List<dynamic> data = jsonDecode(response.body);
      print(data);

      List<Appointment> listAppointmentFromServer = [];
      for (var i = 0; i < data.length; i++) {
        Appointment appt = Appointment.fromJson((data[i]));
        listAppointmentFromServer.add(appt);
      }

      setState(() {
        listAppointment = listAppointmentFromServer;
      });
    } catch (p) {
      // rolenya salah
      print(p);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Anda bukan pasien.")),
      );
    }
  }

  @override
  void initState() {
    loginPasien();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('View All Appointment'),
      ),
      drawer: const NavigationDrawer(),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 0, 15),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Container(
                  padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                  child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Padding(
                          padding: EdgeInsets.only(right: 20.0),
                          child: ElevatedButton(
                            child: const Text(
                              "Login As Pasien",
                            ),
                            onPressed: () {
                              loginPasien();
                            },
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(right: 0.0),
                          child: ElevatedButton(
                            child: const Text(
                              "Login As Other Role",
                            ),
                            onPressed: () {
                              loginOther();
                            },
                          ),
                        ),
                      ])),
              Container(
                padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                child: ElevatedButton(
                  onPressed: () {
                    setState(() {
                      listAppointment = [];
                    });
                    getData();
                  },
                  child: const Text('Refresh Data'),
                  style: ElevatedButton.styleFrom(
                      primary: Colors.blue,
                      onPrimary: Colors.white,
                      shape: const RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(5)),
                      )),
                ),
              ),
              Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: listAppointment
                    .map((appointment) => AppointmentPasienCard(appointment))
                    .toList(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
