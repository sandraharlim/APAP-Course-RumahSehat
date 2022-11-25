import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/models/pasienmodel.dart';

class FormSaldo extends StatefulWidget {
  const FormSaldo({Key? key}) : super(key: key);

  @override
  FormSaldoState createState() => FormSaldoState();
}

Future<Pasien> fetchPasien() async {
  String token = "uuid-1";
  String url = 'http://127.0.0.1:8080/api/pasien/profile';

  final response = await http.get(Uri.parse(url),
      headers: {"Authorization": token, "Content-Type": "application/json"});
  if (response.statusCode == 200) {
    var data = json.decode(response.body);
    // print(data);
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
    body: jsonEncode(<String, int>{
      'saldo': saldo,
    }),
  );
  if (response.statusCode == 200) {
    return Pasien.fromJson(jsonDecode(response.body));
  } else {
    throw Exception('Failed Top Up Saldo');
  }
}

class FormSaldoState extends State<FormSaldo> {
  final TextEditingController saldoController = TextEditingController();
  late Future<Pasien>? _futurePasien;

  @override
  void initState() {
    super.initState();
    _futurePasien = fetchPasien();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Top Up Saldo",
      home: Scaffold(
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
            builder: (context, snapshot) {
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
                      ElevatedButton(
                        onPressed: () {
                          setState(() {
                            _futurePasien =
                                updateSaldo(int.parse(saldoController.text));
                            // _futurePasien = fetchPasien();
                          });
                        },
                        child: const Text("Update Saldo"),
                      )
                    ],
                  );
                } else if (snapshot.hasError) {
                  return Text('Error telah terjadi, silahkan kembali');
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
