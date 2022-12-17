import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:rumahsehat_app/profilepage.dart';
import 'package:rumahsehat_app/providers/auth.dart';
import 'package:rumahsehat_app/viewall_tagihan.dart';
import 'appointment_form.dart';
import 'appointment_index.dart';
import 'main.dart';

class NavigationDrawer extends StatelessWidget {
  const NavigationDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          const DrawerHeader(child: Text("Navigation Menu")),
          ListTile(
            leading: const Icon(Icons.home),
            title: const Text('Home'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => MyHomePage()),
              );
            },
          ),
          ListTile(
            leading: const Icon(Icons.person),
            title: const Text("Profile"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const ProfilePageState()));
            },
          ),
          ListTile(
            leading: const Icon(Icons.money),
            title: const Text("View All Tagihan"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const ViewAllTagihanScreen()));
            },
          ),
          ExpansionTile(
            title: const Text("Appointment"),
            leading: const Icon(Icons.calendar_today_sharp),
            children: <Widget>[
              ListTile(
                leading: const Icon(Icons.add),
                title: const Text('Create'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => AppointmentForm()),
                  );
                },
              ),
              ListTile(
                leading: const Icon(Icons.calendar_today_sharp),
                title: const Text('View'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const AppointmentViewAll()),
                  );
                },
              ),
            ],
          ),
          Container(
            padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
            child: ElevatedButton(
              child: const Text(
                "Logout",
              ),
              onPressed: () =>
                  Provider.of<Authentication>(context, listen: false).logout(),
            ),
          ),
        ],
      ),
    );
  }
}
