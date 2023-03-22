# Inventory-New
This app is a variant of Google's basic-android-kotlin-compose-training-bus-schedule-app-starter
Main differences are:
1. UI layer has 3 screens: HomeScreen (shows a list of items), EntryScreen where users input data to create a new item, and View/EditScreen where users view item details or edit item data (transition between View and Edit Screen is handled by clicking the Edit FAB on the screen)
2. The app has only ONE ViewModel class applied for all 3 screens. The viewModel instance is created in the app's navGraph (InventoryNavGraph)
3. The ViewModel class doesn't have a SaveHandle property. Item properties are tranfered in transition from HomeScreen to View/EditScreen (There's no navArgument to be created for this purpose).
