import 'package:flutter/widgets.dart';
import 'package:flutter/material.dart';
import 'conversations/threads.dart';
import 'sim/sim_bloc.dart';
import 'sim/sim_bloc_provider.dart';

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    final bloc = new SimCardsBloc();
    bloc.loadSimCards();

    return new SimCardsBlocProvider(
      simCardBloc: bloc,
      child: new MaterialApp(
        title: 'Flutter SMS',
        home: Threads(),
      ),
    );
  }
}
