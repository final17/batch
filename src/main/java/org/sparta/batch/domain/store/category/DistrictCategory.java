package org.sparta.batch.domain.store.category;

import org.sparta.batch.domain.store.category.HierarchicalCategory;

import java.util.ArrayList;
import java.util.List;

public enum DistrictCategory implements HierarchicalCategory {
    ROOT("region", "지역", "R", null),

    // 서울
    SEOUL("district", "서울", "01", ROOT),
    SEOUL_JONGNO("legal_district", "종로", "1", SEOUL),
    SEOUL_JUNG("legal_district", "중구", "2", SEOUL),
    SEOUL_YONGSAN("legal_district", "용산", "3", SEOUL),
    SEOUL_SEONGDONG("legal_district", "성동", "4", SEOUL),
    SEOUL_GWANGJIN("legal_district", "광진구", "5", SEOUL),
    SEOUL_DONGDAEMUN("legal_district", "동대문", "6", SEOUL),
    SEOUL_JUNGNANG("legal_district", "중랑", "7", SEOUL),
    SEOUL_SEONGBUK("legal_district", "성북", "8", SEOUL),
    SEOUL_GANGBUK("legal_district", "강북", "9", SEOUL),
    SEOUL_DOBONG("legal_district", "도봉", "10", SEOUL),
    SEOUL_NOWON("legal_district", "노원", "11", SEOUL),
    SEOUL_EUNPYEONG("legal_district", "은평", "12", SEOUL),
    SEOUL_SEODAEMUN("legal_district", "서대문", "13", SEOUL),
    SEOUL_MAPO("legal_district", "마포", "14", SEOUL),
    SEOUL_YANGCHEON("legal_district", "양천", "15", SEOUL),
    SEOUL_GANGSEO("legal_district", "강서", "16", SEOUL),
    SEOUL_GURO("legal_district", "구로", "17", SEOUL),
    SEOUL_GEUMCHEON("legal_district", "금천", "18", SEOUL),
    SEOUL_YEONGDEUNGPO("legal_district", "영등포", "19", SEOUL),
    SEOUL_DONGJAK("legal_district", "동작", "20", SEOUL),
    SEOUL_GWANAK("legal_district", "관악", "21", SEOUL),
    SEOUL_SEOCHO("legal_district", "서초", "22", SEOUL),
    SEOUL_GANGNAM("legal_district", "강남", "23", SEOUL),
    SEOUL_SONGPA("legal_district", "송파", "24", SEOUL),
    SEOUL_GANGDONG("legal_district", "강동", "25", SEOUL),

    // 경기도
    GYEONGGI("district", "경기도", "02", ROOT),
    GYEONGGI_SUWON("legal_district", "수원", "1", GYEONGGI),
    GYEONGGI_SEONGNAM("legal_district", "성남", "2", GYEONGGI),
    GYEONGGI_GOYANG("legal_district", "고양", "3", GYEONGGI),
    GYEONGGI_YONGIN("legal_district", "용인", "4", GYEONGGI),
    GYEONGGI_BUCHEON("legal_district", "부천", "5", GYEONGGI),
    GYEONGGI_ANSAN("legal_district", "안산", "6", GYEONGGI),
    GYEONGGI_ANYANG("legal_district", "안양", "7", GYEONGGI),
    GYEONGGI_UIJEONGBU("legal_district", "의정부", "8", GYEONGGI),
    GYEONGGI_PYEONGTAEK("legal_district", "평택", "9", GYEONGGI),
    GYEONGGI_GWANGMYEONG("legal_district", "광명", "10", GYEONGGI),
    GYEONGGI_POCHEON("legal_district", "포천", "11", GYEONGGI),
    GYEONGGI_GIMPO("legal_district", "김포", "12", GYEONGGI),
    GYEONGGI_GWANGJU("legal_district", "광주", "13", GYEONGGI),
    GYEONGGI_GURI("legal_district", "구리", "14", GYEONGGI),
    GYEONGGI_NAMYANGJU("legal_district", "남양주", "15", GYEONGGI),
    GYEONGGI_OSAN("legal_district", "오산", "16", GYEONGGI),
    GYEONGGI_SIHEUNG("legal_district", "시흥", "17", GYEONGGI),
    GYEONGGI_GUNPO("legal_district", "군포", "18", GYEONGGI),
    GYEONGGI_HWASEONG("legal_district", "화성", "18", GYEONGGI),
    GYEONGGI_HANAM("legal_district", "하남", "19", GYEONGGI),
    GYEONGGI_ICHEON("legal_district", "이천", "20", GYEONGGI),
    GYEONGGI_YANGJU("legal_district", "양주", "21", GYEONGGI),
    GYEONGGI_YEOJU("legal_district", "여주", "22", GYEONGGI),
    GYEONGGI_YANGPYEONG("legal_district", "양평", "23", GYEONGGI),
    GYEONGGI_PAJU("legal_district", "파주", "24", GYEONGGI),
    GYEONGGI_GAPYEONG("legal_district", "가평", "25", GYEONGGI),
    GYEONGGI_YEONCHEON("legal_district", "연천", "26", GYEONGGI),

