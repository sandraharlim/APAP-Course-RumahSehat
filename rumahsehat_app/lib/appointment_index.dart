import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import '../models/appointment_model.dart';
import '../models/appointment_pasien_card.dart';

import 'appointment_form.dart';
import 'navbar.dart';

class AppointmentViewAll extends StatefulWidget {
  const AppointmentViewAll({Key? key}) : super(key: key);

  @override
  _AppointmentViewAllState createState() => _AppointmentViewAllState();
}

class _AppointmentViewAllState extends State<AppointmentViewAll> {
  List<AppointmentModel> listAppointment = [];
  String token_prefix = "Bearer ";

  Future<void> getData() async {
    String? token = Provider.of<Appointment>(context, listen: false).token;

    String url = "https://apap-061.cs.ui.ac.id/api/appointment/viewall";

    try {
      final response = await http.get(Uri.parse(url),
          headers: {"Authorization": (token_prefix + token!)});

      List<dynamic> data = jsonDecode(response.body);

      List<AppointmentModel> listAppointmentFromServer = [];
      for (var i = 0; i < data.length; i++) {
        AppointmentModel appt = AppointmentModel.fromJson((data[i]));
        listAppointmentFromServer.add(appt);
      }

      setState(() {
        listAppointment = listAppointmentFromServer;
      });
    } catch (p) {
      print(p);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Anda bukan pasien.")),
      );
    }
  }

  @override
  void initState() {
    getData();
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
                child: ElevatedButton(
                  onPressed: () {
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
