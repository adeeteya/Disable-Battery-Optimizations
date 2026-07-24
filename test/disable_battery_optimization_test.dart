import 'package:disable_battery_optimization/disable_battery_optimization.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  const channel = MethodChannel('in.jvapps.disable_battery_optimization');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(
            channel, (MethodCall methodCall) async => true);
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, null);
  });

  test('reads the native battery optimization status', () async {
    expect(
      await DisableBatteryOptimization.isBatteryOptimizationDisabled,
      isTrue,
    );
  });
}
