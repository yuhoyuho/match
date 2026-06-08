import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { StyleSheet, Text, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { colors } from '../theme/colors';

const Tab = createBottomTabNavigator();

// 임시 스크린 컴포넌트
const DummyScreen = ({ route }) => (
  <View style={styles.container}>
    <Text style={styles.text}>{route.name} 임시</Text>
  </View>
);

export default function MainScreen() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        headerShown: false,
        tabBarShowLabel: false, // 하단바 텍스트 라벨 숨기기
        tabBarStyle: {
          backgroundColor: '#151517',
          borderTopWidth: 0,
          elevation: 0,
          height: 64,
          paddingBottom: 12,
          paddingTop: 8,
        },
        // 하단바 클릭 시 색상
        tabBarActiveTintColor: '#ffffffff',
        tabBarInactiveTintColor: colors.text.secondary,
        tabBarIcon: ({ focused, color, size }) => {
          let iconName;

          if (route.name === '홈') {
            iconName = focused ? 'home' : 'home-outline';
          } else if (route.name === '커뮤니티') {
            iconName = focused ? 'planet' : 'planet-outline';
          } else if (route.name === '랜덤 통화') {
            iconName = focused ? 'call' : 'call-outline';
          } else if (route.name === '채팅') {
            iconName = focused ? 'chatbubble' : 'chatbubble-outline';
          } else if (route.name === '마이페이지') {
            iconName = focused ? 'person' : 'person-outline';
          }

          return <Ionicons name={iconName} size={26} color={color} />;
        },
      })}
    >
      <Tab.Screen name="커뮤니티" component={DummyScreen} />
      <Tab.Screen name="랜덤 통화" component={DummyScreen} />
      <Tab.Screen name="홈" component={DummyScreen} />
      <Tab.Screen name="채팅" component={DummyScreen} />
      <Tab.Screen name="마이페이지" component={DummyScreen} />
    </Tab.Navigator>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background.main,
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    color: colors.text.primary,
    fontSize: 20,
    fontWeight: 'bold',
  },
});
