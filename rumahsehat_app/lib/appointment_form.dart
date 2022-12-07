// https://medium.flutterdevs.com/date-and-time-picker-in-flutter-72141e7531c
// https://www.flutterbeads.com/dropdown-in-flutter/
// stackoverflow.com/questions/49780858/flutter-dropdown-text-field
import 'package:date_format/date_format.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

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

  List<String> listNamaTarif = [
    // "Tirta-1000",
    // "Rakha-2000",
    // "Tata-3000",
  ];
  Map<String, String> correspondingUuid = {
    // {"nama-tarif" : "uuid"}
    // "Tirta-1000": "qwerty1",
    // "Rakha-2000": "qwerty2",
    // "Tata-3000": "qwerty3"
  };

  String selectedNamaTarif = ""; // Tirta-1000
  String token = "";
  // "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXNpZW4yIiwiZXhwIjoxNjcxMjQ0MTc3fQ.4vuSC4zLB67MMvnNSN-s36ELL2iVRO5aUl3DNRlXKWrNWCErEjFRJZQO1zzXSSBYuoAXtgsCx0XhpIjvYLbLRA";

  Future<void> getListOfDokter() async {
    // localhost yg bisa diakses dr emulator
    const url = 'http://10.0.2.2:8080/api/appointment/doctors-flutter';
    // mock server
    // const url =
    //     'https://22202f32-174d-4a73-abb7-98e3816b7709.mock.pstmn.io/api/appointment/doctors-flutter';
    try {
      final response =
          await http.get(Uri.parse(url), headers: {"Authorization": token});

      List<dynamic> data = jsonDecode(response.body);

      print(data);

      listNamaTarif = [];
      correspondingUuid = {};

      for (var i = 0; i < data.length; i++) {
        var dokter = data[i];
        String uuid = dokter['uuid'];
        String namaTarif = dokter['nama-tarif'];
        listNamaTarif.add(namaTarif);
        correspondingUuid[namaTarif] = uuid;
      }

      setState(() {
        listNamaTarif = listNamaTarif;
        correspondingUuid = correspondingUuid;
        selectedNamaTarif = listNamaTarif.first;
      });
    } catch (p) {
      print(p);
      if (token == "") {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Anda belum login")),
        );
      }
    }
  }

  late double _height;
  late double _width;

  String _setDate = "";
  String _setTime = "";

  late String _hour, _minute, _time;

  late String dateTime;

  DateTime selectedDate = DateTime.now();

  TimeOfDay selectedTime = const TimeOfDay(hour: 00, minute: 00);

  TextEditingController _dateController = TextEditingController();
  TextEditingController _timeController = TextEditingController();

  Future<Null> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
        context: context,
        initialDate: selectedDate,
        initialDatePickerMode: DatePickerMode.day,
        firstDate: DateTime(2021),
        lastDate: DateTime(2101));
    if (picked != null)
      setState(() {
        selectedDate = picked;
        _dateController.text = DateFormat.yMd().format(selectedDate);
      });
  }

  Future<Null> _selectTime(BuildContext context) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: selectedTime,
    );
    if (picked != null)
      setState(() {
        selectedTime = picked;
        _hour = selectedTime.hour.toString();
        _minute = selectedTime.minute.toString();
        _time = _hour + ' : ' + _minute;
        _timeController.text = _time;
        _timeController.text = formatDate(
            DateTime(2019, 08, 1, selectedTime.hour, selectedTime.minute),
            [hh, ':', nn, " ", am]).toString();
      });
  }

  @override
  void initState() {
    getListOfDokter();

    _dateController.text = DateFormat.yMd().format(DateTime.now());

    _timeController.text = formatDate(
        DateTime(2019, 08, 1, DateTime.now().hour, DateTime.now().minute),
        [hh, ':', nn, " ", am]).toString();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    dateTime = DateFormat.yMd().format(DateTime.now());
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('Create Appointment'),
      ),
      drawer: const NavigationDrawer(),
      body: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Container(
            width: _width,
            height: _height,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                Column(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 30, 0, 0),
                      child: Text(
                        'Choose Date',
                        style: TextStyle(
                            fontStyle: FontStyle.italic,
                            fontWeight: FontWeight.w600,
                            letterSpacing: 0.5),
                      ),
                    ),
                    InkWell(
                      onTap: () {
                        _selectDate(context);
                      },
                      child: Container(
                        width: _width / 1.7,
                        height: _height / 9,
                        margin: EdgeInsets.only(top: 20),
                        alignment: Alignment.center,
                        decoration: BoxDecoration(color: Colors.grey[200]),
                        child: TextFormField(
                          style: TextStyle(fontSize: 40),
                          textAlign: TextAlign.center,
                          enabled: false,
                          keyboardType: TextInputType.text,
                          controller: _dateController,
                          onSaved: (val) {
                            _setDate = val!;
                            print(val);
                            print(_setDate);
                          },
                          decoration: InputDecoration(
                              disabledBorder: UnderlineInputBorder(
                                  borderSide: BorderSide.none),
                              // labelText: 'Time',
                              contentPadding: EdgeInsets.only(top: 0.0)),
                        ),
                      ),
                    ),
                  ],
                ),
                Column(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 30, 0, 0),
                      child: Text(
                        'Choose Time',
                        style: TextStyle(
                            fontStyle: FontStyle.italic,
                            fontWeight: FontWeight.w600,
                            letterSpacing: 0.5),
                      ),
                    ),
                    InkWell(
                      onTap: () {
                        _selectTime(context);
                      },
                      child: Container(
                        margin: EdgeInsets.only(top: 20),
                        width: _width / 1.7,
                        height: _height / 9,
                        alignment: Alignment.center,
                        decoration: BoxDecoration(color: Colors.grey[200]),
                        child: TextFormField(
                          style: TextStyle(fontSize: 40),
                          textAlign: TextAlign.center,
                          onSaved: (val) {
                            _setTime = val!;
                            print(val);
                            print(_setTime);
                          },
                          enabled: false,
                          keyboardType: TextInputType.text,
                          controller: _timeController,
                          decoration: InputDecoration(
                              disabledBorder: UnderlineInputBorder(
                                  borderSide: BorderSide.none),
                              // labelText: 'Time',
                              contentPadding: EdgeInsets.all(5)),
                        ),
                      ),
                    ),
                  ],
                ),
                Column(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 30, 0, 0),
                      child: Text(
                        'Choose Doctor',
                        style: TextStyle(
                            fontStyle: FontStyle.italic,
                            fontWeight: FontWeight.w600,
                            letterSpacing: 0.5),
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.fromLTRB(0, 20, 0, 0),
                        child: DropdownButton<String>(
                          value: selectedNamaTarif,
                          isDense: true,
                          items: listNamaTarif
                              .map<DropdownMenuItem<String>>((String val) {
                            return DropdownMenuItem<String>(
                              value: val,
                              child: Text(
                                val,
                                style: TextStyle(fontSize: 20),
                              ),
                            );
                          }).toList(),
                          onChanged: (String? newValue) {
                            setState(() {
                              selectedNamaTarif = newValue!;
                            });
                          },
                        )),
                  ],
                ),
                Padding(
                  padding: EdgeInsets.only(top: 40.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Padding(
                        padding: EdgeInsets.only(right: 20.0),
                        child: ElevatedButton(
                          child: const Text(
                            "login",
                          ),
                          onPressed: () {
                            login();
                          },
                        ),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          getListOfDokter();
                        },
                        child: const Text('Refresh list of doctors'),
                        style: ElevatedButton.styleFrom(
                            primary: Colors.amber,
                            onPrimary: Colors.white,
                            shape: const RoundedRectangleBorder(
                              borderRadius:
                                  BorderRadius.all(Radius.circular(5)),
                            )),
                      ),
                      Padding(
                        padding: EdgeInsets.only(left: 20.0),
                        child: ElevatedButton(
                          child: const Text(
                            "Submit",
                          ),
                          onPressed: () {
                            if (_formKey.currentState!.validate()) {}

                            // printAllVar();

                            if (selectedNamaTarif == "") {
                              ScaffoldMessenger.of(context).showSnackBar(
                                const SnackBar(
                                    content:
                                        Text("Anda belum memilih dokter!")),
                              );
                              return;
                            }

                            if (_formKey.currentState!.validate()) {
                              // Jika form valid, tampilkan sebuah snackbar
                              submitForm();
                            }
                          },
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void printAllVar() {
    print(_dateController.text);
    print(_timeController.text);
    print(selectedNamaTarif);
    print(correspondingUuid[selectedNamaTarif]);
  }

  Future<void> login() async {
    const urlPost = "http://10.0.2.2:8080/login";
    String username = "pasien1";
    String password = "Qwerty123";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({"username": username, "password": password}),
          // body: jsonEncode({"username": "apoteker1", "password": "Qwerty123"}), // ngetes kalo salah user
          headers: {"Authorization": token});
      Map<String, String> headers = response.headers;
      String? jwtToken = headers["authorization"];

      if (jwtToken != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("berhasil login dengan username " + username)),
        );
        setState(() {
          token = jwtToken;
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Username atau password salah")),
        );
      }
    } catch (p) {
      print(p);
    }
  }

  Future<void> submitForm() async {
    const urlPost = "http://10.0.2.2:8080/api/appointment/create";
    try {
      final response = await http.post(Uri.parse(urlPost),
          body: jsonEncode({
            "uuid": correspondingUuid[selectedNamaTarif],
            "date": _dateController.text,
            "time": _timeController.text
          }),
          headers: {
            // https://stackoverflow.com/questions/53388426/flutter-send-json-over-http-post
            "content-type": "application/json",
            "accept": "application/json",
            "Authorization": token
          });

      Map<String, dynamic> extractedData = jsonDecode(response.body);

      String? errorMessage = extractedData["error"];
      if (errorMessage != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(errorMessage)),
        );
        return;
      }

      String? successMessage = extractedData['success'];
      if (successMessage != null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(successMessage)),
        );
        refreshPage(); // set ulang date, time, dokter.
        return;
      }

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Tidak ada respon dari server')),
      );
    } catch (p) {
      print(p);
    }
  }

  void refreshPage() {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => AppointmentForm()),
    );
  }
}