    // 세종특별자치시
    SEJONG("district", "세종", "03", ROOT),

    // 인천광역시
    INCHEON("district", "인천", "04", ROOT),
    INCHEON_JUNG("legal_district", "중구", "1", INCHEON),
    INCHEON_DONG("legal_district", "동구", "2", INCHEON),
    INCHEON_MICHUHOL("legal_district", "미추홀", "3", INCHEON),
    INCHEON_YEONSU("legal_district", "연수", "4", INCHEON),
    INCHEON_NAMDONG("legal_district", "남동", "5", INCHEON),
    INCHEON_BUPYEONG("legal_district", "부평", "6", INCHEON),
    INCHEON_GYEYANG("legal_district", "계양", "7", INCHEON),
    INCHEON_SEO("legal_district", "서구", "8", INCHEON),
    INCHEON_GANGHWA("legal_district", "강화", "9", INCHEON),
    INCHEON_ONGJIN("legal_district", "옹진", "10", INCHEON),

    // 부산광역시
    BUSAN("district", "부산", "05", ROOT),
    BUSAN_JUNG("legal_district", "중구", "1", BUSAN),
    BUSAN_SEO("legal_district", "서구", "2", BUSAN),
    BUSAN_DONG("legal_district", "동구", "3", BUSAN),
    BUSAN_YEONGDO("legal_district", "영도", "4", BUSAN),
    BUSAN_BUSANJIN("legal_district", "부산진", "5", BUSAN),
    BUSAN_DONGNAE("legal_district", "동래구", "6", BUSAN),
    BUSAN_NAM("legal_district", "남구", "7", BUSAN),
    BUSAN_BUK("legal_district", "북구", "8", BUSAN),
    BUSAN_HAEUNDAE("legal_district", "해운대", "9", BUSAN),
    BUSAN_SAHA("legal_district", "사하", "10", BUSAN),
    BUSAN_GEUMJEONG("legal_district", "금정", "11", BUSAN),
    BUSAN_GANGSEO("legal_district", "강서", "12", BUSAN),
    BUSAN_YEONJE("legal_district", "연제", "13", BUSAN),
    BUSAN_SUYEONG("legal_district", "수영", "14", BUSAN),
    BUSAN_SASANG("legal_district", "사상", "15", BUSAN),
    BUSAN_GIJANG("legal_district", "기장", "16", BUSAN),

    // 대구광역시
    DAEGU("district", "대구", "06", ROOT),
    DAEGU_JUNG("legal_district", "중구", "1", DAEGU),
    DAEGU_DONG("legal_district", "동구", "2", DAEGU),
    DAEGU_SEO("legal_district", "서구", "3", DAEGU),
    DAEGU_NAM("legal_district", "남구", "4", DAEGU),
    DAEGU_BUK("legal_district", "북구", "5", DAEGU),
    DAEGU_SUSUNG("legal_district", "수성", "6", DAEGU),
    DAEGU_DALSEO("legal_district", "달서", "7", DAEGU),
    DAEGU_DALSEONG("legal_district", "달성", "8", DAEGU),

