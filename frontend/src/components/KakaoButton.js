import React from 'react';
import { TouchableOpacity, Text, StyleSheet, View } from 'react-native';

export default function KakaoButton({ onPress }) {
  return (
    <TouchableOpacity
      style={styles.button}
      activeOpacity={0.85}
      onPress={onPress}
      accessibilityRole="button"
      accessibilityLabel="카카오로 시작하기"
    >
      <View style={styles.contentContainer}>
        <Text style={styles.buttonText}>카카오로 시작하기</Text>
      </View>
    </TouchableOpacity>
  );
}

// 카카오 로그인 버튼
const styles = StyleSheet.create({
  button: {
    backgroundColor: '#FEE500',
    width: '100%',
    height: 52,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 3.84,
    elevation: 3,
  },
  contentContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: '#191919',
    fontSize: 16,
    fontWeight: '600',
    fontFamily: 'System',
  },
});
