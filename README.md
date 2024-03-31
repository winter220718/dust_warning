## 📜 경보 단계 발령 시스템

> Java<br>
Oracle 11g<br>
Spring Boot v3.2.4, IntelliJ IDEA 2023.3.4<br>

#### 구현 사항
- 발령 기준 충족 시 DB에 저장
- 데이터가 없는 경우(null) 해당 일자 점검 내역 DB에 저장
- 매일 밤 11시 30분에 그 날 발령된 경보를 /dust-alarm으로 전송

#### 참고 사항
- DB계정에 권한 부여 필요 (GRANT CONNECT, DBA, RESOURCE TO DUST;)
