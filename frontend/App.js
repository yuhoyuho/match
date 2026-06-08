import 'react-native-gesture-handler';
import React from 'react';
import { StyleSheet, View, Platform, SafeAreaView } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LandingScreen from './src/screens/LandingScreen';
import LoginScreen from './src/screens/LoginScreen';

const Stack = createStackNavigator();

function AppNavigator() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false, cardStyle: { backgroundColor: '#0A0A0C' } }}>
      <Stack.Screen name="Landing" component={LandingScreen} />
      <Stack.Screen name="Login" component={LoginScreen} />
    </Stack.Navigator>
  );
}

export default function App() {
  if (Platform.OS === 'web') {
    return (
      <View style={styles.webWrapper}>
        <View style={styles.webDeviceFrame}>
          <NavigationContainer>
            <AppNavigator />
          </NavigationContainer>
        </View>
      </View>
    );
  }

  return (
    <SafeAreaView style={styles.appContainer}>
      <NavigationContainer>
        <AppNavigator />
      </NavigationContainer>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  webWrapper: {
    flex: 1,
    backgroundColor: '#1C1C1E', // 데스크톱 웹 배경색
    justifyContent: 'center',
    alignItems: 'center',
    ...Platform.select({
      web: {
        width: '100vw',
        height: '100vh',
      }
    })
  },
  webDeviceFrame: {
    width: 412,
    height: 840,
    borderRadius: 40,
    borderWidth: 12,
    borderColor: '#2C2C2E', // 스마트폰 베젤 느낌의 경계선
    backgroundColor: '#0A0A0C',
    overflow: 'hidden',
    ...Platform.select({
      web: {
        boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.7)'
      }
    })
  },
  appContainer: {
    flex: 1,
    backgroundColor: '#0A0A0C',
  },
});
