package yerong.wedle.category.restaurant.domain;

public enum MenuCategory {
    POPULAR_MENU("인기메뉴"),
    RECOMMENDED_MENU("추천메뉴"),
    ADDITIONAL_MENU("추가메뉴"),
    SHAVED_ICE("빙수"),
    PORRIDGE("죽"),
    BOTTLED_DRINKS("병과"),
    COFFEE("커피"),
    TEA("차"),
    BEVERAGES("음료"),
    MAIN_MENU("메인메뉴"),
    DUMPLINGS("만두"),
    MEALS("식사"),
    SIDE_DISHES("사이드"),
    ALCOHOL("주류"),
    MEAT("고기"),
    TTEOKBOKKI_SET("떡볶이세트"),
    DESSERTS("디저트"),
    OTHER("기타"),
    CURRY("카레"),
    BULGOGI("불고기"),
    NOODLES_AND_SOUPS("냉면및국수"),
    SOUPS("탕및국"),
    KIDS_AND_SIDES("어린이및사이드"),
    SOONDUBU_AND_GUKBAP("순대및국밥"),
    COLD_NOODLES("냉면"),
    BOILED_MEAT_AND_STEW("수육및전골"),
    SWEET_AND_SOUR_PORK("탕수육"),
    SHRIMP_DISHES("새우류"),
    CHICKEN_DISHES("닭고기류"),
    RICE_DISHES("식사류"),
    SUSHI("초밥"),
    OKONOMIYAKI("오코노미야키"),
    YAKISOBA("야키소바"),
    UDON("우동"),
    CHARCOAL_GRILLED_MENU("숯불메뉴"),
    STIR_FRIED_MENU("볶음메뉴"),
    STEW_MENU("전골메뉴"),
    ADDITIONAL("추가"),
    SHABU_SET("샤브세트");

    private final String displayName;

    MenuCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MenuCategory fromDisplayName(String displayName) {
        for (MenuCategory category : MenuCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}