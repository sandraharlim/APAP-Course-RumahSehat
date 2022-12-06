import 'package:flutter/material.dart';
import 'package:rumahsehat_app/profilepage.dart';
import 'appointment_form.dart';
import 'main.dart';

class NavigationDrawer extends StatelessWidget {
  const NavigationDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          DrawerHeader(child: Text("Navigation Menu")),
          ListTile(
            leading: Icon(Icons.home),
            title: Text('Home'),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) =>
                        MyHomePage()), // harusnya classnya Home()
              );
            },
          ),
          ExpansionTile(
            title: Text("Appointment"),
            // leading: FaIcon(FontAwesomeIcons.syringe),
            leading: Icon(Icons.calendar_today_sharp),
            children: <Widget>[
              ListTile(
                // leading: FaIcon(FontAwesomeIcons.syringe),
                leading: Icon(Icons.add),
                title: Text('Create'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => AppointmentForm()),
                  );
                },
              ),
              ListTile(
                leading: Icon(Icons.calendar_today_sharp),
                title: Text('View'),
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) =>
                            AppointmentForm()), // harusnya class index aja
                  );
                },
              ),
              ListTile(
                leading: Icon(Icons.person),
                title: Text("Profile"),
                onTap: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const ProfilePageState()));
                },
              )
            ],
          )
        ],
      ),
    );
  }
}
