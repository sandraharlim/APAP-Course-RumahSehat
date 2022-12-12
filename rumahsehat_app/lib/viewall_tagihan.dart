import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'models/tagihan_detail.dart';
import 'models/tagihan_model.dart';

void main() {
  runApp(const ViewAllTagihanScreen());
}

class TagihanService{
  Future<List<TagihanModel>> getAllTagihan() async{
    final response = await http.get(
      Uri.parse('https://mocki.io/v1/b3317438-8f02-44a9-812f-d8ffb612eb68')
    );
    if (response.statusCode == 200){
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((data) => TagihanModel.fromJson(data)).toList();
    }else{
      throw Exception("Failed to load Data");
    }
  }
}

class ViewAllTagihanScreen extends StatefulWidget {
  const ViewAllTagihanScreen({ Key? key }) : super(key: key);

  @override
  State<ViewAllTagihanScreen> createState() => _ViewAllTagihanState();
}

class _ViewAllTagihanState extends State<ViewAllTagihanScreen> {
  TagihanService serviceAPI = TagihanService();
  late Future<List<TagihanModel>> listTagihan;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    listTagihan = serviceAPI.getAllTagihan();
  }

  @override
  Widget build(BuildContext context){
    return MaterialApp(
        home: Scaffold(
          appBar: AppBar(title: Text("List Tagihan")),
          body: Container(
            child: FutureBuilder<List<TagihanModel>>(
              future: listTagihan,
              builder: (context, snapshot){
                if (snapshot.hasData){
                  List<TagihanModel> isiTagihan = snapshot.data!;
                  return ListView.builder(
                    itemCount: isiTagihan.length,
                    itemBuilder: (context, index){
                      return Card(
                        child: ListTile(
                          title: Text(
                              isiTagihan[index].kode,
                            style: TextStyle(
                              fontSize: 18,
                              letterSpacing: 0.5,
                            )
                          ),
                          subtitle: isiTagihan[index].kode == true?
                          Text(
                            'Tagihan Sudah Lunas',
                            style: TextStyle(
                              color: Colors.green[800],
                            ),
                          )
                              : Text(
                            'Tagihan Belum Lunas',
                            style: TextStyle(
                              color: Colors.red[800],
                            ),
                          ),
                          trailing: Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              IconButton(
                                  onPressed: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => new TagihanDetail(isiTagihan[index])));
                                  }, icon: const Icon(Icons.arrow_circle_right)
                              ),
                            ],
                          ),
                        ),
                      );
                    },
                  );
                } else if (snapshot.hasError){
                  return Text("${snapshot.error}");
                }
                return const CircularProgressIndicator();
              },
            ),
          ),
        )
    );
  }
}





