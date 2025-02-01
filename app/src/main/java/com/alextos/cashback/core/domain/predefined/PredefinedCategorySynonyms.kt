package com.alextos.cashback.core.domain.predefined

val PredefinedCategory.synonyms: List<String>
    get() = when (this) {
        PredefinedCategory.ALL_PURCHASES -> listOf("За все покупки", "На все покупки", "На всё")
        PredefinedCategory.EDUCATION -> listOf("Курсы")
        PredefinedCategory.AIRPLANE_TICKETS -> listOf("Авиаперелеты")
        PredefinedCategory.VIP_ZONE -> emptyList()
        PredefinedCategory.CAR_RENT -> listOf("Прокат авто", "Автопрокат")
        PredefinedCategory.RAILROAD_TICKETS -> listOf(
            "Железные дороги",
            "Железнодорожные билеты",
            "Жд билеты"
        )

        PredefinedCategory.TOUR_AGENCIES -> listOf("Туризм")
        PredefinedCategory.CRUISES -> emptyList()
        PredefinedCategory.HOTELS -> emptyList()
        PredefinedCategory.DUTY_FREE_SHOPS -> listOf("Duty Free")
        PredefinedCategory.TRANSPORT -> emptyList()
        PredefinedCategory.TAXI -> listOf("Yandex Go", "Яндекс Go", "Такси и каршеринг")
        PredefinedCategory.CAR_SHARING -> listOf("Такси и каршеринг")
        PredefinedCategory.AUTO_SERVICES -> listOf("Автозапчасти", "Автосервис")
        PredefinedCategory.PHARMACIES -> listOf("Аптека", "Фармацея")
        PredefinedCategory.BEAUTY_AND_SPA -> listOf("Салоны красоты", "Барбершопы", "Красота")
        PredefinedCategory.COSMETICS_AND_PARFUME -> listOf(
            "Косметика и парфюмерия",
            "Магазины косметики"
        )

        PredefinedCategory.HOUSE_AND_RENOVATION -> listOf("Товары для дома")
        PredefinedCategory.ART -> listOf("Культура и искусство")
        PredefinedCategory.BOOKS -> listOf(
            "Книжные магазины",
            "Книжные лавки",
            "Канцтовары",
            "Книги и канцтовары"
        )

        PredefinedCategory.ZOO_GOODS -> listOf(
            "Зоомагазины",
            "Товары для животных",
            "Магазины для животных",
            "Животные",
            "Питомцы",
            "Домашние животные"
        )

        PredefinedCategory.CINEMA -> listOf("Кинотеатры")
        PredefinedCategory.ENTERTAINMENT -> emptyList()
        PredefinedCategory.EXHIBITIONS_AND_MUSEUMS -> listOf(
            "Выставки",
            "Музеи",
            "Музеи и выставки"
        )

        PredefinedCategory.MEDICINE_SERVICES -> emptyList()
        PredefinedCategory.FITNESS -> listOf("Спортзалы", "Спортивные залы")
        PredefinedCategory.CLINICS_AND_ESTHETICS -> emptyList()
        PredefinedCategory.DENTIST -> listOf("Стоматология", "Стоматологи")
        PredefinedCategory.MUSIC -> listOf("Магазины музыки")
        PredefinedCategory.SUPERMARKETS -> emptyList()
        PredefinedCategory.SHOPS -> emptyList()
        PredefinedCategory.ELECTRONIC_DEVICES -> listOf(
            "Магазины бытовой техники",
            "Магазины электроники"
        )

        PredefinedCategory.RESTAURANTS -> listOf("Кафе и рестораны", "Бары")
        PredefinedCategory.FASTFOOD -> emptyList()
        PredefinedCategory.COMMUNICATION_AND_TELECOM -> emptyList()
        PredefinedCategory.PHOTO_AND_VIDEO -> emptyList()
        PredefinedCategory.SOUVENIRS -> listOf("Сувенирные магазины")
        PredefinedCategory.FLOWERS -> listOf("Магазины цветов", "Цветочные")
        PredefinedCategory.SPORT_GOODS -> listOf("Магазины спортивных товаров", "Спортивные товары")
        PredefinedCategory.CLOTHES_AND_SHOES -> listOf(
            "Аксессуары",
            "Магазины одежды",
            "Магазины обуви"
        )

        PredefinedCategory.SBP -> listOf("СБП")
        PredefinedCategory.DIGITAL_GOODS -> listOf("Цифровой контент")
        PredefinedCategory.YANDEX -> emptyList()
        PredefinedCategory.BURGER_KING -> listOf("Burger King")
        PredefinedCategory.GAS_STATION -> listOf("Топливо", "Автозаправки", "АЗС", "Бензин")
        PredefinedCategory.COMMUNAL_SERVICES -> listOf("ЖКУ")
        PredefinedCategory.ACCESSORIES -> emptyList()
    }