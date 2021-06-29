# GoogleDriveUploader
Apache Camel project made during the JNP2 - Architecture and Integration of IT Systems course on MIMUW

## Uruchamianie

Aby uruchomić projekt, należy utworzyć folder `resources` w `src/main` i dodać do niego plik `application.properties`, w którym znajdują się dane parametry:

* `directory` - ścieżka do folderu, z którego będą wysyłane pliki
* `access.token`
* `client.id`
* `client.secret` - te 3 pola to dane do autentykacji konta Google.
* `limit.extension` - rozszerzenie plików, które mają być wysyłane, np `".c"`. Pozostałe pliki będą usuwane z folderu bez wysyłania.

Następnie należy użyć komend `gradle build` oraz `gradle bootRun`, używając pliku build.gradle.

## Użycie

Po skonfigurowaniu i uruchomieniu programu, program prześle wszystkie pliki znajdujące się w folderze,
do którego prowadzi ścieżka `directory` wskazana w pliku konfiguracyjnym, których rozszerzenie jest zgodne z tym podanym w `limit.extension`,
na konto GoogleDrive użytkownika wskazanego w parametrach dotyczących autentykacji.

Następnie będzie nasłuchiwał, czy nie pojawiły się inne pliki w tym folderze. Jeśli tak, przetworzy je i jeśli rozszerzenia będą się zgadzać, prześle je.

Każdy przetworzony plik jest usuwany z folderu.

## Komponenty Apache Camel

Użyte komponenty to `camel-file` i `camel-google-drive`.
