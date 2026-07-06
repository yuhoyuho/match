import { createNativeBottomTabNavigator } from '@bottom-tabs/react-navigation';
import { StyleSheet, Text, View, Platform } from 'react-native';
import { colors } from '../theme/colors';

const Tab = createNativeBottomTabNavigator();

// 네이티브 엔진 메모리 충돌을 막기 위해 화면 5개를 각각 독립된 컴포넌트로 분리
const CommunityScreen = () => <View style={styles.container}><Text style={styles.text}>커뮤니티 임시</Text></View>;
const CallScreen = () => <View style={styles.container}><Text style={styles.text}>랜덤 통화 임시</Text></View>;
const HomeScreen = () => <View style={styles.container}><Text style={styles.text}>홈 임시</Text></View>;
const ChatScreen = () => <View style={styles.container}><Text style={styles.text}>채팅 임시</Text></View>;
const ProfileScreen = () => <View style={styles.container}><Text style={styles.text}>마이페이지 임시</Text></View>;

export default function MainScreen() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        headerShown: false,
        tabBarActiveTintColor: colors.primary,
        tabBarInactiveTintColor: colors.text.secondary,
        tabBarIcon: ({ focused }) => {
          let iconName;

          if (route.name === '커뮤니티') {
            iconName = focused ? 'globe' : 'globe';
          } else if (route.name === '랜덤 통화') {
            iconName = focused ? 'phone.fill' : 'phone';
          } else if (route.name === '홈') {
            iconName = focused ? 'house.fill' : 'house';
          } else if (route.name === '채팅') {
            iconName = focused ? 'message.fill' : 'message';
          } else if (route.name === '마이페이지') {
            iconName = focused ? 'person.fill' : 'person';
          }

          // 네이티브 탭바가 요구하는 애플 순정 아이콘(SF Symbols) 형식으로 반환
          return Platform.OS === 'ios' ? { sfSymbol: iconName } : undefined;
        },
      })}
    >
      <Tab.Screen name="홈" component={HomeScreen} getId={() => '홈'} />
      <Tab.Screen name="커뮤니티" component={CommunityScreen} getId={() => '커뮤니티'} />
      <Tab.Screen name="랜덤 통화" component={CallScreen} getId={() => '랜덤통화'} />
      <Tab.Screen name="채팅" component={ChatScreen} getId={() => '채팅'} />
      <Tab.Screen name="마이페이지" component={ProfileScreen} getId={() => '마이페이지'} />
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