    // 광주광역시
    GWANGJU("district", "광주", "07", ROOT),
    GWANGJU_DONG("legal_district", "동구", "1", GWANGJU),
    GWANGJU_SEO("legal_district", "서구", "2", GWANGJU),
    GWANGJU_NAM("legal_district", "남구", "3", GWANGJU),
    GWANGJU_BUK("legal_district", "북구", "4", GWANGJU),
    GWANGJU_GWANGSAN("legal_district", "광산", "5", GWANGJU),

    // 대전광역시
    DAEJEON("district", "대전", "08", ROOT),
    DAEJEON_DONG("legal_district", "동구", "1", DAEJEON),
    DAEJEON_JUNG("legal_district", "중구", "2", DAEJEON),
    DAEJEON_SEO("legal_district", "서구", "3", DAEJEON),
    DAEJEON_YUSEONG("legal_district", "유성", "4", DAEJEON),
    DAEJEON_DAEDEOK("legal_district", "대덕", "5", DAEJEON),

    // 울산광역시
    ULSAN("district", "울산", "09", ROOT),
    ULSAN_JUNG("legal_district", "중구", "1", ULSAN),
    ULSAN_NAM("legal_district", "남구", "2", ULSAN),
    ULSAN_DONG("legal_district", "동구", "3", ULSAN),
    ULSAN_BUK("legal_district", "북구", "4", ULSAN),
    ULSAN_ULJU("legal_district", "울주", "5", ULSAN),

    // 강원도
    GANGWON("district", "강원도", "10", ROOT),
    GANGWON_CHUNCHEON("legal_district", "춘천", "1", GANGWON),
    GANGWON_WONJU("legal_district", "원주", "2", GANGWON),
    GANGWON_GANGNEUNG("legal_district", "강릉", "3", GANGWON),
    GANGWON_DONGHAE("legal_district", "동해", "4", GANGWON),
    GANGWON_TAEBAEK("legal_district", "태백", "5", GANGWON),
    GANGWON_SOKCHO("legal_district", "속초", "6", GANGWON),
    GANGWON_SAMCHEOK("legal_district", "삼척", "7", GANGWON),
    GANGWON_HONGCHEON("legal_district", "홍천", "8", GANGWON),
    GANGWON_HOENGSEONG("legal_district", "횡성", "9", GANGWON),
    GANGWON_YEONGWOL("legal_district", "영월", "10", GANGWON),
    GANGWON_PYEONGCHANG("legal_district", "평창", "11", GANGWON),
    GANGWON_JEONGSEON("legal_district", "정선", "12", GANGWON),
    GANGWON_CHEORWON("legal_district", "철원", "13", GANGWON),
    GANGWON_INJE("legal_district", "인제", "14", GANGWON),
    GANGWON_GOSEONG("legal_district", "고성", "15", GANGWON),
    GANGWON_YANGYANG("legal_district", "양양", "16", GANGWON),

    // 충청북도
    CHUNGBUK("district", "충청북도", "11", ROOT),
    CHUNGBUK_CHEONGJU("legal_district", "청주", "1", CHUNGBUK),
    CHUNGBUK_CHUNGJU("legal_district", "충주", "2", CHUNGBUK),
    CHUNGBUK_JECHEON("legal_district", "제천", "3", CHUNGBUK),
    CHUNGBUK_BOEUN("legal_district", "보은", "4", CHUNGBUK),
    CHUNGBUK_OKCHEON("legal_district", "옥천", "5", CHUNGBUK),
    CHUNGBUK_YEONGDONG("legal_district", "영동", "6", CHUNGBUK),
    CHUNGBUK_JEUNGPYEONG("legal_district", "증평", "7", CHUNGBUK),

    // 충청남도
    CHUNGNAM("district", "충청남도", "12", ROOT),
    CHUNGNAM_CHEONAN("legal_district", "천안", "1", CHUNGNAM),
    CHUNGNAM_GONGJU("legal_district", "공주", "2", CHUNGNAM),
    CHUNGNAM_BORYEONG("legal_district", "보령", "3", CHUNGNAM),
    CHUNGNAM_ASAN("legal_district", "아산", "4", CHUNGNAM),
    CHUNGNAM_SEOSAN("legal_district", "서산", "5", CHUNGNAM),
    CHUNGNAM_NONSAN("legal_district", "논산", "6", CHUNGNAM),
    CHUNGNAM_GYERYONG("legal_district", "계룡", "7", CHUNGNAM),
    CHUNGNAM_DANGJIN("legal_district", "당진", "8", CHUNGNAM),
    CHUNGNAM_TAEAN("legal_district", "태안", "9", CHUNGNAM),
    CHUNGNAM_GEUMSAN("legal_district", "금산", "10", CHUNGNAM),

