# w3-SpringBoot_Part_A  
## 1W.2D Mission 내용  
바우처 관리 Command-line Application을 만들어본다. (https://dzone.com/articles/interactive-console-applications-in-java)  
- CommandLineApplication 클래스를 작성한다.
- AnnotationConfigApplicationContext 를 이용해서 IoC 컨테이너를 생성하고 서비스, 레포지토리를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

```bash
=== Voucher Program ===
Type **exit** to exit the program.
Type **create** to create a new voucher.
Type **list** to list all vouchers.
```

- create / list 커맨드를 지원한다.
    - create 커맨드를 통해 바우처를 생성할수 있습니다. (FixedAmountVoucher, PercentDiscountVoucher)
    - list 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
- 바우처를 매모리에 관리해세요. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. → 나중에 영속성을 가지도록 변경할거에요 걱정마세요!  
  
  
  