import React, { useEffect } from 'react';
import { StyleSheet, Text, View, Alert, StatusBar, Platform, TouchableOpacity, Linking } from 'react-native';
import SocialLoginButton from '../components/SocialLoginButton';
import { colors } from '../theme/colors';

export default function LoginScreen({ navigation, route }) {
  const handleLogin = (provider) => {

    const BACKEND_URL = Platform.select({
      android: 'http://10.0.2.2:8080',
      ios: 'http://localhost:8080',
      default: 'http://localhost:8080',
    });

    const authUrl = `${BACKEND_URL}/oauth2/authorization/${provider}`;

    Linking.openURL(authUrl).catch((err) => {
      Alert.alert('오류', '로그인 페이지를 열 수 없습니다.');
      console.error(err);
    });
  };

  useEffect(() => {
    if (route.params?.accessToken) {
      const { accessToken, refreshToken, userId, profileCompleted } = route.params;

      console.log('로그인 성공');
      console.log('Access Token:', accessToken);
      console.log('Refresh Token:', refreshToken);
      console.log('User ID:', userId);
      console.log('Profile Completed:', profileCompleted);

      // profileCompleted가 문자열 true 아니면 boolean true일 때 메인으로 분기 ~
      const isCompleted = profileCompleted === 'true' || profileCompleted === true;
      if (isCompleted) {
        navigation.replace('Main');
      } else {
        navigation.replace('Onboarding');
      }
    }
  }, [route.params]);

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
          <SocialLoginButton provider="kakao" onPress={() => handleLogin('kakao')} />
        </View>
        <View style={styles.buttonWrapper}>
          <SocialLoginButton provider="naver" onPress={() => handleLogin('naver')} />
        </View>
        <View style={styles.buttonWrapper}>
          <SocialLoginButton provider="google" onPress={() => handleLogin('google')} />
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
