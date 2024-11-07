package org.sparta.batch.domain.store.category;

import java.util.List;

public interface HierarchicalCategory extends CategoryMapper {

    /**
     * 자식 카테고리 목록 반환
     * @return
     */
    List<HierarchicalCategory> getChildren();

    /**
     * 부모 카테고리 반환
     * @return
     */
    HierarchicalCategory getParent();

    /**
     * 해당 카테고리의 루트 카테고리 반환
     * @return
     */
    HierarchicalCategory getRoot();

    /**
     * 해당 카테고리가 루트 카테고리인지 검사
     * @return
     */
    boolean isRoot();

    /**
     * 해당 카테고리가 리프 카테고리인지 검사
     * @return
     */
    boolean isLeaf();
}
