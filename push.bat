@echo off
echo Lancement du push vers GitHub...

git add .
set /p commitMsg="Entrez le message de commit: "
if "%commitMsg%"=="" set commitMsg="Mise a jour automatique"

git commit -m "%commitMsg%"
git push origin main

echo.
echo Termine ! Appuyez sur une touche pour fermer.
pause > nul
