import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/viewall_tagihan.dart';

import 'models/tagihan_model.dart';

import 'appointment_form.dart';
import 'package:provider/provider.dart';

class KonfirmasiBayarTagihanScreen extends StatefulWidget {
  final TagihanModel tagihan;
  // ignore: use_key_in_widget_constructors
  const KonfirmasiBayarTagihanScreen(this.tagihan);

  @override
  State<KonfirmasiBayarTagihanScreen> createState() =>
      _KonfirmasiBayarTagihanState();
}

class _KonfirmasiBayarTagihanState extends State<KonfirmasiBayarTagihanScreen> {
  String hasilBayar = "";

  Future<void> bayarTagihan() async {
    String token_prefix = "Bearer ";
    String? token = Provider.of<Appointment>(context, listen: false).token;

    var url = "https://apap-061.cs.ui.ac.id/api/pasien/tagihan/";
    url = url + widget.tagihan.kode + "/bayar";

    final response = await http.post(Uri.parse(url),
        headers: {"Authorization": (token_prefix + token!)});
    if (response.statusCode == 200) {
      String responsebody = response.body;

      if (responsebody != "Berhasil") {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(
          content: Text(responsebody),
        ));
      } else {
        Navigator.pop(context);
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => ViewAllTagihanScreen()),
        );
      }
    } else {
      throw Exception("Failed to load Data");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('View All Appointment'),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 0, 15),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Text(
                  "Apakah Anda yakin ingin membayar tagihan dengan kode ${widget.tagihan.kode} seharga ${widget.tagihan.jumlahTagihan}?"),
              Container(
                padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                child: ElevatedButton(
                  onPressed: () {
                    bayarTagihan();
                  },
                  child: const Text('Konfirmasi'),
                  style: ElevatedButton.styleFrom(
                      primary: Colors.blue,
                      onPrimary: Colors.white,
                      shape: const RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(5)),
                      )),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
