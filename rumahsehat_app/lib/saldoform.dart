import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/models/pasienmodel.dart';
import 'package:rumahsehat_app/profilepage.dart';

class FormSaldo extends StatefulWidget {
  final String token;
  const FormSaldo({Key? key, required this.token}) : super(key: key);

  @override
  FormSaldoState createState() => FormSaldoState();
}

class FormSaldoState extends State<FormSaldo> {
  final TextEditingController saldoController = TextEditingController();
  late Future<Pasien>? _futurePasien;
  late String jwtToken;
  String token_prefix = "Bearer ";

  Future<Pasien> fetchPasien() async {
    // String url = "https://apap-061.cs.ui.ac.id/api/pasien/profile";

    String url = 'http://10.0.2.2:8080/api/pasien/profile';

    final response = await http.get(Uri.parse(url), headers: <String, String>{
      "Authorization": (token_prefix + jwtToken),
      // "Content-Type": "application/json;charset=UTF-8"
    });
    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      print(data);
      return Pasien.fromJson(jsonDecode(response.body));
    } else {
      throw Exception("Failed to fetch pasien data");
    }
  }

  void updateSaldo(int saldo) async {
    // String url = "https://apap-061.cs.ui.ac.id/api/pasien/profile/update-saldo";
    String url = 'http://10.0.2.2:8080/api/pasien/profile/update-saldo';
    final response = await http.put(
      Uri.parse(url),
      headers: {
        "Authorization": (token_prefix + jwtToken),
        'Content-Type': 'application/json; charset=UTF-8',
        "Access-Control_Allow_Origin": "*",
      },
      body: jsonEncode(<String, int>{
        'saldo': saldo,
      }),
    );
    if (response.statusCode == 200) {
      print("Success!");
      // return Pasien.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed Top Up Saldo');
    }
  }

  @override
  void initState() {
    jwtToken = widget.token;
    super.initState();
    _futurePasien = fetchPasien();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Top Up Saldo",
      home: Scaffold(
        appBar: AppBar(
          title: Text("Top Up"),
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
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.refresh),
          onPressed: () => setState(() {
            _futurePasien = fetchPasien();
          }),
        ),
        body: Container(
          alignment: Alignment.center,
          padding: const EdgeInsets.all(8.0),
          child: FutureBuilder<Pasien>(
            future: _futurePasien,
            builder: (context, AsyncSnapshot<Pasien> snapshot) {
              if (snapshot.connectionState == ConnectionState.done) {
                if (snapshot.hasData) {
                  return Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Text("Saldo Sekarang: " +
                          (snapshot.data!.saldo).toString()),
                      Padding(
                          padding: EdgeInsets.only(bottom: 15.0, right: 40.0)),
                      TextField(
                        controller: saldoController,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: "Top up saldo",
                          isDense: true,
                        ),
                        keyboardType: TextInputType.number,
                        inputFormatters: <TextInputFormatter>[
                          FilteringTextInputFormatter.digitsOnly
                        ],
                      ),
                      Padding(padding: EdgeInsets.only(bottom: 15)),
                      GestureDetector(
                        onTap: () => setState(() {
                          updateSaldo(int.parse(saldoController.text));
                          showDialog<String>(
                              context: context,
                              builder: (BuildContext context) => AlertDialog(
                                    title: const Text("Saldo Update"),
                                    content: const Text('Success!!'),
                                    actions: <Widget>[
                                      TextButton(
                                          onPressed: (() => Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      new ProfilePageState()))),
                                          child: const Text("Confirm")),
                                    ],
                                  ));
                        }),
                        child: Container(
                          alignment: Alignment.center,
                          margin: EdgeInsets.only(left: 20, right: 20, top: 40),
                          padding: EdgeInsets.only(left: 20, right: 20),
                          height: 40,
                          decoration: BoxDecoration(
                            gradient: LinearGradient(
                                colors: [
                                  (Color.fromARGB(255, 138, 198, 224)),
                                  Color.fromARGB(255, 75, 183, 210)
                                ],
                                begin: Alignment.centerLeft,
                                end: Alignment.centerRight),
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
                      ),
                    ],
                  );
                } else if (snapshot.hasError) {
                  return Text('Error!');
                }
              }
              return const CircularProgressIndicator();
            },
          ),
        ),
      ),
    );
  }
}
