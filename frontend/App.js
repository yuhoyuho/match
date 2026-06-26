import 'react-native-gesture-handler';
import { StyleSheet, View, Platform, SafeAreaView } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LandingScreen from './src/screens/LandingScreen';
import LoginScreen from './src/screens/LoginScreen';
import MainScreen from './src/screens/MainScreen';
import OnboardingScreen from './src/screens/OnboardingScreen';
import { colors } from './src/theme/colors';

const Stack = createStackNavigator();

function AppNavigator() {
  return (
    <Stack.Navigator screenOptions={{ headerShown: false, cardStyle: { backgroundColor: colors.background.main } }}>
      <Stack.Screen name="Landing" component={LandingScreen} />
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Onboarding" component={OnboardingScreen} />
      <Stack.Screen name="Main" component={MainScreen} />
    </Stack.Navigator>
  );
}

const linking = {
  prefixes: ['realmatch://'],
  config: {
    screens: {
      Login: 'oauth/callback',
    },
  },
};

export default function App() {
  if (Platform.OS === 'web') {
    return (
      <View style={styles.webWrapper}>
        <View style={styles.webDeviceFrame}>
          <NavigationContainer linking={linking}>
            <AppNavigator />
          </NavigationContainer>
        </View>
      </View>
    );
  }

  return (
    <SafeAreaView style={styles.appContainer}>
      <NavigationContainer linking={linking}>
        <AppNavigator />
      </NavigationContainer>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  // 웹 관련 - 전부 임시
  webWrapper: {
    flex: 1,
    backgroundColor: colors.background.webWrapper,
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
    borderColor: colors.background.webBorder,
    backgroundColor: colors.background.main,
    overflow: 'hidden',
    ...Platform.select({
      web: {
        boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.7)'
      }
    })
  },
  appContainer: {
    flex: 1,
    backgroundColor: colors.background.main,
  },
});
