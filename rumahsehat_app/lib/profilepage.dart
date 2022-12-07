import 'dart:convert';
//import 'dart:html';
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
  String token = "";

  Future<void> loginPasien() async {
    const urlPost = "http://localhost:8080/login";
    String username = "pasien3";
    String password = "Pasienpasien3";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({"username": username, "password": password}),
          headers: {"Authorization": token});
      Map<String, String> headers = response.headers;
      String? jwtToken = headers["authorization"];

      if (jwtToken != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("berhasil login dengan username " + username)),
        );
        setState(() {
          token = jwtToken;
          print(token);
          // token = Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkb2t0ZXIxIiwiZXhwIjoxNjcxMjcwNjU4fQ.RlSo0uZnTibReemyzony7An7Na9_ajiqxJPid9l4BlZdAg30q5ODxQaYZsRE3sMjNOhBo_Ai7LhP2SSbPoJQew
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
    String url = 'http://localhost:8080/api/pasien/profile';

    final response = await http.get(Uri.parse(url), headers: <String, String>{
      "Authorization": token,
      "Content-Type": "application/json;charset=UTF-8"
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
                        padding: const EdgeInsets.only(bottom: 27.0),
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
            Padding(
              padding: EdgeInsets.all(12.0),
              child: ElevatedButton(
                child: const Text(
                  "Refresh Saldo",
                  style: TextStyle(fontSize: 12),
                ),
                onPressed: () => setState(() {
                  futurePasien = fetchPasien();
                }),
              ),
            ),
            Padding(
              padding: EdgeInsets.all(12.0),
              child: ElevatedButton(
                child: const Text(
                  "Top up saldo",
                  style: TextStyle(fontSize: 12),
                ),
                onPressed: () {
                  Navigator.of(context).push(
                    new MaterialPageRoute(
                        builder: (_) => new FormSaldo(
                              token: token,
                            )),
                  );
                },
              ),
            )
          ],
        ),
      ),
    );
  }
}
