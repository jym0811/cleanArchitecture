# 계층형 아키텍쳐?

웹 → 도메인 → 영속성

1) Presentation(Web) Layer 

	- 사용자와의 Request, Response 등의 상호처리
	- View, Controller 등

2) Domain(Business) Layer

	- 소프트웨어가 해결하고자 하는 문제의 영역        →	   Domain ?
	- 도메인 처리에 초점을 둔 영역 				- 요구사항과 같은 해결하고자 하는 문제의 영역  
								- 게시글, 댓글, 결제기능 등

3) Persistence Layer

	- 엔티티를 영구히 저장하는 환경.		 →	   Entity ?	
								- 데이터의 집합
								- 저장되고 관리되어야 하는 데이터들
								- DB 테이블의 경우 레코드에 해당

	- 프로그램의 실행의 종료에도 데이터는 보존.


# 계층형 아키텍쳐의 특징

장점: 선택의 폭이 넓어지고 변화하는 요구사항과 외부요인에 빠르게 적응할 수 있게 해준다.
단점: 시간이 지날수록 소프트웨어를 점점 더 변경하기 어렵게 만든다.


# 계층형 아키텍쳐의 문제점

① 데이터베이스 주도 설계 유도
	- 웹은 도메인에 도메인은 영속성계층에 의존하므로 자연스레 데이터베이스에 의존 (ORM 프레임 워크의 사용이 원인)
	- 그동안 우리는 데이터베이스(영속성)의 구조를 먼저 생각하고 도메인 로직을 구현함
	- 계층형 아키텍쳐적 관점에서는 합리적이나 비즈니스 관점에서는 맞지 않음

	※ 도메인 로직을 가장 우선적으로 구현해야한다.


② 지름길을 택하기 쉬워진다.
	- 계층형 아키텍쳐에서 전체적으로 적용되는 규칙 → 특정한 계층에서는 같은 계층에 있는 컴포넌트나 아래 계층만 접근이 가능
	- 만약 상위 계층의 컴포넌트에 접근이 필요하다면, 컴포넌트를 아래 계층으로 내리면 된다. → 지름길
	- 지름길은 최선의 선택이 아니며, 빌드가 실패하는 결과를 초래한다.


③ 테스트하기 어려워진다.
	- 만약 엔티티의 필드를 하나만 조작하는 경우에는 도메인 로직을 구현할 필요가 없지 않을까? 
	
	위 방법대로 구현했을 경우에 문제점
	    1) 몇번은 괜찮아 보일 수 있으나 유스케이스가 확장된다면 애플리케이션 전체에 책임이 섞이고 핵심 도메인 로직들이 퍼질 확률이 높다.
	    2) 영속성 계층에서도 모킹이 필요하다. → 테스트의 복잡도가 향상 → 테스트 코드 작성시간보다 종속성 이해와 Mock 생성에 더 많은 시간 소요

            Mocking ?
	    - 테스트 코드가 의존하는 Function 이나 클래스의 모조품(Mock)을 만들어 일단 돌아가게 하는 것


④ 유스케이스를 숨긴다,
	- 도메인 로직이 여러 계층에 흩어지기 쉽다.
	- 새로운 기능을 추가할 적당한 위치를 찾는 일이 어려워진다.
	- 계층형 아키텍쳐는 서비스의 너비에 제한을 두지 않기 때문에 여러개의 유스케이스를 담당하는 넓은 서비스가 만들어지기도 하는데 → 영속성 계층에 너무 많은 의존성을 가지게 된다.

	Usecase?
	    사용자가 시스템을 사용하여 특정 목표를 달성하는 방법 ex) 자판기에서 음료를 산다.

⑤ 동시 작업이 어려워진다.
	- 계층형 아키텍쳐에서는 모든 것이 영속성 계층 위에 만들어지므로 영속성 계층을 먼저 개발하고 도메인 계층을 마지막으로 웹 계층을 만든다.
	- 특정 기능은 동시에 한 명의 개발자만 작업할 수 있으므로, 코드에 넓은 서비스가 있다면 서로 다른 기능을 동시에 작업하기 어려워지고
	  같은 서비스를 동시에 편집하게 되므로 conflict 를 야기한다.

	

