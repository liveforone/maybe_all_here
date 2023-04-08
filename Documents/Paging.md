## 문제 발생
* 상품의 수는 늘어나며, 상품 홈이나, 검색 시에 엄청난 양의 데이터를 리턴한다.
* 그러다보니 조회할때 성능이 많이 떨어지는 문제가 발생했다.
* 당연히 인덱스는 적절히 걸려있었고, 다른 방법으로 해결해야했다.

## 해결 : No Offset
* 참신하고 좋은 해결책이 바로 no offset이었다.
* no offset은 쿼리로 풀면 아래와 같다.
```
SELECT *
FROM items
WHERE 조건문
AND id < 마지막조회ID # 직전 조회 결과의 마지막 id
ORDER BY id DESC
LIMIT 페이지사이즈
```
* 이렇게 하면 기존의 페이징처럼 큐와 같이 데이터가 쌓여 가는 구조가 아닌,
* 필요한 부분만 가져오는 형태로 바뀌어 성능이 크게 향상된다.

## Controller 에서 구현
* 쿼리 스트링을 사용하여 마지막 데이터의 id와
* 페이지 사이즈를 입력받았다.
* 약간 더 응용하면 페이지 사이즈를 사용자가 지정하는 방식이 아니라 정책상 정해져있다면
* 이를 쿼리 스트링 으로 받을 필요없이 repository에서 limit값으로 고정해버려도 된다.
```
@GetMapping("/my-record/{bankBookNum}")
public ResponseEntity<?> getMyRecord(
        @PathVariable String bankBookNum,
        @RequestParam(name = "lastId") Long lastId,
        @RequestParam(name = "pageSize") int pageSize
) {
    List<RecordResponse> records = recordService.getRecordsByBankBookNum(bankBookNum, lastId, pageSize);

    return ResponseEntity.ok(records);
}
```

## Repository에서 구현
* desc일 경우 lt(id)
* asc일 경우 gt(id)를 사용하면 된다.
```
public List<Record> findRecordsByBankBookNum(String bankBookNum, Long recordId, int pageSize) {
    return queryFactory.selectFrom(record)
            .where(
                record.bankBookNum.eq(bankBookNum),
                ltBookId(recordId)
            )
            .orderBy(record.id.desc())
            .limit(pageSize)
            .fetch();
}

private BooleanExpression ltBookId(Long recordId) {
    if (recordId == null) {
        return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
    }

    return record.id.lt(recordId);
}
```