import { StyleSheet, Text, View, StatusBar, TouchableOpacity } from 'react-native';
export default function LandingScreen({ navigation }) {
  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#0A0A0C" />

      {/* 중앙 정렬된 브랜드 및 액션 콘텐츠 영역 */}
      <View style={styles.contentContainer}>
        {/* 로고 영역 */}
        <View style={styles.brandingContainer}>
          <Text style={styles.logoText}>RealMatch<Text style={styles.plusText}>+</Text></Text>
          <Text style={styles.tagline}>
            소개 문구
          </Text>
        </View>

        {/* 시작하기 */}
        <View style={styles.actionContainer}>
          <TouchableOpacity
            style={styles.button}
            activeOpacity={0.8}
            onPress={() => navigation.navigate('Login')}
            accessibilityRole="button"
            accessibilityLabel="시작하기"
          >
            <Text style={styles.buttonText}>시작하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0A0A0C', // 뒷 배경 (베이스 검정)
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 28,
  },
  contentContainer: {
    width: '100%',
    alignItems: 'center',
  },
  brandingContainer: {
    alignItems: 'center',
    marginBottom: 36,
  },
  logoText: {
    color: '#FFFFFF',
    fontSize: 40,
    fontWeight: '900',
    letterSpacing: -0.5,
    marginBottom: 12,
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
    width: '100%',
    alignItems: 'center',
  },
  button: {
    backgroundColor: '#FFFFFF',
    width: '100%',
    height: 52,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.15,
    shadowRadius: 8,
    elevation: 4,
  },
  buttonText: {
    color: '#0A0A0C',
    fontSize: 16,
    fontWeight: '700',
  },
});