# 단일 책임 원칙
- 하나의 컴포넌트는 오로지 한가지 일만 해야하고, 그것을 올바르게 수행해야한다.
- 책임은 오로지 한가지 일보다는 변경할 이유로 해석되어야 한다. → 변경할 이유가 한가지라면 소프트웨어가 여러번 변경되더라도 기대하는 결과값이 동일하므로

          Component?
	  시스템의 구성요소로 배포할 수 있는 가장 작은 단위
	  잘 설계된 컴포넌트라면 반드시 독립적으로 배포, 개발 가능해야 한다.


# 의존성 역전 원칙
	- 계층형 아키텍쳐에서 계층간 의존성은 다음 계층인 아래방향을 의미하는데 도메인 계층이 영속성 계층을 의존하므로 영속성 계층을 변경할 때마다 잠재적으로 도메인 계층을 변경해야한다.
	- 엔티티는 도메인 객체를 표현하고 도메인 코드는 엔티티의 상태를 변경하므로 먼저 영속성의 엔티티를 도메인 계층으로 올린다. 
	  → 영속성 계층의 리포지토리가 도메인 계층의 엔티티에 의존하므로 두 계층 사시에 순환 의존성이 생성된다. → 도메인 로직을 의존성으로부터 해방

	리포지터리(Repository)
	   - 유즈 케이스가 필요로 하는 데이터의 저장 및 수정 등의 기능을 제공하는 영역

# 클린 아키텍쳐
	- 도메인 코드가 바깥으로 향하는 어떤 의존성도 없어야함.
	- 계층 간의 모든 의존성이 안쪽으로 향해야 한다.
	- 코어(도메인 계층과 애플리케이션 계층을 합친 개념)에 주변 유스케이스에서 접근하는 도메인 엔티티 존재 → 넓은 서비스 문제 해결가능
	- 도메인코드에서는 어떤 영속성 프레임 워크나 UI 프레임워크가 사용되는지 알 수 없기 떄문에 비즈니스 로직에 집중 가능 → 도메인 코드의 자유로운 모델링 가능
	   → 하지만 도메인 계층이 영속성이 UI 같은 외부 계층과 철저히 분리돼야 하므로 엔티티에 대한 모델을 각 계층에서 보수해야함.

# 육각형 아키텍쳐 (헥사고날 아키텍쳐)
	- 애플리캐이션이 다른 시스템이나 어댑터와 연결되는 4개 이상의 면을 가질 수 있음.
	- 모든 의존성은 코어를 향한다.
	- 주도하는 어댑터는 그러한 포트가 코어에 있는 유스케이스 클래스 중 하나에 의해 구현되고 어댑터에 의해 호출.
	- 주도되는 어댑터는 그러한 포트가 어댑터에 의해 구현되고 코어에 의해 호출.
	- 마지막 계층에는 도메인 엔티티가 위치함.

클린 아키텍쳐, 헥사고날 아키텍쳐와 같은 방법으로 의존성을 역전시키는 이유는 변경할 이유를 줄여 유지소수성을 향상시키기 위함.


 ==========================================================================================

 # 2회차 정리

 # 도메인
- 프로그래밍으로 해결하고자 하는 주제에 대한 영역과 핵심 비즈니스 요구사항
- 도메인은 하위 도메인으로 나누어 질 수 있다. 상품 -> 상품명, 가격


# 도메인 모델
- 도메인을 단순화하고 의식적으로 구조화 -> 실제로 표현되지 않아도 존재한다.

# 도메인 객체모델
도메인 모델에 대한 표현을 코드로 나타내면 도메인 객체 모델이 된다.
상품 도메인을 Item 클래스로 나타내는 것과 같다. 이 클래스가 인스턴스화 된 것을
도메인 객체라고 부를 수 있을 것.


# 어댑터
- 포트를 통해 Application 코어와 외부 세계를 연결한다.
- 특정외부 기술이나 프레임워크에 의존적인 로직을 담당한다.

# 인커밍 어댑터(주도하는)
- UI 또는 테스트 또는 외부 시스템으로부터의 요청등
- MVC 의 Controller

# 아웃고잉 어댑터(주도되는)
- 코어에서 외부에 데이터를 전달하는
- MVC의 Repository

