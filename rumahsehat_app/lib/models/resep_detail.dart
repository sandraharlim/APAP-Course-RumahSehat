import 'dart:js_util';

import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../navbar.dart';
import 'resep_model.dart';
import 'appointment_model.dart';

class ResepDetail extends StatefulWidget {
  final Appointment appointment;
  const ResepDetail(
      this.appointment,
      {Key? key}) : super(key: key
  );

  @override
  State<ResepDetail> createState() => _ResepDetailState();
}

class _ResepDetailState extends State<ResepDetail> {
  late Future<Resep> resep;
  String token_prefix = "Bearer ";
  String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJydXIiLCJleHAiOjE2NzA3NzA5MDYsImlhdCI6MTY3MDc1MjkwNn0.U5NCWwc844WE9tdV1_TaMhnnBT4i3FWlQ37BNYPhwfU5Q-TKa7yr263FBgYcOS_jJFV-fQ8dsh1u9Wug8sQQjg"; //isi tokennya

  Future<Resep> getResep() async {
    // String url = "https://apap-061.cs.ui.ac.id/api/appointment/viewall";
    String url = "http://127.0.0.1:49996/api/resep/detail/" + widget.appointment.resepId;

    final response = await http.get(Uri.parse(url),
        headers: {"Authorization": (token_prefix + token)});

    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      print(data);
      return Resep.fromJson(jsonDecode(response.body));
    }
    else {
      throw Exception('Gagal');
    }
  }


  @override
  void initState() {
    super.initState();
    resep = getResep();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Detail Resep'),
        ),
        body: SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(0, 30, 0, 15),
              child: Center(
                  child: Column(children: <Widget>[
                    FutureBuilder<Resep>(
                      future: resep,
                      builder: (context, snapshot) {
                        if(snapshot.hasData){
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 27.0),
                          child: TextField(
                            enabled: false,
                            decoration: InputDecoration(
                              contentPadding: EdgeInsets.only(bottom: 4),
                              labelText: "Id",
                              floatingLabelBehavior: FloatingLabelBehavior.always,
                              hintText: snapshot.data!.id,
                              hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 27.0),
                              child: TextField(
                                enabled: false,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(bottom: 4),
                                    labelText: "Tanggal dan Waktu Pemesana",
                                    floatingLabelBehavior: FloatingLabelBehavior.always,
                                    hintText: snapshot.data!.tanggalWaktu,
                                    hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 27.0),
                              child: TextField(
                                enabled: false,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(bottom: 4),
                                    labelText: "Dokter",
                                    floatingLabelBehavior: FloatingLabelBehavior.always,
                                    hintText: snapshot.data!.dokter,
                                    hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 27.0),
                              child: TextField(
                                enabled: false,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(bottom: 4),
                                    labelText: "Pasien",
                                    floatingLabelBehavior: FloatingLabelBehavior.always,
                                    hintText: snapshot.data!.pasien,
                                    hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 27.0),
                              child: TextField(
                                enabled: false,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(bottom: 4),
                                    labelText: "status",
                                    floatingLabelBehavior: FloatingLabelBehavior.always,
                                    hintText: snapshot.data!.status,
                                    hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 27.0),
                              child: TextField(
                                enabled: false,
                                decoration: InputDecoration(
                                    contentPadding: EdgeInsets.only(bottom: 4),
                                    labelText: "apoteker",
                                    floatingLabelBehavior: FloatingLabelBehavior.always,
                                    hintText: snapshot.data!.apoteker,
                                    hintStyle: const TextStyle(
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
                        }),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            List<String>? obatList = snapshot.data!.jumlah;
                            return ListView.builder(
                                padding: const EdgeInsets.all(8),
                                itemCount: obatList.length,
                                itemBuilder: (BuildContext context, int index) {
                                  return Container(
                                    height: 50,
                                    child: Center(child: Text(obatList[index])),
                                  );
                                }
                            );
                          } else if (snapshot.hasError) {
                            return Text('${snapshot.error}');
                          }
                          return const CircularProgressIndicator();
                        }),
                    ])))
            ));
  }
}