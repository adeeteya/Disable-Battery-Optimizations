// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility that Flutter provides. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.

import 'package:flutter_test/flutter_test.dart';

import 'package:disable_battery_optimization_example/main.dart';

void main() {
  testWidgets('renders the example app', (WidgetTester tester) async {
    await tester.pumpWidget(MyApp());

    expect(find.text('Disable Battery Optimizations Plugin example app'),
        findsOneWidget);
    expect(find.text('Is Auto Start Enabled'), findsOneWidget);
  });
}
