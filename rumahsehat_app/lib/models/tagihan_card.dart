import 'package:flutter/material.dart';
import 'package:rumahsehat_app/models/tagihan_detail.dart';
import 'package:rumahsehat_app/models/tagihan_model.dart';

class TagihanCard extends StatefulWidget {
  final TagihanModel tagihan;
  const TagihanCard(this.tagihan);

  @override
  State<TagihanCard> createState() => _TagihanCardState();
}

class _TagihanCardState extends State<TagihanCard> {
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
            ListTile(
              title: Text(widget.tagihan.kode,
                  style: TextStyle(
                    fontSize: 18,
                    letterSpacing: 0.5,
                  )),
              subtitle: widget.tagihan.isPaid
                  ? Text(
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
                              builder: (context) =>
                                  TagihanDetail(widget.tagihan)));
                    },
                    icon: const Icon(Icons.arrow_circle_down),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
