import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/main.dart';
import 'package:rumahsehat_app/models/pasienmodel.dart';
import 'package:rumahsehat_app/saldoform.dart';

class ProfilePageState extends StatefulWidget {
  // final String jwtToken;
  const ProfilePageState({
    Key? key,
    // required this.jwtToken,
  }) : super(key: key);

  @override
  ProfilePage createState() => ProfilePage();
}

class ProfilePage extends State<ProfilePageState> {
  // late String jwtToken;
  late Future<Pasien> futurePasien;
  String token_prefix = "Bearer ";
  String token = "";

  Future<void> loginPasien() async {
    // const urlPost = "https://apap-061.cs.ui.ac.id/authenticate";
    const urlPost = "http://localhost:8080/authenticate";
    String username = "pasien20";
    String password = "Pasienpasien20";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({"username": username, "password": password}),
          headers: {
            "content-type": "application/json",
            "accept": "application/json"
          });
      Map<String, dynamic> extractedData = jsonDecode(response.body);
      String? jwtToken = extractedData["jwttoken"];
      print(jwtToken);
      if (jwtToken != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("berhasil login dengan username " + username)),
        );
        setState(() {
          token = jwtToken;
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

  Future<Pasien> fetchPasien() async {
    // String url = 'https://apap-061.cs.ui.ac.id/api/pasien/profile';
    String url = 'http://localhost:8080/api/pasien/profile';

    final response = await http.get(Uri.parse(url), headers: <String, String>{
      "Authorization": (token_prefix + token),
    });
    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      print(data);
      return Pasien.fromJson(jsonDecode(response.body));
    } else {
      throw Exception("Failed to fetch pasien data");
    }
  }

  @override
  void initState() {
    loginPasien();
    futurePasien = fetchPasien();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        elevation: 1,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.black,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: Container(
        padding: EdgeInsets.only(left: 16, top: 25, right: 16),
        child: ListView(
          children: [
            Text(
              "Your Profile",
              style: TextStyle(fontSize: 25, fontWeight: FontWeight.w500),
            ),
            SizedBox(
              height: 15,
            ),
            Center(
              child: Stack(
                children: [
                  Container(
                    width: 130,
                    height: 130,
                    decoration: BoxDecoration(
                        border: Border.all(
                            width: 4,
                            color: Theme.of(context).scaffoldBackgroundColor),
                        boxShadow: [
                          BoxShadow(
                              spreadRadius: 2,
                              blurRadius: 10,
                              color: Colors.black.withOpacity(0.2),
                              offset: Offset(0, 10))
                        ],
                        shape: BoxShape.circle,
                        image: DecorationImage(
                            fit: BoxFit.cover,
                            image: NetworkImage(
                                "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg"))),
                  ),
                ],
              ),
            ),
            SizedBox(
              height: 35,
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, AsyncSnapshot<Pasien> snapshot) {
                switch (snapshot.connectionState) {
                  case ConnectionState.waiting:
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  default:
                    if (snapshot.hasData) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 27.0),
                        child: TextField(
                          enabled: false,
                          decoration: InputDecoration(
                              contentPadding: EdgeInsets.only(bottom: 4),
                              labelText: "Nama",
                              floatingLabelBehavior:
                              FloatingLabelBehavior.always,
                              hintText: snapshot.data!.nama,
                              hintStyle: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.black,
                              )),
                        ),
                      );
                    } else {
                      return Text('Error: ${snapshot.error}');
                    }
                }
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, AsyncSnapshot<Pasien> snapshot) {
                switch (snapshot.connectionState) {
                  case ConnectionState.waiting:
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  default:
                    if (snapshot.hasData) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 27.0),
                        child: TextField(
                          enabled: false,
                          decoration: InputDecoration(
                              contentPadding: EdgeInsets.only(bottom: 4),
                              labelText: "Username",
                              floatingLabelBehavior:
                              FloatingLabelBehavior.always,
                              hintText: snapshot.data!.username,
                              hintStyle: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.black,
                              )),
                        ),
                      );
                    } else {
                      return Text('Error: ${snapshot.error}');
                    }
                }
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, AsyncSnapshot<Pasien> snapshot) {
                switch (snapshot.connectionState) {
                  case ConnectionState.waiting:
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  default:
                    if (snapshot.hasData) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 27.0),
                        child: TextField(
                          enabled: false,
                          decoration: InputDecoration(
                              contentPadding: EdgeInsets.only(bottom: 4),
                              labelText: "Email",
                              floatingLabelBehavior:
                              FloatingLabelBehavior.always,
                              hintText: snapshot.data!.email,
                              hintStyle: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.black,
                              )),
                        ),
                      );
                    } else {
                      return Text('Error: ${snapshot.error}');
                    }
                }
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, AsyncSnapshot<Pasien> snapshot) {
                switch (snapshot.connectionState) {
                  case ConnectionState.waiting:
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  default:
                    if (snapshot.hasData) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 5.0),
                        child: TextField(
                          enabled: false,
                          decoration: InputDecoration(
                              contentPadding: EdgeInsets.only(bottom: 4),
                              labelText: "Saldo",
                              floatingLabelBehavior:
                              FloatingLabelBehavior.always,
                              hintText: (snapshot.data!.saldo).toString(),
                              hintStyle: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Colors.black,
                              )),
                        ),
                      );
                    } else {
                      return Text('Error: ${snapshot.error}');
                    }
                }
              },
            ),
            GestureDetector(
              onTap: () => setState(() {
                futurePasien = fetchPasien();
              }),
              child: Container(
                alignment: Alignment.center,
                margin: EdgeInsets.only(left: 20, right: 20, top: 40),
                padding: EdgeInsets.only(left: 20, right: 20),
                height: 40,
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
                  "Refresh Saldo",
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
            GestureDetector(
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => new FormSaldo(token: token)));
              },
              child: Container(
                alignment: Alignment.center,
                margin:
                EdgeInsets.only(left: 20, right: 20, top: 10, bottom: 20),
                padding: EdgeInsets.only(left: 20, right: 20),
                height: 40,
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
                  "Top up saldo",
                  style: TextStyle(color: Colors.white),
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
