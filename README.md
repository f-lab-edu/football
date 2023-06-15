## 📌 서비스 설명
**football 프로젝트**는 소속팀이 없더라도 원하는 장소, 시간에 축구나 풋살 경기를 진행할 수 있도록 경기를 매칭해주는 서비스입니다.

매치 진행은 매니저에 의해 진행되며 참가자와 매니저는 채팅을 통해 소통할 수 있는 서비스를 제공합니다.

## 📌 사용 기술 스택
- Java / Spring Boot / Gradle
- JPA / MySQL / Flyway / Redis
- Elasticsearch
- Docker
- AWS
- Github Flow / Github Actions

## 📌 프로젝트 주요 관심사
- 아키텍처 설계 단계부터 확장 가능성을 고려해 Scale Out이 가능한 구조로 설계하기 위해 고민했습니다.
- 실제 사용자가 접근 가능한 배포 환경을 구성하기 위해 노력했습니다.
- 반복적인 작업은 자동화 구조를 통해 해결하고 업무의 효율성을 높이기 위해 노력했습니다.
- OOP의 특징과 장점을 최대한 활용할 수 있는 코드를 작성하기 위해 노력했습니다.
- 기술 도입에 있어 명확한 근거와 이유를 가지며 해당 기술에 대한 깊은 이해를 바탕으로 활용하기 위해 노력했습니다.


## 📌 프로젝트 전체 구성도
<img width="100%" height="100%" alt="스크린샷 2022-09-24 오전 2 09 25" src="https://user-images.githubusercontent.com/77563468/192015356-c87b76f5-933b-43a3-8a8c-24d55f563b36.png">


### 📎 다른 설계 도안이 궁금하다면?
- **아키텍처 설계 도안**

  ➡️ [링크](https://github.com/f-lab-edu/football/wiki/Application-Architecture)로 이동해주세요.

- **AWS 인프라스트럭처 도안**

  ➡️ [링크](https://github.com/f-lab-edu/football/wiki/AWS-InfraStructure)로 이동해주세요.

## 📌 ERD
![football_add_chat](https://user-images.githubusercontent.com/77563468/179156877-057c161e-eab5-4d0f-afdf-554781360aa8.png)

## 📌 Use Case
> [상세 설명](https://github.com/f-lab-edu/football/wiki/Use-Case) 페이지로 이동해주세요

## 📌 Trouble Shooting
> 모든 트러블 슈팅 내용이 궁금하다면! [링크](https://github.com/f-lab-edu/football/wiki/Trouble-Shooting)로 이동해주세요.

### ‼️ 핵심 트러블 슈팅
- **Scale Out을 고려한 아키텍처 설계** ➡️ [상세설명](https://kimcno3.github.io/posts/Scale-Out%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%9C-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%84%A4%EA%B3%84/)

- **AWS를 활용한 배포 환경 구성** ➡️ [상세설명](https://kimcno3.github.io/posts/AWS%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EB%B0%B0%ED%8F%AC-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1/)

- **멀티 모듈 구조를 통한 서비스 단위 서버 구분** ➡️ [상세설명](https://kimcno3.github.io/posts/%EB%A9%80%ED%8B%B0-%EB%AA%A8%EB%93%88-%EA%B5%AC%EC%A1%B0%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8B%A8%EC%9C%84-%EC%84%9C%EB%B2%84-%EA%B5%AC%EB%B6%84/)

- **Github Actions를 활용한 CI/CD 구조 설계** ➡️ [상세설명](https://kimcno3.github.io/posts/Github-Actions%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-CI-CD/)

- **JPA를 활용한 객체 지향적인 설계를 위한 고민** ➡️ [상세설명](https://kimcno3.github.io/posts/JPA%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5%EC%A0%81%EC%9D%B8-%EC%84%A4%EA%B3%84%EB%A5%BC-%EC%9C%84%ED%95%9C-%EA%B3%A0%EB%AF%BC/)

