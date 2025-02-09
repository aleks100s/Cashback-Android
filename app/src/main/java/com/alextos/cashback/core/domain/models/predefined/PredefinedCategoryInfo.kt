package com.alextos.cashback.core.domain.models.predefined

val PredefinedCategory.info: String?
    get() = when (this) {
        PredefinedCategory.ALL_PURCHASES -> null
        PredefinedCategory.EDUCATION -> "Школы, колледжи и университеты, бизнес-курсы и образовательные услуги"
        PredefinedCategory.AIRPLANE_TICKETS -> null
        PredefinedCategory.VIP_ZONE -> null
        PredefinedCategory.CAR_RENT -> "Прокат автомобилей, домов на колесах, трейлеров и грузовиков"
        PredefinedCategory.RAILROAD_TICKETS -> "Билеты на поезда дальнего следования"
        PredefinedCategory.TOUR_AGENCIES -> null
        PredefinedCategory.CRUISES -> null
        PredefinedCategory.HOTELS -> "Гостиничные номера, отели, мотели, курорты, рекреационные и спортивные лагеря, кемпинги"
        PredefinedCategory.DUTY_FREE_SHOPS -> "Беспошлинные магазины Duty Free"
        PredefinedCategory.TRANSPORT -> "Городской и междугородный транспорт, такси, стоянка автомобилей; продажа снегоходов, автодомов и прицепов"
        PredefinedCategory.TAXI -> null
        PredefinedCategory.CAR_SHARING -> null
        PredefinedCategory.AUTO_SERVICES -> "Продажа, лизинг, сервис и ремонт автомобилей, магазины автозапчастей, мотоциклов, автомойки"
        PredefinedCategory.PHARMACIES -> null
        PredefinedCategory.BEAUTY_AND_SPA -> "Парикмахерские и салоны красоты, массаж и магазины косметики, СПА"
        PredefinedCategory.COSMETICS_AND_PARFUME -> null
        PredefinedCategory.HOUSE_AND_RENOVATION -> "Магазины строительных материалов, мебели, хозтоваров, например IKEA и «Леруа Мерлен»"
        PredefinedCategory.ART -> "Антикварные магазины, магазины художественных изделий и мастерства, картинные галереи; магазины марок и монет; религиозные товары"
        PredefinedCategory.BOOKS -> "Книжные магазины и канцтовары"
        PredefinedCategory.ZOO_GOODS -> "Зоомагазины и ветеринарные услуги"
        PredefinedCategory.CINEMA -> "Кинотеатры и прокат фильмов"
        PredefinedCategory.ENTERTAINMENT -> "Билеты в театр и на концерты, боулинг, бильярд, спортивные клубы; аквариумы, дельфинарии и зоопарки, парки аттракционов, гольф-поля"
        PredefinedCategory.EXHIBITIONS_AND_MUSEUMS -> null
        PredefinedCategory.MEDICINE_SERVICES -> "Скорая помощь, больницы, стоматологические клиники, медицинские лаборатории, ортопедические салоны и оптика"
        PredefinedCategory.FITNESS -> null
        PredefinedCategory.CLINICS_AND_ESTHETICS -> null
        PredefinedCategory.DENTIST -> null
        PredefinedCategory.MUSIC -> "Магазины музыкальных инструментов, студии звукозаписи"
        PredefinedCategory.SUPERMARKETS -> "Супер- и гипермаркеты"
        PredefinedCategory.SHOPS -> "Универмаги, непродовольственные товары, ломбарды, канцтовары, магазины игрушек"
        PredefinedCategory.ELECTRONIC_DEVICES -> "Компьютеры, офисная и бытовая техника"
        PredefinedCategory.RESTAURANTS -> "Кафе, рестораны, столовые, бары и ночные клубы"
        PredefinedCategory.FASTFOOD -> null
        PredefinedCategory.COMMUNICATION_AND_TELECOM -> "Телефония, денежные переводы, обработка данных, поиск информации, ремонт компьютеров"
        PredefinedCategory.PHOTO_AND_VIDEO -> "Фотостудии и фотолаборатории, копировальные услуги"
        PredefinedCategory.SOUVENIRS -> "Магазины подарков, сувениров и открыток"
        PredefinedCategory.FLOWERS -> "Магазины цветов, товары для флористики"
        PredefinedCategory.SPORT_GOODS -> "Спортивная одежда, веломагазины и другие спорттовары"
        PredefinedCategory.CLOTHES_AND_SHOES -> "Магазины одежды и обуви, ювелирные магазины, аксессуары, изделия из кожи и меха"
        PredefinedCategory.SBP -> "Оплата по СБП"
        PredefinedCategory.DIGITAL_GOODS -> "Цифровые товары и программное обеспечение"
        PredefinedCategory.YANDEX -> null
        PredefinedCategory.BURGER_KING -> null
        PredefinedCategory.GAS_STATION -> "Покупки на автозаправках, даже если купить там кофе"
        PredefinedCategory.COMMUNAL_SERVICES -> null
        PredefinedCategory.ACCESSORIES -> null
        PredefinedCategory.SBER -> "Покупки в сервисах «Самокат», «Купер», «Звук», «Okko»"
    }