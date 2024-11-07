package org.sparta.batch.domain.store.category;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HierarchicalCategoryUtils {

    /**
     * 해당 카테고리의 해당 깊이 카테고리 목록 반환
     * @param type
     * @param depth
     * @return
     * @param <T>
     */
    public static  <T extends Enum<T> & HierarchicalCategory> List<HierarchicalCategory> getCategoriesByDepth(Class<T> type, int depth) {
        List<HierarchicalCategory> categories = new ArrayList<>();
        T[] enumConstants = type.getEnumConstants();
        for(T e : enumConstants) {
            if( e.getDepth() == depth ) {
                categories.add(e);
            }
        }

        return categories;
    }

    /**
     * 해당 카테고리 타입의 루트 카테고리 반환
     * @param type
     * @return
     * @param <T>
     */
    public static <T extends Enum<T> & HierarchicalCategory> HierarchicalCategory getRootCategory(Class<T> type) {
        T[] enumConstants = type.getEnumConstants();

        // 0번이 최상위 노드
        return enumConstants[0];
    }

    /**
     * 하위 카테고리의 이름 목록 반환
     * @param category
     * @return
     */
    public static List<String> getChildrenNames(HierarchicalCategory category) {
        return category.getChildren().stream().map(HierarchicalCategory::getName).toList();
    }

    /**
     * 하위 카테고리의 코드 목록 반환
     * @param category
     * @return
     */
    public static List<String> getChildrenCodes(HierarchicalCategory category) {
        return category.getChildren().stream().map(HierarchicalCategory::getCode).toList();
    }

    /**
     * 하위 카테고리의 카테고리 경로 목록 반환
     * @param category
     * @return
     */
    public static List<String> getChildrenPaths(HierarchicalCategory category) {
        return category.getChildren().stream().map(HierarchicalCategory::getPath).toList();
    }



    /**
     * 해당 노드의 하위 카테고리 전부 가져옵니다.
     * @param node 루트 노드
     * @return
     */
    public static List<HierarchicalCategory> getChildCategories(HierarchicalCategory node){
        List<HierarchicalCategory> childCategories = new ArrayList<>();
        traverse(node, childCategories::add);
        return childCategories;
    }

    /**
     * DFS로 순회하면서 action 실행
     * @param node
     * @param action
     */
    private static void traverse(HierarchicalCategory node, Consumer<HierarchicalCategory> action) {
        action.accept(node);
        for (HierarchicalCategory child : node.getChildren()) {
            traverse(child, action);
        }
    }
}
