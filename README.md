- (FE) 아이디, 비번 BE로 전달 
- (BE) 아이디, 비번 인증처리 > AT, RT 발행
                                   > AT, RT 안에는 MyPrincipal(userPk) 객체 내용이 포함
                                   > Response Body에 AT를 담아서 리턴, RT Cookie에 담는다.
- (FE) Request를 보낼 때 항상 Header에 Authorization 키값에 Bearer ${AT}를 담아서 보낸다.
- (BE) Request에 Authorization 키값이 있는지 확인 
           > 없으면 그냥 통과
           > 있으면 기간만료가 되었는지 확인 
                  > 만료가 되었다면 그냥 통과
                  > Token값을 빼내서, Authentication 객체를 만들어서
                       SecurityContextHolder 안의 SecurityContext 객체 안에 
                       Authentication 객체 주소값을 담는다.
- (BE) SecurityContext 객체 안에 Authentication 객체 주소값이 있으면 로그인되었다라고
        판단, 없으면 비로그인 상태라고 판단
