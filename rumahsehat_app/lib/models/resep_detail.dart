import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'resep_model.dart';
import 'appointment_model.dart';
import 'package:provider/provider.dart';

class ResepT with ChangeNotifier {
  String? token;

  void updateData(tokenData) {
    token = tokenData;
    notifyListeners();
  }
}

class ResepDetail extends StatefulWidget {
  final AppointmentModel appointment;
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
  // String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXNpZW4xIiwiZXhwIjoxNjcwODMyODYwLCJpYXQiOjE2NzA4MTQ4NjB9.8jvhkT2GFXi6HA_Bpsl-gb1SLNL0tl44clXeJOnR0xRxd_Os0ZoVXc3nSgcaHDrGN20MDQKD45n3zd2E2za7mw"; //isi tokennya

  Future<Resep> getResep() async {
    String? token = Provider.of<ResepT>(context, listen: false).token;

    // String url = "https://apap-061.cs.ui.ac.id/api/appointment/viewall";
    String url = "http://10.0.2.2:8080/api/resep/detail/" + widget.appointment.resepId;

    final response = await http.get(Uri.parse(url),
        headers: {"Authorization": (token_prefix + token!)});

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return Resep.fromJson(data);
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
        body: Container(
            padding: EdgeInsets.only(left: 16, top: 25, right: 16),
            child: ListView(
              padding: const EdgeInsets.fromLTRB(0, 30, 0, 15),
              children: [
                    const Text(
                      "Detail Resep",
                      style: TextStyle(fontSize: 25, fontWeight: FontWeight.w500),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    FutureBuilder<Resep>(
                      future: resep,
                      builder: (context, AsyncSnapshot<Resep> snapshot) {
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
                                      contentPadding: const EdgeInsets.only(
                                          bottom: 4),
                                      labelText: "Id",
                                      floatingLabelBehavior: FloatingLabelBehavior
                                          .always,
                                      hintText: snapshot.data!.id,
                                      hintStyle: const TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.bold,
                                        color: Colors.black,
                                      )),
                                ),
                              );
                            } else {
                              return Text('${snapshot.error}');
                            }
                        }
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
                                    hintText: (snapshot.data!.tanggalWaktu).toString(),
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
                                    hintText: (snapshot.data!.dokter).toString(),
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
                                    hintText: (snapshot.data!.pasien).toString(),
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
                                    hintText: (snapshot.data!.status).toString(),
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
                                    hintText: (snapshot.data!.apoteker).toString(),
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
                    const Text(
                      "Daftar Obat",
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    FutureBuilder<Resep>(
                        future: resep,
                        builder: (context, snapshot) {
                          if(snapshot.hasData){
                            List<String>? obatList = snapshot.data!.jumlah;
                            return ListView.builder(
                                shrinkWrap: true,
                                padding: const EdgeInsets.all(8),
                                itemCount: obatList.length,
                                itemBuilder: (BuildContext context, int index) {
                                  return Container(
                                    decoration: BoxDecoration(
                                        color: Colors.grey,
                                        borderRadius: BorderRadius.circular(17)),
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
                    ]))
            );
  }
}