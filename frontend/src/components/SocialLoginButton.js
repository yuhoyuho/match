import { TouchableOpacity, Text, StyleSheet, View, Image } from 'react-native';

const PROVIDER_CONFIG = {
  kakao: {
    backgroundColor: '#FEE500',
    textColor: '#191919',
    text: '카카오 로그인',
    icon: require('../../assets/login/kakao.png'),
  },
  naver: {
    backgroundColor: '#03A94D',
    textColor: '#FFFFFF',
    text: '네이버 로그인',
    icon: require('../../assets/login/naver.png'),
  },
  google: {
    backgroundColor: '#FFFFFF',
    textColor: '#191919',
    text: 'Google 로그인',
    icon: require('../../assets/login/google-light.svg'),
  }
};

export default function SocialLoginButton({ provider, onPress }) {
  const config = PROVIDER_CONFIG[provider];

  if (!config) return null;

  return (
    <TouchableOpacity
      style={[styles.button, { backgroundColor: config.backgroundColor }]}
      activeOpacity={0.85}
      onPress={onPress}
      accessibilityRole="button"
      accessibilityLabel={config.text}
    >
      <View style={styles.contentContainer}>
        <View style={styles.iconContainer}>
          <Image source={config.icon} style={styles.iconStyle} resizeMode="contain" />
        </View>
        <Text style={[styles.buttonText, { color: config.textColor }]}>{config.text}</Text>
      </View>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  button: {
    width: '100%',
    height: 52,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 3.84,
    elevation: 3,
  },
  contentContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    position: 'relative',
  },
  iconContainer: {
    position: 'absolute',
    left: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  iconStyle: {
    width: 20,
    height: 20,
  },
  buttonText: {
    fontSize: 16,
    fontWeight: '600',
    fontFamily: 'System',
  },
});
