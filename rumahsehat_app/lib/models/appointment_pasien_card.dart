import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/models/appointment_detail.dart';
import 'appointment_model.dart';

class AppointmentPasienCard extends StatefulWidget {
  final AppointmentModel appointment;
  const AppointmentPasienCard(this.appointment);

  @override
  State<AppointmentPasienCard> createState() => _AppointmentPasienCardState();
}

class _AppointmentPasienCardState extends State<AppointmentPasienCard> {
  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.fromLTRB(20, 16, 20, 0),
      color: Colors.grey[200],
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Text(
              'Dokter: ${widget.appointment.dokter}',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            Text(
              'Waktu Awal: ${widget.appointment.waktuAwal}',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            Text(
              'Status: ${widget.appointment.status}',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[800],
                letterSpacing: 0.5,
              ),
            ),
            widget.appointment.resepId != "0"
                ? ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) =>
                                AppointmentDetail(widget.appointment)),
                      );
                    },
                    child: const Text('Detail'),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.black,
                        onPrimary: Colors.white,
                        shape: const RoundedRectangleBorder(
                          borderRadius: BorderRadius.all(Radius.circular(5)),
                        )),
                  )
                : const Text(""),
          ],
        ),
      ),
    );
  }
}
