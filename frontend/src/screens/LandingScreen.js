import { StyleSheet, Text, View, Alert, StatusBar, Platform } from 'react-native';
import KakaoButton from '../components/KakaoButton';

export default function LandingScreen() {
  const handleKakaoLogin = () => {
    Alert.alert(
      '카카오 로그인',
      '카카오 로그인',
      [{ text: '확인' }]
    );
  };

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#0A0A0C" />

      {/* 상단 로고 */}
      <View style={styles.brandingContainer}>
        <Text style={styles.logoText}>RealMatch<Text style={styles.plusText}>+</Text></Text>
        <Text style={styles.tagline}>
          소개 문구{'\n'}추가 필요
        </Text>
      </View>

      {/* 로그인 버튼  */}
      <View style={styles.actionContainer}>
        <KakaoButton onPress={handleKakaoLogin} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0A0A0C', // 뒷 배경 (베이스 검정)
    justifyContent: 'space-between',
    paddingHorizontal: 28,
    paddingTop: Platform.OS === 'ios' ? 80 : 60,
    paddingBottom: Platform.OS === 'ios' ? 50 : 36,
    overflow: 'hidden',
  },
  brandingContainer: {
    alignItems: 'center',
    marginTop: 40,
  },
  logoText: {
    color: '#FFFFFF',
    fontSize: 38,
    fontWeight: '900',
    letterSpacing: 0.5,
    marginBottom: 16,
  },
  plusText: {
    color: '#ffffffff',
  },
  tagline: {
    color: '#9E9EAF',
    fontSize: 16,
    lineHeight: 24,
    textAlign: 'center',
    fontWeight: '400',
  },
  actionContainer: {
    alignItems: 'center',
    width: '100%',
  },
});