    // 전라북도
    JEONBUK("district", "전라북도", "13", ROOT),
    JEONBUK_JEONJU("legal_district", "전주", "1", JEONBUK),
    JEONBUK_GUNSAN("legal_district", "군산", "2", JEONBUK),
    JEONBUK_IKSAN("legal_district", "익산", "3", JEONBUK),
    JEONBUK_JEONGEUP("legal_district", "정읍", "4", JEONBUK),
    JEONBUK_NAMWON("legal_district", "남원", "5", JEONBUK),
    JEONBUK_GIMJE("legal_district", "김제", "6", JEONBUK),

    // 전라남도
    JEONNAM("district", "전라남도", "14", ROOT),
    JEONNAM_MOKPO("legal_district", "목포", "1", JEONNAM),
    JEONNAM_YEOSU("legal_district", "여수", "2", JEONNAM),
    JEONNAM_SUNCHEON("legal_district", "순천", "3", JEONNAM),
    JEONNAM_GWANGYANG("legal_district", "광양", "4", JEONNAM),
    JEONNAM_NAJU("legal_district", "나주", "5", JEONNAM),
    JEONNAM_DAMYANG("legal_district", "담양", "6", JEONNAM),
    JEONNAM_GOKSEONG("legal_district", "곡성", "7", JEONNAM),
    JEONNAM_GURYE("legal_district", "구례", "8", JEONNAM),
    JEONNAM_GOHEUNG("legal_district", "고흥", "9", JEONNAM),
    JEONNAM_BOSEONG("legal_district", "보성", "10", JEONNAM),
    JEONNAM_HWA("legal_district", "화순", "11", JEONNAM),
    JEONNAM_JANGHEUNG("legal_district", "장흥", "12", JEONNAM),
    JEONNAM_GANGJIN("legal_district", "강진", "13", JEONNAM),
    JEONNAM_HAENAM("legal_district", "해남", "14", JEONNAM),
    JEONNAM_YEONGAM("legal_district", "영암", "15", JEONNAM),
    JEONNAM_MUAN("legal_district", "무안", "16", JEONNAM),
    JEONNAM_HAMPYEONG("legal_district", "함평", "17", JEONNAM),
    JEONNAM_YEONGGWANG("legal_district", "영광", "18", JEONNAM),
    JEONNAM_JANGSEONG("legal_district", "장성", "19", JEONNAM),
    JEONNAM_WANDO("legal_district", "완도", "20", JEONNAM),
    JEONNAM_JINDO("legal_district", "진도", "21", JEONNAM),
    JEONNAM_SHINAN("legal_district", "신안", "22", JEONNAM),

