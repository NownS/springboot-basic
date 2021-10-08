# [3W4D] Mission

고객 블랙리스트 명단을 작성해본다.

- `customer_blacklist.csv`파일을 만들고 Spring Application에서 해당 파일을 읽을 수 있고 블랙리스트를 조회할 수 있도록 설계한다.  
  (추가할 필요는 없고, 블랙리스트는 파일로만 관리된다고 가정한다.)
- `YAML properties`를 만들고 어떤 설정을 만들 수 있을지 고민해 본다.
- 바우처를 메모리에서 관리하는 Repository는 **개발 profile에서만 동작**하게 한다.
  <br/>

## 구현

- customer_blacklist.csv 파일 생성  
  ![image](https://user-images.githubusercontent.com/60170616/132816062-8036187c-a681-4f47-bda1-fbd8ee232b24.png)
- blacklist 조회  
  ![image](https://user-images.githubusercontent.com/60170616/132816372-dfdcccde-3b89-4512-a1b7-edb2d86e6952.png)

___