import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_app/models/tagihan_card.dart';
import 'package:rumahsehat_app/navbar.dart';

import 'models/tagihan_model.dart';

import 'appointment_form.dart';
import 'package:provider/provider.dart';

class ViewAllTagihanScreen extends StatefulWidget {
  const ViewAllTagihanScreen({Key? key}) : super(key: key);

  @override
  State<ViewAllTagihanScreen> createState() => _ViewAllTagihanState();
}

class _ViewAllTagihanState extends State<ViewAllTagihanScreen> {
  List<TagihanModel> listTagihan = [];

  Future<void> getAllTagihan() async {
    String token_prefix = "Bearer ";
    String? token = Provider.of<Appointment>(context, listen: false).token;

    var url = "https://apap-061.cs.ui.ac.id/api/pasien/tagihan";

    final response = await http.get(Uri.parse(url),
        headers: {"Authorization": (token_prefix + token!)});
    if (response.statusCode == 200) {
      List jsonResponse = jsonDecode(response.body);
      List<TagihanModel> listTagihanBaru =
          jsonResponse.map((data) => TagihanModel.fromJson(data)).toList();

      setState(() {
        listTagihan = listTagihanBaru;
      });
    } else {
      throw Exception("Failed to load Data");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    getAllTagihan();
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
                    getAllTagihan();
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
                children:
                    listTagihan.map((tagihan) => TagihanCard(tagihan)).toList(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
