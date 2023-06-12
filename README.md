# 스마트폰게임프로그래밍 텀프로젝트 2018182007 김성태

# 게임 컨셉
● 프로젝트 이름 : 몬스터 생존기 

● High Concept : 재화를 이용하여 캐릭터를 강화하고, 강화한 캐릭터로 몬스터를 피해 살아남는 서바이벌 액션 게임

● 핵심 메카닉 : 캐릭터를 강화하여 시간이 지날수록 강해지는 몬스터들로부터 살아남기 

# 게임 방법
1. 플레이어는 로비에서 스킬, 스탯을 강화할 수 있다
2. 게임플레이시 스킬은 쿨타임마다 자동으로 사용되며, 플레이어는 조이스틱을 이용해 몬스터들을 피해 다닌다
3. 플레이어는 게임씬에서 스킬, 스탯을 강화할 수 있지만, 게임씬에서 강화한 내용은 로비로 나갈 때 초기화된다
4. 플레이어는 5분마다 획득한 재화를 가지고 로비로 나갈지 다음 라운드로 갈지 정할 수 있다
5. 게임플레이시 죽게 된다면 획득한 모든 재화를 잃고 로비로 가게된다

# 예상 게임 실행 흐름
1. 로비에서 재화를 사용하여 스킬, 스탯 강화 가능(이미지에서는 무기로 예시)

