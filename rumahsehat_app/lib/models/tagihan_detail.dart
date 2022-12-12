import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../navbar.dart';
import 'tagihan_model.dart';

class TagihanDetail extends StatefulWidget {
  final TagihanModel tagihan;
  const TagihanDetail(this.tagihan);

  @override
  State<TagihanDetail> createState() => _TagihanDetailState();
}

class _TagihanDetailState extends State<TagihanDetail> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Detail Tagihan'),
        ),
        body: SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(0, 30, 0, 15),
              child: Center(
                  child: Column(children: <Widget>[
                    Text(
                      'Kode: ${widget.tagihan.kode}',
                      style: const TextStyle(
                        fontSize: 20,
                        letterSpacing: 1,
                      ),
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    Text(
                      'Tanggal Terbuat: ${widget.tagihan.tanggalTerbuat}',
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
                      'Tanggal Bayar: ${widget.tagihan.tanggalBayar}',
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
                      'Status: ${widget.tagihan.isPaid}',
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
                      'Status: ${widget.tagihan.jumlahTagihan}',
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
                        // masuk ke bayar tagihan nanti
                        // Navigator.push(
                        //   context,
                        //   MaterialPageRoute(
                        //       builder: (context) =>
                        //           DetailResep()), // ini diganti BayarTagihan nanti
                        // );
                      },
                      child: const Text('Bayar Tagihan'),
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
