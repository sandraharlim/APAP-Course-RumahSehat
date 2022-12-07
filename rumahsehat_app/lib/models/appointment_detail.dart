import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../navbar.dart';
import 'appointment_model.dart';

class AppointmentDetail extends StatefulWidget {
  final Appointment appointment;
  const AppointmentDetail(this.appointment);

  @override
  State<AppointmentDetail> createState() => _AppointmentDetailState();
}

class _AppointmentDetailState extends State<AppointmentDetail> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Detail Appointment'),
        ),
        // drawer: const NavigationDrawer(),
        body: SingleChildScrollView(
            child: Padding(
          padding: const EdgeInsets.fromLTRB(0, 30, 0, 15),
          child: Center(
              child: Column(children: <Widget>[
            Text(
              'Kode: ${widget.appointment.kode}',
              style: const TextStyle(
                fontSize: 20,
                letterSpacing: 1,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              'Dokter: ${widget.appointment.dokter}',
              style: TextStyle(
                fontSize: 18,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              'Waktu Awal: ${widget.appointment.waktuAwal}',
              style: TextStyle(
                fontSize: 18,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              'Status: ${widget.appointment.status}',
              style: TextStyle(
                fontSize: 18,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            const SizedBox(
              height: 10,
            ),
            ElevatedButton(
              onPressed: () {
                // masuk ke resep detail
              },
              child: const Text('Detail Resep'),
              style: ElevatedButton.styleFrom(
                  primary: Colors.black,
                  onPrimary: Colors.white,
                  shape: const RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(Radius.circular(5)),
                  )),
            ),
          ])),
        )));
  }
}
