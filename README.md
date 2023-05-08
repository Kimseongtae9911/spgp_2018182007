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

