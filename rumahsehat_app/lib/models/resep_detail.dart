import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../navbar.dart';
import 'resep_model.dart';

class ResepDetail extends StatefulWidget {
  final Resep resep;
  const ResepDetail(this.resep);

  @override
  State<ResepDetail> createState() => _ResepDetailState();
}

class _ResepDetailState extends State<ResepDetail> {
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
                    Text(
                      'ID Resep: ${widget.resep.id}',
                      style: const TextStyle(
                        fontSize: 20,
                        letterSpacing: 1,
                      ),
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    Text(
                      'Tanggal & Waktu Pemesanan: ${widget.resep.tanggalWaktu}',
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
                      'Dokter: ${widget.resep.dokter}',
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
                      'Pasien: ${widget.resep.pasien}',
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
                      'Status: ${widget.resep.status}',
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
                      'Apoteker: ${widget.resep.apoteker}',
                      style: TextStyle(
                        fontSize: 18,
                        color: Colors.grey[800],
                        letterSpacing: 0.5,
                      ),
                    ),
                    const SizedBox(
                      height: 10,
                    ),


                    //prr!! blm naro buat list obat yg ada di dlm resepppppp


                  ])),
            )));
  }
}