    // 경상북도
    GYEONGBUK("district", "경상북도", "15", ROOT),
    GYEONGBUK_POHANG("legal_district", "포항", "1", GYEONGBUK),
    GYEONGBUK_GYEONGJU("legal_district", "경주", "2", GYEONGBUK),
    GYEONGBUK_GIMCHEON("legal_district", "김천", "3", GYEONGBUK),
    GYEONGBUK_ANDONG("legal_district", "안동", "4", GYEONGBUK),
    GYEONGBUK_GUMI("legal_district", "구미", "5", GYEONGBUK),
    GYEONGBUK_YEONGCHEON("legal_district", "영천", "6", GYEONGBUK),
    GYEONGBUK_YEONGJU("legal_district", "영주", "7", GYEONGBUK),
    GYEONGBUK_SANGJU("legal_district", "상주", "8", GYEONGBUK),
    GYEONGBUK_MUNGYEONG("legal_district", "문경", "9", GYEONGBUK),
    GYEONGBUK_GYEONGSAN("legal_district", "경산", "10", GYEONGBUK),
    GYEONGBUK_UISUNG("legal_district", "의성", "11", GYEONGBUK),
    GYEONGBUK_CHEONGSONG("legal_district", "청송", "12", GYEONGBUK),
    GYEONGBUK_YEONGYANG("legal_district", "영양", "13", GYEONGBUK),
    GYEONGBUK_YEONGDUK("legal_district", "영덕", "14", GYEONGBUK),
    GYEONGBUK_CHEONGDO("legal_district", "청도", "15", GYEONGBUK),
    GYEONGBUK_GORYEONG("legal_district", "고령", "16", GYEONGBUK),
    GYEONGBUK_SEONGJU("legal_district", "성주", "17", GYEONGBUK),
    GYEONGBUK_CHILGOK("legal_district", "칠곡", "18", GYEONGBUK),
    GYEONGBUK_YECHON("legal_district", "예천", "19", GYEONGBUK),
    GYEONGBUK_BONGHWA("legal_district", "봉화", "20", GYEONGBUK),
    GYEONGBUK_ULJIN("legal_district", "울진", "21", GYEONGBUK),
    GYEONGBUK_ULLEUNG("legal_district", "울릉", "22", GYEONGBUK),

    // 경상남도
    GYEONGNAM("district", "경상남도", "16", ROOT),
    GYEONGNAM_CHANGWON("legal_district", "창원", "1", GYEONGNAM),
    GYEONGNAM_JINJU("legal_district", "진주", "2", GYEONGNAM),
    GYEONGNAM_TONGYEONG("legal_district", "통영", "3", GYEONGNAM),
    GYEONGNAM_SACHEON("legal_district", "사천", "4", GYEONGNAM),
    GYEONGNAM_GIMHAE("legal_district", "김해", "5", GYEONGNAM),
    GYEONGNAM_MIRYANG("legal_district", "밀양", "6", GYEONGNAM),
    GYEONGNAM_UIREONG("legal_district", "의령", "7", GYEONGNAM),
    GYEONGNAM_HAMAN("legal_district", "함안", "8", GYEONGNAM),
    GYEONGNAM_CHANGNYEONG("legal_district", "창녕", "9", GYEONGNAM),
    GYEONGNAM_HADONG("legal_district", "하동", "10", GYEONGNAM),
    GYEONGNAM_NAMHAE("legal_district", "남해", "11", GYEONGNAM),
    GYEONGNAM_HAMYANG("legal_district", "함양", "12", GYEONGNAM),
    GYEONGNAM_SANCHEONG("legal_district", "산청", "13", GYEONGNAM),
    GYEONGNAM_GEOTCHAN("legal_district", "거창", "14", GYEONGNAM),
    GYEONGNAM_HAPCHEON("legal_district", "합천", "15", GYEONGNAM),

    // 제주도
    JEJU("district", "제주도", "17", ROOT),
    JEJU_JEJU("legal_district", "제주", "1", JEJU),
    JEJU_SEOGWIPO("legal_district", "서귀포", "2", JEJU);



    private final String type;
    private final String name;
    private final String code;
    private final HierarchicalCategory parent;
    private final List<HierarchicalCategory> children;
    private final int depth;

    DistrictCategory(String type, String name, String code, HierarchicalCategory parent) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        this.code = code;
        children = new ArrayList<>();

        // root
        if(parent == null){
            depth = 0;
        } else {
            depth = 1 + parent.getDepth();
            parent.getChildren().add(this);
        }
    }

    @Override
    public String getCode() {
        if(parent == null) return code;

        return parent.getCode()  + code;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        if(parent == ROOT || parent == null) return name;

        return parent.getPath() + "/" + name;
    }

    @Override
    public int getDepth() {
        return depth;
    }


    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public boolean isRoot() {
        return this == ROOT || getParent() == ROOT;
    }

    @Override
    public List<HierarchicalCategory> getChildren() {
        return children;
    }

    @Override
    public HierarchicalCategory getParent() {
        return parent;
    }

    @Override
    public HierarchicalCategory getRoot() {
        HierarchicalCategory t = this;
        while(!t.isRoot()) {
            t = t.getParent();
        }

        return t;
    }
}