# 포트
- 코어의 경계를 정의하며 어댑터를 통해 코어에 접근하는 인터페이스

# 인커밍 포트(주도하는)
- 외부 요청이 애플리케이션 코어로 들어오는 경로를 정의
- 웹 요청, GUI 이벤트 등이 인커밍 포트를 통해 코어로 들어올 수 있다.
- Controller 와 service 사이의 인터페이스 

# 아웃고잉 포트(주도되는)
- 코어가 외부 세계에 서비스를 제공하기 위한 경로를 제공
- DB에 데이터를 전송하거나 요청하는 경우
- JPA Repository 와 service 사이의 인터페이스


# mybatis vs jdbctemplate

JdbcTemplate는 JDBC 코어 패키지의 중앙 클래스로  spring-jdbc 라이브러리에 포함되어 
복잡한 설정 없이 바로 사용이 가능하다.
JDBC 를 사용할 때 발생하는 반복 작업을 대신 처리해준다. ( conn 획득, statement 준비하고 실행, 결과 루프 반복, conn 종료 등)
단점으로는 상황에 따라서 다른 SQL문을 만드는 것인 동적 SQL을 해결하기 어렵다는 것이다.

Mybatis 또한 JDBC의 사용시 발생하는 반복작업을 해소함으로 더 중요한 측면에 집중할 수 있도록한다.
JdbcTemplate의 치명적인 단점인 동적 SQL 해결은 Mybatis 를 사용함으로 해소가 가능하다.
이렇게보면 JdbcTemplate 이 Mybatis 보다 좋은 점은 잘 모르겠다.


# 3장 내용 정리


# 계층으로 구성하기

코드를 구조화 하는 첫 번째 접근법은 계층을 이용하는 것이고 계층을 이용했을 경우 3가지 문제점을 소개하고 있다.
1) 패키지의 경계가 없어 새로운 기능을 추가할 때 서로 연관되지 않은 기능들끼리 예상하지 못한 부수효과를 유발할 수 있다.
2) 유스케이스를 파악하기 어려워 특정 기능을 찾기 위해서는 어떤 메소드 어떤 책임을 수행하는지 찾아봐야한다.
3) 아키텍처를 파악할 수없다. 어떤 기능이 웹 어댑터에서 호출되는지 영속성 어댑터가 도메인 계층에 어떤 기능을 제공하는지
한눈에 파악이 어렵다.

# 기능으로 구성하기

위에 대한 문제를 해결하기 위해 기능에 의한 패키지 방식을 사용할 수 있으나 가시성을 많이 떨어뜨린다.
육각형 아키텍처를 이용해 아키텍쳐적으로 표현력 있는 패키지 구조를 구성할 수 있다.

# 아키텍쳐 적으로 표현력이 있는 아키텍처 구조

육각형 아키텍쳐에서 구조적으로 핵심적인 요소는 엔티티, 유스케이스, 인커밍/아웃커밍 포트, 인커밍/아웃커밍 어댑터이다.
이런 표현력 있는 패키지 구조는 아키텍쳐에 대한 적극적인 사고를 촉진해서 많은 패키지가 생기는데, 이런경우에는 모든 것을
public 으로 만들어서 접근을 허용해야할까?
-> 적어도 어댑터 패키지에서는 그렇지 않다. 이 패키지에 들어있는 클래스들은 application 패키지 내에 있는 포트 인터페이스를
통하지 않고서는 바깥에서 호출되지 않기 때문이다. 하지만, 의도적으로 어댑터에서 접근 가능해야하는 포트들은 public 이어야 한다.

# 의존성 주입의 역할

클린 아키텍쳐의 본질적인 요건은 애플리케이션 계층이 인커밍/아웃고잉 어댑터에 의존성을 갖지 않는 것이다.
영속성 어탭어와 같이 아웃고잉 어댑터에 대해서 의존성 역전 원칙을 적용하기 위해서 모든 계층에 의존성을 가진
중립적인 컴포넌트를 하나 도입하여 아키텍쳐를 구성하는 대부분의 클래스를 초기화할 수 있다.

 
# 4장 내용 정리
육각형 아키텍쳐 스타일에서 유스케이스를 구현한다.

# 유스케이스 둘러보기

