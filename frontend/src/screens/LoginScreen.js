import { StyleSheet, Text, View, Alert, StatusBar, Platform, TouchableOpacity } from 'react-native';
import SocialLoginButton from '../components/SocialLoginButton';
import { colors } from '../theme/colors';

export default function LoginScreen({ navigation }) {
  const handleLogin = (provider) => {
    Alert.alert(
      `${provider} 로그인`,
      `${provider} 로그인 클릭`,
      [{ text: '확인' }]
    );
  };

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor={colors.background.main} />

      {/* 타이틀 */}
      <View style={styles.titleContainer}>
        <Text style={styles.titleText}>간편하게 시작하기</Text>
      </View>

      {/* 소셜 로그인 리스트 (세로 정렬) */}
      <View style={styles.buttonList}>
        <View style={styles.buttonWrapper}>
          <SocialLoginButton provider="kakao" onPress={() => handleLogin('카카오')} />
        </View>
        <View style={styles.buttonWrapper}>
          <SocialLoginButton provider="naver" onPress={() => handleLogin('네이버')} />
        </View>
        <View style={styles.buttonWrapper}>
          <SocialLoginButton provider="google" onPress={() => handleLogin('Google')} />
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background.main,
    paddingHorizontal: 28,
    justifyContent: 'center',
  },
  titleContainer: {
    marginBottom: 36,
    alignItems: 'center',
  },
  titleText: {
    color: colors.text.primary,
    fontSize: 40,
    fontWeight: '900',
    letterSpacing: -0.5,
    marginBottom: 12,
    textAlign: 'center',
  },
  buttonList: {
    width: '100%',
  },
  buttonWrapper: {
    marginBottom: 14,
  },
});
