import React, { useState, useRef, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity, SafeAreaView, KeyboardAvoidingView, Platform, ScrollView, Switch, Dimensions, PanResponder, Animated, Modal } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import { colors } from '../theme/colors';

const { width } = Dimensions.get('window');

// 상수 데이터
const YEARS = Array.from({ length: 28 }, (_, i) => 2007 - i); // 2007 ~ 1980
const HEIGHTS = Array.from({ length: 71 }, (_, i) => 140 + i); // 140 ~ 210
const MBTI_LIST = ['ESTJ', 'ESTP', 'ESFJ', 'ESFP', 'ENTJ', 'ENTP', 'ENFJ', 'ENFP', 'ISTJ', 'ISTP', 'ISFJ', 'ISFP', 'INTJ', 'INTP', 'INFJ', 'INFP'];
const JOBS = ['학생', '회사원', '전문직', '사업가', '프리랜서', '기타'];
const EDU = ['고등학교 졸업', '대학교 재학', '대학교 졸업', '기타'];
const REGIONS = ['서울', '경기', '인천', '강원', '대전', '충청', '부산', '대구', '경상', '광주', '전라', '제주'];
const INTERESTS = ['운동', '영화', '맛집탐방', '독서', '여행', '음악감상', '게임', '카페', '반려동물', '자기계발', '전시회', '사진'];
const PURPOSES = ['진지한 연애', '가벼운 만남', '동네 친구', '랜선 통화'];

// 커스텀 양방향 슬라이더 컴포넌트
const CustomRangeSlider = ({ min, max, values, onValuesChange }) => {
  const sliderWidth = width - 56;
  const thumbSize = 28;
  const trackWidth = sliderWidth - thumbSize;

  const valueToPos = (val) => ((val - min) / (max - min)) * trackWidth;
  const posToValue = (pos) => Math.round((pos / trackWidth) * (max - min)) + min;

  const lowPos = useRef(new Animated.Value(valueToPos(values[0]))).current;
  const highPos = useRef(new Animated.Value(valueToPos(values[1]))).current;

  const lowRef = useRef(valueToPos(values[0]));
  const highRef = useRef(valueToPos(values[1]));

  useEffect(() => {
    const lId = lowPos.addListener(({ value }) => { lowRef.current = value; });
    const hId = highPos.addListener(({ value }) => { highRef.current = value; });
    return () => { lowPos.removeListener(lId); highPos.removeListener(hId); };
  }, []);

  const createPanResponder = (isLow) => {
    let startPos = 0;
    return PanResponder.create({
      onStartShouldSetPanResponder: () => true,
      onPanResponderGrant: () => { startPos = isLow ? lowRef.current : highRef.current; },
      onPanResponderMove: (evt, gestureState) => {
        let newPos = startPos + gestureState.dx;
        if (newPos < 0) newPos = 0;
        if (newPos > trackWidth) newPos = trackWidth;

        // 동그라미 두 개가 겹쳐서 통과하지 못하게 방어 (최소 간격 10px)
        const gap = 10;
        if (isLow && newPos > highRef.current - gap) newPos = highRef.current - gap;
        if (!isLow && newPos < lowRef.current + gap) newPos = lowRef.current + gap;

        if (isLow) lowPos.setValue(newPos);
        else highPos.setValue(newPos);

        // 드래그 중 실시간 텍스트 업데이트를 위한 부모 콜백
        onValuesChange([posToValue(lowRef.current), posToValue(highRef.current)]);
      },
      onPanResponderRelease: () => {
        onValuesChange([posToValue(lowRef.current), posToValue(highRef.current)]);
      }
    });
  };

  const lowPan = useRef(createPanResponder(true)).current;
  const highPan = useRef(createPanResponder(false)).current;

  return (
    <View style={{ width: sliderWidth, height: 40, justifyContent: 'center', marginTop: 10 }}>
      {/* 회색 배경 선 */}
      <View style={{ position: 'absolute', width: '100%', height: 6, backgroundColor: colors.background.webBorder, borderRadius: 3 }} />

      {/* 채워진 선 (보라색) */}
      <Animated.View style={{
        position: 'absolute', height: 6, backgroundColor: colors.primary, borderRadius: 3,
        left: lowPos, width: Animated.subtract(highPos, lowPos)
      }} />

      {/* 최소 나이 손잡이 */}
      <Animated.View
        {...lowPan.panHandlers}
        style={[styles.sliderThumb, { left: lowPos, zIndex: 2 }]}
      />

      {/* 최대 나이 손잡이 */}
      <Animated.View
        {...highPan.panHandlers}
        style={[styles.sliderThumb, { left: highPos, zIndex: 1 }]}
      />
    </View>
  );
};

