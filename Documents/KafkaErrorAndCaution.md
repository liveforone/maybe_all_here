## Kafka 에러
* kafka에서 자주 발생하고, 대표적인 에러는 로그가 있다.
* 콘솔창에서(웹 어플리케이션) 로그가 미친듯이 주르륵 내려가는 현상이다.
* 이 경우 kafka와 zookeeper 로그가 저장된 폴더(필자의 경우 var이다)에서 두 로그 기록을 지우면 해결이 된다.
* 로그를 지운 후 카프카와 zookeeper를 다시 시작하면 정상 복구 된다.

## 카프카 설정 시 주의 사항
* 메세지를 직렬/역질력화 하는 Serializer와 Deserializer는  
* import org.apache.kafka.common.serialization... 을 사용해야한다. 이와 비슷한 jackson을 사용하면 에러가 발생한다.
```
[예시]
import org.apache.kafka.common.serialization.StringDeserializer;
```