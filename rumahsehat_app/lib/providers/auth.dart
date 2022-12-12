import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

class Authentication with ChangeNotifier {
  String? _token;
  String? _tempToken;

  void tempData(){
    _token = _tempToken;
    notifyListeners();
  }

  bool get isAuth{
    return token != null;
  }

  String? get token{
    return _token;
  }

  Future<void> login(String? username, String? password) async {
    Uri url = Uri.parse('http://10.0.2.2:8080/authenticate');
    var response = await http.post(url,
        headers: {"Content-Type": "application/json"},
        body: json.encode({"username": username, "password": password}));

    var data = json.decode(response.body);
    _token = data['jwttoken'];
    notifyListeners();
  }

  Future<void> signUp(String? email, String? username, String? nama, String? password, String? umur) async {
    Uri url = Uri.parse('http://10.0.2.2:8080/sign-up');
    var response = await http.post(url,
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": email,
          "username": username,
          "nama": nama,
          "password": password,
          "umur" : umur
        }));

    var data = json.decode(response.body);
    _token = data['jwttoken'];
    notifyListeners();
  }

  void logout(){
    _token = null;
    notifyListeners();
  }
}