export default function OnboardingScreen({ navigation }) {
  const [step, setStep] = useState(0);
  const totalSteps = 5;

  // 기본 정보
  const [nickname, setNickname] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [gender, setGender] = useState(null);
  const [birthYear, setBirthYear] = useState(1999); // 초기값
  const [isYearPickerVisible, setIsYearPickerVisible] = useState(false);

  // 프로필
  const [heightCm, setHeightCm] = useState(170); // 초기값
  const [mbti, setMbti] = useState('ESTJ'); // 초기값 설정
  const [isHeightPickerVisible, setIsHeightPickerVisible] = useState(false);
  const [isMbtiPickerVisible, setIsMbtiPickerVisible] = useState(false);
  const [jobTitle, setJobTitle] = useState(null);
  const [educationLevel, setEducationLevel] = useState(null);

  // 지역 + 관심사 + 만남 목적
  const [regionCode, setRegionCode] = useState(null);
  const [interests, setInterests] = useState([]);
  const [meetingPurpose, setMeetingPurpose] = useState(null);

  // 이상형
  const [preferredGender, setPreferredGender] = useState(null);
  const [preferredAgeRange, setPreferredAgeRange] = useState([20, 35]); // 커스텀 슬라이더 값
  const [allowRandomCall, setAllowRandomCall] = useState(true);

  // 전화번호 하이픈 자동 포맷팅
  const handlePhoneChange = (text) => {
    const cleaned = text.replace(/[^0-9]/g, '');
    let formatted = cleaned;
    if (cleaned.length > 3 && cleaned.length <= 7) {
      formatted = `${cleaned.slice(0, 3)}-${cleaned.slice(3)}`;
    } else if (cleaned.length > 7) {
      formatted = `${cleaned.slice(0, 3)}-${cleaned.slice(3, 7)}-${cleaned.slice(7, 11)}`;
    }
    setPhoneNumber(formatted);
  };

  // 유효성 검사
  const isStepValid = () => {
    if (step === 0) return nickname.trim().length >= 2 && phoneNumber.replace(/-/g, '').length >= 10 && gender;
    if (step === 1) return jobTitle && educationLevel; // 키와 MBTI는 기본값이 있으므로 무조건 통과
    if (step === 2) return regionCode && interests.length > 0 && meetingPurpose;
    if (step === 3) return preferredGender; // 슬라이더 기본값 존재
    if (step === 4) return true;
    return false;
  };

  const handleNext = () => {
    if (!isStepValid()) return;

    if (step < totalSteps - 1) {
      setStep(step + 1);
    } else {
      const finalData = {
        User: { nickname, phoneNumber: phoneNumber.replace(/-/g, ''), gender, birthYear },
        UserProfile: {
          heightCm,
          jobTitle,
          educationLevel,
          mbti,
          regionCode,
          introduction: `[목적] ${meetingPurpose}\n[관심사] ${interests.join(', ')}`
        },
        UserPreference: {
          preferredGender,
          preferredAgeMin: preferredAgeRange[0],
          preferredAgeMax: preferredAgeRange[1],
          allowRandomCall
        }
      };
      console.log("=== 가입 데이터 ===", JSON.stringify(finalData, null, 2));
      navigation.replace('Main');
    }
  };

  const handlePrev = () => {
    if (step > 0) setStep(step - 1);
    else navigation.goBack();
  };

  const toggleArrayItem = (array, setArray, item, max = 99) => {
    if (array.includes(item)) setArray(array.filter(i => i !== item));
    else if (array.length < max) setArray([...array, item]);
  };

  const renderGridButtons = (dataArray, stateValue, setStateFunction, multiSelect = false, max = 99) => (
    <View style={styles.gridContainer}>
      {dataArray.map(item => {
        const isSelected = multiSelect ? stateValue.includes(item) : stateValue === item;
        return (
          <TouchableOpacity
            key={item}
            style={[styles.gridButton, isSelected && styles.gridButtonActive]}
            onPress={() => multiSelect ? toggleArrayItem(stateValue, setStateFunction, item, max) : setStateFunction(item)}
          >
            <Text style={[styles.gridButtonText, isSelected && styles.gridButtonTextActive]}>{item}</Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );

  const renderStep0 = () => (
    <ScrollView showsVerticalScrollIndicator={false}>
      <Text style={styles.title}>기본 정보를 입력해 주세요!</Text>

      <Text style={styles.label}>닉네임 (2~8자)</Text>
      <TextInput style={styles.input} placeholder="닉네임 입력" placeholderTextColor={colors.text.secondary} value={nickname} onChangeText={setNickname} maxLength={8} />

      <Text style={styles.label}>전화번호</Text>
      <TextInput style={styles.input} placeholder="010-1234-5678" placeholderTextColor={colors.text.secondary} keyboardType="number-pad" value={phoneNumber} onChangeText={handlePhoneChange} maxLength={13} />

      <Text style={styles.label}>성별</Text>
      <View style={styles.row}>
        <TouchableOpacity style={[styles.flexButton, gender === 'M' && styles.flexButtonActive]} onPress={() => setGender('M')}>
          <Text style={[styles.flexButtonText, gender === 'M' && styles.flexButtonTextActive]}>남성</Text>
        </TouchableOpacity>
        <TouchableOpacity style={[styles.flexButton, gender === 'F' && styles.flexButtonActive]} onPress={() => setGender('F')}>
          <Text style={[styles.flexButtonText, gender === 'F' && styles.flexButtonTextActive]}>여성</Text>
        </TouchableOpacity>
      </View>

      <Text style={styles.label}>태어난 연도</Text>
      <TouchableOpacity style={styles.selectorButton} onPress={() => setIsYearPickerVisible(true)}>
        <Text style={styles.selectorButtonText}>{birthYear}년생</Text>
        <Text style={styles.selectorArrow}>▼</Text>
      </TouchableOpacity>
      <View style={{ height: 40 }} />
    </ScrollView>
  );

  const renderStep1 = () => (
    <ScrollView showsVerticalScrollIndicator={false}>
      <Text style={styles.title}>나의 매력을 채워주세요!</Text>

      <Text style={styles.label}>키</Text>
      <TouchableOpacity style={styles.selectorButton} onPress={() => setIsHeightPickerVisible(true)}>
        <Text style={styles.selectorButtonText}>{heightCm} cm</Text>
        <Text style={styles.selectorArrow}>▼</Text>
      </TouchableOpacity>

      <Text style={styles.label}>MBTI</Text>
      <TouchableOpacity style={styles.selectorButton} onPress={() => setIsMbtiPickerVisible(true)}>
        <Text style={styles.selectorButtonText}>{mbti}</Text>
        <Text style={styles.selectorArrow}>▼</Text>
      </TouchableOpacity>

      <Text style={styles.label}>직업</Text>
      {renderGridButtons(JOBS, jobTitle, setJobTitle)}

      <Text style={styles.label}>학력</Text>
      {renderGridButtons(EDU, educationLevel, setEducationLevel)}
      <View style={{ height: 40 }} />
    </ScrollView>
  );

  const renderStep2 = () => (
    <ScrollView showsVerticalScrollIndicator={false}>
      <Text style={styles.title}>어떤 관심사를 가지고 계신가요?</Text>
      <Text style={styles.label}>주 활동 지역</Text>
      {renderGridButtons(REGIONS, regionCode, setRegionCode)}
      <Text style={styles.label}>관심사 (최대 3개)</Text>
      {renderGridButtons(INTERESTS, interests, setInterests, true, 3)}
      <Text style={styles.label}>원하는 만남</Text>
      {renderGridButtons(PURPOSES, meetingPurpose, setMeetingPurpose)}
      <View style={{ height: 40 }} />
    </ScrollView>
  );

  const renderStep3 = () => (
    <ScrollView showsVerticalScrollIndicator={false}>
      <Text style={styles.title}>어떤 상대를 원하시나요?</Text>

      <Text style={styles.label}>선호하는 성별</Text>
      <View style={styles.row}>
        {['M', 'F', 'ANY'].map(g => (
          <TouchableOpacity key={g} style={[styles.flexButton, preferredGender === g && styles.flexButtonActive]} onPress={() => setPreferredGender(g)}>
            <Text style={[styles.flexButtonText, preferredGender === g && styles.flexButtonTextActive]}>
              {g === 'M' ? '남성' : g === 'F' ? '여성' : '상관없음'}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      <Text style={styles.label}>선호하는 나이대 ({preferredAgeRange[0]}세 ~ {preferredAgeRange[1]}세)</Text>
      <View style={styles.sliderContainer}>
        <CustomRangeSlider min={20} max={50} values={preferredAgeRange} onValuesChange={setPreferredAgeRange} />
      </View>

      <View style={styles.switchContainer}>
        <View>
          <Text style={styles.switchTitle}>랜덤 통화 허용</Text>
          <Text style={styles.switchDesc}>낯선 이와 목소리로 대화해 보세요!</Text>
        </View>
        <Switch
          trackColor={{ false: colors.background.webBorder, true: colors.primary }}
          thumbColor={'#fff'}
          onValueChange={setAllowRandomCall}
          value={allowRandomCall}
        />
      </View>
      <View style={{ height: 40 }} />
    </ScrollView>
  );

  const renderStep4 = () => (
    <View style={styles.stepContainer}>
      <Text style={styles.title}>마지막으로,{'\n'}사진을 등록해주세요.</Text>
      <Text style={styles.subtitle}>자신을 잘 나타내는 사진일수록 매칭률이 올라갑니다!</Text>
      <TouchableOpacity style={styles.photoBox}>
        <Text style={styles.photoBoxText}>사진 등록</Text>
      </TouchableOpacity>
    </View>
  );

  const stepsArray = [renderStep0, renderStep1, renderStep2, renderStep3, renderStep4];

  return (
    <SafeAreaView style={styles.safeArea}>
      <KeyboardAvoidingView behavior={Platform.OS === 'ios' ? 'padding' : 'height'} style={styles.container}>

        <View style={styles.header}>
          <TouchableOpacity onPress={handlePrev} style={styles.backButton}>
            <Text style={styles.backText}>←</Text>
          </TouchableOpacity>
          <View style={styles.progressContainer}>
            <View style={[styles.progressBar, { width: `${((step + 1) / totalSteps) * 100}%` }]} />
          </View>
        </View>

        <View style={styles.content}>
          {stepsArray[step]()}
        </View>

        <View style={styles.footer}>
          <TouchableOpacity
            style={[styles.button, !isStepValid() && styles.buttonDisabled]}
            onPress={handleNext}
            disabled={!isStepValid()}
          >
            <Text style={styles.buttonText}>
              {step === totalSteps - 1 ? '시작하기' : '다음'}
            </Text>
          </TouchableOpacity>
        </View>

        <Modal
          visible={isYearPickerVisible}
          transparent={true}
          animationType="slide"
          onRequestClose={() => setIsYearPickerVisible(false)}
        >
          <TouchableOpacity
            style={styles.modalOverlay}
            activeOpacity={1}
            onPress={() => setIsYearPickerVisible(false)}
          >
            <View style={styles.modalContent} onStartShouldSetResponder={() => true}>
              <View style={styles.modalHeader}>
                <Text style={styles.modalTitle}>태어난 연도 선택</Text>
                <TouchableOpacity onPress={() => setIsYearPickerVisible(false)} style={styles.modalCloseButton}>
                  <Text style={styles.modalCloseButtonText}>완료</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.modalPickerContainer}>
                <Picker
                  selectedValue={birthYear}
                  onValueChange={(itemValue) => setBirthYear(itemValue)}
                  style={styles.picker}
                  itemStyle={styles.pickerItem}
                >
                  {YEARS.map(y => (
                    <Picker.Item key={y} label={`${y}년`} value={y} color={colors.text.primary} />
                  ))}
                </Picker>
              </View>
            </View>
          </TouchableOpacity>
        </Modal>

        <Modal
          visible={isHeightPickerVisible}
          transparent={true}
          animationType="slide"
          onRequestClose={() => setIsHeightPickerVisible(false)}
        >
          <TouchableOpacity
            style={styles.modalOverlay}
            activeOpacity={1}
            onPress={() => setIsHeightPickerVisible(false)}
          >
            <View style={styles.modalContent} onStartShouldSetResponder={() => true}>
              <View style={styles.modalHeader}>
                <Text style={styles.modalTitle}>키 선택</Text>
                <TouchableOpacity onPress={() => setIsHeightPickerVisible(false)} style={styles.modalCloseButton}>
                  <Text style={styles.modalCloseButtonText}>완료</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.modalPickerContainer}>
                <Picker
                  selectedValue={heightCm}
                  onValueChange={(val) => setHeightCm(val)}
                  style={styles.picker}
                  itemStyle={styles.pickerItem}
                >
                  {HEIGHTS.map(h => (
                    <Picker.Item key={h} label={`${h} cm`} value={h} color={colors.text.primary} />
                  ))}
                </Picker>
              </View>
            </View>
          </TouchableOpacity>
        </Modal>

        <Modal
          visible={isMbtiPickerVisible}
          transparent={true}
          animationType="slide"
          onRequestClose={() => setIsMbtiPickerVisible(false)}
        >
          <TouchableOpacity
            style={styles.modalOverlay}
            activeOpacity={1}
            onPress={() => setIsMbtiPickerVisible(false)}
          >
            <View style={styles.modalContent} onStartShouldSetResponder={() => true}>
              <View style={styles.modalHeader}>
                <Text style={styles.modalTitle}>MBTI 선택</Text>
                <TouchableOpacity onPress={() => setIsMbtiPickerVisible(false)} style={styles.modalCloseButton}>
                  <Text style={styles.modalCloseButtonText}>완료</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.modalPickerContainer}>
                <Picker
                  selectedValue={mbti}
                  onValueChange={(val) => setMbti(val)}
                  style={styles.picker}
                  itemStyle={styles.pickerItem}
                >
                  {MBTI_LIST.map(m => (
                    <Picker.Item key={m} label={m} value={m} color={colors.text.primary} />
                  ))}
                </Picker>
              </View>
            </View>
          </TouchableOpacity>
        </Modal>

      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: { flex: 1, backgroundColor: colors.background.main },
  container: { flex: 1 },
  header: { flexDirection: 'row', alignItems: 'center', paddingHorizontal: 20, paddingTop: 10, paddingBottom: 20 },
  backButton: { padding: 10, marginRight: 10 },
  backText: { color: colors.text.primary, fontSize: 24 },
  progressContainer: { flex: 1, height: 4, backgroundColor: colors.background.webBorder, borderRadius: 2, overflow: 'hidden' },
  progressBar: { height: '100%', backgroundColor: colors.primary },
  content: { flex: 1, paddingHorizontal: 28 },
  stepContainer: { flex: 1, paddingTop: 20 },
  title: { color: colors.text.primary, fontSize: 26, fontWeight: 'bold', lineHeight: 36, marginBottom: 12, marginTop: 10 },
  subtitle: { color: colors.text.secondary, fontSize: 14, marginBottom: 20 },
  label: { color: colors.text.secondary, fontSize: 14, marginTop: 24, marginBottom: 10, fontWeight: '600' },
  input: { height: 56, backgroundColor: colors.background.webWrapper, borderRadius: 12, paddingHorizontal: 16, color: colors.text.primary, fontSize: 16 },
  row: { flexDirection: 'row', gap: 10 },
  flexButton: { flex: 1, height: 56, borderWidth: 1, borderColor: colors.background.webBorder, borderRadius: 12, justifyContent: 'center', alignItems: 'center' },
  flexButtonActive: { borderColor: colors.primary, backgroundColor: 'rgba(139, 92, 246, 0.1)' },
  flexButtonText: { color: colors.text.secondary, fontSize: 16, fontWeight: '600' },
  flexButtonTextActive: { color: colors.primary },
  pickerContainer: { height: 150, backgroundColor: colors.background.webWrapper, borderRadius: 12, overflow: 'hidden', justifyContent: 'center' },
  picker: { width: '100%', height: '100%' },
  pickerItem: { color: colors.text.primary, fontSize: 18 },
  gridContainer: { flexDirection: 'row', flexWrap: 'wrap', gap: 10 },
  gridButton: { paddingHorizontal: 16, paddingVertical: 12, backgroundColor: colors.background.webWrapper, borderRadius: 12, borderWidth: 1, borderColor: 'transparent' },
  gridButtonActive: { borderColor: colors.primary, backgroundColor: 'rgba(139, 92, 246, 0.1)' },
  gridButtonText: { color: colors.text.secondary, fontSize: 14 },
  gridButtonTextActive: { color: colors.primary, fontWeight: 'bold' },
  sliderContainer: { alignItems: 'center', paddingVertical: 20, marginHorizontal: 14 },
  sliderThumb: { position: 'absolute', width: 28, height: 28, borderRadius: 14, backgroundColor: '#fff', borderWidth: 2, borderColor: colors.primary, shadowColor: '#000', shadowOffset: { width: 0, height: 2 }, shadowOpacity: 0.2, shadowRadius: 3, elevation: 3 },
  switchContainer: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginTop: 40, padding: 20, backgroundColor: colors.background.webWrapper, borderRadius: 12 },
  switchTitle: { color: colors.text.primary, fontSize: 16, fontWeight: 'bold', marginBottom: 4 },
  switchDesc: { color: colors.text.secondary, fontSize: 12 },
  photoBox: { width: 140, height: 180, backgroundColor: colors.background.webWrapper, borderRadius: 12, justifyContent: 'center', alignItems: 'center', alignSelf: 'center', marginTop: 20, borderWidth: 2, borderColor: colors.background.webBorder, borderStyle: 'dashed' },
  photoBoxText: { color: colors.text.secondary, fontWeight: '600' },
  footer: { paddingHorizontal: 28, paddingBottom: 40, paddingTop: 10 },
  button: { height: 56, backgroundColor: colors.primary, borderRadius: 12, justifyContent: 'center', alignItems: 'center' },
  buttonDisabled: { backgroundColor: colors.background.webBorder },
  buttonText: { color: colors.text.inverse, fontSize: 16, fontWeight: 'bold' },
  selectorButton: {
    height: 56,
    backgroundColor: colors.background.webWrapper,
    borderRadius: 12,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 16,
    marginTop: 10,
  },
  selectorButtonText: {
    color: colors.text.primary,
    fontSize: 16,
  },
  selectorArrow: {
    color: colors.text.secondary,
    fontSize: 12,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.4)',
    justifyContent: 'flex-end',
  },
  modalContent: {
    backgroundColor: colors.background.main,
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    paddingBottom: Platform.OS === 'ios' ? 40 : 20,
    paddingHorizontal: 20,
  },
  modalHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 16,
    borderBottomWidth: 1,
    borderBottomColor: colors.background.webBorder,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: colors.text.primary,
  },
  modalCloseButton: {
    padding: 8,
  },
  modalCloseButtonText: {
    color: colors.primary,
    fontSize: 16,
    fontWeight: 'bold',
  },
  modalPickerContainer: {
    height: 200,
    justifyContent: 'center',
  },
});
