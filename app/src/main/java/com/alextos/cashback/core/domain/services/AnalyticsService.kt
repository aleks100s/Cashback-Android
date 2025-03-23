package com.alextos.cashback.core.domain.services

enum class AnalyticsEvent(val rawValue: String) {
    CardListAppear("Card List Appear"),
    CardListAddCardButtonTapped("Card List Add Card Button Tapped"),
    CardListFavouriteToggle("Card List Favourite Toggle"),
    CardListFilterTapped("Card List Filter Tapped"),
    CardListCardTapped("Card List Card Tapped"),
    CardListEditButtonTapped("Card List Edit Button Tapped"),
    CardsSettingsToggleCompactView("Cards Settings Toggle Compact View"),

    AddCardCurrencyChange("Add Card Currency Change"),
    AddCardSaveButtonTapped("Add Card Save Button Tapped"),

    CardDetailEditButtonTapped("Card Detail Edit Button Tapped"),
    CardDetailScanCashbackButtonTapped("Card Detail Scan Cashback Button Tapped"),
    CardDetailAddCashbackButtonTapped("Card Detail Add Cashback Button Tapped"),
    CardDetailDeleteCashback("Card Detail Delete Cashback"),
    CardDetailCashbackTapped("Card Detail Cashback Tapped"),
    CardDetailCashbackReorder("Card Detail Cashback Reorder"),
    CardDetailCurrencyChange("Card Detail Currency Change"),
    CardDetailFavouriteToggle("Card Detail Favourite Toggle"),
    CardDetailDeleteCardButtonTapped("Card Detail Delete Card Button Tapped"),
    CardDetailDeleteAllCashbackButtonTapped("Card Detail Delete All Cashback Button Tapped"),
    CardDetailDeleteTransactionsButtonTapped("Card Detail Delete Transactions Button Tapped"),
    CardDetailDoneButtonTapped("Card Detail Done Button Tapped"),

    AddCashbackSaveButtonTapped("Add Cashback Save Button Tapped"),
    AddCashbackSelectCategoryButtonTapped("Add Cashback Select Category Button Tapped"),
    AddCashbackSelectPercentButtonTapped("Add Cashback Select Percent Button Tapped"),

    EditCashbackSaveButtonTapped("Edit Cashback Save Button Tapped"),
    EditCashbackSelectCategoryButtonTapped("Edit Cashback Select Category Button Tapped"),
    EditCashbackSelectPercentButtonTapped("Edit Cashback Select Percent Button Tapped"),

    SelectCategoryAppear("Select Category Appear"),
    SelectCategoryCreateNewButtonTapped("Select Category Create New Button Tapped"),
    SelectCategorySelect("Select Category Select"),
    SelectCategoryInfoButtonTapped("Select Category Info Button Tapped"),
    SelectCategoryDeleteButtonTapped("Select Category Delete Button Tapped"),
    SelectCategoryEditButtonTapped("Select Category Edit Button Tapped"),

    CreateCategorySaveButtonTapped("Create Category Save Button Tapped"),

    PaymentsAppear("Payments Appear"),
    PaymentsTogglePeriodButtonTapped("Payments Toggle Period Button Tapped"),
    PaymentsPreviousButtonTapped("Payments Previous Button Tapped"),
    PaymentsNextButtonTapped("Payments Next Button Tapped"),
    PaymentsAddPaymentButtonTapped("Payments Add Payment Button Tapped"),
    PaymentsSelect("Payments Select"),
    PaymentsDelete("Payments Delete"),

    AddPaymentSaveButtonTapped("Add Payment Save Button Tapped"),
    AddPaymentSelectDateButtonTapped("Add Payment Select Date Button Tapped"),
    AddPaymentSelectCardButtonTapped("Add Payment Select Card Button Tapped"),

    EditPaymentSaveButtonTapped("Edit Payment Save Button Tapped"),
    EditPaymentSelectDateButtonTapped("Edit Payment Select Date Button Tapped"),
    EditPaymentSelectCardButtonTapped("Edit Payment Select Card Button Tapped"),

    PlacesAppear("Places Appear"),
    PlacesAddPlaceButtonTapped("Places Add Place Button Tapped"),
    PlacesFavouriteToggle("Places Favourite Toggle"),
    PlacesEdit("Places Edit"),
    PlacesSelect("Places Select"),
    PlacesDelete("Places Delete"),

    AddPlaceSaveButtonTapped("Add Place Save Button Tapped"),
    AddPlaceSelectCategoryButtonTapped("Add Place Select Category Button Tapped"),

    PlaceDetailDoneButtonTapped("Place Detail Done Button Tapped"),
    PlaceDetailSelectCategoryButtonTapped("Place Detail Select Category Button Tapped"),
    PlaceDetailDelete("Place Detail Delete"),
    PlaceDetailToggleFavourite("Place Detail Toggle Favourite"),
    PlaceDetailEditButtonTapped("Place Detail Edit Button Tapped"),

    SettingsAppear("Settings Appear"),
    SettingsToggleNotifications("Settings Toggle Notifications"),
    SettingsExportButtonTapped("Settings Export Button Tapped"),
    SettingsExportFinished("Settings Export Finished"),
    SettingsImportButtonTapped("Settings Import Button Tapped"),
    SettingsImportFinished("Settings Import Finished"),
    SettingsShowOnboardingButtonTapped("Settings Show Onboarding Button Tapped"),
    SettingsToggleCardsFeature("Settings Toggle Cards Feature"),
    SettingsTogglePaymentsFeature("Settings Toggle Payments Feature"),
    SettingsTogglePlacesFeature("Settings Toggle Places Feature"),
    SettingsToggleCategoriesFeature("Settings Toggle Categories Feature"),
    SettingsOpenCardsTrashbin("Settings Open Cards Trashbin"),
    SettingsOpenCategoriesTrashbin("Settings Open Categories Trashbin"),

    TrashbinCardsRestoreButtonTapped("Trashbin Cards Restore Button Tapped"),
    TrashbinRestoreCard("Trashbin Restore Card"),

    TrashbinCategoriesRestoreButtonTapped("Trashbin Categories Restore Button Tapped"),
    TrashbinRestoreCategory("Trashbin Restore Category")
}

interface AnalyticsService {
    fun logEvent(event: AnalyticsEvent)
}