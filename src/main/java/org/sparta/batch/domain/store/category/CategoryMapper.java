package org.sparta.batch.domain.store.category;

public interface CategoryMapper {

    /**
     * 카테고리 코드 반환
     * @return
     */
    String getCode();

    /**
     * 카테고리 이름 반환
     * @return
     */
    String getName();

    /**
     * 카테고리 경로 반환
     * @return
     */
    String getPath();

    /**
     * 카테고리 깊이 반환
     * @return
     */
    int getDepth();
}