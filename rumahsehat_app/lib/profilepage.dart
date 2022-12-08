import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/main.dart';
import 'package:rumahsehat_app/models/pasienmodel.dart';
import 'package:rumahsehat_app/saldoform.dart';

class ProfilePageState extends StatefulWidget {
  const ProfilePageState({Key? key}) : super(key: key);

  @override
  ProfilePage createState() => ProfilePage();
}

Future<Pasien> fetchPasien() async {
  String token = "uuid-1";
  String url = 'http://127.0.0.1:8080/api/pasien/profile';

  final response = await http.get(Uri.parse(url),
      headers: {"Authorization": token, "Content-Type": "application/json"});
  if (response.statusCode == 200) {
    var data = json.decode(response.body);
    print(data);
    return Pasien.fromJson(jsonDecode(response.body));
  } else {
    throw Exception("Failed to fetch pasien data");
  }
}

Future<Pasien> updateSaldo(int saldo) async {
  String token = "uuid-1";
  String url = 'http://127.0.0.1:8080/api/pasien/profile/update-saldo';
  final response = await http.put(
    Uri.parse(url),
    headers: {
      "Authorization": token,
      'Content-Type': 'application/json; charset=UTF-8',
      "Access-Control_Allow_Origin": "*",
    },
    body: jsonEncode(<String, String>{
      'saldo': saldo.toString(),
    }),
  );

  if (response.statusCode == 200) {
    return Pasien.fromJson(jsonDecode(response.body));
  } else {
    throw Exception('Failed Top Up Saldo');
  }
}

class ProfilePage extends State<ProfilePageState> {
  final TextEditingController saldoController = TextEditingController();
  late Future<Pasien> futurePasien;

  @override
  void initState() {
    super.initState();
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
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Padding(
                    padding: const EdgeInsets.only(bottom: 27.0),
                    child: TextField(
                      enabled: false,
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(bottom: 4),
                          labelText: "Nama",
                          floatingLabelBehavior: FloatingLabelBehavior.always,
                          hintText: snapshot.data!.nama,
                          hintStyle: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          )),
                    ),
                  );
                } else if (snapshot.hasError) {
                  return Text('${snapshot.error}');
                }
                return const CircularProgressIndicator();
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Padding(
                    padding: const EdgeInsets.only(bottom: 27.0),
                    child: TextField(
                      enabled: false,
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(bottom: 3),
                          labelText: "Username",
                          floatingLabelBehavior: FloatingLabelBehavior.always,
                          hintText: snapshot.data!.username,
                          hintStyle: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          )),
                    ),
                  );
                } else if (snapshot.hasError) {
                  return Text('${snapshot.error}');
                }
                return const CircularProgressIndicator();
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Padding(
                    padding: const EdgeInsets.only(bottom: 27.0),
                    child: TextField(
                      enabled: false,
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(bottom: 3),
                          labelText: "Email",
                          floatingLabelBehavior: FloatingLabelBehavior.always,
                          hintText: snapshot.data!.email,
                          hintStyle: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          )),
                    ),
                  );
                } else if (snapshot.hasError) {
                  return Text('${snapshot.error}');
                }
                return const CircularProgressIndicator();
              },
            ),
            FutureBuilder<Pasien>(
              future: futurePasien,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Padding(
                    padding: const EdgeInsets.only(bottom: 27.0),
                    child: TextField(
                      enabled: false,
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(bottom: 3),
                          labelText: "Saldo",
                          floatingLabelBehavior: FloatingLabelBehavior.always,
                          hintText: (snapshot.data!.saldo).toString(),
                          hintStyle: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          )),
                    ),
                  );
                } else if (snapshot.hasError) {
                  return Text('${snapshot.error}');
                }
                return const CircularProgressIndicator();
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
                  Navigator.of(context)
                      .push(
                        new MaterialPageRoute(builder: (_) => new FormSaldo()),
                      )
                      .then((val) => {fetchPasien()});
                },
              ),
            )
          ],
        ),
      ),
    );
  }
}