![image](https://user-images.githubusercontent.com/84197808/229057101-7722e121-b522-44da-9911-3b5652e1be66.png)

2. 조이스틱을 이용하여 사방에서 나오는 몬스터들을 피하며, 자동으로 사용되는 스킬을 이용하여 죽인다

![image](https://user-images.githubusercontent.com/84197808/229052368-01488c3e-8411-4084-96d6-4bf521d7a8bf.png)

3. 게임플레이 중에도 강화가 가능하다

![image](https://user-images.githubusercontent.com/84197808/229054494-b3a0292f-5809-44da-8421-ef04e95ecbc1.png)

4. 5분마다 로비나 다음 라운드로 이동할지 정할 수 있다

![image](https://user-images.githubusercontent.com/84197808/229056911-81838465-58b4-4b8d-bfbf-a330d0f2a084.png)


# 개발 범위 및 일정
개발 범위

![image](https://user-images.githubusercontent.com/84197808/229061185-50174cb8-963e-4a42-a445-fb7346e44165.png)

개발 일정

![image](https://user-images.githubusercontent.com/84197808/229063267-0a0264cf-92ef-43a9-bb28-8b9cf065dda1.png)

# 발표 영상 링크 

https://youtu.be/JYg99dAH_0U

# 이미지 출처
https://blog.naver.com/sonny123123/222903131171

https://blog.naver.com/anisaver/222936080187

https://blog.naver.com/fun3200/222896160919

==================================================================================================================================

# 추가 상세기획
로비 UI

![image](https://user-images.githubusercontent.com/84197808/233760595-0dc8b7d7-a502-49d4-9a50-c5e409ca3305.png)


스탯 종류

파워(Power) : 해당 수치만큼 몬스터에게 추가 피해를 입힌다

스피드(Speed) : 플레이어의 이동속도

방어력(Defense) : 해당 수치의 비율만큼 몬스터로부터 입는 피해 감소

쿨타임(Cooltime) : 수치만큼 모든 스킬 재사용 대기시간 감소


스킬 종류

![image](https://user-images.githubusercontent.com/84197808/236837811-a573b8c5-ca10-4880-8df6-72e881f22caa.png)

쿨타임 3초, 가장 가까운 적을 공격하며 적과 충돌 시 사라진다

![image](https://user-images.githubusercontent.com/84197808/236838069-7cd86287-f72a-44cc-81d7-c0eee50066f6.png)

쿨타임 5초, 랜덤한 방향으로 날라가며 모든 적을 관통

![image](https://user-images.githubusercontent.com/84197808/236838201-8099a1fb-ebde-4a5f-a0d6-119af4c71762.png)

쿨타임 9초, 바라보고 있는 방향으로 화면 끝까지 번개를 발사, 모든 적을 관통

![image](https://user-images.githubusercontent.com/84197808/236838328-cdcee585-7b89-48bc-a121-3faed88e1bf5.png)

쿨타임 9초, 현재 위치를 중심으로 원형으로 퍼지며 주변 적에게 피해를 준다

![image](https://user-images.githubusercontent.com/84197808/236838523-6cba54dc-6c96-4f3e-9851-defec06d4c30.png)

쿨타임 20초, 데미지 1회 무효, 효과 발동 직후 쿨타임 20초

![image](https://user-images.githubusercontent.com/84197808/236838755-68435b48-e033-448b-991b-7c02cc73bf87.png)

쿨타임 15초, 주변에 불꽃이 4곳 떨어지며, 2초동안 해당 지역 몬스터들에게 피해를 입힌다

==================================================================================

# 스마트폰 게임프로그래밍 2차발표

# 게임 컨셉
● 프로젝트 이름 : 몬스터 생존기 

● High Concept : 재화를 이용하여 캐릭터를 강화하고, 강화한 캐릭터로 몬스터를 피해 살아남는 서바이벌 액션 게임

● 핵심 메카닉 : 캐릭터를 강화하여 시간이 지날수록 강해지는 몬스터들로부터 살아남기 

# 현재까지의 진행 상황

![image](https://user-images.githubusercontent.com/84197808/236854860-96438715-32cc-48a5-b780-a1f44fb94940.png)

# 주차별 git commit 횟수

![image](https://user-images.githubusercontent.com/84197808/236854959-0d7ff14c-9040-4a77-b3d3-a5bac351bab2.png)

# MainScene클래스 구성 정보

![image](https://user-images.githubusercontent.com/84197808/236855089-f5c6a85e-2c58-49d7-82d4-51e696512b90.png)

# JoyStick 주요코드

![image](https://user-images.githubusercontent.com/84197808/236855173-4e2c2f08-a8ba-4a33-b398-0d7d28c707a5.png)

![image](https://user-images.githubusercontent.com/84197808/236855242-76c34899-7257-452e-9956-a5ff2da38658.png)


1. 터치 이벤트가 발생하였을 때 조이스틱의 방향을 구한다
2. 조이스틱의 콜백함수인 onJoystickMoved를 호출하여 hero의 방향을 설정한다

# Hero 주요코드

![image](https://user-images.githubusercontent.com/84197808/236855453-a3bf1a98-d6da-437c-8581-31ebc712a98e.png)

![image](https://user-images.githubusercontent.com/84197808/236855480-e20b74bc-2050-4a06-98c9-8b99498bee20.png)

1. Hero가 화면 밖으로 움직이려고 하면 Hero의 좌표는 고정시키고 맵스크롤링을 위해 moveX, moveY의 값을 설정한다
2. 해당 값을 InfiniteScrollBackground에 적용시킨다

# InfiniteScrollBackGround 주요코드

![image](https://user-images.githubusercontent.com/84197808/236855833-1f045646-6a4c-4c36-ab9c-b4e630bfeec1.png)

플레이어를 기준으로 맵을 좌우, 상하, 대각선에 그려서 플레이어가 맵을 계속 돌아다니는 것처럼 만든다

# Monster 주요코드

![image](https://user-images.githubusercontent.com/84197808/236856061-edf1c065-9e4c-4fe7-8bc3-fdd1a021e833.png)

1. 플레이어의 좌표를 이용하여 이동방향을 설정한다
2. 맵이 스크롤중이라면 스크롤하는 값만큼 좌표에 추가연산을 한다

# 2차발표 영상 링크

https://www.youtube.com/watch?v=X5-Xh-3JUfg

==========================================================================================

# 추가 상세기획

파워(Power) : 스탯증가:7, 강화증가:5

스피드(Speed) : 스탯증가:0.5, 강화증가:0.1

방어력(Defense) : 스탯증가:3. 강화증가:2

쿨타임(Cooltime) : 스탯증가:2%, 강화증가:2% (최대 70%)

최대체력 : 레벨업 할때마다 10%증가
최대경험치 : 레벨업 할때마다 20%증가

난이도 : 30초마다 몬스터 스폰량 7증가, 30초마다 몬스터 레벨1 증가
몬스터 : 체력 = 레벨 * 10, 공격력 = 레벨 * 10

재화 획득 : 0 ~ (몬스터 레벨 + 1)
점수 획득 : 몬스터 레벨 * 10 + 시간 * 10

================================================================================

# 3차 발표

# 게임 개요

프로젝트 이름 : 몬스터 생존기
High Concept : 재화를 이용하여 캐릭터를 강화하고, 강화한 캐릭터로 몬스터를 피해 살아남는 서바이벌 액션 게임
핵심 메카닉 : 캐릭터를 강화하여 시간이 지날수록 강해지는 몬스터들로부터 살아남기

# 개발 진행

![image](https://github.com/Kimseongtae9911/spgp_2018182007/assets/84197808/f0e38750-6d96-41ed-8089-e826c686df2d)
2차 발표 당시 진행도

![image](https://github.com/Kimseongtae9911/spgp_2018182007/assets/84197808/2442154e-0e3d-4433-9557-45812cfea9b2)
현재 진행도

![image](https://github.com/Kimseongtae9911/spgp_2018182007/assets/84197808/612c28cf-67a3-40fb-ab41-0bca2eda0add)
주차별 git commit 횟수

# 기술 구현

![image](https://github.com/Kimseongtae9911/spgp_2018182007/assets/84197808/8f904530-c673-4580-a243-1b841e0b1867)

# 보완점

1. 하고 싶었지만 못한 것
   - 더욱 다양한 스킬과 스탯 구현
   - 다양한 몬스터와 공격 패턴
   - 버튼 누를 때 이미지 변경
2. 출시를 위해 보완할 점
   - 불친절한 UI 보완
   - 난이도 조절
3. 결국 해결하지 못한 버그
   - 게임 플레이 도중 사운드가 출력되지 않는 버그

# 수업 후기

1. 기대한 것
   - android환경에서 프로그래밍할 수 있는 능력 터ㄷ그
2. 얻은 것
   - 스마트폰 게임 프로그래밍에 대한 전반적인 이해
3. 얻지 못한 것
   - andriod studio의 다양한 기능
4. 더 좋은 수업이 되기 위해 변화할 점
   - 모든 코드에 대한 설명이 아니라 핵심적인 부분에 대한 설명과 실습은 과제를 통해 수행하면 더        좋을 것 같습니다

# 3차 발표 영상 링크

