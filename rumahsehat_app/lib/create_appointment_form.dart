import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:collection';

import 'navbar.dart';

class AppointmentForm extends StatefulWidget {
  @override
  _AppointmentFormState createState() => _AppointmentFormState();
}

class _AppointmentFormState extends State<AppointmentForm> {
  final _formKey = GlobalKey<FormState>();

  List<String> listNamaTarif = [];
  List<String> listUuid = [];
  Map<String, String> correspondingUuid = {}; // {"nama-tarif" : "uuid"}

  String selectedNamaTarif = "";
  String selectedUuid = "";

  Future<void> getListOfDokter() async {
    const url = 'http://localhost:8080/appointment/create';
    try {
      final response = await http.get(Uri.parse(url));
      Map<String, dynamic> data = jsonDecode(response.body);

      var listNamaTarifDokter = data["nama-tarif"];
      var listUuidDokter = data["uuid"];

      for (var i=0; i < listNamaTarifDokter.length; i++) {
        String namaTarif = listNamaTarifDokter[i];
        String uuid = listUuidDokter[i];
        listNamaTarif.add(namaTarif);
        listUuid.add(uuid);
        correspondingUuid[namaTarif] = uuid;
      }

      setState(() {
        listNamaTarif = listNamaTarif;
        listUuid = listUuid;
        correspondingUuid = correspondingUuid;
      });
    } catch (p) {
      print(p);
    }
  }

  @override
  void initState() {
    getListOfDokter();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Create Appointment"),
          centerTitle: true,
        ),
        drawer: const NavigationDrawer(),
        body: Form(
          key: _formKey,
          child: SingleChildScrollView(
            child: Container(
              padding: const EdgeInsets.all(20.0),
              child: Column(
                children: const [
                  Padding(
                    padding: EdgeInsets.all(8.0),
                    child: Text("Waktu Awal"),
                  ),
                  Padding(
                    padding: EdgeInsets.all(8.0),
                    child: Text("Pilih Dokter"),
                  ),

                ],
              ),
            ),
          ),
        )
      );
  }
}