일반적으로 유스케이스는 다음과 같은 단계를 따른다.
1) 입력을 받는다. 
유스케이스는 인커밍 어댑터로부터 입력을 받는다 

2)  비즈니스 규칙을 검증한다
유스케이스는 비즈니스 규칙을 검을할 책임이 있고, 도메인 엔티티와 이 책임을 공유한다.

3) 모델 상태를 조작한다.
비즈니스 규칙을 충족하면 어떤 방법으로든 모델의 상태를 변경한다.

4) 출력을 반환한다.
아웃고잉 어댑터에서 온 출력값을, 유스케이스를 호출한 어댑터로 반환할 출력 객체로 변환한다.


# 입력 유효성 검증

과연 유스케이스에서 필요로 하는 것을 호출자가 모두 검증했다고 및을 수 있을까?
애플리케이션 계층에서 유효성 검증을 해야하는 이유는 코어의 바깥쪽으로부터 유효하지 않은 입력값을 받게되면
모델의 상태를 해칠 수 있기 때문이다.

입력 모델에 있는 유효성 검증코드를 통해 유스케이스 구현체 주위에 잘못된 입력을 호출자에게 돌려주는 보호막 역할의
오류방지 계층을 구현할 수 있다.


# 유스케이스마다 다른 입력모델

각기 다른 유스케이스에 동일한 입력모델을 사용하고 싶을 때 예를 들어 계좌 등록하기와 계좌 정보 업데이트하기 
거의 똑같은 계좌 상세정보가 필요하다.

이런경우 유효성을 어떻게 검증해야할까? 아마도 유스케이스에 커스텀 유효성 검증 로직을 넣어야 할 것이고
이것은 비즈니스 코드를 해칠 수 있다.

# 비즈니스 규칙 검증하기
입력 유효성을 검증하는 것은 구분상의 유효성을 검증하고, 비즈니스 규칙은 유스케이스 맥락속에서 의미적인
유효성을 검증하는 일이라 할 수 있다.

예를들어 출금계좌에서 초과출금이 되어서는 안된다는 규칙을 예로 들자면 이 규칙은 출금계좌와 입금계좌가
존재하는지 확인하기 위해 모델의 현재 상태에 접근해야 하기 때문에 비즈니스 규칙이다.

반대로 송금되는 금액은 0보다 커야 한다라는 규칙은 모델에 접근하지 않고도 검증될 수 있기 때문에 유효성 검증으로
구현할 수 있다.

비즈니스 규칙 검증은 도메인 엔티티 안에 구현하는 것이 가장 좋다. 비즈니스 로직 바로 옆에 규칙이 위치하기 때문에
위치를 정하는 것도 쉽고 추론하기도 쉽기 때문이다.


# 풍부한 도메인 모델 vs 빈약한 도메인 모델

풍부한 도메인 모델에서는 엔티티에서 가능한 많은 도메인 로직이 구현되고 도메인 모델의 진입점으로 유스케이스가
동작한다.

빈약한 도메인모델에서는 엔티티 자체가 굉장히 얇고 어떤 도메인 로직도 가지고 있지 않다. 이 말인 즉슨
도메인 로직이 유스케이스 클래스에 구현돼 있다는 것이다. 풍부함이 엔티티 대신 유스케이스에 존재하는 것이다.


# 유스케이스마다 다른 출력 모델

유스케이스가 할일을 다하고 나면 무엇을 반환해야 할까?
입력과 비슷하게 출력도 가능하면 각 유스케이스에 맞게 구체적일 수록 좋다. 하지만 만약 의심스럽다면
가능한 적게 반환하자.

유스케이스 간에 같은 출력모델을 공유하게되면 유스케이스들도 강하게 결합되므로 단일 책임 원칙을 적용하고
모델을 분리해서 유스케이스의 결합을 제거할 수있다.

# 읽기전용 유스케이스
읽기전용 유스케이스는 어떻게 구현할 수 있을까? → 이 책에서는 쿼리를 위한 인커밍 전용 포트를 만들고 이를 쿼리 서비스에
구현하는 것으로 구현했다. 서비스는 아웃고잉 포트로 쿼리를 전달하는 것 외에 다른일을 하고 있지 않다.



